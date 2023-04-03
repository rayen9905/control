package com.pfe.repos;

import com.pfe.entities.Controlleur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ControllerRepository extends JpaRepository<Controlleur, Long> {

}