package com.example.ecommerce.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@RestController
@RequestMapping("/api/consent")
public class ConsentProxyController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/banner-version")
    public ResponseEntity<String> getBannerVersion(@RequestParam("domain") String domain) {
        String url = "https://autocops.org/v1/public/cookies/banner-version/" + domain;
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return ResponseEntity.status(response.getStatusCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/i18n/banner/{lang}")
    public ResponseEntity<String> getTranslations(@PathVariable("lang") String lang) {
        String url = "https://autocops.org/v1/public/i18n/banner/" + lang;
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return ResponseEntity.status(response.getStatusCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/cookies")
    public ResponseEntity<String> saveConsent(@RequestBody Map<String, Object> payload) {
        String url = "https://autocops.org/v1/cookies/consents";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            return ResponseEntity.status(response.getStatusCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/capture")
    public ResponseEntity<String> captureConsent(@RequestBody Map<String, Object> payload,
                                                 @RequestHeader(value = "User-Agent", required = false) String userAgent) {
        String url = "https://autocops.org/v1/consent/external/capture";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-Key", "dpdp_420800f8e4091705cd5302c214265eaa8d4b8bc981fb5255");
        if (userAgent != null) {
            headers.set("User-Agent", userAgent);
        }
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            return ResponseEntity.status(response.getStatusCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Failed to proxy consent capture.\", \"message\": \"" + e.getMessage() + "\"}");
        }
    }
}
