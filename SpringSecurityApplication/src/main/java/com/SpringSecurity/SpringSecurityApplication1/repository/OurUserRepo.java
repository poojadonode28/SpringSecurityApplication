package com.SpringSecurity.SpringSecurityApplication1.repository;

import com.SpringSecurity.SpringSecurityApplication1.model.OurUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OurUserRepo extends JpaRepository<OurUsers,Integer> {

    @Query(value = "select * from our_users where email=?1",nativeQuery = true)
    Optional<OurUsers> findByEmail(String email);
}
