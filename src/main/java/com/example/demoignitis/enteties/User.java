package com.example.demoignitis.enteties;

import javax.persistence.*;
import java.util.Set;

@Table
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique=true)
    private String email;

    @Column
    private String password;

    @OneToMany(mappedBy = "user")
    Set<BlogPost> blogPosts;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
