package me.modul153.NotenVerwaltung.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.modul153.NotenVerwaltung.dao.adresse.Adresse;
import me.modul153.NotenVerwaltung.dao.adresse.Ort;

@Data
@AllArgsConstructor
public class AdresseOrt {
    Adresse adresse;
    Ort ort;
}
