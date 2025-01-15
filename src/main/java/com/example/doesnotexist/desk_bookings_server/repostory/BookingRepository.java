package com.example.doesnotexist.desk_bookings_server.repostory;

import org.bson.types.ObjectId;

import com.example.doesnotexist.desk_bookings_server.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BookingRepository extends MongoRepository<Booking, ObjectId> {
    Optional<Booking> findOneByIdAndEmail(ObjectId bookingId, String email);
}
