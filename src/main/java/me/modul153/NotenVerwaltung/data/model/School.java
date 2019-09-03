package me.modul153.NotenVerwaltung.data.model;

import lombok.Data;
import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.ISqlType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractSchool;
import me.modul153.NotenVerwaltung.data.complex.SchoolComplex;
import me.modul153.NotenVerwaltung.managers.AdressManager;

@Data
public class School extends AbstractSchool implements ISqlType {
    int adressId;

    public School(int schoolId, String schoolName, int adressId) {
        super(schoolId, schoolName);
        this.adressId = adressId;
    }

    @Override
    public AbstractionType getType() {
        return AbstractionType.SQL_TYPE;
    }

    @Override
    public SchoolComplex toComplexType() {
        return new SchoolComplex(getSchoolId(), getSchoolName(), AdressManager.getInstance().getComplexType(getAdressId()));
    }
}
