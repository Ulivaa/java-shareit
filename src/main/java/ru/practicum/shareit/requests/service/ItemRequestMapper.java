package ru.practicum.shareit.requests.service;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;

public class ItemRequestMapper {
    public static ItemRequestDto toItemRequestDto(ItemRequest itemRequest, Collection<ItemDto> items) {
        return ItemRequestDto.builder()
                .id(itemRequest.getId())
                .description(itemRequest.getDescription())
                .requestor(new ItemRequestDto.Requestor(
                        itemRequest.getRequestor().getId(),
                        itemRequest.getRequestor().getName())).created(itemRequest.getCreated())
                .items(items).build();
    }

    public static ItemRequestDto toItemRequestDto(ItemRequest itemRequest) {

        return ItemRequestDto.builder()
                .id(itemRequest.getId())
                .description(itemRequest.getDescription())
                .requestor(new ItemRequestDto.Requestor(
                        itemRequest.getRequestor().getId(),
                        itemRequest.getRequestor().getName())).created(itemRequest.getCreated()).build();
    }

    public static ItemRequest toItemRequest(ItemRequestDto itemRequestDto) {
        return new ItemRequest(itemRequestDto.getId(),
                itemRequestDto.getDescription(),
                itemRequestDto.getRequestor() == null ? null : User.builder()
                        .id(itemRequestDto.getRequestor().getId())
                        .name(itemRequestDto.getRequestor().getName())
                        .build(),
                itemRequestDto.getCreated());
    }
}
