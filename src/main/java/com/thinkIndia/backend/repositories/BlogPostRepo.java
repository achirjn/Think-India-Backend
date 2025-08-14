package com.thinkIndia.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thinkIndia.backend.entities.BlogPost;

@Repository
public interface BlogPostRepo extends JpaRepository<BlogPost, Integer>{

    public void deleteByHeading(String heading);
}
