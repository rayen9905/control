package com.pfe.repos;

import com.pfe.entities.Event;
import com.pfe.entities.Porte;
import com.pfe.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> , JpaSpecificationExecutor<Event> {
    @Query(value = "select count(*) from event where et_event = :em and date_event = :d",nativeQuery=true)
   Long countevent(@Param("em")String adr, @Param("d") LocalDate dd);
    @Query(value = "select * from event where date_event = :em ",nativeQuery=true)
    List<Event> countevent1(@Param("em") LocalDate dd);
}
