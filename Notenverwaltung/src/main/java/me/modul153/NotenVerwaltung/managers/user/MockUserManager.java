package me.modul153.NotenVerwaltung.managers.user;

import me.modul153.NotenVerwaltung.data.Administrator;
import me.modul153.NotenVerwaltung.data.Student;
import me.modul153.NotenVerwaltung.data.Teacher;
import me.modul153.NotenVerwaltung.data.User;

import java.sql.SQLException;
import java.util.HashMap;

public class MockUserManager implements IUserManager {

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
    public HashMap<Integer, User> getAll() throws SQLException {
        return null;
    }

    @Override
    public HashMap<Integer, User> getMultiple(int... keys) throws SQLException {
        return null;
    }

    @Override
    public void updateMultiple(User... objects) throws SQLException {

    }

    @Override
    public void deleteMultiple(int... keys) throws SQLException {

    }
}
