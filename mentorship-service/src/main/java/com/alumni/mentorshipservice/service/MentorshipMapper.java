package com.alumni.mentorshipservice.service;

import com.alumni.mentorshipservice.domain.model.Mentorship;
import com.alumni.mentorshipservice.dto.*;

public class MentorshipMapper {

    public static Mentorship toEntity(MentorshipRequestDTO dto) {
        Mentorship m = new Mentorship();
        m.setStudentId(dto.getStudentId());
        m.setAlumniId(dto.getAlumniId());
        return m;
    }

    public static MentorshipResponseDTO toDTO(Mentorship m) {
        MentorshipResponseDTO dto = new MentorshipResponseDTO();
        dto.setId(m.getId());
        dto.setStudentId(m.getStudentId());
        dto.setAlumniId(m.getAlumniId());
        dto.setStatus(m.getStatus().name());
        return dto;
    }
}