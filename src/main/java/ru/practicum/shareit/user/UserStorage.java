package ru.practicum.shareit.user;

import java.util.Collection;
import java.util.Optional;

public interface UserStorage {
    User addUser(User user);

    User updateUser(long userId, User user);

    void deleteUser(long userId);

    Optional<User> getUserById(long userId);

    Collection<User> getAllUsers();

    Optional<User> getUserByEmail(String email);
}
