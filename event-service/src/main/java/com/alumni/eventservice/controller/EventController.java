package com.alumni.eventservice.controller;

import com.alumni.eventservice.model.Event;
import com.alumni.eventservice.model.EventRegistration;
import com.alumni.eventservice.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event,
                                             @RequestHeader("X-User-Id") UUID userId) {
        event.setCreatedBy(userId);
        return ResponseEntity.ok(eventService.createEvent(event));
    }

    @PutMapping("/{eventId}")
    public Event updateEvent(@PathVariable UUID eventId,
                             @RequestBody Event event) {
        return eventService.updateEvent(eventId, event);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> cancelEvent(@PathVariable UUID eventId) {
        eventService.cancelEvent(eventId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<Event> getUpcomingEvents() {
        return eventService.getUpcomingEvents();
    }

    @GetMapping("/{eventId}")
    public Event getEvent(@PathVariable UUID eventId) {
        return eventService.getEventById(eventId);
    }

    @PostMapping("/{eventId}/register")
    public ResponseEntity<Void> register(@PathVariable UUID eventId,
                                         @RequestHeader("X-User-Id") UUID studentId) {
        eventService.register(eventId, studentId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{eventId}/register")
    public ResponseEntity<Void> unregister(@PathVariable UUID eventId,
                                           @RequestHeader("X-User-Id") UUID studentId) {
        eventService.unregister(eventId, studentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{eventId}/participants")
    public List<EventRegistration> getParticipants(@PathVariable UUID eventId) {
        return eventService.getParticipants(eventId);
    }
}
