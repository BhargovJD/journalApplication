package com.companyname.journalApp.service;

import com.companyname.journalApp.entity.User;
import com.companyname.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


//In Spring Boot, @Component and @Autowired work together to let Spring create and provide objects for you.

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Save a user
    public void saveEntry(User user) {
        userRepository.save(user);
    }

    // Get all users
    public List<User> getAll() {
        return userRepository.findAll();
    }

    // Find user by ID
    public Optional<User> findById(ObjectId id) {
        return userRepository.findById(id);
    }

    // Delete user by ID
    public boolean deleteById(ObjectId id) {

        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }

        return false;
    }


    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}