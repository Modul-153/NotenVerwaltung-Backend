package me.modul153.NotenVerwaltung.dao.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.modul153.NotenVerwaltung.dao.adresse.Adresse;

@Data
@AllArgsConstructor
public abstract class User {
    private int userId;
    private String name;
    private String nachname;
    private String userName;
    private Adresse adresse;
}
