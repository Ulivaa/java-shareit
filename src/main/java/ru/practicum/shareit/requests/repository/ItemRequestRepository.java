package ru.practicum.shareit.requests.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.requests.model.ItemRequest;

import java.util.Collection;
import java.util.List;

public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {
    Collection<ItemRequest> findItemRequestsByRequestor_Id(long userId);

    List<ItemRequest> findItemRequestsByRequestor_IdIsNotOrderByCreatedDesc(long userId, Pageable pageable);
}
