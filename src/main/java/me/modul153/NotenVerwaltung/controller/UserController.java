package me.modul153.NotenVerwaltung.controller;

import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractUser;
import me.modul153.NotenVerwaltung.data.model.User;
import me.modul153.NotenVerwaltung.data.complex.UserComplex;
import me.modul153.NotenVerwaltung.managers.UserManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @GetMapping("/getUser")
    public User getUser(@RequestParam(value = "userId") Integer id) {
        AbstractUser user = UserManager.getInstance().get(id);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " not found.");
        }

        if (user.getType() == AbstractionType.SQL_TYPE) {
            return (User) user;
        }else if(user.getType() == AbstractionType.COMPLEX_TYPE) {
            return ((UserComplex) user).toSqlType();
        }else {
            return null;
        }
    }
    @GetMapping("/getUserComplex")
    public UserComplex getUserComplex(@RequestParam(value = "userId") Integer id) {
        AbstractUser user = UserManager.getInstance().get(id);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " not found.");
        }

        if (user.getType() == AbstractionType.SQL_TYPE) {
            return ((User) user).toComplexType();
        }else if(user.getType() == AbstractionType.COMPLEX_TYPE) {
            return (UserComplex) user;
        }else {
            return null;
        }
    }

    @PutMapping("/addUser/")
    public void addUser(@RequestBody User user) {
        addAbstractUser(user);
    }

    @PutMapping("/addUserComplex/")
    public void addUserComplex(@RequestBody UserComplex user) {
        addAbstractUser(user);
    }

    @PutMapping("/addUsers/")
    public void addUsers(@Valid @RequestBody User[] users) {
        for (User user : users) {
            addAbstractUser(user);
        }
    }

    @PutMapping("/addComplexUsers/")
    public void addUsersComplex(@RequestBody UserComplex[] users) {
        for (UserComplex user : users) {
            addAbstractUser(user);
        }
    }

    private void addAbstractUser(AbstractUser user) {
        if (!UserManager.getInstance().validate(user)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with id " + user.getUserId() + " not valid.");
        }

        UserManager.getInstance().add(user.getUserId(), user);
    }
}
