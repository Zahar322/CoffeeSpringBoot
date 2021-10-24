package com.controller.client;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ApiClient {

    private Logger log = LoggerFactory.getLogger(ApiClient.class);

    private String basePath;
    private RestTemplate restTemplate;
    private HttpStatus statusCode;
    private HttpHeaders responseHeaders;

    public ApiClient(String basePath, RestTemplate restTemplate) {
        this.basePath = basePath;
        this.restTemplate = restTemplate;
    }

    public <T> T invokeAPI(String path, HttpMethod method, MultiValueMap<String, String> queryParams, Object body, HttpHeaders headerParams, MultiValueMap<String, Object> formParams, List<MediaType> accept, MediaType contentType, String[] authNames, ParameterizedTypeReference<T> returnType) {
//        this.updateParamsForAuth(authNames, queryParams, headerParams);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.basePath).path(path);
        if (queryParams != null) {
            builder.queryParams(queryParams);
        }

        RequestEntity.BodyBuilder requestBuilder = RequestEntity.method(method, builder.build().toUri());
        if (accept != null) {
            requestBuilder.accept((MediaType[])accept.toArray(new MediaType[accept.size()]));
        }

        if (contentType != null) {
            requestBuilder.contentType(contentType);
        }

//        this.addHeadersToRequest(headerParams, requestBuilder);
//        this.addHeadersToRequest(this.defaultHeaders, requestBuilder);
//        this.addCommonHeadersToRequestIfNeed(requestBuilder);
        RequestEntity requestEntity = requestBuilder.body(this.selectBody(body, formParams, contentType));

        try {
            ResponseEntity<T> responseEntity = this.restTemplate.exchange(requestEntity, returnType);
            this.statusCode = responseEntity.getStatusCode();
            this.responseHeaders = responseEntity.getHeaders();
            return responseEntity.getStatusCode() == HttpStatus.NO_CONTENT ? null : responseEntity.getBody();
        } catch (ResourceAccessException exception) {
            ResourceAccessException e = exception;
            Class responseClass = returnType.getType() instanceof Class ? (Class)returnType.getType() : null;

            try {
//                ErrorDetails errorDetails = new ErrorDetails();
//                errorDetails.setCode("api.client.error");
//                Error error = new Error();
//                error.setCode("service.unavailable.error");
//                error.addDetails(errorDetails);
                Field errorField = responseClass.getDeclaredField("error");
                Object baseResponse = responseClass.getDeclaredConstructor().newInstance();
                errorField.setAccessible(true);
//                errorField.set(baseResponse, error);
                log.error("Api client error", e);
                return (T) baseResponse;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException var20) {
                log.info("Error handling ResourceAccessException: ", var20);
                throw new ServiceException("internal.service.error");
            }
        } catch (RestClientException clientException) {
            log.info("Error occurred while attempting to invoke the API: ", clientException);
            throw new ServiceException("internal.service.error");
        }
    }

    private Object selectBody(Object body, MultiValueMap<String, Object> formParams, MediaType contentType) {
        return body;
    }
}
