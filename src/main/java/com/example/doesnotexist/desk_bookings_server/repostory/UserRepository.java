package com.example.doesnotexist.desk_bookings_server.repostory;

import org.bson.types.ObjectId;

import com.example.doesnotexist.desk_bookings_server.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    Optional<User> findByUsername(String username);
}
