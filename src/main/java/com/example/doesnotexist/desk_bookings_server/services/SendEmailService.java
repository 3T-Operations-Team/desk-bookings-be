package com.example.doesnotexist.desk_bookings_server.services;

import com.example.doesnotexist.desk_bookings_server.dto.SenderIdentityDto;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.example.doesnotexist.desk_bookings_server.config.SendGridProperties;
import com.sendgrid.*;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SendEmailService {

    private final SendGrid sendGridClient;
    private List<SenderIdentityDto> senderList = new ArrayList<>();

    public SendEmailService(SendGridProperties sendGridProperties) {
        this.sendGridClient = new SendGrid(sendGridProperties.getKey());
        if (!Strings.isBlank(sendGridProperties.getHost())) {
            this.sendGridClient.setHost(sendGridProperties.getHost());
        }
        if (!Strings.isBlank(sendGridProperties.getVersion())) {
            this.sendGridClient.setVersion(sendGridProperties.getVersion());
        }
        getSenderList();
    }

    private void getSenderList() {
        try {
            Request request = new Request();
            request.setMethod(Method.GET);
            request.setEndpoint("/senders");
            Response response = sendGridClient.api(request);
            SendGridSenderService sendGridSenderService = new SendGridSenderService();
            if (response.getStatusCode() == HttpStatus.OK.value()) {
                senderList.addAll(sendGridSenderService.parseSenderList(response.getBody()));
            }
        }
        catch (IOException exception) {
            throw new SenderNotAvailable(exception);
        }
    }

    public void sendEmail(String recipient, String subject, String body) {
        if (senderList.isEmpty()) {
            return;
        }
        // Use the apiKey here to send emails
        SenderIdentityDto sender = senderList.get(0); // for now just get the first sender
        Email from = new Email(sender.getFrom().getEmail()); // Replace with your domain
        Email to = new Email(recipient);
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(from, subject, to, content);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGridClient.api(request);
            System.out.println("email sent with status code : " + response.getStatusCode());
        }
        catch (Exception ex) {
            throw new MailNotSentException(ex);
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class MailNotSentException extends RuntimeException {
        MailNotSentException(Exception e) {
            super("Sending email failed ", e);
        }
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    public static class SenderNotAvailable extends RuntimeException {
        SenderNotAvailable(Exception e) {
            super("Sender unavailable", e);
        }
    }
}
