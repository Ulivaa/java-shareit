package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class BookingServiceTest {
    @Autowired
    private BookingService service;

    @MockBean
    private UserService userService;

    @MockBean
    private BookingRepository repository;

    @MockBean
    private ItemService itemService;

    Booking booking;
    Item item;
    User user;

    @BeforeEach
    void upSet() {
        item = Item.builder()
                .id(1L).available(true)
                .build();
        user = User.builder().id(1).name("name").email("user@user.com").build();
    }

    @Test
    void addBooking() {
        booking = Booking.builder()
                .start(LocalDateTime.now().plusMinutes(2))
                .end(LocalDateTime.now().plusDays(1))
                .item(Item.builder()
                        .id(1L).available(true)
                        .build())
                .build();
        when(itemService.getItemById(1L)).thenReturn(item);
        service.addBooking(1, booking);
        verify(repository, times(1)).save(booking);
    }

    @Test
    void approveBooking() {
        booking = Booking.builder().id(1)
                .start(LocalDateTime.now().plusMinutes(2))
                .end(LocalDateTime.now().plusDays(1)).status(Status.WAITING)
                .item(Item.builder()
                        .id(1L).available(true)
                        .build())
                .build();
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(booking));
        when(itemService.isUserEqualsOwnerItem(anyLong(), anyLong())).thenReturn(true);
        service.approveBooking(1, 1, true);
        verify(repository, times(1)).save(booking);
    }

    @Test
    void requestBookingByIdByOwnerBookingOrItem() {
        booking = Booking.builder().id(1)
                .start(LocalDateTime.now().plusMinutes(2))
                .end(LocalDateTime.now().plusDays(1)).status(Status.WAITING)
                .item(Item.builder()
                        .id(1L).available(true)
                        .build())
                .build();
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(booking));
        when(itemService.isUserEqualsOwnerItem(anyLong(), anyLong())).thenReturn(true);

        service.requestBookingByIdByOwnerBookingOrItem(1, 1);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void getUserBookings_ALL() {
        booking = Booking.builder().id(1)
                .start(LocalDateTime.now().plusMinutes(2))
                .end(LocalDateTime.now().plusDays(1)).status(Status.WAITING)
                .item(Item.builder()
                        .id(1L).available(true)
                        .build())
                .build();
        when(userService.getUserById(1L)).thenReturn(user);

        service.getUserBookings(1, "ALL");
        verify(repository, times(1)).findByBookerIdOrderByStartDesc(anyLong());
    }

    @Test
    void getUserBookings_Waiting() {
        booking = Booking.builder().id(1)
                .start(LocalDateTime.now().plusMinutes(2))
                .end(LocalDateTime.now().plusDays(1)).status(Status.WAITING)
                .item(Item.builder()
                        .id(1L).available(true)
                        .build())
                .build();
        when(userService.getUserById(1L)).thenReturn(user);

        service.getUserBookings(1, "WAITING");
        verify(repository, times(1)).findByBookerIdAndStatusOrderByStartDesc(anyLong(), any());
    }

    @Test
    void getUserBookings_REJECTED() {
        booking = Booking.builder().id(1)
                .start(LocalDateTime.now().plusMinutes(2))
                .end(LocalDateTime.now().plusDays(1)).status(Status.REJECTED)
                .item(Item.builder()
                        .id(1L).available(true)
                        .build())
                .build();
        when(userService.getUserById(1L)).thenReturn(user);

        service.getUserBookings(1, "REJECTED");
        verify(repository, times(1)).findByBookerIdAndStatusOrderByStartDesc(anyLong(), any());
    }

    @Test
    void getUserBookings_CURRENT() {
        booking = Booking.builder().id(1)
                .start(LocalDateTime.now().plusMinutes(2))
                .end(LocalDateTime.now().plusDays(1)).status(Status.REJECTED)
                .item(Item.builder()
                        .id(1L).available(true)
                        .build())
                .build();
        when(userService.getUserById(1L)).thenReturn(user);

        service.getUserBookings(1, "CURRENT");
        verify(repository, times(1)).findByBookerIdAndStartBeforeAndEndAfter(anyLong(), any(), any(), any());
    }

    @Test
    void getUserBookings_PAST() {
        booking = Booking.builder().id(1)
                .start(LocalDateTime.now().plusMinutes(2))
                .end(LocalDateTime.now().plusDays(1)).status(Status.REJECTED)
                .item(Item.builder()
                        .id(1L).available(true)
                        .build())
                .build();
        when(userService.getUserById(1L)).thenReturn(user);

        service.getUserBookings(1, "PAST");
        verify(repository, times(1)).findByBookerIdAndEndBeforeOrderByStartDesc(anyLong(), any());
    }

    @Test
    void getUserBookings_FUTURE() {
        booking = Booking.builder().id(1)
                .start(LocalDateTime.now().plusMinutes(2))
                .end(LocalDateTime.now().plusDays(1)).status(Status.REJECTED)
                .item(Item.builder()
                        .id(1L).available(true)
                        .build())
                .build();
        when(userService.getUserById(1L)).thenReturn(user);

        service.getUserBookings(1, "FUTURE");
        verify(repository, times(1)).findByBookerIdAndStartAfterOrderByStartDesc(anyLong(), any(LocalDateTime.class));
    }


}
