package ru.practicum.shareit.requests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.requests.repository.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@DataJpaTest
public class ItemRequestRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private ItemRequestRepository repository;

    @Test
    void findItemRequestsByRequestor_Id() {
        User requestor = User.builder().name("newName").email("newUser@user.com").build();
        ItemRequest itemRequest = ItemRequest.builder().description("descr").created(LocalDateTime.now()).requestor(requestor).build();
        em.persist(requestor);
        em.persist(itemRequest);
        Collection<ItemRequest> result = repository.findItemRequestsByRequestor_Id(requestor.getId());
        then(result).size().isEqualTo(1);
        then(result).containsExactlyElementsOf(List.of(itemRequest));
    }

    @Test
    void findItemRequestsByRequestor_IdIsNotOrderByCreatedDesc() {
        User requestor = User.builder().name("newName").email("newUser@user.com").build();
        ItemRequest itemRequest = ItemRequest.builder().description("descr").created(LocalDateTime.now()).requestor(requestor).build();
        em.persist(requestor);
        em.persist(itemRequest);
        Collection<ItemRequest> result = repository.findItemRequestsByRequestor_IdIsNotOrderByCreatedDesc(2, PageRequest.of(0, 10));
        then(result).size().isEqualTo(1);
        then(result).containsExactlyElementsOf(List.of(itemRequest));
    }
}
