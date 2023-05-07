package com.pfe.repos;

import com.pfe.entities.Historique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface HistoriqueRepository extends JpaRepository<Historique,Long> {
    @Query(value = "select count(*) from historique where etat_historique = :em and date_historique = :d",nativeQuery=true)
    int counthis(@Param("em")String adr, @Param("d") LocalDate dd);

}