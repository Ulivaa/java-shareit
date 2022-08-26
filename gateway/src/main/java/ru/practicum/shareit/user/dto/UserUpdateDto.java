package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class UserUpdateDto {
    private long id;
    private String name;
    @Email
    private String email;
}
