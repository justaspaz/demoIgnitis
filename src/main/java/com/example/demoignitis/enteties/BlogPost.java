package com.example.demoignitis.enteties;

import javax.persistence.*;

@Table
@Entity
public class BlogPost {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String title;

    @Column
    private String text;

    @ManyToOne
    @JoinColumn(name="user_id")
    User user;

    public Long getId() {
        return id;
    }
    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
