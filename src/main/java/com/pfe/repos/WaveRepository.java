package com.pfe.repos;

import com.pfe.entities.User;
import com.pfe.entities.WaveShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface WaveRepository extends JpaRepository<WaveShare, String> {
    @Query(value="select count(*) from waveshare where status = :dis and date_status = :d",nativeQuery = true)
    int countdisw (@Param("dis") String dis, @Param("d") LocalDate d);
}
