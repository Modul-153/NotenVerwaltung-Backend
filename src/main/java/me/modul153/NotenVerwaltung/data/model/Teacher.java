package me.modul153.NotenVerwaltung.data.model;

import lombok.Data;
import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.ISqlType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractTeacher;
import me.modul153.NotenVerwaltung.data.complex.TeacherComplex;
import me.modul153.NotenVerwaltung.managers.UserManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;

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
        try {
            return new TeacherComplex(getTeacherId(), UserManager.getInstance().getSimple(getUserId()));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error loading user with id " + getUserId() + "\n" + e.getMessage());
        }
    }
}
