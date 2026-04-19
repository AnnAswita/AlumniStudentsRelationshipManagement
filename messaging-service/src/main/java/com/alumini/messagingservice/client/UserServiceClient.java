package com.alumini.messagingservice.client;

import com.alumini.messagingservice.dto.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

    @Component
    public class UserServiceClient {

        private final RestTemplate restTemplate;

        public UserServiceClient(RestTemplate restTemplate) {
            this.restTemplate = restTemplate;
        }

        public UserDTO getUserById(Long userId) {
            String url = "http://USER-SERVICE/users/" + userId;
            return restTemplate.getForObject(url, UserDTO.class);
        }

        public boolean userExists(Long userId) {
            try {
                UserDTO user = getUserById(userId);
                return user != null && user.getId() != null;
            } catch (Exception e) {
                return false;
            }
        }
    }
