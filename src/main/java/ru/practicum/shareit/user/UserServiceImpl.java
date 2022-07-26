package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.DuplicateEmailException;
import ru.practicum.shareit.exception.IncorrectParameterException;
import ru.practicum.shareit.exception.UserNotFoundException;

import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(User user) {
        hasParams(user);
        checkValidParams(user);
        userRepository.save(user);
        return user;
    }

    @Override
    public User updateUser(long userId, User user) {
        User updateUser = getUserById(userId);
        checkValidParams(user);
        if (user.getName() != null) {
            updateUser.setName(user.getName());
        }
        if (user.getEmail() != null) {
            updateUser.setEmail(user.getEmail());
        }
        userRepository.save(updateUser);
        return updateUser;
    }

    @Override
    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User getUserById(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(String.format("Пользователь № %d не найден", userId)));
    }

    @Override
    public Collection<User> getAllUsers() {
        return userRepository.findAll();
    }

    private boolean checkValidParams(User user) {
        if (user.getEmail() != null) {
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
        if (userRepository.findByEmailContainingIgnoreCase(userEmail).isEmpty()) {
            return true;
        } else throw new DuplicateEmailException();
    }
}
