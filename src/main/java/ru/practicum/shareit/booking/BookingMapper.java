package ru.practicum.shareit.booking;


import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

public class BookingMapper {
    public static BookingDto toBookingDto(Booking booking) {
        return new BookingDto(booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                booking.getStatus(),
                new BookingDto.Booker(
                        booking.getBooker().getId(),
                        booking.getBooker().getName()
                ),
                new BookingDto.Item(
                        booking.getItem().getId(),
                        booking.getItem().getName()
                )
        );
    }

    public static Booking toBooking(BookingDto bookingDto) {
        return new Booking(bookingDto.getId(),
                bookingDto.getStart(),
                bookingDto.getEnd(),
                Item.builder()
                        .id(bookingDto.getItem().getId())
                        .name(bookingDto.getItem().getName())
                        .build(),
                User.builder()
                        .id(bookingDto.getBooker().getId())
                        .name(bookingDto.getBooker().getName())
                        .build(),
                bookingDto.getStatus());
    }
}

