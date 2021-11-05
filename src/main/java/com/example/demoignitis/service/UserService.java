package com.example.demoignitis.service;

import com.example.demoignitis.dao.UserRepo;
import com.example.demoignitis.enteties.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public List<User> list() {
        return userRepo.findAll();
    }

    public User save(User newUser) {
        User user = userRepo.save(newUser);
        return user;
    }
    public User findUserByEmailAndPassword(User userToLogin){
        User user = userRepo.findByEmailAndPassword(userToLogin.getEmail(),userToLogin.getPassword());
        return user;
    }
    public User findUserByEmail(String email){
        User user =userRepo.findByEmail(email);
        return user;
    }
}
