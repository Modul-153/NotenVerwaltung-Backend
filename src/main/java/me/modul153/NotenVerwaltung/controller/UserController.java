package me.modul153.NotenVerwaltung.controller;

import me.modul153.NotenVerwaltung.dao.user.User;
import me.modul153.NotenVerwaltung.exceptions.UserBadRequestException;
import me.modul153.NotenVerwaltung.exceptions.UserNotFoundException;
import me.modul153.NotenVerwaltung.managers.UserManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    @GetMapping("/getUser/{userId}")
    public User getUser(@PathVariable(value = "userId") Integer id) {
        User user = UserManager.getInstance().getUser(id);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

    @PutMapping("/addUser/")
    public void addUser(@RequestBody User user) {
        if (user == null) {
            throw new UserBadRequestException();
        }

        UserManager.getInstance().addUser(user);
    }

    @PutMapping("/addUsers/")
    public void addUsers(@Valid @RequestBody User[] users) {
        for (User user : users) {
            UserManager.getInstance().addUser(user);
        }
    }
}
