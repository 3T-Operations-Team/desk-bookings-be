package com.example.doesnotexist.desk_bookings_server.services;

import com.example.doesnotexist.desk_bookings_server.config.SecretKeyProperties;
import com.example.doesnotexist.desk_bookings_server.dto.SenderIdentityDto;
import com.example.doesnotexist.desk_bookings_server.model.Booking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.sendgrid.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(properties = "secure.sendgrid.key.value=test-api-key")
class EmailServiceTest {
    @Autowired
    private SendEmailService sendEmailService;
    private SendGrid sendGridMock;
    @Autowired
    private SecretKeyProperties secretKeyProperties;

    @Test
    void testSecretKeyProperties() {
        assertEquals("test-api-key", secretKeyProperties.value());
    }

    @BeforeEach
    void setUp() throws IOException {
        //mock sendgrid
        sendGridMock = mock(SendGrid.class);
        Response mockResponse = new Response();
        mockResponse.setStatusCode(200);
        mockResponse.setBody("""
                    [
                        {
                            "id": 1,
                            "from": {"email": "sender1@example.com"},
                            "nickname": "Sender One"
                        }
                    ]
                """);
        when(sendGridMock.api(any(Request.class))).thenReturn(mockResponse);

        SenderIdentityDto sender1 = new SenderIdentityDto();
        sender1.setId("1");
        SenderIdentityDto.From from = new SenderIdentityDto.From();
        from.setEmail("sender1@example.com");
        sender1.setFrom(from);

        SendGridSenderService sendGridSenderServiceMock = mock(SendGridSenderService.class);
        when(sendGridSenderServiceMock.parseSenderList(anyString())).thenReturn(
                List.of(sender1)
);
        sendEmailService = mock(SendEmailService.class);
    }

    @Test
    void testCanSendConfirmation() {
        EmailService emailService = new EmailService(sendEmailService);
        LocalDate today = LocalDate.now();
        String email = "test@example.com";
        String subject = "Desk booking for " + today + " confirmed";

        emailService.sendConfirmation(Booking.builder().email(email).bookingDate(today).deskNumber(1).build());

        String expectedBody = "Your desk booking is confirmed. " +  today + ", Desk #" + 1;
        verify(sendEmailService).sendEmail(email, subject, expectedBody);
    }

    @Test
    void testCanSendReminder() {
        EmailService emailService = new EmailService(sendEmailService);

        String email = "test@example.com";

        emailService.sendReminder(Booking.builder().email(email).deskNumber(1).build());

        String expectedBody = "This is a reminder for your desk booking: You've booked the desk #1";
        verify(sendEmailService).sendEmail(email, "Reminder: Booking", expectedBody);
    }
}
