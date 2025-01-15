package com.example.doesnotexist.desk_bookings_server.services;

import org.bson.types.ObjectId;

import com.example.doesnotexist.desk_bookings_server.model.Booking;
import com.example.doesnotexist.desk_bookings_server.repostory.BookingRepository;
import com.example.doesnotexist.desk_bookings_server.repostory.DeskRepository;
import com.example.doesnotexist.desk_bookings_server.repostory.GroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookingServiceTest {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private DeskRepository deskRepository;
    @Autowired
    private GroupDeskService groupDeskService;
    @Autowired
    private BookingService service;
    @BeforeEach
    void setUp() {
        bookingRepository.deleteAll();
        deskRepository.deleteAll();
        groupRepository.deleteAll();
        groupDeskService.setUpInitialData();
    }

    @Test
    void canCreateBookingIfNewDeskHasBeenFreed() {
        // Arrange
        LocalDate date = LocalDate.now();

        Booking bookingToCancel = service.book("user1@example.com", 2, date);
        service.cancel("user1@example.com", bookingToCancel.getId());

        // Act/Assert
        assertDoesNotThrow(
                () -> service.book("user1@example.com", 2, date));
    }
    @Test
    void canCreateBooking() {
        // Arrange
        LocalDate date = LocalDate.now();
        // Act
        Booking booking = service.book("user@example.com", 2, date);
        // Assert
        assertEquals(booking, bookingRepository.findById(booking.getId()).orElse(null));
    }

    @Test
    void cannotCreateBookingIfTableDoesNotExist() {
        // Arrange
        LocalDate date = LocalDate.now();

        // Act/Assert
        assertThrows(BookingService.ResourceNotFoundException.class, () -> service.book("user@example.com", 50, date));
    }

    @Test
    void cannotCreateBookingIfDeskIsBusy() {
        // Arrange
        LocalDate date = LocalDate.now();

        service.book("user1@example.com", 2, date);

        // Act/Assert
        assertThrows(BookingService.BookingException.class,
                () -> service.book("user2@example.com", 2, date));
    }

    @Test
    void canModifyBooking() {
        // Arrange
        LocalDate date = LocalDate.now();
        LocalDate newDate = date.plusDays(1);

        Booking oldBooking = service.book("user@example.com", 2, date);

        // Act
        Booking newBooking = service.modify("user@example.com", oldBooking.getId(), 3, newDate);
        // Assert
        assertEquals(newBooking, bookingRepository.findById(oldBooking.getId()).orElse(null));
        assertNotEquals(oldBooking, newBooking);
        assertEquals(oldBooking.getEmail(), newBooking.getEmail());
        assertEquals(newBooking.getDeskNumber(), 3);
        assertEquals(newBooking.getBookingDate(), newDate);
    }

    @Test
    void cannotModifyBookingIfNewDeskIsBusy() {
        // Arrange
        LocalDate date = LocalDate.now();

        Booking booking = service.book("user1@example.com", 2, date);
        service.book("user2@example.com", 3, date);


        // Act/Assert
        assertThrows(BookingService.BookingException.class,
                () -> service.modify("user1@example.com", booking.getId(), 3, date));
    }

    @Test
    void canModifyBookingIfNewDeskHasBeenFreed() {
        // Arrange
        LocalDate date = LocalDate.now();

        Booking booking = service.book("user1@example.com", 2, date);
        Booking bookingToCancel = service.book("user2@example.com", 3, date);
        service.cancel("user2@example.com", bookingToCancel.getId());

        // Act/Assert
        assertDoesNotThrow(
                () -> service.modify("user1@example.com",
                        booking.getId(), 3, date));
    }

    @Test
    void cannotModifyBookingIfDoesNotExist() {
        // Arrange
        LocalDate date = LocalDate.now();
        Booking booking = service.book("user@example.com", 2, date);

        // Act/Assert
        assertThrows(BookingService.ResourceNotFoundException.class,
                () -> service.modify("user@example.com", new ObjectId(), 3, date));
        assertTrue(bookingRepository.findOne(Example.of(booking)).isPresent());
    }

    @Test
    void canCancelBooking() {
        // Arrange
        LocalDate date = LocalDate.now();
        Booking booking = service.book("user@example.com", 2, date);

        // Act
        service.cancel("user@example.com", booking.getId());
        // Assert
        assertTrue(bookingRepository.findById(booking.getId()).isEmpty());
    }

    @Test
    void cannotCancelBookingTwice() {
        // Arrange
        LocalDate date = LocalDate.now();
        Booking booking = service.book("user@example.com", 2, date);
        service.cancel("user@example.com", booking.getId());

        // Act/Assert
        assertThrows(BookingService.ResourceNotFoundException.class,
                () -> service.cancel("user@example.com", booking.getId()));

    }



    @Test
    void cannotCancelBookingIfDoesNotExist() {
        // Arrange
        LocalDate date = LocalDate.now();
        Booking booking = service.book("user@example.com", 2, date);

        // Act/Assert
        assertThrows(BookingService.ResourceNotFoundException.class,
                () -> service.cancel("user@example.com", new ObjectId()));
        assertTrue(bookingRepository.findOne(Example.of(booking)).isPresent());
    }

    @Test
    void getBookingsForWithNullParamsReturnsAllBookings() {
        // Arrange
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = date1.plusDays(1);

        Booking booking1 = service.book("user1@example.com", 2, date1);
        Booking booking2 = service.book("user2@example.com", 3, date2);
        // Act
        List<Booking> bookings = service.getBookingsFor(null, null, null);
        // Assert
        assertFalse(bookings.isEmpty());
        assertTrue(bookings.contains(booking1));
        assertTrue(bookings.contains(booking2));
    }

    @Test
    void getBookingsForWithEmailContainsBookingsOnlyForThatEmail() {
        // Arrange
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = date1.plusDays(1);

        Booking booking1 = service.book("user1@example.com", 2, date1);
        Booking booking2 = service.book("user2@example.com", 3, date2);
        // Act
        List<Booking> bookings = service.getBookingsFor("user1@example.com", null, null);
        // Assert
        assertFalse(bookings.isEmpty());
        assertTrue(bookings.contains(booking1));
        assertFalse(bookings.contains(booking2));
    }

    @Test
    void getBookingsForWithDateContainsBookingsOnlyForThatDate() {
        // Arrange
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = date1.plusDays(1);

        Booking booking1 = service.book("user1@example.com", 2, date1);
        Booking booking2 = service.book("user2@example.com", 3, date2);
        // Act
        List<Booking> bookings = service.getBookingsFor(null, date1, null);
        // Assert
        assertFalse(bookings.isEmpty());
        assertTrue(bookings.contains(booking1));
        assertFalse(bookings.contains(booking2));
    }

    @Test
    void getBookingsForWithDeskContainsBookingsOnlyForThatDesk() {
        // Arrange
        LocalDate date = LocalDate.now();

        Booking booking1 = service.book("user1@example.com", 2, date);
        Booking booking2 = service.book("user2@example.com", 3, date);
        // Act
        List<Booking> bookings = service.getBookingsFor(null, null, 3);
        // Assert
        assertFalse(bookings.isEmpty());
        assertTrue(bookings.contains(booking2));
        assertFalse(bookings.contains(booking1));
    }
}
