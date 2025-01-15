package com.example.doesnotexist.desk_bookings_server.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class BookingTest {

    @Test
    void createdAtNotNull() {
        Booking booking = Booking.builder().build();
        assertNotNull(booking.getCreatedAt());
    }
}
