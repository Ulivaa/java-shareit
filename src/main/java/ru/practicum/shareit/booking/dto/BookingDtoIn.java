package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.booking.model.Status;

import java.time.LocalDateTime;

// К сожалению я другого варианта не увидела кроме как два дто, тк на вход приходит боди :
//{
//    "itemId": 2,
//    "start": "{{start}}",
//    "end": "{{end}}"
//}
// А на выход от нас хотят объект,
// и если оставить одно дто с объектом item то он его не находит при входе в контроллер, т.к. там есть только id
@Getter
@Setter
@AllArgsConstructor
public class BookingDtoIn {
    private long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Status status;
    private Booker booker;
    private long itemId;

    @Data
    public static class Booker {
        private final long id;
        private final String name;
    }

    @Data
    public static class Item {
        private final long id;
        private final String name;
    }
}
