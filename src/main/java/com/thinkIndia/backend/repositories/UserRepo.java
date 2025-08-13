package com.thinkIndia.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.thinkIndia.backend.entities.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{

    @Query(value="select * from user where email = ?1", nativeQuery=true)
    public User findByEmail(String email);

}
