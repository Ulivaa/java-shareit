package ru.practicum.shareit.requests.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ItemRequestDto {
    private long id;
    private String description;
    private Requestor requestor;
    private LocalDateTime created;

    @Data
    public static class Requestor {
        private final long id;
        private final String name;
    }
}
