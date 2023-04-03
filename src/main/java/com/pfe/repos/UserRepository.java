package com.pfe.repos;

import java.util.Optional;

import com.pfe.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  @Query(value = "select * from user where email = :em",nativeQuery=true)
  Optional<User> findByEmail(@Param("em")String email);

}
