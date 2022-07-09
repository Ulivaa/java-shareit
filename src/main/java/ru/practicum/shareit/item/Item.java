package ru.practicum.shareit.item;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Item {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private Long owner;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long requestId;
}
