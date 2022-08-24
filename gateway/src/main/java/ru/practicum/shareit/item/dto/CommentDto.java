package ru.practicum.shareit.item.dto;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class CommentDto {
    private long id;
    private String text;
    private Item item;
    private String authorName;
    private LocalDate created;

    @Data
    public static class Item {
        private final long id;
        private final String name;
    }
}
