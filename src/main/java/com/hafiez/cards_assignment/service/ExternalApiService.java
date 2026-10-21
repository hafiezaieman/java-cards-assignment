package com.hafiez.cards_assignment.service;

import com.hafiez.cards_assignment.dto.ExternalPostDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ExternalApiService {

    private final RestClient restClient;

    public ExternalApiService(RestClient restClient) {
        this.restClient = restClient;
    }

    public ExternalPostDto getPost(int id) {
        try {
            return restClient.get()
                    .uri("/posts/{id}", id)
                    .retrieve()
                    .body(ExternalPostDto.class);

        } catch (RestClientResponseException e) {
            // Third party answered, but with an error (e.g. 404)
            throw new ResponseStatusException(
                    HttpStatus.valueOf(e.getStatusCode().value()),
                    "Third-party API returned an error: " + e.getStatusCode());

        } catch (RestClientException e) {
            // Third party unreachable: no internet, timeout, DNS failure
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "Third-party service is unavailable");
        }
    }
}