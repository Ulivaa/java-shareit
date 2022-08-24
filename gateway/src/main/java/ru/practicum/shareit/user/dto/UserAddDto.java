package ru.practicum.shareit.user.dto;

import lombok.*;

import javax.validation.constraints.*;

//import javax.validation.constraints.FutureOrPresent;
@Builder
@Getter
@Setter
@AllArgsConstructor
public class UserAddDto {
    private long id;
    @NotEmpty
    private String name;
    @NotEmpty
    @Email
    private String email;
}
