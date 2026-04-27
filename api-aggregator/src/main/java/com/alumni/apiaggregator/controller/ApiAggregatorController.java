package com.alumni.apiaggregator.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
@CrossOrigin(origins = "http://localhost:3000")
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
    @PutMapping("/mentorship/{id}/accept")
    public ResponseEntity<?> accept(@PathVariable Long id, HttpServletRequest incoming) {
        return forwardPut("http://mentorship-service/mentorship/" + id + "/accept", incoming);
    }
    @PutMapping("/mentorship/{id}/reject")
    public ResponseEntity<?> reject(@PathVariable Long id, HttpServletRequest incoming) {
        return forwardPut("http://mentorship-service/mentorship/" + id + "/reject", incoming);
    }
    @PutMapping("/mentorship/{id}/complete")
    public ResponseEntity<?> complete(@PathVariable Long id, HttpServletRequest incoming) {
        return forwardPut("http://mentorship-service/mentorship/" + id + "/complete", incoming);
    }
    @PutMapping("/mentorship/{id}/cancel")
    public ResponseEntity<?> cancel(@PathVariable Long id, HttpServletRequest incoming) {
        return forwardPut("http://mentorship-service/mentorship/" + id + "/cancel", incoming);
    }
    @GetMapping("/mentorship/{studentId}/getMentorshipById/{alumniId}")
    public ResponseEntity<?> getMentorship(@PathVariable Long studentId,
                                           @PathVariable Long alumniId,
                                           HttpServletRequest incoming) {

        HttpHeaders headers = new HttpHeaders();
        copyAuth(incoming, headers);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        String url = "http://mentorship-service/mentorship/" + studentId + "/getMentorshipById/" + alumniId;

        return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
    }
    @PostMapping("/meeting/schedule")
    public ResponseEntity<?> scheduleMeeting(@RequestBody Object dto,
                                             HttpServletRequest incoming) {

        HttpHeaders headers = new HttpHeaders();
        copyAuth(incoming, headers);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> entity = new HttpEntity<>(dto, headers);

        String url = "http://mentorship-service/meeting/schedule";

        return restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
    }
    @GetMapping("/meeting")
    public ResponseEntity<?> getMeetings(@RequestParam Long mentorshipId,
                                         HttpServletRequest incoming) {

        HttpHeaders headers = new HttpHeaders();
        copyAuth(incoming, headers);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        String url = "http://mentorship-service/meeting?mentorshipId=" + mentorshipId;

        return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
    }
    private ResponseEntity<?> forwardPut(String url, HttpServletRequest incoming) {
        HttpHeaders headers = new HttpHeaders();
        copyAuth(incoming, headers);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.PUT, entity, Object.class);
    }
    private HttpHeaders buildHeaders(HttpServletRequest req) {
        HttpHeaders headers = new HttpHeaders();
        String auth = req.getHeader("Authorization");

        if (auth != null && !auth.isBlank()) {
            headers.set("Authorization", auth);
        }

        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @GetMapping("/mentorship/student/{studentId}")
    public ResponseEntity<?> getByStudent(@PathVariable Long studentId, HttpServletRequest incoming) {
        HttpHeaders headers = buildHeaders(incoming);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        String url = "http://mentorship-service/mentorship/student/" + studentId;
        return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
    }

    @GetMapping("/mentorship/alumni/{alumniId}")
    public ResponseEntity<?> getByAlumni(@PathVariable Long alumniId, HttpServletRequest incoming) {
        HttpHeaders headers = buildHeaders(incoming);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        String url = "http://mentorship-service/mentorship/alumni/" + alumniId;
        return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
    }


    @PostMapping("/opportunities")
    public ResponseEntity<?> createOpportunity(@RequestBody Object dto,
                                               HttpServletRequest incoming) {

        HttpHeaders headers = new HttpHeaders();
        copyAuth(incoming, headers);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> entity = new HttpEntity<>(dto, headers);

        String url = "http://opportunity-service/api/opportunities";

        return restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
    }

    @GetMapping("/opportunities")
    public ResponseEntity<?> getAllOpportunities(HttpServletRequest incoming) {

        HttpHeaders headers = buildHeaders(incoming);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        String url = "http://opportunity-service/api/opportunities";

        return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
    }

    @GetMapping("/opportunities/{id}")
    public ResponseEntity<?> getOpportunityById(@PathVariable Long id,
                                               HttpServletRequest incoming) {

        HttpHeaders headers = buildHeaders(incoming);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        String url = "http://opportunity-service/api/opportunities/" + id;

        return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
    }

    @PutMapping("/opportunities/{id}")
    public ResponseEntity<?> updateOpportunity(@PathVariable Long id,
                                              @RequestParam Long userId,
                                              @RequestBody Object dto,
                                              HttpServletRequest incoming) {

        HttpHeaders headers = buildHeaders(incoming);
        HttpEntity<Object> entity = new HttpEntity<>(dto, headers);

        String url = "http://opportunity-service/api/opportunities/" + id + "?userId=" + userId;

        return restTemplate.exchange(url, HttpMethod.PUT, entity, Object.class);
    }

    @DeleteMapping("/opportunities/{id}")
    public ResponseEntity<?> deleteOpportunity(@PathVariable Long id,
                                              HttpServletRequest incoming) {

        HttpHeaders headers = buildHeaders(incoming);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        String url = "http://opportunity-service/api/opportunities/" + id;

        return restTemplate.exchange(url, HttpMethod.DELETE, entity, Object.class);
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

    @PostMapping("/users/register")
    public ResponseEntity<?> registerUser(@RequestBody Object userDto,
                                      HttpServletRequest incoming) {

        HttpHeaders headers = new HttpHeaders();
        copyAuth(incoming, headers);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> entity = new HttpEntity<>(userDto, headers);

        String url = "http://opportunity-service/api/users";

        return restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
    }

    @PostMapping("/messaging/request")
    public ResponseEntity<?> requestMessaging(@RequestBody Object dto,
                                               HttpServletRequest incoming) {

        HttpHeaders headers = new HttpHeaders();
        copyAuth(incoming, headers);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> entity = new HttpEntity<>(dto, headers);

        String url = "http://message-service/messaging";

        return restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
    }

    @GetMapping("/messages/{conversationId}")
    public ResponseEntity<?> getMessagesByConversation(@PathVariable Long conversationId,
                                                       HttpServletRequest incoming) {
        HttpHeaders headers = buildHeaders(incoming);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        String url = "http://MESSAGING-SERVICE/messages/" + conversationId;

        return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
    }

    @PostMapping("/messages")
    public ResponseEntity<?> sendMessage(@RequestBody Object dto,
                                         HttpServletRequest incoming) {
        HttpHeaders headers = buildHeaders(incoming);
        HttpEntity<Object> entity = new HttpEntity<>(dto, headers);

        String url = "http://MESSAGING-SERVICE/messages";

        return restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
    }

    @PutMapping("/messages/read/{id}")
    public ResponseEntity<?> markMessageAsRead(@PathVariable Long id,
                                               HttpServletRequest incoming) {
        HttpHeaders headers = buildHeaders(incoming);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        String url = "http://MESSAGING-SERVICE/messages/read/" + id;

        return restTemplate.exchange(url, HttpMethod.PUT, entity, Object.class);
    }

    @PostMapping("/conversations/start")
    public Object startConversation(@RequestBody Object request) {
        return restTemplate.postForObject(
                "http://MESSAGING-SERVICE/conversations/start",
                request,
                Object.class
        );
    }

    private void copyAuth(HttpServletRequest req, HttpHeaders headers) {
        String auth = req.getHeader("Authorization");
        if (auth != null) {
            headers.set("Authorization", auth);
        }
    }
}
