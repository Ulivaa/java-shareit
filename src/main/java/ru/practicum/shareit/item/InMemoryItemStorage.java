package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class InMemoryItemStorage implements ItemStorage {
    private final Map<Long, Item> items = new HashMap<>();

    private static long id = 0;

    public Item addItem(Item item) {
        item.setId(getId());
        items.put(item.getId(), item);
        return item;
    }

    public Item updateItem(long itemId, Item item) {
        Item updateItem = items.get(itemId);
        if (item.getName() != null) {
            updateItem.setName(item.getName());
        }
        if (item.getDescription() != null) {
            updateItem.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            updateItem.setAvailable(item.getAvailable());
        }
        if (item.getRequestId() != null) {
            updateItem.setRequestId(item.getRequestId());
        }
        return updateItem;
    }


    public Optional<Item> getItemById(long itemId) {
        return Optional.ofNullable(items.get(itemId));
    }

    @Override
    public Collection<Item> getAllItem() {
        return items.values();
    }

    public Collection<Item> getAllItemByUserId(long userId) {
        return items.values().stream().filter(item -> item.getOwner() == userId).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Collection<Item> searchBySubstring(String substr) {
        final String lowerCaseSubstr = substr.toLowerCase(Locale.ROOT);
        return items.values().stream()
                .filter(item -> item.getDescription().toLowerCase(Locale.ROOT).contains(lowerCaseSubstr))
                .filter(item -> item.getAvailable())
                .collect(Collectors.toUnmodifiableList());
    }

    public void deleteItem(long itemId) {
        items.remove(itemId);
    }

    private long getId() {
        return ++id;
    }

//    та же история что в юзерах
//    private long getId() {
//        long lastId = items.values()
//                .stream()
//                .mapToLong(Item::getId)
//                .max()
//                .orElse(0);
//        return lastId + 1;
//    }

}
