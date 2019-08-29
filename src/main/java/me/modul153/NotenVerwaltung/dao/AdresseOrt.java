package me.modul153.NotenVerwaltung.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.modul153.NotenVerwaltung.dao.adresse.Adresse;
import me.modul153.NotenVerwaltung.dao.adresse.Ort;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class AdresseOrt implements Serializable {
    Adresse adresse;
    Ort ort;

    public boolean valid() {
        return adresse != null && ort != null;
    }
}
