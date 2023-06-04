package com.pfe.repos;

import com.pfe.entities.Refreshtoken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<Refreshtoken, Integer> {
    @Query(value = "select * from refreshtoken where token = :em",nativeQuery=true)
    Optional<Refreshtoken> findByToken(@Param("em")String token);
    @Query(value = "select * from refreshtoken where ref = :em",nativeQuery=true)
    Refreshtoken findByRefToken(@Param("em")int id);
    @Query(value = """
      select t from Refreshtoken t inner join User u\s
      on t.user.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
    List<Refreshtoken> findAllValidTokenByUser(Integer id);
}
