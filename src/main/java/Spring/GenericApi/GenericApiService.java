package org.example;

import org.springframework.http.HttpMethod;

public interface GenericApiService {
    public <T> T callApi(String url, HttpMethod method, Object requestBody, Class<T> responseType);
}
