package com.blog9.controller;

import com.blog9.entity.User;
import com.blog9.payload.SignUpDto;
import com.blog9.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepo;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpDto singUpDto){


       Boolean emailExits = userRepo.existsByEmail(singUpDto.getEmail());
       if (emailExits){
           return new ResponseEntity<>("email exits",HttpStatus.BAD_REQUEST);
       }

        Boolean userNameExists = userRepo.existsByUsername(singUpDto.getUsername());
       if (userNameExists){
           return new ResponseEntity<>("username Exists",HttpStatus.BAD_REQUEST);
       }



        User user = new User();
         user.setName(singUpDto.getName());
         user.setUsername(singUpDto.getUsername());
         user.setEmail(singUpDto.getEmail());
         user.setPassword(passwordEncoder.encode(singUpDto.getPassword()));
         userRepo.save(user);

         return new ResponseEntity<>(user, HttpStatus.CREATED);


    }
}
