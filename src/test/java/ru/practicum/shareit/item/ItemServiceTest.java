package ru.practicum.shareit.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.requests.service.ItemRequestService;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class ItemServiceTest {

    @Autowired
    private ItemService service;

    @MockBean
    private UserService userService;
    @MockBean
    private ItemRepository itemRepository;
    @MockBean
    private CommentRepository commentRepository;
    @MockBean
    private BookingRepository bookingRepository;
    @MockBean
    private ItemRequestService itemRequestService;

    User user;

    Booking booking;
    Collection<Booking> bookings;

    @BeforeEach
    void setUp() {
        user = User.builder().id(1).name("name").email("user@user.com").build();
        booking = Booking.builder().id(1).build();
        bookings = new ArrayList<>();
        bookings.add(booking);
    }


    @Test
    void addItem() {
        Item item = Item.builder()
                .name("Дрель")
                .description("description")
                .available(true)
                .request(ItemRequest.builder().id(1).build())
                .build();
        service.addItem(1, item);
        verify(itemRepository, times(1)).save(item);
    }

    @Test
    void updateItem() {
        Item item = Item.builder()
                .name("Дрель+")
                .description("description+")
                .available(true)
                .owner(User.builder().id(1).build())
                .request(ItemRequest.builder().id(1).build())
                .build();
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        service.updateItem(1, 1, item);
        verify(itemRepository, times(1)).save(item);
    }

    @Test
    void addComment() {
        Comment comment = Comment.builder().text("text").build();
        Item item = Item.builder().id(1L)
                .name("Дрель+")
                .description("description+")
                .available(true)
                .owner(User.builder().id(1).build())
                .request(ItemRequest.builder().id(1).build())
                .build();
        when(bookingRepository.findByBookerIdAndItemIdAndEndBefore(anyLong(), anyLong(), any(), any())).thenReturn(bookings);
        when(itemRepository.findById(1L)).thenReturn(Optional.ofNullable(item));
        service.addComment(1, 1, comment);
        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void deleteItem() {
        Item item = Item.builder().id(1L)
                .name("Дрель+")
                .description("description+")
                .available(true)
                .owner(User.builder().id(1).build())
                .request(ItemRequest.builder().id(1).build())
                .build();
        when(itemRepository.findById(1L)).thenReturn(Optional.ofNullable(item));
        service.deleteItem(1);
        verify(itemRepository, times(1)).deleteById(1L);
    }

}
