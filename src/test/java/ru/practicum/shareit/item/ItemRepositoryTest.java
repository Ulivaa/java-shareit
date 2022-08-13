package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@DataJpaTest
public class ItemRepositoryTest {


    @Autowired
    private TestEntityManager em;

    @Autowired
    private ItemRepository repository;

    @Test
    void findItemsByOwnerId() {
        User user = User.builder().name("newName").email("newUser@user.com").build();
        Item item = Item.builder()
                .name("Дрель")
                .description("description")
                .available(true).owner(user)
                .build();
        em.persist(user);
        em.persist(item);
        Collection<Item> result = repository.findItemsByOwnerId(user.getId());
        then(result).size().isEqualTo(1);
        then(result).containsExactlyElementsOf(List.of(item));
    }

    @Test
    void findAllByRequestId() {
        User owner = User.builder().name("newName").email("newUser@user.com").build();
        User requestor = User.builder().name("requestor").email("requestor@user.com").build();
        ItemRequest itemRequest = ItemRequest.builder().description("descr").created(LocalDateTime.now()).requestor(requestor).build();
        Item item = Item.builder()
                .name("Дрель")
                .description("description")
                .available(true)
                .owner(owner)
                .request(itemRequest)
                .build();
        em.persist(owner);
        em.persist(requestor);
        em.persist(itemRequest);
        em.persist(item);
        Collection<Item> result = repository.findAllByRequestId(itemRequest.getId());
        then(result).size().isEqualTo(1);
        then(result).containsExactlyElementsOf(List.of(item));
    }

    @Test
    void searchByNameAndDescription() {
        User owner = User.builder().name("newName").email("newUser@user.com").build();
        Item item = Item.builder()
                .name("Дрель")
                .description("description")
                .available(true)
                .owner(owner)
                .build();
        em.persist(owner);
        em.persist(item);
        Collection<Item> result = repository.searchByNameAndDescription("Дрель");
        then(result).size().isEqualTo(1);
        then(result).containsExactlyElementsOf(List.of(item));
    }


}
