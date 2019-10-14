package me.modul153.NotenVerwaltung.managers.user.sub;

import me.modul153.NotenVerwaltung.data.Student;

import java.sql.SQLException;
import java.util.HashMap;

public interface IStudentManager {
    HashMap<Integer, Student> getAllStudents() throws SQLException;

    HashMap<Integer, Student> getMultipleStudents(int... keys) throws SQLException;

    default Student getStudent(int id) throws SQLException {
        HashMap<Integer, Student> r = this.getMultipleStudents(id);
        if (r.size() != 1) {
            return null;
        }
        return r.get(id);
    }

    default void updateStudent(Student object) throws SQLException {
        updateMultipleStudents(object);
    }

    void updateMultipleStudents(Student... objects) throws SQLException;

    default void deleteStudent(int key) throws SQLException {
        deleteMultipleStudents(key);
    }

    void deleteMultipleStudents(int... keys) throws SQLException;

    void removeStudents(int... id);

    default void removeStudent(int id) {
        removeStudents(id);
    }

}
