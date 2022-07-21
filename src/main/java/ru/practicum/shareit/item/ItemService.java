package ru.practicum.shareit.item;

import java.util.Collection;

public interface ItemService {
    Item addItem(long userId, Item item);

    Item updateItem(long userId, long itemId, Item item);

    void deleteItem(long itemId);

    Item getItemById(long itemId);

    Collection<Item> getAllItem();

    Collection<Item> getAllItemByUserId(long userId);

    Collection<Item> searchBySubstring(String substr);

}
