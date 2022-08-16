package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService service;

    @MockBean
    private UserRepository repository;

    @Test
    void addUser() {
        User user = User.builder().name("name").email("user@user.com").build();
        service.addUser(user);
        verify(repository, times(1)).save(user);
    }

    @Test
    void updateUser() {
        User user = User.builder().name("newName").email("newUser@user.com").build();
        service.addUser(user);
        verify(repository, times(1)).save(user);
    }

    @Test
    void deleteUser() {
        service.deleteUser(1);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void getUserById() {
        User user = User.builder().name("newName").email("newUser@user.com").build();
        service.addUser(user);
        when(repository.findById(1L)).thenReturn(Optional.of(user));
        service.getUserById(1);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void getAllUsers() {
        User user = User.builder().id(1).name("newName").email("newUser@user.com").build();
        List<User> users = new ArrayList<>();
        users.add(user);
        when(repository.findAll()).thenReturn(users);
        service.getAllUsers();
        verify(repository, times(1)).findAll();
    }
}
