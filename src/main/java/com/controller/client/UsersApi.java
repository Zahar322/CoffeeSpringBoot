package com.controller.client;

import com.controller.entity.User;
import com.controller.response.CoffeeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
public class UsersApi {

    @Autowired
    private ApiClient apiClient;

    public List<User> confirmOffers() throws RestClientException {
//        Object postBody = request;
//
//        // verify the required parameter 'request' is set
//        if (request == null) {
//            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'request' when calling confirmOffers");
//        }

        String path = UriComponentsBuilder.fromPath("/users").build().toUriString();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {
                "application/json"
        };
//        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
//        final String[] contentTypes = {
//                "application/json"
//        };
//        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<User>> returnType = new ParameterizedTypeReference<List<User>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, null, headerParams, formParams, null, null, authNames, returnType);
    }

    public CoffeeResponse coffeeResponse() throws RestClientException {
//        Object postBody = request;
//
//        // verify the required parameter 'request' is set
//        if (request == null) {
//            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'request' when calling confirmOffers");
//        }

        String path = UriComponentsBuilder.fromPath("/coffees").build().toUriString();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {
                "application/json"
        };
//        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
//        final String[] contentTypes = {
//                "application/json"
//        };
//        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<CoffeeResponse> returnType = new ParameterizedTypeReference<CoffeeResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, null, headerParams, formParams, null, null, authNames, returnType);
    }


}
