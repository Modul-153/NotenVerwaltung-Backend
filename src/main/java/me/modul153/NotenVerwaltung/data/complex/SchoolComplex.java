package me.modul153.NotenVerwaltung.data.complex;

import lombok.Data;
import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.IComplexType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractSchool;
import me.modul153.NotenVerwaltung.data.model.School;

@Data
public class SchoolComplex extends AbstractSchool implements IComplexType {
    AdressComplex adress;

    public SchoolComplex(int schoolId, String schoolName, AdressComplex adress) {
        super(schoolId, schoolName);
        this.adress = adress;
    }

    @Override
    public AbstractionType getType() {
        return AbstractionType.COMPLEX_TYPE;
    }

    @Override
    public School toSqlType() {
        return new School(getSchoolId(), getSchoolName(), getAdress().getAdressId());
    }
}
