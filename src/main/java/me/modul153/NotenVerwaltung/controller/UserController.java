package me.modul153.NotenVerwaltung.controller;

import me.modul153.NotenVerwaltung.dao.adresse.Adresse;
import me.modul153.NotenVerwaltung.dao.adresse.Ort;
import me.modul153.NotenVerwaltung.dao.user.Schueler;
import me.modul153.NotenVerwaltung.dao.user.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @GetMapping("/get")
    public User getUser() {
        Ort dorf = new Ort(1, 5000, "Dorf");
        Adresse hauptstrasse = new Adresse(1, "Hauptstrasse", 10, dorf);
        return new Schueler(1, "peter", "Hans", "hans.peter", hauptstrasse);
    }
}
