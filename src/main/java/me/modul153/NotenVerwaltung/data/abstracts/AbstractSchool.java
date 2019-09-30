package me.modul153.NotenVerwaltung.data.abstracts;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.modul153.NotenVerwaltung.api.IAbstract;

@AllArgsConstructor
@Data
public abstract class AbstractSchool implements IAbstract {
    int schoolId;
    String schoolName;
    String street;
    int number;
}
