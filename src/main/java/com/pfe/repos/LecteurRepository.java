package com.pfe.repos;

import com.pfe.entities.Lecteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface LecteurRepository extends JpaRepository<Lecteur, Long> {
    @Query(value="select count(*) from lecteur where etat_lecteur = :dis and date_status = :d",nativeQuery = true)
    int countdisl (@Param("dis") String dis, @Param("d") LocalDate d);
}
