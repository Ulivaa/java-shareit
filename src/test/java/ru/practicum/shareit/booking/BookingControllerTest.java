package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.controller.BookingController;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    Booking booking;

    @Test
    public void addBooking() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.now();
        String json = "{\"itemId\": 2,\"start\": \"" + localDateTime + "\",\"end\": \"" + localDateTime.plusDays(1) + "\"}";
        booking = Booking.builder()
                .id(1)
                .start(localDateTime)
                .end(localDateTime.plusDays(1))
                .item(Item.builder()
                        .id(1L)
                        .name("item")
                        .build())
                .status(Status.WAITING)
                .booker(User.builder().id(1).name("User").build())
                .build();
        when(bookingService.addBooking(anyLong(), any()))
                .thenReturn(booking);

        this.mockMvc
                .perform(post("/bookings")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON).header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void approveBooking() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.now();
        booking = Booking.builder()
                .id(1)
                .start(localDateTime)
                .end(localDateTime.plusDays(1))
                .item(Item.builder()
                        .id(1L)
                        .name("item")
                        .build())
                .status(Status.WAITING)
                .booker(User.builder().id(1).name("User").build())
                .build();
        when(bookingService.approveBooking(anyLong(), anyLong(), anyBoolean()))
                .thenReturn(booking);

        this.mockMvc
                .perform(patch("/bookings/1")
                        .param("approved", "true")
                        .contentType(MediaType.APPLICATION_JSON).header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void getBookingById() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.now();
        booking = Booking.builder()
                .id(1)
                .start(localDateTime)
                .end(localDateTime.plusDays(1))
                .item(Item.builder()
                        .id(1L)
                        .name("item")
                        .build())
                .status(Status.WAITING)
                .booker(User.builder().id(1).name("User").build())
                .build();
        when(bookingService.requestBookingByIdByOwnerBookingOrItem(anyLong(), anyLong()))
                .thenReturn(booking);

        this.mockMvc
                .perform(get("/bookings/1")
                        .contentType(MediaType.APPLICATION_JSON).header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserBookings() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.now();
        Collection<Booking> bookings = new ArrayList<>();
        booking = Booking.builder()
                .id(1)
                .start(localDateTime)
                .end(localDateTime.plusDays(1))
                .item(Item.builder()
                        .id(1L)
                        .name("item")
                        .build())
                .status(Status.WAITING)
                .booker(User.builder().id(1).name("User").build())
                .build();
        bookings.add(booking);
        when(bookingService.getUserBookings(anyLong(), anyString()))
                .thenReturn(bookings);

        this.mockMvc
                .perform(get("/bookings")
                        .contentType(MediaType.APPLICATION_JSON).header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void getItemBookingsForOwner() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.now();
        Collection<Booking> bookings = new ArrayList<>();
        booking = Booking.builder()
                .id(1)
                .start(localDateTime)
                .end(localDateTime.plusDays(1))
                .item(Item.builder()
                        .id(1L)
                        .name("item")
                        .build())
                .status(Status.WAITING)
                .booker(User.builder()
                        .id(1)
                        .name("User").build())
                .build();
        bookings.add(booking);
        when(bookingService.getUserBookings(anyLong(), anyString()))
                .thenReturn(bookings);

        this.mockMvc
                .perform(get("/bookings/owner")
                        .contentType(MediaType.APPLICATION_JSON).header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk());
    }
}
