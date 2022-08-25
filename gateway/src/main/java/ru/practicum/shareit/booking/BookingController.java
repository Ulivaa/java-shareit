package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDtoIn;
import ru.practicum.shareit.booking.dto.State;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {
    private final BookingClient bookingClient;

    @PostMapping
    public ResponseEntity<Object> addBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                             @RequestBody @Valid BookingDtoIn bookingDtoIn) {
        log.info("Creating booking {}, userId={}", bookingDtoIn, userId);
        return bookingClient.addBooking(userId, bookingDtoIn);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> approveBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                                 @PathVariable Long bookingId,
                                                 @RequestParam boolean approved) {
        log.info("Approve booking {}, userId={}", bookingId, userId);
        return bookingClient.approveBooking(userId, bookingId, approved);
    }

    //     Может быть выполнено либо автором бронирования, либо владельцем вещи
    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                             @PathVariable Long bookingId) {
        log.info("Get booking {}, userId={}", bookingId, userId);
        return bookingClient.getBooking(userId, bookingId);
    }

    @GetMapping
    public ResponseEntity<Object> getUserBookings(@RequestHeader("X-Sharer-User-Id") long userId,
                                                  @RequestParam(name = "state", defaultValue = "ALL") String stateBooking) {
        State state = State.from(stateBooking)
                .orElseThrow(() -> new IllegalArgumentException("Unknown state: " + stateBooking));
        log.info("Get booking with state {}, userId={}", state, userId);
        return bookingClient.getUserBookings(userId, state);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getItemBookingsForOwner(@RequestHeader("X-Sharer-User-Id") long userId,
                                                          @RequestParam(name = "state", defaultValue = "ALL") String stateBooking) {
        State state = State.from(stateBooking)
                .orElseThrow(() -> new IllegalArgumentException("Unknown state: " + stateBooking));
        log.info("Get booking with state {}, userId={}", state, userId);
        return bookingClient.getItemBookingsForOwner(userId, state);
    }
}
