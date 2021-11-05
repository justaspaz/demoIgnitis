package com.example.demoignitis.controllers;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import com.example.demoignitis.enteties.User;
import com.example.demoignitis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.management.BadAttributeValueExpException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping(path = "register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@RequestBody User newUser) throws Exception {
        User user = userService.save(newUser);
        if (user == null) {
            throw new Exception();
        } else {
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
    }
    @PostMapping(path = "login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> loginUser(@RequestBody User userLogin) throws BadAttributeValueExpException {
        User user = userService.findUserByEmailAndPassword(userLogin);
        if (user == null) {
            throw new BadAttributeValueExpException(userLogin);
        } else {
            String token = getJWTToken(user.getEmail());
            user.setToken(token);
            userService.save(user);
            return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
        }
    }
    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("JWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return token;
    }

}
