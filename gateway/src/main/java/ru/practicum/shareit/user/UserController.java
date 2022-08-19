//package ru.practicum.shareit.user;
//
//import org.apache.catalina.User;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Collection;
//
//@Controller
//public class UserController {
//
//    @PostMapping
//    public User addUser(@RequestBody User user) {
//        return userService.addUser(user);
//    }
//
//    @PatchMapping("/{userId}")
//    public User updateUser(@PathVariable long userId, @RequestBody User user) {
//        return userService.updateUser(userId, user);
//    }
//
//    @GetMapping("/{userId}")
//    public User getUserById(@PathVariable long userId) {
//        return userService.getUserById(userId);
//    }
//
//    @GetMapping
//    public Collection<User> getAllUsers() {
//        return userService.getAllUsers();
//    }
//
//    @DeleteMapping("/{userId}")
//    public void deleteUser(@PathVariable long userId) {
//        userService.deleteUser(userId);
//    }
//}
