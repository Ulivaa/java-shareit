package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ItemDto {
    private long id;
    @NotBlank
    private String name;
    @NotEmpty
    private String description;
    //    Почему-то выдает ошибку, если оставляю с аннотацией ниже.
//    @NotEmpty
    @NotNull
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
