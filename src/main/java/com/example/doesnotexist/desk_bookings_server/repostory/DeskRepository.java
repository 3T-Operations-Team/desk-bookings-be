package com.example.doesnotexist.desk_bookings_server.repostory;

import com.example.doesnotexist.desk_bookings_server.model.Desk;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeskRepository extends MongoRepository<Desk, Integer> {
  boolean existsByIdAndSelectableIsTrue(Integer id);
}
