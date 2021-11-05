package com.example.demoignitis.dao;

import com.example.demoignitis.enteties.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmailAndPassword(String email,String password);
    User findByEmail(String email);
}