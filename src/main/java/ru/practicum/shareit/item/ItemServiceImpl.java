package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.IncorrectParameterException;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.util.Collection;
import java.util.Collections;

@Service
public class ItemServiceImpl implements ItemService {
    private final UserService userService;
    private final ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(UserService userService, ItemRepository itemRepository) {
        this.userService = userService;
        this.itemRepository = itemRepository;
    }

    public Item addItem(long userId, Item item) {
        hasParams(item);
        User user = userService.getUserById(userId);
        item.setOwner(user);
        itemRepository.save(item);
        return item;
    }

    @Override
    public Item updateItem(long userId, long itemId, Item item) {
        userService.getUserById(userId);
        if (getItemById(itemId).getOwner().getId() != userId) {
            throw new ItemNotFoundException(String.format("Вещь № %d не пренадлежит пользователю № %d", itemId, userId));
        }
        Item updateItem = getItemById(itemId);
        if (item.getName() != null) {
            updateItem.setName(item.getName());
        }
        if (item.getDescription() != null) {
            updateItem.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            updateItem.setAvailable(item.getAvailable());
        }
        if (item.getItemRequest() != null) {
            updateItem.setItemRequest(item.getItemRequest());
        }
        itemRepository.save(updateItem);
        return updateItem;
    }

    public void deleteItem(long itemId) {
        if (getItemById(itemId) != null) {
            itemRepository.deleteById(itemId);
//            itemStorage.deleteItem(itemId);
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
        return itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(String.format("Пользователь № %d не найден", itemId)));
    }

    @Override
    public Collection<Item> getAllItem() {
        return itemRepository.findAll();
    }

    @Override
    public Collection<Item> getAllItemByUserId(long userId) {
        return itemRepository.findItemsByOwnerId(userId);
    }

    //    TODO!!!!
    @Override
    public Collection<Item> searchBySubstring(String substr) {
        if (substr.isBlank()) {
            return Collections.emptyList();
        }
        return itemRepository.searchByNameAndDescription(substr);
    }
}

