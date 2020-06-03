package com.controller.service.serviceImpl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.naming.AuthenticationException;
import javax.security.auth.message.AuthException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User;
        if (oAuth2UserRequest.getClientRegistration().getRegistrationId().equals("vk")) {
            oAuth2User = loadVkUser(oAuth2UserRequest);
        } else {
            oAuth2User = super.loadUser(oAuth2UserRequest);
        }
        return oAuth2User;

//        try {
//            return processOAuth2User(oAuth2UserRequest, oAuth2User);
//        } catch (Exception ex) {
//            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
//            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
//        }
    }

//    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
//        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
//
//    .....
//        return CustomUserPrincipal.create(socialUser, oAuth2User.getAttributes());
//    }


    private OAuth2User loadVkUser(OAuth2UserRequest oAuth2UserRequest) {
        RestTemplate template = new RestTemplate();

        MultiValueMap<String, String> headers = new LinkedMultiValueMap();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", oAuth2UserRequest.getAccessToken().getTokenType().getValue() + " " + oAuth2UserRequest.getAccessToken().getTokenValue());
        HttpEntity<?> httpRequest = new HttpEntity(headers);
        String uri = oAuth2UserRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();
        String userNameAttributeName = oAuth2UserRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        uri = uri.replace("{user_id}", userNameAttributeName + "=" + oAuth2UserRequest.getAdditionalParameters().get(userNameAttributeName));

        try {
            ResponseEntity<Object> entity = template.exchange(uri, HttpMethod.GET, httpRequest, Object.class);
            Map<String, Object> response = (Map) entity.getBody();
            ArrayList valueList = (ArrayList) response.get("response");
            Map<String, Object> userAttributes = (Map<String, Object>) valueList.get(0);
            userAttributes.put(userNameAttributeName, oAuth2UserRequest.getAdditionalParameters().get(userNameAttributeName));


            Set<GrantedAuthority> authorities = Collections.singleton(new OAuth2UserAuthority(userAttributes));
            return new DefaultOAuth2User(authorities, userAttributes, userNameAttributeName);

        } catch (HttpClientErrorException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
