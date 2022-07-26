package ru.practicum.shareit.booking;

import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDtoIn;
import ru.practicum.shareit.booking.dto.BookingDtoOut;

import java.util.Collection;

//тк в тз и тестах нет никаких требований пока что к этим классам,
// я не стала реализовывать их, чтобы потом не переделывать.

@RestController
@RequestMapping(path = "/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public BookingDtoOut addBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                    @RequestBody BookingDtoIn bookingDto) {
        return BookingMapper.toBookingDto(bookingService.addBooking(userId, BookingMapper.toBooking(bookingDto)));
    }

    // Может быть выполнено владельцем вещи
    @PatchMapping("/{bookingId}")
    public BookingDtoOut approveBooking(@RequestHeader("X-Sharer-User-Id") long ownerId,
                                        @PathVariable long bookingId,
                                        @RequestParam boolean approved) {
        return BookingMapper.toBookingDto(bookingService.approveBooking(ownerId, bookingId, approved));
    }

    //     Может быть выполнено либо автором бронирования, либо владельцем вещи
    @GetMapping("/{bookingId}")
    public Booking getBookingById(@RequestHeader("X-Sharer-User-Id") long userId,
                                  @PathVariable long bookingId) {
        return bookingService.requestBookingByIdByOwnerBookingOrItem(bookingId, userId);
    }

    @GetMapping
    public Collection<Booking> getUserBookings(@RequestHeader("X-Sharer-User-Id") long userId,
                                               @RequestParam(defaultValue = "ALL") String state) {
        return bookingService.getUserBookings(userId, state);
    }

    @GetMapping("/owner")
    public Collection<Booking> getItemBookingsForOwner(@RequestHeader("X-Sharer-User-Id") long userId,
                                                       @RequestParam(defaultValue = "ALL") String state) {
        return bookingService.getItemBookingsForOwner(userId, state);
    }
}
