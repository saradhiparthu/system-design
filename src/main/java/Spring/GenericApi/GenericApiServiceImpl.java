package org.example;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class GenericApiServiceImpl implements GenericApiService {

    private final RestTemplate restTemplate;

    public GenericApiServiceImpl(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Override
    public <T> T callApi(String url, HttpMethod method, Object requestBody, Class<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<T> response = restTemplate.exchange(url, method, entity, responseType);
        return response.getBody();
    }
}
