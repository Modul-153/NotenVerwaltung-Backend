package me.modul153.NotenVerwaltung.data.complex;

import lombok.Data;
import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.IComplexType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractClass;
import me.modul153.NotenVerwaltung.data.model.Class;
import me.modul153.NotenVerwaltung.data.model.School;

@Data
public class ClassComplex extends AbstractClass implements IComplexType {
    School school;

    public ClassComplex(int classId, String name, School school) {
        super(classId, name);
        this.school = school;
    }

    @Override
    public AbstractionType getType() {
        return AbstractionType.COMPLEX_TYPE;
    }

    @Override
    public Class toSqlType() {
        return new Class(getClassId(), getName(), school.getSchoolId());
    }
}
