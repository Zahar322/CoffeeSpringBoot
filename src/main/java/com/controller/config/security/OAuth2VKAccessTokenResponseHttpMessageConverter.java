package com.controller.config.security;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;

import java.util.Map;

public class OAuth2VKAccessTokenResponseHttpMessageConverter extends OAuth2AccessTokenResponseHttpMessageConverter {

    private GenericHttpMessageConverter<Object> jsonMessageConverter = new MappingJackson2HttpMessageConverter();
    private static final ParameterizedTypeReference<Map<String, String>> PARAMETERIZED_RESPONSE_TYPE = new ParameterizedTypeReference<Map<String, String>>() {};
    protected Converter<Map<String, String>, OAuth2AccessTokenResponse> converter = new CustomTokenResponseConverter();

    protected OAuth2AccessTokenResponse readInternal(Class<? extends OAuth2AccessTokenResponse> clazz, HttpInputMessage inputMessage) throws HttpMessageNotReadableException {
        try {
            Map<String, String> tokenResponseParameters = (Map)this.jsonMessageConverter.read(PARAMETERIZED_RESPONSE_TYPE.getType(), (Class)null, inputMessage);
            return (OAuth2AccessTokenResponse)getConverter(tokenResponseParameters).convert(tokenResponseParameters);
        } catch (Exception var4) {
            throw new HttpMessageNotReadableException("An error occurred reading the OAuth 2.0 Access Token Response: " + var4.getMessage(), var4, inputMessage);
        }
    }

    private Converter<Map<String, String>, OAuth2AccessTokenResponse> getConverter(Map<String, String> tokenResponseParameters) {
        if (tokenResponseParameters.containsKey("token_type")) {
            return tokenResponseConverter;
        }
        return converter;
    }

}
