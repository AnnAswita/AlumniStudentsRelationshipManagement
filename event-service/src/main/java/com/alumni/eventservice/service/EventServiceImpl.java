package com.alumni.eventservice.service;

import com.alumni.eventservice.model.*;
import com.alumni.eventservice.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventRegistrationRepository registrationRepository;

    public EventServiceImpl(EventRepository eventRepository,
                            EventRegistrationRepository registrationRepository) {
        this.eventRepository = eventRepository;
        this.registrationRepository = registrationRepository;
    }

    @Override
    public Event createEvent(Event event) {
        event.setStatus(EventStatus.UPCOMING);
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(UUID eventId, Event updated) {
        Event existing = getEventById(eventId);

        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setDateTime(updated.getDateTime());
        existing.setLocation(updated.getLocation());
        existing.setCapacity(updated.getCapacity());

        return eventRepository.save(existing);
    }

    @Override
    public void cancelEvent(UUID eventId) {
        Event event = getEventById(eventId);
        event.setStatus(EventStatus.CANCELLED);
        eventRepository.save(event);
    }

    @Override
    public List<Event> getUpcomingEvents() {
        return eventRepository.findByStatus(EventStatus.UPCOMING);
    }

    @Override
    public Event getEventById(UUID eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    @Override
    public void register(UUID eventId, UUID studentId) {
        if (registrationRepository.existsByEventIdAndStudentId(eventId, studentId)) {
            return;
        }

        EventRegistration reg = new EventRegistration();
        reg.setEventId(eventId);
        reg.setStudentId(studentId);
        reg.setRegisteredAt(LocalDateTime.now());

        registrationRepository.save(reg);
    }

    @Override
    public void unregister(UUID eventId, UUID studentId) {
        registrationRepository.findAll().stream()
                .filter(r -> r.getEventId().equals(eventId) && r.getStudentId().equals(studentId))
                .findFirst()
                .ifPresent(registrationRepository::delete);
    }

    @Override
    public List<EventRegistration> getParticipants(UUID eventId) {
        return registrationRepository.findByEventId(eventId);
    }
}
