package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ItemDto {
    private long id;
    private String name;
    private String description;
    private Boolean available;
    private Owner owner;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long requestId;

    @Data
    public static class Owner {
        private final long id;
        private final String name;
    }
}
