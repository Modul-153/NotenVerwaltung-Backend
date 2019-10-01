package me.modul153.NotenVerwaltung.managers.neww.user;

import me.modul153.NotenVerwaltung.data.Student;
import me.modul153.NotenVerwaltung.data.Teacher;
import me.modul153.NotenVerwaltung.data.User;
import me.modul153.NotenVerwaltung.managers.neww.IManager;

import java.sql.SQLException;

public interface IUserManager extends IManager<User> {
    Student getStudent(int userId) throws SQLException;

    Teacher getTeacher(int userId) throws SQLException;

    Teacher getAdministrator(int userId) throws SQLException;

    default boolean isTeacher(int userId) throws SQLException {
        return getTeacher(userId) != null;
    }

    default boolean isStudent(int userId) throws SQLException {
        return getStudent(userId) != null;
    }

    default boolean isAdministrator(int userId) throws SQLException {
        return getAdministrator(userId) != null;
    }


}

