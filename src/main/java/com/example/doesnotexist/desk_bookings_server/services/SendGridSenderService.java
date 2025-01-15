package com.example.doesnotexist.desk_bookings_server.services;

import com.example.doesnotexist.desk_bookings_server.dto.SenderIdentityDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SendGridSenderService {

    private final ObjectMapper objectMapper;

    public SendGridSenderService() {
        this.objectMapper = new ObjectMapper();
    }

    public List<SenderIdentityDto> parseSenderList(String jsonResponse) throws IOException {
        return objectMapper.readValue(jsonResponse, new TypeReference<List<SenderIdentityDto>>() {});
    }
}
