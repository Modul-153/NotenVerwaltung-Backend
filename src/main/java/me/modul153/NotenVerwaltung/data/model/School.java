package me.modul153.NotenVerwaltung.data.model;

import lombok.Data;
import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractSchool;

@Data
public class School extends AbstractSchool {
    int adressId;

    public School(int schoolId, String schoolName, int adressId) {
        super(schoolId, schoolName);
        this.adressId = adressId;
    }

    @Override
    public AbstractionType getType() {
        return AbstractionType.SQL_TYPE;
    }
}
