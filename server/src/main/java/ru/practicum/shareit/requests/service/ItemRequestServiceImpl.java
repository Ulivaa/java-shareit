package ru.practicum.shareit.requests.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.IncorrectParameterException;
import ru.practicum.shareit.exception.ItemRequestNotFoundException;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.requests.repository.ItemRequestRepository;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestRepository itemRequestRepository;
    private final UserService userService;

    public ItemRequestServiceImpl(ItemRequestRepository itemRequestRepository, UserService userService) {
        this.itemRequestRepository = itemRequestRepository;
        this.userService = userService;
    }

    @Override
    public ItemRequest addItemRequest(ItemRequest itemRequest, long userId) {
        isValidItemRequest(itemRequest);
        itemRequest.setRequestor(userService.getUserById(userId));
        itemRequest.setCreated(LocalDateTime.now());
        itemRequestRepository.save(itemRequest);
        return itemRequest;
    }

    @Override
    public Collection<ItemRequest> getItemRequestByOwner(long userId) {
        userService.getUserById(userId);
        return itemRequestRepository.findItemRequestsByRequestor_Id(userId);
    }

    public ItemRequest getItemRequestById(long userId, long requestId) {
        userService.getUserById(userId);
        return itemRequestRepository.findById(requestId).orElseThrow(() -> new ItemRequestNotFoundException("Такого запроса не существует"));
    }


    @Override
    public Collection<ItemRequest> getAllItemRequest(long userId, int from, int size) {
        if (size <= 0) {
            throw new IncorrectParameterException("size");
        }
        if (from < 0) {
            throw new IncorrectParameterException("from");
        }
        userService.getUserById(userId);
        return itemRequestRepository.findItemRequestsByRequestor_IdIsNotOrderByCreatedDesc(userId, PageRequest.of(from, size));
    }

    private boolean isValidItemRequest(ItemRequest itemRequest) {

        if (itemRequest.getDescription() == null) {
            throw new IncorrectParameterException("description");
        }
        return true;
    }
}
