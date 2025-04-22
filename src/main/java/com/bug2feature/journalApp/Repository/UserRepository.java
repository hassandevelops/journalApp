package com.bug2feature.journalApp.Repository;

import com.bug2feature.journalApp.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    public User findByUserName(String username);
    public User deleteByUserName(String username);
}
