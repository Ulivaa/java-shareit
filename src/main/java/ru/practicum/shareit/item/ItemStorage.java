package ru.practicum.shareit.item;

import java.util.Collection;
import java.util.Optional;

public interface ItemStorage {
    Item addItem(Item item);

    Item updateItem(long itemId, Item item);

    void deleteItem(long itemId);

    Optional<Item> getItemById(long itemId);

    Collection<Item> getAllItem();

    Collection<Item> getAllItemByUserId(long userId);

    Collection<Item> searchBySubstring(String substr);

}
