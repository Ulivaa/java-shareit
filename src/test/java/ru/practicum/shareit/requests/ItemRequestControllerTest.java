package ru.practicum.shareit.requests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.requests.controller.ItemRequestController;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.requests.service.ItemRequestService;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemRequestController.class)
public class ItemRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemRequestService itemRequestService;

    @MockBean
    private ItemService itemService;

    ItemRequest itemRequest;

    @Test
    public void addItemRequest() throws Exception {
        String json = "{\"description\": \"Хотел бы воспользоваться щёткой для обуви\"}";
        itemRequest = ItemRequest.builder()
                .id(1)
                .description("Хотел бы воспользоваться щёткой для обуви")
                .requestor(User.builder().id(1).name("User").build())
                .created(LocalDateTime.now())
                .build();

        when(itemRequestService.addItemRequest(any(), anyLong()))
                .thenReturn(itemRequest);

        this.mockMvc
                .perform(post("/requests")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON).header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void getItemRequestByOwner() throws Exception {
        Collection<ItemRequest> itemRequests = new ArrayList<>();
        itemRequest = ItemRequest.builder()
                .id(1)
                .description("Хотел бы воспользоваться щёткой для обуви")
                .requestor(User.builder().id(1).name("User").build())
                .created(LocalDateTime.now())
                .build();
        itemRequests.add(itemRequest);
        when(itemRequestService.getItemRequestByOwner(anyLong()))
                .thenReturn(itemRequests);

        this.mockMvc
                .perform(get("/requests")
                        .contentType(MediaType.APPLICATION_JSON).header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void getItemRequestById() throws Exception {
        itemRequest = ItemRequest.builder()
                .id(1)
                .description("Хотел бы воспользоваться щёткой для обуви")
                .requestor(User.builder().id(1).name("User").build())
                .created(LocalDateTime.now())
                .build();
        when(itemRequestService.getItemRequestById(anyLong(), anyLong()))
                .thenReturn(itemRequest);

        this.mockMvc
                .perform(get("/requests/1")
                        .contentType(MediaType.APPLICATION_JSON).header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void getAllItemRequest() throws Exception {
        Collection<ItemRequest> itemRequests = new ArrayList<>();

        itemRequest = ItemRequest.builder()
                .id(1)
                .description("Хотел бы воспользоваться щёткой для обуви")
                .requestor(User.builder().id(1).name("User").build())
                .created(LocalDateTime.now())
                .build();
        itemRequests.add(itemRequest);
        when(itemRequestService.getAllItemRequest(anyLong(), anyInt(), anyInt()))
                .thenReturn(itemRequests);

        this.mockMvc
                .perform(get("/requests")
                        .contentType(MediaType.APPLICATION_JSON).header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk());
    }

}
