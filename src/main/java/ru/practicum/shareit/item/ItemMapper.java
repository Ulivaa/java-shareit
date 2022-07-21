package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.requests.ItemRequest;
import ru.practicum.shareit.user.User;

public class ItemMapper {
    public static ItemDto toItemDto(Item item) {
        return new ItemDto(item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                new ItemDto.Owner(item.getId(),
                        item.getName()),
                new ItemDto.ItemRequest(item.getId()));
    }

    public static Item toItem(ItemDto itemDto) {
        return new Item(itemDto.getId(),
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getAvailable(),
                User.builder()
                        .id(itemDto.getOwner().getId())
                        .name(itemDto.getOwner().getName())
                        .build(),
                ItemRequest.builder()
                        .id(itemDto.getItemRequest().getId())
                        .build());
    }
}
