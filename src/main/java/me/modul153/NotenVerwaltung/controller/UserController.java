package me.modul153.NotenVerwaltung.controller;

import me.modul153.NotenVerwaltung.dao.UserAdresseOrt;
import me.modul153.NotenVerwaltung.dao.user.User;
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
        User user = UserManager.getInstance().getUser(id);
        if (user == null) {
            throw new NotFoundException();
        }
        return user;
    }

    @PutMapping("/addUser/")
    public void addUser(@RequestBody User user) {
        if (user == null) {
            throw new BadRequestException();
        }

        if (user.getAdresse() == null) {
            throw new ForeignKeyNotFoundException();
        }

        if (user.getAdresse().getOrt() == null) {
            throw new ForeignKeyNotFoundException();
        }

        UserManager.getInstance().addUser(user);
    }

    @PutMapping("/addUser/")
    public void addUser(@RequestBody UserAdresseOrt userAdresseOrt) {
        if (userAdresseOrt == null) {
            throw new BadRequestException();
        }

        if (!userAdresseOrt.valid()) {
            throw new BadRequestException();
        }

        OrtManager.getInstance().addOrt(userAdresseOrt.getOrt());
        AdressManager.getInstance().addAdresse(userAdresseOrt.getAdresse());
        UserManager.getInstance().addUser(userAdresseOrt.getUser());
    }

    @PutMapping("/addUsers/")
    public void addUsers(@Valid @RequestBody User[] users) {
        for (User user : users) {
            OrtManager.getInstance().addOrt(user.getAdresse().getOrt());
            AdressManager.getInstance().addAdresse(user.getAdresse());
            UserManager.getInstance().addUser(user);
        }
    }
}
