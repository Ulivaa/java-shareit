package ru.practicum.shareit.requests.service;

import ru.practicum.shareit.requests.model.ItemRequest;

import java.util.Collection;

public interface ItemRequestService {
    ItemRequest addItemRequest(ItemRequest itemRequest, long userId);

    Collection<ItemRequest> getItemRequestByOwner(long userId);

    ItemRequest getItemRequestById(long userId, long requestId);

    Collection<ItemRequest> getAllItemRequest(long userId, int from, int size);

}
