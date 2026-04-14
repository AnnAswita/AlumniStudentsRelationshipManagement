package com.alumni.apiaggregator.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class ApiAggregatorController {

    private final RestTemplate restTemplate;

    public ApiAggregatorController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/mentorship/request")
    public ResponseEntity<?> requestMentorship(@RequestBody Object dto,
                                               HttpServletRequest incoming) {

        HttpHeaders headers = new HttpHeaders();
        copyAuth(incoming, headers);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> entity = new HttpEntity<>(dto, headers);

        String url = "http://mentorship-service/mentorship/request";

        return restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id,
                                     HttpServletRequest incoming) {

        HttpHeaders headers = new HttpHeaders();
        copyAuth(incoming, headers);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        String url = "http://user-service/users/" + id;

        return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
    }

    private void copyAuth(HttpServletRequest req, HttpHeaders headers) {
        String auth = req.getHeader("Authorization");
        if (auth != null) {
            headers.set("Authorization", auth);
        }
    }
}
