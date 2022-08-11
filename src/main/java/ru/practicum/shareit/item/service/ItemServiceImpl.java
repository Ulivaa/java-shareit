package ru.practicum.shareit.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.IncorrectParameterException;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    private final UserService userService;
    private final ItemRepository itemRepository;
    private final CommentRepository commentRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    public ItemServiceImpl(UserService userService, ItemRepository itemRepository, CommentRepository commentRepository, BookingRepository bookingRepository) {
        this.userService = userService;
        this.itemRepository = itemRepository;
        this.commentRepository = commentRepository;
        this.bookingRepository = bookingRepository;
    }

    public Item addItem(long userId, Item item) {
        hasParams(item);
        User user = userService.getUserById(userId);
        item.setOwner(user);
        itemRepository.save(item);
        return item;
    }

    @Override
    public Comment addComment(long userId, long itemId, Comment comment) {
        if (!isUserBookedItem(userId, itemId)) {
            throw new IncorrectParameterException("Пользователь не имеет завершенных бронирований для этой вещи");
        }
        if (comment.getText().isBlank()) {
            throw new IncorrectParameterException("text не может быть пустым");
        }
        comment.setItem(getItemById(itemId));
        comment.setAuthor(userService.getUserById(userId));
        comment.setCreated(LocalDate.now());
        commentRepository.save(comment);
        return comment;
    }

    public Collection<Comment> getCommentsByItemId(long itemId) {
        return commentRepository.findCommentsByItemIdOrderByCreatedDesc(itemId);
    }

    @Override
    public boolean isUserBookedItem(long userId, long itemId) {
        return !bookingRepository.findByBookerIdAndItemIdAndEndBefore(userId, itemId, LocalDateTime.now(), Sort.by(Sort.Direction.DESC,
                "start")).isEmpty();
    }

    @Override
    public Item updateItem(long userId, long itemId, Item item) {
        userService.getUserById(userId);
        if (getItemById(itemId).getOwner().getId() != userId) {
            throw new ItemNotFoundException(String.format("Вещь № %d не принадлежит пользователю № %d", itemId, userId));
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
        return itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(String.format("Вещь № %d не найдена", itemId)));
    }

    @Override
    public Collection<Item> getAllItem() {
        return itemRepository.findAll();
    }

    @Override
    public Collection<Item> getAllItemByUserId(long userId) {
        return itemRepository.findItemsByOwnerId(userId);
    }

    @Override
    public Collection<Item> searchBySubstring(String substr) {
        if (substr.isBlank()) {
            return Collections.emptyList();
        }
        return itemRepository.searchByNameAndDescription(substr).stream().filter(item -> item.getAvailable()).collect(Collectors.toList());
    }

    @Override
    public boolean isUserEqualsOwnerItem(long userId, long itemId) {
        return getItemById(itemId).getOwner().getId() == userId;
    }
}

