package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoOwner;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private final BookingService bookingService;

    @Autowired
    public ItemController(ItemService itemService, BookingService bookingService) {
        this.itemService = itemService;
        this.bookingService = bookingService;
    }

    @PostMapping
    public ItemDto addItem(@RequestHeader("X-Sharer-User-Id") long userId,
                           @RequestBody ItemDto itemDto) {
        return ItemMapper.toItemDto(itemService.addItem(userId, ItemMapper.toItem(itemDto)));
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestHeader("X-Sharer-User-Id") long userId,
                              @PathVariable long itemId,
                              @RequestBody ItemDto itemDto) {
        return ItemMapper.toItemDto(itemService.updateItem(userId, itemId, ItemMapper.toItem(itemDto)));
    }

    @GetMapping("/{itemId}")
    // хотела сделать ItemDtoOwner наследником ItemDto но он хочет тогда чтобы я создала конструктор руками..
    // не понимаю как это должно работать
    public ItemDtoOwner getItemById(@RequestHeader("X-Sharer-User-Id") long userId,
                                    @PathVariable long itemId) {
        Item item = itemService.getItemById(itemId);
        LocalDateTime now = LocalDateTime.now();
        Collection<CommentDto> comments = itemService.getCommentsByItemId(itemId)
                .stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
        if (item.getOwner().getId() == userId) {
            Booking lastB = bookingService.getLastItemBooking(itemId, now);
            Booking nextB = bookingService.getNextItemBooking(itemId, now);
            return ItemMapper.toItemDtoOwner(itemService.getItemById(itemId), lastB, nextB, comments);
        }
        return ItemMapper.toItemDtoOwner(itemService.getItemById(itemId), null, null, comments);
    }

    @GetMapping
    public Collection<ItemDtoOwner> getAllItem(@RequestHeader("X-Sharer-User-Id") long userId) {
        LocalDateTime now = LocalDateTime.now();
        return itemService.getAllItemByUserId(userId)
                .stream()
                .map(item -> {
                    Booking lastB = bookingService.getLastItemBooking(item.getId(), now);
                    Booking nextB = bookingService.getNextItemBooking(item.getId(), now);
                    Collection<CommentDto> comments = itemService.getCommentsByItemId(item.getId())
                            .stream()
                            .map(CommentMapper::toCommentDto)
                            .collect(Collectors.toList());
                    return ItemMapper.toItemDtoOwner(itemService.getItemById(item.getId()), lastB, nextB, comments);
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    public Collection<ItemDto> searchBySubstring(@RequestParam String text) {
        return itemService.searchBySubstring(text)
                .stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{itemId}")
    public void deleteItemById(@PathVariable long itemId) {
        itemService.deleteItem(itemId);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable long itemId, @RequestBody CommentDto commentDto) {
        return CommentMapper.toCommentDto(itemService.addComment(userId,
                itemId,
                CommentMapper.toComment(commentDto)));
    }
}
