package ru.practicum.shareit.booking.service;


import ru.practicum.shareit.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.Collection;

public interface BookingService {
    Booking addBooking(long userId, Booking booking);

    Booking approveBooking(long ownerId, long bookingId, boolean approved);

    Booking getBookingById(long bookingId);

    Booking requestBookingByIdByOwnerBookingOrItem(long bookingId, long userId);

    Collection<Booking> getUserBookings(long userId, String state);

    Collection<Booking> getItemBookingsForOwner(long userId, String state);

    boolean isUserEqualsOwnerBooking(long userId, long bookingId);

    Booking getLastItemBooking(long itemId, LocalDateTime now);

    Booking getNextItemBooking(long itemId, LocalDateTime now);

}
