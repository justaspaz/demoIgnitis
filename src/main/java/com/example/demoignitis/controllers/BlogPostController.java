package com.example.demoignitis.controllers;

import com.example.demoignitis.enteties.BlogPost;
import com.example.demoignitis.enteties.Blogger;
import com.example.demoignitis.enteties.User;
import com.example.demoignitis.service.BlogPostService;
import com.example.demoignitis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;

@RestController
public class BlogPostController {
    @Autowired
    private BlogPostService blogPostService;
    @Autowired
    private UserService userService;
    @PostMapping(path = "blogPost/new",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BlogPost> createBlogPost(@RequestBody Blogger newBloger, @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByEmail(getEmailFromToken(token));
        BlogPost newBlogPost = new BlogPost();
        newBlogPost.setText(newBloger.getText());
        newBlogPost.setTitle(newBloger.getTitle());
        newBlogPost.setUser(user);
        BlogPost blogPost = blogPostService.save(newBlogPost);
        if (blogPost == null) {
            throw new Exception();
        } else {
            return new ResponseEntity<>(blogPost, HttpStatus.CREATED);
        }
    }
    @GetMapping("blogPost/delete")
    public ResponseEntity<BlogPost> delete(@RequestParam Long id,@RequestHeader("Authorization") String token) throws Exception {
        getEmailFromToken(token);
        BlogPost blogPost = blogPostService.findById(id).get();
        if (blogPost == null) {
            throw new Exception();
        } else {
            blogPostService.deleteById(id);
            return new ResponseEntity<>(blogPost, HttpStatus.ACCEPTED);
        }
    }
    @PostMapping(path = "blogPost/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BlogPost> updateBlogPost(@RequestBody BlogPost newBlogPost,@RequestHeader("Authorization") String token) throws Exception {
        getEmailFromToken(token);
        BlogPost blogPost = blogPostService.findById(newBlogPost.getId()).get();
        if (blogPost == null) {
            throw new Exception();
        } else {
            if(newBlogPost.getText()!=null){
                blogPost.setText(newBlogPost.getText());
            }if(newBlogPost.getTitle()!=null){
                blogPost.setTitle(newBlogPost.getTitle());
            }
            blogPostService.save(blogPost);
            return new ResponseEntity<>(blogPost, HttpStatus.CREATED);
        }
    }
    @PostMapping(path = "blogPost/getAllUser",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BlogPost>> getAllUserBlogPost(@RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByEmail(getEmailFromToken(token));
        List<BlogPost> blogPost = blogPostService.listAllUser(user);
        if (blogPost == null) {
            throw new Exception();
        } else {
            return new ResponseEntity<>(blogPost, HttpStatus.CREATED);
        }
    }
    public String getEmailFromToken(String token){
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        String email = payload.split("sub\":\"")[1].split("\",")[0];
        System.out.println(payload);
        return email;
    }
    @RequestMapping("hello")
    public String helloWorld(@RequestParam(value="name", defaultValue="World") String name) {
        return "Hello "+name+"!!";
    }
}
