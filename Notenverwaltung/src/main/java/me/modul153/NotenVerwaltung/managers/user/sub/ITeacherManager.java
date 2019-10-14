package me.modul153.NotenVerwaltung.managers.user.sub;

import me.modul153.NotenVerwaltung.data.Teacher;

import java.sql.SQLException;
import java.util.HashMap;

public interface ITeacherManager {
    HashMap<Integer, Teacher> getAllTeachers() throws SQLException;

    HashMap<Integer, Teacher> getMultipleTeachers(int... keys) throws SQLException;

    default Teacher getTeacher(int id) throws SQLException {
        HashMap<Integer, Teacher> r = this.getMultipleTeachers(id);
        if (r.size() != 1) {
            return null;
        }
        return r.get(id);
    }

    default void updateTeacher(Teacher object) throws SQLException {
        updateMultipleTeachers(object);
    }

    void updateMultipleTeachers(Teacher... objects) throws SQLException;

    default void deleteTeacher(int key) throws SQLException {
        deleteMultipleTeachers(key);
    }

    void deleteMultipleTeachers(int... keys) throws SQLException;

    void removeTeachers(int... id);

    default void removeTeacher(int id) {
        removeTeachers(id);
    }
}
