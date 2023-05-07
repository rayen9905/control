package com.pfe.repos;

import com.pfe.entities.Event;
import com.pfe.entities.Porte;
import com.pfe.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(value = "select count(*) from event where et_event = :em and date_event = :d",nativeQuery=true)
   Long countevent(@Param("em")String adr, @Param("d") LocalDate dd);
}
