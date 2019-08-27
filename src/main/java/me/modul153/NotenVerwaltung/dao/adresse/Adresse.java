package me.modul153.NotenVerwaltung.dao.adresse;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Adresse {
    private int adressId;
    private String strasse;
    private int nummer;
    private Ort ort;
}
