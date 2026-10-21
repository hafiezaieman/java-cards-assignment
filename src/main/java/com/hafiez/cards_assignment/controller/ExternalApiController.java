package com.hafiez.cards_assignment.controller;

import com.hafiez.cards_assignment.dto.ExternalPostDto;
import com.hafiez.cards_assignment.service.ExternalApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/external")
public class ExternalApiController {

    private final ExternalApiService externalApiService;

    public ExternalApiController(ExternalApiService externalApiService) {
        this.externalApiService = externalApiService;
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<ExternalPostDto> getPost(@PathVariable int id) {
        return ResponseEntity.ok(externalApiService.getPost(id));
    }
}