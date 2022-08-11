package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

public interface ItemService {
    Item addItem(long userId, Item item);

    Comment addComment(long userId, long itemId, Comment comment);

    Item updateItem(long userId, long itemId, Item item);

    void deleteItem(long itemId);

    Item getItemById(long itemId);

    Collection<Item> getAllItem();

    Collection<Item> getAllItemByUserId(long userId);

    Collection<Item> searchBySubstring(String substr);

    boolean isUserEqualsOwnerItem(long userId, long itemId);

    boolean isUserBookedItem(long userId, long itemId);

    Collection<Comment> getCommentsByItemId(long itemId);

}
