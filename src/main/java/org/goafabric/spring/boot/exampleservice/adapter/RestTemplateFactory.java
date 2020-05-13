package org.goafabric.spring.boot.exampleservice.adapter;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class RestTemplateFactory {
    private RestTemplateFactory() {
    }

    public static RestTemplate createRestTemplate(final int timeout, final String user, final String password) {
        final RestTemplate restTemplate = new RestTemplate(
                createClientHttpRequestFactory(timeout));
        restTemplate.setMessageConverters(Collections.singletonList(new MappingJackson2HttpMessageConverter()));
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            request.getHeaders().setBasicAuth(user, password);
            return execution.execute(request, body);
        });
        return restTemplate;
    }


    private static HttpComponentsClientHttpRequestFactory createClientHttpRequestFactory(final int timeout) {
        final RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(timeout).setConnectionRequestTimeout(timeout)
                .setSocketTimeout(timeout).build();
        return new HttpComponentsClientHttpRequestFactory(HttpClientBuilder
                .create().setDefaultRequestConfig(config).build());
    }

}
