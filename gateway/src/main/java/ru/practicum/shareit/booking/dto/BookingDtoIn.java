package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class BookingDtoIn {
    @FutureOrPresent
    private LocalDateTime start;
    @Future
    private LocalDateTime end;
    private long itemId;
}
