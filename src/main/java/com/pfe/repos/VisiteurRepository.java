package com.pfe.repos;

import com.pfe.entities.User;
import com.pfe.entities.Visiteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VisiteurRepository extends JpaRepository<Visiteur, Integer> , JpaSpecificationExecutor<Visiteur> {

    @Query(value = "select * from user where email = :em",nativeQuery=true)
    User findByEmail1(@Param("em")String email);
}
