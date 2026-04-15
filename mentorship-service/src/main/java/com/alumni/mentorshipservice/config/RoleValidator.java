package com.alumni.mentorshipservice.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class RoleValidator {

    public void allowOnlyStudent(HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        if (!"STUDENT".equals(role)) {
            throw new RuntimeException("Only students can request mentorship.");
        }
    }

    public void allowOnlyAlumni(HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        if (!"ALUMNI".equals(role)) {
            throw new RuntimeException("Only alumni can accept or reject mentorship.");
        }
    }

}
