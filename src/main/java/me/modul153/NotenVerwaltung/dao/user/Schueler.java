package me.modul153.NotenVerwaltung.dao.user;

import me.modul153.NotenVerwaltung.dao.adresse.Adresse;

public class Schueler extends User {
    public Schueler(int userId, String name, String nachname, String userName, Adresse adresse) {
        super(userId, name, nachname, userName, adresse);
    }
}
