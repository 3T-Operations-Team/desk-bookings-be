package com.example.doesnotexist.desk_bookings_server.repostory;

import com.example.doesnotexist.desk_bookings_server.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupRepository extends MongoRepository<Group, Integer> {
}
