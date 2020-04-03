package org.goafabric.spring.boot.exampleservice.adapter;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Configuration
public class AdapterConfiguration {
    @Value("${adapter.calleeserviceadapter.user}")
    private String user;

    @Value("${adapter.calleeserviceadapter.password}")
    private String password;

    @Value("${adapter.calleeserviceadapter.timeout}")
    private Integer timeout;

    @Bean
    public RestTemplate adapterRestTemplate() {
        final RestTemplate restTemplate = new RestTemplate(
                createClientHttpRequestFactory(timeout));

        restTemplate.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter()));
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            request.getHeaders().setBasicAuth(user, password);
            return execution.execute(request, body);
        });
        return restTemplate;
    }

    private static HttpComponentsClientHttpRequestFactory createClientHttpRequestFactory(final int timeout) {
        final RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout)
                .setSocketTimeout(timeout)
                .build();
        final CloseableHttpClient client = HttpClientBuilder
                .create()
                .setDefaultRequestConfig(config)
                .build();
        return new HttpComponentsClientHttpRequestFactory(client);
    }

}
