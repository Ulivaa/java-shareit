package ru.practicum.shareit.item.service;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoOwner;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.requests.model.ItemRequest;

import java.util.Collection;

public class ItemMapper {

    public static ItemDto toItemDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .owner(new ItemDto.Owner(item.getOwner().getId(), item.getOwner().getName()))
                .requestId(item.getRequest() == null ? null : item.getRequest().getId()).build();
    }

    public static ItemDtoOwner toItemDtoOwner(Item item, Booking lastB, Booking nextB, Collection<CommentDto> comments) {
        return ItemDtoOwner.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .owner(new ItemDtoOwner.Owner(item.getOwner().getId(), item.getOwner().getName()))
                .lastBooking(lastB == null ? null : new ItemDtoOwner.Booking(lastB.getId(), lastB.getBooker().getId()))
                .nextBooking(nextB == null ? null : new ItemDtoOwner.Booking(nextB.getId(), nextB.getBooker().getId()))
                .comments(comments)
                .build();
    }

    public static Item toItem(ItemDto itemDto) {
        return Item.builder()
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .request(itemDto.getRequestId() == null ? null : ItemRequest.builder().id(itemDto.getRequestId()).build())
                .build();
    }
}
