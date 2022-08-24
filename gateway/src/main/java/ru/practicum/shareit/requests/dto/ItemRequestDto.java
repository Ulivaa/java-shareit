package ru.practicum.shareit.requests.dto;

import lombok.*;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Collection;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ItemRequestDto {
    private long id;
    @NotEmpty
    private String description;
//    private Requestor requestor;
//    private LocalDateTime created;
//    private Collection<ItemDto> items;

//    @Data
//    public static class Requestor {
//        private final long id;
//        private final String name;
//    }
}
