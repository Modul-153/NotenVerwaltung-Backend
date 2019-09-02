package me.modul153.NotenVerwaltung.controller;

import me.modul153.NotenVerwaltung.data.dao.CredentialsDao;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/authenticate")
    public String authenticate(@RequestBody CredentialsDao credentials) {

    }
}
