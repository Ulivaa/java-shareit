package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.booking.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class BookingDto {
    private long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private long itemId; // мб id надо
    private long bookerId; // мб id надо
    private Status status;
}
