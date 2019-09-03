package me.modul153.NotenVerwaltung.data.complex;

import lombok.Data;
import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.IComplexType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractTeacher;
import me.modul153.NotenVerwaltung.data.model.Teacher;
import me.modul153.NotenVerwaltung.data.model.User;

@Data
public class TeacherComplex extends AbstractTeacher implements IComplexType {
    User user;

    public TeacherComplex(int studentId, User user) {
        super(studentId);
        this.user = user;
    }

    @Override
    public AbstractionType getType() {
        return AbstractionType.COMPLEX_TYPE;
    }

    @Override
    public Teacher toSqlType() {
        return new Teacher(getTeacherId(), user.getUserId());
    }
}
