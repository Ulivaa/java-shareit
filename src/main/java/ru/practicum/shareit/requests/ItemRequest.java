package ru.practicum.shareit.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

//тк в тз и тестах нет никаких требований пока что к этим классам,
// я не стала реализовывать их, чтобы потом не переделывать
@Getter
@Setter
@AllArgsConstructor
public class ItemRequest {
    private long id;
    private String description;
    private long requestorId;
    private LocalDateTime created;
}
