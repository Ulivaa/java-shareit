package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

//тк в тз и тестах нет никаких требований пока что к этим классам,
// я не стала реализовывать их, чтобы потом не переделывать.

@Getter
@Setter
@AllArgsConstructor
public class Booking {
    private long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private long itemId; // мб id надо
    private long bookerId; // мб id надо
    private Status status;

}
