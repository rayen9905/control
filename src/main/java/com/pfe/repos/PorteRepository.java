package com.pfe.repos;

import com.pfe.entities.Porte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PorteRepository extends JpaRepository<Porte, Long> {

}

