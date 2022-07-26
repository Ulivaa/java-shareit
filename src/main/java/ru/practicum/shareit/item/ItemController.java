package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.item.dto.ItemDto;

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
    public ItemDto getItemById(@RequestHeader("X-Sharer-User-Id") long userId,
                               @PathVariable long itemId) {
        Item item = itemService.getItemById(itemId);
        if (item.getOwner().getId() == userId) {
//            Booking lastB = bookingService;
            return ItemMapper.toItemDtoOwner(itemService.getItemById(itemId));
        }
        return ItemMapper.toItemDto(itemService.getItemById(itemId));
    }

    @GetMapping
    public Collection<ItemDto> getAllItem(@RequestHeader("X-Sharer-User-Id") long userId) {
       return itemService.getAllItemByUserId(userId)
               .stream()
               .map(ItemMapper::toItemDto)
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
}
