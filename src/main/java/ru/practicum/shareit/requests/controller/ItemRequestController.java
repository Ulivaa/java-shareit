package ru.practicum.shareit.requests.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemMapper;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.requests.service.ItemRequestMapper;
import ru.practicum.shareit.requests.service.ItemRequestService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping(path = "/requests")
public class ItemRequestController {
    private final ItemRequestService itemRequestService;
    private final ItemService itemService;

    @Autowired
    public ItemRequestController(ItemRequestService itemRequestService, ItemService itemService) {
        this.itemRequestService = itemRequestService;
        this.itemService = itemService;
    }

    @PostMapping
    public ItemRequestDto addItemRequest(@RequestHeader("X-Sharer-User-Id") long userId,
                                         @RequestBody ItemRequestDto itemRequestDto) {
        return ItemRequestMapper.toItemRequestDto(itemRequestService
                .addItemRequest(ItemRequestMapper.toItemRequest(itemRequestDto), userId));
    }

    @GetMapping
    public Collection<ItemRequestDto> getItemRequestByOwner(@RequestHeader("X-Sharer-User-Id") long userId) {
        return itemRequestService.getItemRequestByOwner(userId).stream()
                .map(itemRequest -> ItemRequestMapper.toItemRequestDto(itemRequest,
                        itemDtoCollection(itemService.getAllItemByItemRequestId(itemRequest.getId()))))
                .collect(Collectors.toList());
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getItemRequestById(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable long requestId) {
        return ItemRequestMapper.toItemRequestDto(itemRequestService.getItemRequestById(userId, requestId),
                itemDtoCollection(itemService.getAllItemByItemRequestId(requestId)));
    }

    //    получить список запросов, созданных другими пользователями
    @GetMapping("/all")
    public Collection<ItemRequestDto> getAllItemRequest(@RequestHeader("X-Sharer-User-Id") long userId,
                                                        @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                        @RequestParam(defaultValue = "10") @Positive int size) {

        return itemRequestService.getAllItemRequest(userId, from, size).stream()
                .map(itemRequest -> ItemRequestMapper.toItemRequestDto(itemRequest,
                        itemDtoCollection(itemService.getAllItemByItemRequestId(itemRequest.getId()))))
                .collect(Collectors.toList());
    }

    private Collection<ItemDto> itemDtoCollection(Collection<Item> items) {
        return items.stream().map(ItemMapper::toItemDto).collect(Collectors.toList());
    }

}
