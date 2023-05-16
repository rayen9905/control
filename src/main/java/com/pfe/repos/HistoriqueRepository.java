package com.pfe.repos;

import com.pfe.entities.Event;
import com.pfe.entities.Historique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HistoriqueRepository extends JpaRepository<Historique,Long> , JpaSpecificationExecutor<Historique> {
    @Query(value = "select count(*) from historique where etat_historique = :em and date_historique = :d",nativeQuery=true)
    int counthis(@Param("em")String adr, @Param("d") LocalDate dd);
    @Query(value = "select * from historique where date_historique = :em ",nativeQuery=true)
    List<Historique> counthis1(@Param("em") LocalDate dd);
    @Query(value = "select * from historique where etat_historique = :em and date_historique = :emm",nativeQuery=true)
    List<Historique> counthis2(@Param("em") String dd,@Param("emm") LocalDate dh);

}