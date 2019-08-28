package me.modul153.NotenVerwaltung.controller;

import me.modul153.NotenVerwaltung.dao.user.User;
import me.modul153.NotenVerwaltung.exceptions.UserNotFoundException;
import me.modul153.NotenVerwaltung.managers.UserManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @GetMapping("/get/{id}")
    public User getUser(@PathVariable(value = "id") Integer id) {
        User user = UserManager.getInstance().getUser(id);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }
}
