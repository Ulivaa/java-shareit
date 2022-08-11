package ru.practicum.shareit.requests.service;

import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

public class RequestMapper {
    public static ItemRequestDto toItemRequestDto(ItemRequest itemRequest) {
        return new ItemRequestDto(itemRequest.getId(),
                itemRequest.getDescription(),
                new ItemRequestDto.Requestor(
                        itemRequest.getRequestor().getId(),
                        itemRequest.getRequestor().getName()),
                itemRequest.getCreated());
    }

    public static ItemRequest toItemRequest(ItemRequestDto itemRequestDto) {
        return new ItemRequest(itemRequestDto.getId(),
                itemRequestDto.getDescription(),
                User.builder()
                        .id(itemRequestDto.getRequestor().getId())
                        .name(itemRequestDto.getRequestor().getName())
                        .build(),
                itemRequestDto.getCreated());
    }
}
