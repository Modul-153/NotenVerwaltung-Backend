package me.modul153.NotenVerwaltung.data.model;

import lombok.Data;
import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.ISqlType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractTeacher;
import me.modul153.NotenVerwaltung.data.complex.TeacherComplex;

@Data
public class Teacher extends AbstractTeacher implements ISqlType {
    int userId;

    public Teacher(int studentId, int userId) {
        super(studentId);
        this.userId = userId;
    }

    @Override
    public AbstractionType getType() {
        return AbstractionType.SQL_TYPE;
    }

    @Override
    public TeacherComplex toComplexType() {
        return new TeacherComplex(getTeacherId(), UserManager.getInstance().getSqlType(getUserId()));
    }
}
