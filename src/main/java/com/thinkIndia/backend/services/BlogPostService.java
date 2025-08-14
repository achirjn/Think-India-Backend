package com.thinkIndia.backend.services;

import java.util.List;
import java.util.Optional;

import com.thinkIndia.backend.entities.BlogPost;

public interface BlogPostService {

    public BlogPost createBlog(BlogPost blog);
    public List<BlogPost> getAllBlogs();
    public Optional<BlogPost> getBlogById(int id);
    public void deleteByHeading(String heading);
}
