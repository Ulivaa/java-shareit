package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.IncorrectParameterException;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.user.UserService;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemStorage itemStorage;
    private final UserService userService;

    @Autowired
    public ItemServiceImpl(ItemStorage itemStorage, UserService userService) {
        this.itemStorage = itemStorage;
        this.userService = userService;
    }

    public Item addItem(long userId, Item item) {
        hasParams(item);
        userService.getUserById(userId);
        item.setOwner(userId);
        return itemStorage.addItem(item);
    }

    @Override
    public Item updateItem(long userId, long itemId, Item item) {
        userService.getUserById(userId);
        if (getItemById(itemId).getOwner() != userId) {
            throw new ItemNotFoundException(String.format("Вещь № %d не пренадлежит пользователю № %d", itemId, userId));
        }
        return itemStorage.updateItem(itemId, item);
    }

    public void deleteItem(long itemId) {
        if (getItemById(itemId) != null) {
            itemStorage.deleteItem(itemId);
        }
    }

    private boolean hasParams(Item item) {
        if (item.getAvailable() == null) {
            throw new IncorrectParameterException("available");
        }
        if (item.getName() == null || item.getName().isBlank()) {
            throw new IncorrectParameterException("name");
        }
        if (item.getDescription() == null || item.getDescription().isBlank()) {
            throw new IncorrectParameterException("description");
        }
        return true;
    }

    public Item getItemById(long itemId) {
        return itemStorage.getItemById(itemId).orElseThrow(() -> new ItemNotFoundException(String.format("Пользователь № %d не найден", itemId)));
    }

    @Override
    public Collection<Item> getAllItem() {
        return itemStorage.getAllItem();
    }

    @Override
    public Collection<Item> getAllItemByUserId(long userId) {
        return itemStorage.getAllItemByUserId(userId);
    }

    @Override
    public Collection<Item> searchBySubstring(String substr) {
        if (substr.isEmpty()) {
            return new ArrayList<>();
        }
        return itemStorage.searchBySubstring(substr);
    }
}

