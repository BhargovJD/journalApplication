package com.companyname.journalApp.controller;

import com.companyname.journalApp.entity.User;
import com.companyname.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    // @Autowired tells Spring Boot to automatically provide
    // an object of UserService.
    @Autowired
    private UserService userService;


    // =========================
    // READ - GET ALL USERS
    // =========================
    // GET http://localhost:8080/user
    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }


    // =========================
    // CREATE USER
    // =========================
    // POST http://localhost:8080/user
    //
    // Request Body:
    // {
    //     "userName": "bhargov",
    //     "password": "12345"
    // }
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User myUser) {

        try {

            userService.saveEntry(myUser);

            return new ResponseEntity<>(myUser, HttpStatus.CREATED);

        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    // =========================
    // FIND USER BY ID
    // =========================
    // GET http://localhost:8080/user/id/6abdbed8e0ade572331530cd
    @GetMapping("id/{myId}")
    public ResponseEntity<User> getUserById(
            @PathVariable ObjectId myId) {

        Optional<User> user = userService.findById(myId);

        if (user.isPresent()) {

            return new ResponseEntity<>(
                    user.get(),
                    HttpStatus.OK
            );

        } else {

            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }
    }


    // =========================
    // DELETE USER
    // =========================
    // DELETE http://localhost:8080/user/id/6abdbed8e0ade572331530cd
    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteUserById(
            @PathVariable ObjectId myId) {

        boolean deleted = userService.deleteById(myId);

        if (deleted) {

            // 204 = No Content
            return new ResponseEntity<>(
                    HttpStatus.NO_CONTENT
            );
        }

        // 404 = User not found
        return new ResponseEntity<>(
                HttpStatus.NOT_FOUND
        );
    }


    // =========================
    // UPDATE USER
    // =========================
    // PUT http://localhost:8080/user/username/bhargov
    //
    // Request Body:
    // {
    //     "userName": "newUsername",
    //     "password": "newPassword"
    // }


    @PutMapping("username/{userName}")
    public ResponseEntity<?> updateUserByUserName(
            @PathVariable String userName,
            @RequestBody User newUser) {

        User old = userService.findByUserName(userName);

        if (old != null) {

            // Update username if new username is provided
            if (newUser.getUserName() != null
                    && !newUser.getUserName().isEmpty()) {

                old.setUserName(newUser.getUserName());
            }

            // Update password if new password is provided
            if (newUser.getPassword() != null
                    && !newUser.getPassword().isEmpty()) {

                old.setPassword(newUser.getPassword());
            }

            // Save updated user
            userService.saveEntry(old);

            // 200 = OK
            return new ResponseEntity<>(old, HttpStatus.OK);
        }

        // 404 = User not found
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}