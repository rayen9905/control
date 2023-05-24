package com.pfe.repos;

import com.pfe.entities.Controlleur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ControllerRepository extends JpaRepository<Controlleur, Long> {
    @Query(value="select count(*) from controlleur where status = :dis and date_status = :d",nativeQuery = true)
    int countdisc (@Param("dis") String dis, @Param("d")LocalDate d);
    @Query(value="select * from controlleur where serial_number = :sn",nativeQuery = true)
    Controlleur GetBySn (@Param("sn") String sn);

}