package com.example.doesnotexist.desk_bookings_server.services;

import org.bson.types.ObjectId;

import com.example.doesnotexist.desk_bookings_server.model.Booking;
import com.example.doesnotexist.desk_bookings_server.repostory.BookingRepository;
import com.example.doesnotexist.desk_bookings_server.repostory.DeskRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final DeskRepository deskRepository;
    private final EmailService emailService;

    public BookingService(
            BookingRepository bookingRepository,
            DeskRepository deskRepository,
            EmailService emailService
    ) {
        this.bookingRepository = bookingRepository;
        this.deskRepository = deskRepository;
        this.emailService = emailService;
    }

    public List<Booking> getBookingsFor(String email, LocalDate date, Integer deskNumber) {
        return bookingRepository.findAll(Example.of(Booking.builder()
                .email(email)
                .bookingDate(date)
                .deskNumber(deskNumber)
                .createdAt(null)
                .build(), ExampleMatcher.matching().withIgnoreNullValues())
        );
    }

    public Booking book(String email, int deskNumber, LocalDate bookingDate) {
        if (!deskRepository.existsByIdAndSelectableIsTrue(deskNumber)) {
            throw new ResourceNotFoundException("Desk");
        }
        try {
            Booking booking = bookingRepository.save(Booking.builder()
                    .email(email)
                    .deskNumber(deskNumber)
                    .bookingDate(bookingDate)
                    .build());
            // this will be triggered for both modifying and new booking, send email confirmation.
            emailService.sendConfirmation(booking);
            return booking;
        }
        catch (Exception e) {
            throw new BookingException(e);
        }
    }

    public Booking modify(String email, ObjectId bookingId, int deskNumber, LocalDate bookingDate) {
        if (!deskRepository.existsByIdAndSelectableIsTrue(deskNumber)) {
            throw new ResourceNotFoundException("Desk");
        }
        Booking booking = bookingRepository.findOneByIdAndEmail(bookingId, email)
                .orElseThrow(() -> new ResourceNotFoundException("Booking"));
        try {
            return bookingRepository.save(booking
                    .withDeskNumber(deskNumber)
                    .withBookingDate(bookingDate));
        }
        catch (Exception e) {
            throw new BookingException(e);
        }
    }

    public void cancel(String email, ObjectId bookingId) {
        Booking booking = bookingRepository.findOneByIdAndEmail(bookingId, email)
                .orElseThrow(() -> new ResourceNotFoundException("Booking"));
        try {
            bookingRepository.delete(booking);
        }
        catch (Exception e) {
            throw new BookingException(e);
        }
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Requested resource not found")
    public static class ResourceNotFoundException extends RuntimeException {
        ResourceNotFoundException(String resourceName) {
            super(resourceName + " not found");
        }
    }

    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Table is already booked for that day")
    public static class BookingException extends RuntimeException {
        BookingException(Exception e) {
            super("Booking failed", e);
        }
    }
}
