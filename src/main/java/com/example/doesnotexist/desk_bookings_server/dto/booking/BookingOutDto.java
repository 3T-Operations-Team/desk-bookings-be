package com.example.doesnotexist.desk_bookings_server.dto.booking;

import com.example.doesnotexist.desk_bookings_server.model.Booking;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record BookingOutDto(String id,
                            @JsonFormat(pattern="yyyy-MM-dd")
                            LocalDate bookingDate,
                            String email,
                            Integer deskNumber) {
    public static BookingOutDto fromBooking(Booking model) {
        return new BookingOutDto(model.getId().toString(), model.getBookingDate(), model.getEmail(), model.getDeskNumber());
    }
}
