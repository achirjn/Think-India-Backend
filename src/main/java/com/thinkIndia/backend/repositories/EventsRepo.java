package com.thinkIndia.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.thinkIndia.backend.entities.Events;

@Repository
public interface EventsRepo extends JpaRepository<Events, Integer>{

    public Optional<Events> findById(int id);

    @Query(value="select * from events where is_active = ?1 and show_event = 1;", nativeQuery=true)
    public List<Events> getEvents(int isActive);

}
