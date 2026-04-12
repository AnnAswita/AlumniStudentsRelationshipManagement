package com.alumni.mentorshipservice.service;

import com.alumni.mentorshipservice.domain.model.Meeting;
import com.alumni.mentorshipservice.dto.MeetingRequestDTO;
import com.alumni.mentorshipservice.dto.MeetingResponseDTO;


public class MeetingMapper {

    public static Meeting toEntity(MeetingRequestDTO dto){
        Meeting meeting = new Meeting();
        meeting.setMentorshipId(dto.getMentorshipId());
        meeting.setDescription(dto.getDescription());
        meeting.setDate(dto.getDate());
        return meeting;
    }

    public static MeetingResponseDTO toDTO(Meeting m){
        MeetingResponseDTO response = new MeetingResponseDTO();
        response.setId(m.getId());
        response.setMentorshipId(m.getMentorshipId());
        response.setDescription(m.getDescription());
        response.setDate(m.getDate());
        return response;
    }
}
