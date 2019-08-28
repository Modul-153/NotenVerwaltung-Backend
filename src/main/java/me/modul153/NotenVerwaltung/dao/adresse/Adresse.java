package me.modul153.NotenVerwaltung.dao.adresse;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class Adresse implements Serializable {
    private int adressId;
    private String strasse;
    private int nummer;
    private Ort ort;
}
