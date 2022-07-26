package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDtoIn;
import ru.practicum.shareit.booking.dto.BookingDtoOut;
import ru.practicum.shareit.item.Item;

public class BookingMapper {
    public static BookingDtoOut toBookingDto(Booking booking) {
        return new BookingDtoOut(booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                booking.getStatus(),
                new BookingDtoIn.Booker(
                        booking.getBooker().getId(),
                        booking.getBooker().getName()
                ),
                new BookingDtoOut.Item(
                        booking.getItem().getId(),
                        booking.getItem().getName()
                )
        );
    }

    public static Booking toBooking(BookingDtoIn bookingDto) {
        return Booking.builder()
                .id(bookingDto.getId())
                .start(bookingDto.getStart())
                .end(bookingDto.getEnd())
                .item(Item.builder()
                        .id(bookingDto.getItemId())
                        .build())
                .build();
    }
}

