package com.example.userdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select * from users where email = :email",nativeQuery = true)
    Optional<User> findByEmail(@Param(value = "email") String email);
    //@Query(value = "select u from User u where u.email = :email")
    //User findByEmail(String email);

    //User findByEmailAndAgeAfterAndBirth()
}
