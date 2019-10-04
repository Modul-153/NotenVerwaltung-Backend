package me.modul153.NotenVerwaltung.managers.user;

import me.modul153.NotenVerwaltung.data.User;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class MockUserManager implements IUserManager {

    @Override
    public HashMap<Integer, User> getAllStudents() throws SQLException {
        return null;
    }

    @Override
    public HashMap<Integer, User> getAllTeachers() throws SQLException {
        return null;
    }

    @Override
    public HashMap<Integer, User> getAllAdministrators() throws SQLException {
        return null;
    }

    @Override
    public User getStudent(int studentId) throws SQLException {
        return null;
    }

    @Override
    public User getTeacher(int teacherId) throws SQLException {
        return null;
    }

    @Override
    public User getAdministrator(int administratorId) throws SQLException {
        return null;
    }

    @Override
    public User getStudent(String name) throws SQLException {
        return null;
    }

    @Override
    public User getTeacher(String name) throws SQLException {
        return null;
    }

    @Override
    public User getAdministrator(String name) throws SQLException {
        return null;
    }

    @Override
    public void toStudent(int userId) throws SQLException {

    }

    @Override
    public void toTeacher(int userId) throws SQLException {

    }

    @Override
    public void toAdministrator(int userId) throws SQLException {

    }

    @Override
    public void removeStudent(int userId) throws SQLException {

    }

    @Override
    public void removeTeacher(int userId) throws SQLException {

    }

    @Override
    public void removeAdministrator(int userId) throws SQLException {

    }

    @Override
    public HashMap<Integer, User> getAll() throws SQLException {
        return null;
    }

    @Override
    public User get(int id) throws SQLException {
        return null;
    }

    @Override
    public void update(User object) throws SQLException {

    }

    @Override
    public void updateAll(List<User> objects) throws SQLException {

    }

    @Override
    public void delete(int key) throws SQLException {

    }

    @Override
    public void deleteMultiple(List<Integer> keys) throws SQLException {

    }
}
