package me.modul153.NotenVerwaltung.controller;

import me.modul153.NotenVerwaltung.data.model.User;
import me.modul153.NotenVerwaltung.data.response.UserResponse;
import me.modul153.NotenVerwaltung.exceptions.BadRequestException;
import me.modul153.NotenVerwaltung.exceptions.ForeignKeyNotFoundException;
import me.modul153.NotenVerwaltung.exceptions.NotFoundException;
import me.modul153.NotenVerwaltung.managers.AdressManager;
import me.modul153.NotenVerwaltung.managers.OrtManager;
import me.modul153.NotenVerwaltung.managers.UserManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    @GetMapping("/getUser/{userId}")
    public User getUser(@PathVariable(value = "userId") Integer id) {
        User user = UserManager.getInstance().getBuissnesObject(id);
        if (user == null) {
            throw new NotFoundException();
        }
        return user;
    }

    @PutMapping("/addUser/")
    public void addUser(@RequestBody User user) {
        if (!UserManager.getInstance().validate(user)) {
            throw new BadRequestException();
        }

        UserManager.getInstance().add(user.getUserId(), user);
    }

    @PutMapping("/addUsers/")
    public void addUsers(@Valid @RequestBody User[] users) {
        for (User user : users) {
            if (!UserManager.getInstance().validate(user)) {
                throw new BadRequestException();
            }

            UserManager.getInstance().add(user.getUserId(), user);
        }
    }

    @PutMapping("/addComplexUser/")
    public void addUser(@RequestBody UserResponse user) {
        if (!UserManager.getInstance().validate(user)) {
            throw new BadRequestException();
        }

        UserManager.getInstance().add(user.getUserId(), user);
    }


    @PutMapping("/addComplexUsers/")
    public void addUsers(@RequestBody UserResponse[] users) {
        for (UserResponse user : users) {
            if (!UserManager.getInstance().validate(user)) {
                throw new BadRequestException();
            }
            UserManager.getInstance().add(user.getUserId(), user);
        }
    }
}
