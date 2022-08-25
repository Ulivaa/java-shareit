package ru.practicum.shareit.requests.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ItemRequestDto {
    private long id;
    @NotEmpty
    private String description;
}
