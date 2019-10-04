package me.modul153.NotenVerwaltung.managers.user;

import me.modul153.NotenVerwaltung.data.User;
import me.modul153.NotenVerwaltung.managers.IManager;

import java.sql.SQLException;
import java.util.HashMap;

public interface IUserManager extends IManager<User> {

    HashMap<Integer, User> getAllStudents() throws SQLException;

    HashMap<Integer, User> getAllTeachers() throws SQLException;

    HashMap<Integer, User> getAllAdministrators() throws SQLException;

    User getStudent(int studentId) throws SQLException;

    User getTeacher(int teacherId) throws SQLException;

    User getAdministrator(int administratorId) throws SQLException;

    User getStudent(String name) throws SQLException;

    User getTeacher(String name) throws SQLException;

    User getAdministrator(String name) throws SQLException;

    default boolean isTeacher(int userId) throws SQLException {
        return getTeacher(userId) != null;
    }

    default boolean isStudent(int userId) throws SQLException {
        return getStudent(userId) != null;
    }

    default boolean isAdministrator(int userId) throws SQLException {
        return getAdministrator(userId) != null;
    }

    void toStudent(int userId) throws SQLException;

    void toTeacher(int userId) throws SQLException;

    void toAdministrator(int userId) throws SQLException;

    /**
     * should only remove the user from the student list, not the user list.
     */
    void removeStudent(int userId) throws SQLException;

    /**
     * should only remove the user from the student list, not the user list.
     */
    void removeTeacher(int userId) throws SQLException;

    /**
     * should only remove the user from the student list, not the user list.
     */
    void removeAdministrator(int userId) throws SQLException;

}

