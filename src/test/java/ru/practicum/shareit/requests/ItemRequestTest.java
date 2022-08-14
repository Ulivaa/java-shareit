package ru.practicum.shareit.requests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.requests.repository.ItemRequestRepository;
import ru.practicum.shareit.requests.service.ItemRequestService;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ItemRequestTest {
    @Autowired
    private ItemRequestService service;

    @MockBean
    private UserService userService;

    @MockBean
    private  ItemRequestRepository itemRequestRepository;

    @Test
    void addItemRequest() {
        User user = User.builder().id(1).name("name").email("user@user.com").build();
        ItemRequest itemRequest = ItemRequest.builder()
                .description("ddddd")
                .requestor(user)
                .created(LocalDateTime.now())
                .build();
        service.addItemRequest(itemRequest, 1);
        verify(itemRequestRepository, times(1)).save(itemRequest);
    }

    @Test
    void getItemRequestByOwner() {
        User user = User.builder().id(1).name("name").email("user@user.com").build();
        when(userService.getUserById(1L)).thenReturn(user);
        service.getItemRequestByOwner(1);
        verify(itemRequestRepository, times(1)).findItemRequestsByRequestor_Id(1L);
    }

    @Test
    void getItemRequestById() {
        User user = User.builder().id(1).name("name").email("user@user.com").build();
        ItemRequest itemRequest = ItemRequest.builder()
                .description("ddddd")
                .requestor(user)
                .created(LocalDateTime.now())
                .build();
        service.addItemRequest(itemRequest, user.getId());
        when(userService.getUserById(1L)).thenReturn(user);
        when(itemRequestRepository.findById(1L)).thenReturn(Optional.ofNullable(itemRequest));
        service.getItemRequestById(1, 1);
        verify(itemRequestRepository, times(1)).findById(1L);
    }

    @Test
    void getAllItemRequest() {
        User user = User.builder().id(1).name("name").email("user@user.com").build();
        ItemRequest itemRequest = ItemRequest.builder()
                .description("ddddd")
                .requestor(user)
                .created(LocalDateTime.now())
                .build();

        when(userService.getUserById(user.getId())).thenReturn(user);
        when(itemRequestRepository.findItemRequestsByRequestor_IdIsNotOrderByCreatedDesc(user.getId(),
                PageRequest.of(1, 20)))
                .thenReturn(List.of(itemRequest));

        var result = service.getAllItemRequest(1, 1, 20);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(itemRequest.getDescription(), result.stream().findFirst().get().getDescription());
    }
}
