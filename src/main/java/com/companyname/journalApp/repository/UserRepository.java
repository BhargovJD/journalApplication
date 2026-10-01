package com.companyname.journalApp.repository;

import com.companyname.journalApp.entity.JournalEntry;
import com.companyname.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByUserName(String username);
}