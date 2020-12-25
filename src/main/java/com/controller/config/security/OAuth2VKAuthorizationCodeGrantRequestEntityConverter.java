package com.controller.config.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationExchange;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;

public class OAuth2VKAuthorizationCodeGrantRequestEntityConverter implements Converter<OAuth2AuthorizationCodeGrantRequest, RequestEntity<?>> {

    private static final String CLIENT_ID = "client_id=";
    private static final String CODE = "&code=";
    private static final String CLIENT_SECRET = "&client_secret=";
    private static final String REDIRECT_URI = "&redirect_uri=";

    public RequestEntity<?> convert(OAuth2AuthorizationCodeGrantRequest authorizationCodeGrantRequest) {
//        HttpHeaders headers = getTokenRequestHeaders(authorizationCodeGrantRequest.getClientRegistration());
        HttpHeaders headers = null;
        URI uri = UriComponentsBuilder.fromUriString(vkUri(authorizationCodeGrantRequest)).build().toUri();
        return new RequestEntity(null,HttpMethod.GET, uri);
    }

    static HttpHeaders getTokenRequestHeaders(ClientRegistration clientRegistration) {
        HttpHeaders headers = new HttpHeaders();
        headers.addAll(getDefaultTokenRequestHeaders());
        if (ClientAuthenticationMethod.BASIC.equals(clientRegistration.getClientAuthenticationMethod())) {
            headers.setBasicAuth(clientRegistration.getClientId(), clientRegistration.getClientSecret());
        }

        return headers;
    }

    private static HttpHeaders getDefaultTokenRequestHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
        MediaType contentType = MediaType.valueOf("application/x-www-form-urlencoded;charset=UTF-8");
        headers.setContentType(contentType);
        return headers;
    }

    private String vkUri(OAuth2AuthorizationCodeGrantRequest authorizationCodeGrantRequest) {
        ClientRegistration clientRegistration = authorizationCodeGrantRequest.getClientRegistration();
        OAuth2AuthorizationExchange authorizationExchange = authorizationCodeGrantRequest.getAuthorizationExchange();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(clientRegistration.getProviderDetails().getTokenUri())
                     .append("?")
                     .append(CLIENT_ID)
                     .append(clientRegistration.getClientId())
                     .append(CLIENT_SECRET)
                     .append(clientRegistration.getClientSecret())
                     .append(REDIRECT_URI)
                     .append(authorizationExchange.getAuthorizationRequest().getRedirectUri())
                     .append(CODE)
                     .append(authorizationExchange.getAuthorizationResponse().getCode());

        return stringBuilder.toString();


    }

    private MultiValueMap<String, String> buildFormParameters(OAuth2AuthorizationCodeGrantRequest authorizationCodeGrantRequest) {
        ClientRegistration clientRegistration = authorizationCodeGrantRequest.getClientRegistration();
        OAuth2AuthorizationExchange authorizationExchange = authorizationCodeGrantRequest.getAuthorizationExchange();
        MultiValueMap<String, String> formParameters = new LinkedMultiValueMap();
        formParameters.add("grant_type", authorizationCodeGrantRequest.getGrantType().getValue());
        formParameters.add("code", authorizationExchange.getAuthorizationResponse().getCode());
        String redirectUri = authorizationExchange.getAuthorizationRequest().getRedirectUri();
        String codeVerifier = (String)authorizationExchange.getAuthorizationRequest().getAttribute("code_verifier");
        if (redirectUri != null) {
            formParameters.add("redirect_uri", redirectUri);
        }

        if (!ClientAuthenticationMethod.BASIC.equals(clientRegistration.getClientAuthenticationMethod())) {
            formParameters.add("client_id", clientRegistration.getClientId());
        }

        if (ClientAuthenticationMethod.POST.equals(clientRegistration.getClientAuthenticationMethod())) {
            formParameters.add("client_secret", clientRegistration.getClientSecret());
        }

        if (codeVerifier != null) {
            formParameters.add("code_verifier", codeVerifier);
        }

        return formParameters;
    }
}
