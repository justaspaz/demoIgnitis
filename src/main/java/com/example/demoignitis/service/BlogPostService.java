package com.example.demoignitis.service;

import com.example.demoignitis.dao.BlogPostRepo;
import com.example.demoignitis.enteties.BlogPost;
import com.example.demoignitis.enteties.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogPostService {
    @Autowired
    private BlogPostRepo blogPostRepo;

    public List<BlogPost> list() {
        return blogPostRepo.findAll();
    }

    public BlogPost save(BlogPost newBlogPost) {
        BlogPost blogPost = blogPostRepo.save(newBlogPost);
        return blogPost;
    }
    public Optional<BlogPost> findById(Long id) {
        return blogPostRepo.findById(id);
    }
    public void deleteById(Long id){
        blogPostRepo.deleteById(id);
    }
    public List<BlogPost> listAllUser(User user) {
        return blogPostRepo.findByUser(user);
    }
}
