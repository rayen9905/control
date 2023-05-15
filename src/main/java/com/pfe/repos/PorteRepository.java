package com.pfe.repos;

import com.pfe.entities.Porte;
import com.pfe.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PorteRepository extends JpaRepository<Porte, Long> {
    @Query(value = "select * from porte where wsh_adresse = :em",nativeQuery=true)
    Porte findByadresse(@Param("em")String adr);

    @Query(value = "select * from porte where cntrl_id_cont = :em and num_porte = :np",nativeQuery=true)
    Porte findBynum(@Param("em")Long adr,@Param("np")int np);
}

