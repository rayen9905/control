package com.pfe.repos;

import com.pfe.entities.Event;
import com.pfe.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
