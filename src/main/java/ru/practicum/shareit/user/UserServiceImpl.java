package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.DuplicateEmailException;
import ru.practicum.shareit.exception.IncorrectParameterException;
import ru.practicum.shareit.exception.UserNotFoundException;

import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserServiceImpl(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public User addUser(User user) {
        hasParams(user);
        checkValidParams(user);
        checkDuplicateEmail(user.getEmail());
        return userStorage.addUser(user);
    }

    @Override
    public User updateUser(long userId, User user) {
        getUserById(userId);
        checkValidParams(user);
        return userStorage.updateUser(userId, user);
    }

    @Override
    public void deleteUser(long userId) {
        userStorage.deleteUser(userId);
    }

    @Override
    public User getUserById(long userId) {
        return userStorage.getUserById(userId).orElseThrow(() -> new UserNotFoundException(String.format("Пользователь № %d не найден", userId)));
    }

    @Override
    public Collection<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    private boolean checkValidParams(User user) {
        if (user.getEmail() != null) {
            checkDuplicateEmail(user.getEmail());
            if (!user.getEmail().contains("@") || !user.getEmail().contains(".")) {
                throw new IncorrectParameterException("email");
            }
        }
        return true;
    }

    private boolean hasParams(User user) {
        if (user.getEmail() == null) {
            throw new IncorrectParameterException("email");
        }
        if (user.getName() == null) {
            throw new IncorrectParameterException("name");
        }
        return true;
    }

    private boolean checkDuplicateEmail(String userEmail) {
        if (userStorage.getUserByEmail(userEmail).isEmpty()) {
            return true;
        } else throw new DuplicateEmailException();
    }
}
