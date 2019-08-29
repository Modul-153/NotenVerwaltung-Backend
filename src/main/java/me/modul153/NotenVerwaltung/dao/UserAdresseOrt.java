package me.modul153.NotenVerwaltung.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.modul153.NotenVerwaltung.dao.adresse.Adresse;
import me.modul153.NotenVerwaltung.dao.adresse.Ort;
import me.modul153.NotenVerwaltung.dao.user.User;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UserAdresseOrt implements Serializable {
    User user;
    Adresse adresse;
    Ort ort;

    public boolean valid() {
        return user != null && adresse != null && ort != null;
    }
}
