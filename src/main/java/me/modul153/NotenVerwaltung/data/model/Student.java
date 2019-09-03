package me.modul153.NotenVerwaltung.data.model;

import lombok.Data;
import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.ISqlType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractStudent;
import me.modul153.NotenVerwaltung.data.complex.StudentComplex;
import me.modul153.NotenVerwaltung.managers.UserManager;

@Data
public class Student extends AbstractStudent implements ISqlType {
    int userId;

    public Student(int studentId, int userId) {
        super(studentId);
        this.userId = userId;
    }

    @Override
    public AbstractionType getType() {
        return AbstractionType.SQL_TYPE;
    }

    @Override
    public StudentComplex toComplexType() {
        return new StudentComplex(getStudentId(), UserManager.getInstance().getSqlType(getUserId()));
    }
}
