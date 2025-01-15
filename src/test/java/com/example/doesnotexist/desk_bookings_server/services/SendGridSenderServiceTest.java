package com.example.doesnotexist.desk_bookings_server.services;

import com.example.doesnotexist.desk_bookings_server.dto.SenderIdentityDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class SendGridSenderServiceTest {

    @Test
    public void testParseSenderList() throws IOException {
        String jsonResponse = "[{\"id\":\"1\",\"nickname\":\"Main Sender\",\"from\":{\"email\":\"sender@example.com\",\"name\":\"Example Sender\"},\"reply_to\":{\"email\":\"replyto@example.com\",\"name\":\"Reply To\"},\"address\":\"123 Main Street\",\"city\":\"Example City\",\"state\":\"CA\",\"zip\":\"12345\",\"country\":\"USA\"}]";

        SendGridSenderService service = new SendGridSenderService();
        List<SenderIdentityDto> senderList = service.parseSenderList(jsonResponse);
        assertFalse(senderList.isEmpty());
        assertEquals(senderList.get(0).getId(), "1");
        assertEquals(senderList.get(0).getFrom().getEmail(), "sender@example.com");
    }

    @Test
    public void testParseFailSenderList() throws IOException {
        String jsonResponse = "[]";
        SendGridSenderService service = new SendGridSenderService();
        List<SenderIdentityDto> senderList = service.parseSenderList(jsonResponse);
        assertTrue(senderList.isEmpty());
    }
}
