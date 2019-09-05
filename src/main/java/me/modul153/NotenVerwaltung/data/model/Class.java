package me.modul153.NotenVerwaltung.data.model;

import lombok.Data;
import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.IComplexType;
import me.modul153.NotenVerwaltung.api.ISqlType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractClass;
import me.modul153.NotenVerwaltung.data.complex.ClassComplex;
import me.modul153.NotenVerwaltung.managers.SchoolManager;

@Data
public class Class extends AbstractClass implements ISqlType {
    int schoolId;

    public Class(int classId, String name, int schoolId) {
        super(classId, name);
        this.schoolId = schoolId;
    }

    @Override
    public AbstractionType getType() {
        return AbstractionType.SQL_TYPE;
    }

    @Override
    public ClassComplex toComplexType() {
        return new ClassComplex(getClassId(),getName(), SchoolManager.getInstance().getSqlType(getSchoolId()));
    }
}
