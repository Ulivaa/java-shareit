package ru.practicum.shareit.requests.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ItemRequestDto {
    private long id;
    private String description;
    private long requestorId;
    private LocalDateTime created;
}
