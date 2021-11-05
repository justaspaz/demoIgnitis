package com.example.demoignitis.dao;

import com.example.demoignitis.enteties.BlogPost;
import com.example.demoignitis.enteties.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogPostRepo extends JpaRepository<BlogPost, Long> {
    List<BlogPost> findByUser(User user);
}
