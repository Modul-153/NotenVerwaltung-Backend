package me.modul153.NotenVerwaltung.dao.adresse;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.modul153.NotenVerwaltung.managers.OrtManager;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class Adresse implements Serializable {
    private int adressId;
    private String strasse;
    private int nummer;
    private int ortId;

    public Ort getOrt() {
        return OrtManager.getInstance().getOrt(ortId);
    }

    public boolean validOrt() {
        return  (OrtManager.getInstance().getOrt(ortId) != null);
    }
}
