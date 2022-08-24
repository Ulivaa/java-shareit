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
    //    private long id;
    @FutureOrPresent
    private LocalDateTime start;
    @Future
    private LocalDateTime end;
    //    private Status status;
//    private Booker booker;
    private long itemId;

//    @Data
//    public static class Booker {
//        private final long id;
//        private final String name;
//    }
//
//    @Data
//    public static class Item {
//        private final long id;
//        private final String name;
//    }
}
