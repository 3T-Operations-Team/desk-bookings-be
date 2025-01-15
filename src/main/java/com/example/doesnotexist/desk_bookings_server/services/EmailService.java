package com.example.doesnotexist.desk_bookings_server.services;

import com.example.doesnotexist.desk_bookings_server.model.Booking;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final SendEmailService sendEmailService;

    public EmailService(SendEmailService emailServiceService) {
        this.sendEmailService = emailServiceService;
    }

    public void sendConfirmation(Booking booking) {
        String subject = "Desk booking for " + booking.getBookingDate() + " confirmed";
        String body = "Your desk booking is confirmed. " +  booking.getBookingDate() + ", Desk #" + booking.getDeskNumber();
        sendEmailService.sendEmail(booking.getEmail(), subject, body);
    }

    public void sendReminder(Booking booking) {
        String subject = "Reminder: Booking";
        String body = "This is a reminder for your desk booking: " +
                "You've booked the desk #" + booking.getDeskNumber();
        sendEmailService.sendEmail(booking.getEmail(), subject, body);
    }
}
