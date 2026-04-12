package com.alumni.mentorshipservice.service;

import com.alumni.mentorshipservice.domain.model.Meeting;
import com.alumni.mentorshipservice.domain.model.Mentorship;
import com.alumni.mentorshipservice.domain.valueobject.MentorshipStatus;
import com.alumni.mentorshipservice.dto.MeetingRequestDTO;
import com.alumni.mentorshipservice.dto.MeetingResponseDTO;
import com.alumni.mentorshipservice.repository.MeetingRepository;
import com.alumni.mentorshipservice.repository.MentorshipRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeetingService {

    private final MeetingRepository meetingRepo;
    private final MentorshipRepository mentorshipRepo;

    public MeetingService(MeetingRepository meetingRepo, MentorshipRepository mentorshipRepo) {
        this.meetingRepo = meetingRepo;
        this.mentorshipRepo = mentorshipRepo;
    }

    public MeetingResponseDTO schedule(MeetingRequestDTO dto) {

        Mentorship m = mentorshipRepo.findById(dto.getMentorshipId())
                .orElseThrow();

        //Only ACCEPTED or ACTIVE can schedule meetings
        if (m.getStatus() != MentorshipStatus.ACCEPTED && m.getStatus() != MentorshipStatus.ACTIVE) {
            throw new RuntimeException("Cannot schedule meeting when mentorship is in state " + m.getStatus());
        }

        //Unique date per mentorship
        if (meetingRepo.existsByMentorshipIdAndDate(dto.getMentorshipId(), dto.getDate())) {
            throw new RuntimeException("Meeting already scheduled on this date");
        }

        Meeting meeting = MeetingMapper.toEntity(dto);
        meetingRepo.save(meeting);

        //If this is the first meeting then activate mentorship
        boolean isFirstMeeting = meetingRepo.findByMentorshipId(dto.getMentorshipId()).size() == 1;

        if (isFirstMeeting && m.getStatus() == MentorshipStatus.ACCEPTED) {
            m.setStatus(MentorshipStatus.ACTIVE);
            mentorshipRepo.save(m);
        }

        return MeetingMapper.toDTO(meeting);
    }

    public List<MeetingResponseDTO> getMeetingsByMentorship(Long mentorshipId) {

        // Fetch meetings
        List<Meeting> meetings = meetingRepo.findByMentorshipId(mentorshipId);

        return meetings.stream().map(MeetingMapper::toDTO).collect(Collectors.toList());
    }

}