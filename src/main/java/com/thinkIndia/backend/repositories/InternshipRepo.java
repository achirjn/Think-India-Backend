package com.thinkIndia.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.thinkIndia.backend.entities.Internship;


@Repository
public interface InternshipRepo extends JpaRepository<Internship, Integer>{
    public Optional<Internship> findById(int id);

    @Query(value="select * from internship where is_active = 1", nativeQuery=true)
    public List<Internship> findUpcommingInternships();
}
