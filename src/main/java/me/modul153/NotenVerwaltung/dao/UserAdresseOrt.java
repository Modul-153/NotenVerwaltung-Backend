package me.modul153.NotenVerwaltung.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.modul153.NotenVerwaltung.dao.adresse.Adresse;
import me.modul153.NotenVerwaltung.dao.adresse.Ort;
import me.modul153.NotenVerwaltung.dao.user.User;
@Data
@AllArgsConstructor
public class UserAdresseOrt {
    User user;
    Adresse adresse;
    Ort ort;
}
