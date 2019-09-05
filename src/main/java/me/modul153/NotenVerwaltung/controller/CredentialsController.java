package me.modul153.NotenVerwaltung.controller;

import me.modul153.NotenVerwaltung.data.abstracts.Credential;
import me.modul153.NotenVerwaltung.managers.CredentialManager;
import me.modul153.NotenVerwaltung.managers.UserManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/credential")
public class CredentialsController {
    @GetMapping("/get/{id}")
    public Credential get(@PathVariable Integer id) {
        Credential city = CredentialManager.getInstance().getSqlType(id);
        if (city == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "credential with id '" + id + "' not found.");
        }
        return city;
    }

    @PutMapping("/add/")
    public void add(@RequestBody Credential credential) {
        addCredential(credential);
    }

    @PutMapping("/addMultiple/")
    public void addMultiple(@RequestBody Credential[] credentials) {
        for (Credential credential : credentials) {
            addCredential(credential);
        }
    }

    private void addCredential(Credential credential) {
        if (!UserManager.getInstance().contains(credential.getUserId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with id '" + credential.getUserId() + "' not found.");
        }

        CredentialManager.getInstance().add(credential.getUserId(), credential);
    }
}
