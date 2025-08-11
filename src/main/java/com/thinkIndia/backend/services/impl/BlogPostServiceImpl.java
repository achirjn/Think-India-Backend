package com.thinkIndia.backend.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.thinkIndia.backend.entities.BlogPost;
import com.thinkIndia.backend.repositories.BlogPostRepo;
import com.thinkIndia.backend.services.BlogPostService;

@Service
public class BlogPostServiceImpl implements BlogPostService{

    @Autowired
    private BlogPostRepo blogRepository;

    @Override
    public BlogPost createBlog(BlogPost blog) {
        return blogRepository.save(blog);
    }

    @Override
    public List<BlogPost> getAllBlogs() {
        return blogRepository.findAll(Sort.by(Sort.Direction.DESC,"postTime"));
    }

    @Override
    public Optional<BlogPost> getBlogById(int id) {
        return blogRepository.findById(id);
    }
}
