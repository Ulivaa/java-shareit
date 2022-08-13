package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.user.controller.UserController;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    User user;

    @Test
    public void addUser() throws Exception {
        String json = "{\"name\":\"user\", \"email\":\"user@user.com\"}";
        user = User.builder().id(1).name("name").email("user@user.com").build();
        when(userService.addUser(any()))
                .thenReturn(user);

        this.mockMvc
                .perform(post("/users")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("user@user.com")));
    }


    @Test
    public void updateUser() throws Exception {
        String json = "{\"name\":\"update\", \"email\":\"update@mail.com\"}";
        this.mockMvc
                .perform(patch("/users/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserById() throws Exception {
        user = User.builder().id(1).name("update").email("update@mail.com").build();

        when(userService.getUserById(1))
                .thenReturn(user);

        this.mockMvc
                .perform(get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("update@mail.com")));
    }

    @Test
    public void getAllUsers() throws Exception {
        user = User.builder().id(1).name("update").email("update@mail.com").build();
        Collection<User> users = new ArrayList<>();
        users.add(user);
        when(userService.getAllUsers())
                .thenReturn(users);

        this.mockMvc
                .perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
