package me.modul153.NotenVerwaltung.dao.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.modul153.NotenVerwaltung.dao.adresse.Adresse;
import me.modul153.NotenVerwaltung.managers.AdressManager;
import me.modul153.NotenVerwaltung.managers.OrtManager;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class User implements Serializable {
    private int userId;
    private String name;
    private String nachname;
    private String userName;
    private int adresseId;

    public Adresse getAdresse() {
        return AdressManager.getInstance().getAdresse(adresseId);
    }

    public boolean validAdress() {
        return  (AdressManager.getInstance().getAdresse(adresseId) != null);
    }
}
