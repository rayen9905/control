package com.pfe.repos;

import com.pfe.entities.Event;
import com.pfe.entities.Porte;
import com.pfe.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(value = "select count(*) event where et_event = :em",nativeQuery=true)
    int countevent(@Param("em")String adr);
}
