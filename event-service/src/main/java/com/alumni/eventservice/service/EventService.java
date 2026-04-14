package com.alumni.eventservice.service;

import com.alumni.eventservice.model.Event;
import com.alumni.eventservice.model.EventRegistration;

import java.util.List;
import java.util.UUID;

public interface EventService {

    Event createEvent(Event event);

    Event updateEvent(UUID eventId, Event updated);

    void cancelEvent(UUID eventId);

    List<Event> getUpcomingEvents();

    Event getEventById(UUID eventId);

    void register(UUID eventId, UUID studentId);

    void unregister(UUID eventId, UUID studentId);

    List<EventRegistration> getParticipants(UUID eventId);
}
