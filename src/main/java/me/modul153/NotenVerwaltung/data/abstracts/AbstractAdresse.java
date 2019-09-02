package me.modul153.NotenVerwaltung.data.abstracts;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.modul153.NotenVerwaltung.api.IAbstract;

@Data
@AllArgsConstructor
public abstract class AbstractAdresse implements IAbstract {
    private int adressId;
    private String strasse;
    private int nummer;
}