package com.alumni.eventservice.repository;

import com.alumni.eventservice.model.Event;
import com.alumni.eventservice.model.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
    List<Event> findByStatus(EventStatus status);
}
