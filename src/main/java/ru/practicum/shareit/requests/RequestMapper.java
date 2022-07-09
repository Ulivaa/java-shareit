package ru.practicum.shareit.requests;

import ru.practicum.shareit.requests.dto.ItemRequestDto;

public class RequestMapper {
    public static ItemRequestDto toItemRequestDto(ItemRequest itemRequest) {
        return new ItemRequestDto(itemRequest.getId(),
                itemRequest.getDescription(),
                itemRequest.getRequestorId(),
                itemRequest.getCreated());
    }

    public static ItemRequest toItemRequest(ItemRequestDto itemRequestDto) {
        return new ItemRequest(itemRequestDto.getId(),
                itemRequestDto.getDescription(),
                itemRequestDto.getRequestorId(),
                itemRequestDto.getCreated());
    }
}
