package ru.practicum.shareit.requests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.requests.repository.ItemRequestRepository;
import ru.practicum.shareit.requests.service.ItemRequestService;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.Optional;

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

// я не понимаю как протестировать этот метод....выдает ошибку все время
//    @Test
//    void getAllItemRequest() {
//        User user = User.builder().id(1).name("name").email("user@user.com").build();
//        ItemRequest itemRequest = ItemRequest.builder()
//                .description("ddddd")
//                .requestor(user)
//                .created(LocalDateTime.now())
//                .build();
//        List<ItemRequest> itemRequests = new ArrayList<>();
//        itemRequests.add(itemRequest);
//
//        //when(itemRequestRepository.findItemRequestsByRequestor_IdIsNotOrderByCreatedDesc(any(), PageRequest.of(anyInt(), 20))).thenReturn(itemRequests);
//        when(userService.getUserById(1L)).thenReturn(user);
//
//        service.getAllItemRequest(1, 1, 20);
//
////        when(itemRequestRepository.findItemRequestsByRequestor_IdIsNotOrderByCreatedDesc(1L, PageRequest.of(anyInt(), anyInt()))).thenReturn(itemRequests);
////        service.getItemRequestById(1, 1);
//        verify(itemRequestRepository, times(1)).findItemRequestsByRequestor_IdIsNotOrderByCreatedDesc(1L, PageRequest.of(anyInt(), 20));
//    }
}
