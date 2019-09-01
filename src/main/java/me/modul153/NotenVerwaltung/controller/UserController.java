package me.modul153.NotenVerwaltung.controller;

import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractUser;
import me.modul153.NotenVerwaltung.data.model.User;
import me.modul153.NotenVerwaltung.data.response.UserResponse;
import me.modul153.NotenVerwaltung.managers.UserManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    @GetMapping("/getUser")
    public User getUser(@RequestParam(value = "userId") Integer id) {
        AbstractUser user = UserManager.getInstance().get(id);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " not found.");
        }

        if (user.getType() == AbstractionType.BUISSNES_OBJECT) {
            return (User) user;
        }else if(user.getType() == AbstractionType.RESPONSE_TYPE) {
            return ((UserResponse) user).toBusinessObject();
        }else {
            return null;
        }
    }
    @GetMapping("/getUserComplex")
    public UserResponse getUserComplex(@RequestParam(value = "userId") Integer id) {
        AbstractUser user = UserManager.getInstance().get(id);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " not found.");
        }

        if (user.getType() == AbstractionType.BUISSNES_OBJECT) {
            return ((User) user).toResponse();
        }else if(user.getType() == AbstractionType.RESPONSE_TYPE) {
            return (UserResponse) user;
        }else {
            return null;
        }
    }

    @PutMapping("/addUser/")
    public void addUser(@RequestBody User user) {
        addAbstractUser(user);
    }

    @PutMapping("/addUserComplex/")
    public void addUserComplex(@RequestBody UserResponse user) {
        addAbstractUser(user);
    }

    @PutMapping("/addUsers/")
    public void addUsers(@Valid @RequestBody User[] users) {
        for (User user : users) {
            addAbstractUser(user);
        }
    }

    @PutMapping("/addComplexUsers/")
    public void addUsersComplex(@RequestBody UserResponse[] users) {
        for (UserResponse user : users) {
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
