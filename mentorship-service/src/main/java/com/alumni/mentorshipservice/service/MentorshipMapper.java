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

    public static MentorshipResponseDTO toDTO(Mentorship m, UserDTO student, UserDTO alumni) {
        MentorshipResponseDTO dto = new MentorshipResponseDTO();
        dto.setId(m.getId());
        dto.setStudentId(m.getStudentId());
        dto.setAlumniId(m.getAlumniId());
        dto.setStatus(m.getStatus().name());
        dto.setStudentName(student.getName());
        dto.setAlumniName(alumni.getName());
        return dto;
    }

    public static MentorshipResponseDTO toStatusDTO(Mentorship mentorship) {
        MentorshipResponseDTO dto = new MentorshipResponseDTO();
        dto.setStudentId(mentorship.getStudentId());
        dto.setAlumniId(mentorship.getAlumniId());
        dto.setStatus(String.valueOf(mentorship.getStatus()));
        return dto;
    }
}