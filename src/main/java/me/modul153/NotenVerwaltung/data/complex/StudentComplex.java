package me.modul153.NotenVerwaltung.data.complex;

import lombok.Data;
import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.IComplexType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractStudent;
import me.modul153.NotenVerwaltung.data.model.Student;
import me.modul153.NotenVerwaltung.data.model.User;

@Data
public class StudentComplex extends AbstractStudent implements IComplexType {
    User user;

    public StudentComplex(int studentId, User user) {
        super(studentId);
        this.user = user;
    }

    @Override
    public AbstractionType getType() {
        return AbstractionType.COMPLEX_TYPE;
    }

    @Override
    public Student toSqlType() {
        return new Student(getStudentId(), user.getUserId());
    }
}
