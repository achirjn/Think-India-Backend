package com.thinkIndia.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.thinkIndia.backend.entities.Recommendations;

@Repository
public interface RecommendRepo extends JpaRepository<Recommendations, Integer>{

    @Query(value="select * from recommendations where resolved = false order by post_time desc", nativeQuery=true)
    public List<Recommendations> findUnresolvedRecommendations();
    @Query(value="select * from recommendations where resolved = true order by post_time desc", nativeQuery=true)
    public List<Recommendations> findResolvedRecommendations();

}
