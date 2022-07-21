package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();

    private static long id = 0;

    public User addUser(User user) {
        user.setId(getId());
        users.put(user.getId(), user);
        return user;
    }

    public User updateUser(long userId, User user) {
        User updateUser = users.get(userId);
        if (user.getName() != null) {
            updateUser.setName(user.getName());
        }
        if (user.getEmail() != null) {
            updateUser.setEmail(user.getEmail());
        }
        users.put(updateUser.getId(), updateUser);
        return updateUser;
    }

    public void deleteUser(long userId) {
        if (users.containsKey(userId)) {
            users.remove(userId);
        }
    }

    public Optional<User> getUserById(long userId) {
        return Optional.ofNullable(users.get(userId));
    }

    @Override
    public Collection<User> getAllUsers() {
        return users.values();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        for (User user : users.values()) {
            if (user.getEmail().equals(email)) {
                return Optional.ofNullable(user);
            }
        }
        return Optional.empty();
    }

    private long getId() {
        return ++id;
    }

    // Возможно еще понадобится.
    // Пришлось заменить на вариант выше,
    // потому что в тестах при удалении юзера с id = 2 следующего юзера хотят с id = 3
//    private long getId() {
//        long lastId = users.values()
//                .stream()
//                .mapToLong(User::getId)
//                .max()
//                .orElse(0);
//        return lastId + 1;
//    }
}
