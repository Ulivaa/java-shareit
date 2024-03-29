package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Collection;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ItemDtoOwner {
    private long id;
    private String name;
    private String description;
    private Boolean available;
    private Owner owner;
    private Booking lastBooking;
    private Booking nextBooking;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private ItemRequest itemRequest;
    private Collection<CommentDto> comments;

    @Data
    public static class Owner {
        private final long id;
        private final String name;
    }

    @Data
    public static class ItemRequest {
        private final long id;
    }

    @Data
    public static class Booking {
        private final long id;
        private final long bookerId;
    }

}
