package com.example.doesnotexist.desk_bookings_server.controller;

import org.bson.types.ObjectId;

import com.example.doesnotexist.desk_bookings_server.dto.booking.BookingInDto;
import com.example.doesnotexist.desk_bookings_server.dto.booking.BookingOutDto;
import com.example.doesnotexist.desk_bookings_server.services.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.Email;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/booking")
@Validated
@Slf4j
public class BookingController {
    private final BookingService service;

    public BookingController (BookingService service) {
        this.service = service;
    }
    @GetMapping("")
    public List<BookingOutDto> getList(@RequestParam(value = "email", required = false) @Email String email,
                                       @RequestParam(value = "date", required = false) LocalDate date,
                                       @RequestParam(value = "desk", required = false) Integer desk) {
        return service.getBookingsFor(email, date, desk).stream()
                .map(BookingOutDto::fromBooking).toList();
    }

    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public BookingOutDto postBooking(Principal principal, @RequestBody BookingInDto dto) {
        return BookingOutDto.fromBooking(
                service.book(principal.getName(),
                        dto.deskId(),
                        dto.date()));
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public BookingOutDto putUpdated(@PathVariable("id") String id, @RequestBody BookingInDto dto,
                                           @RequestParam("email") @Email String email) {
        return BookingOutDto.fromBooking(
                service.modify(email,
                        new ObjectId(id),
                        dto.deskId(),
                        dto.date()));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void deleteExisting(Principal principal, @PathVariable("id") String id) {
                service.cancel(principal.getName(),
                        new ObjectId(id));
    }
}
