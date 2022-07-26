package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.requests.ItemRequest;
import ru.practicum.shareit.user.User;

public class ItemMapper {

    public static ItemDto toItemDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .owner(new ItemDto.Owner(item.getOwner().getId(), item.getOwner().getName())).build();
    }

    public static ItemDto toItemDtoOwner(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .owner(new ItemDto.Owner(item.getOwner().getId(), item.getOwner().getName())).build();
    }
    public static Item toItem(ItemDto itemDto) {
//        return new Item(itemDto.getId(),
//                itemDto.getName(),
//                itemDto.getDescription(),
//                itemDto.getAvailable(),
//                User.builder()
//                        .id(itemDto.getOwner().getId())
//                        .name(itemDto.getOwner().getName())
//                        .build(),
//                ItemRequest.builder()
//                        .id(itemDto.getItemRequest().getId())
//                        .build());
        return Item.builder()
//                .id(itemDto.getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
//                .owner(User.builder()
//                        .id(itemDto.getOwner().getId())
//                        .name(itemDto.getOwner().getName())
//                        .build())
//                .itemRequest(ItemRequest.builder()
//                        .id(itemDto.getItemRequest().getId())
//                        .build())
                .build();
    }


}
