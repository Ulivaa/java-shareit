package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//тк в тз и тестах нет никаких требований пока что к этим классам,
// я не стала реализовывать их, чтобы потом не переделывать.
// DTO пока не понятно зачем нужно. Переведу, когда будут требования адекватные

@Getter
@Setter
@AllArgsConstructor
public class ItemDto {
    private long id;
    private String name;
    private String description;
    private Boolean available;
    private Long owner;
    private Long requestId;
}
