package com.pfe.repos;

import com.pfe.entities.User;
import com.pfe.entities.WaveShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WaveRepository extends JpaRepository<WaveShare, String> {

}
