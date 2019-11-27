package me.modul153.NotenVerwaltung.managers.subject;

import me.modul153.NotenVerwaltung.data.Subject;

import java.sql.SQLException;
import java.util.HashMap;

public class MockSubjectManager implements ISubjectManager {

    @Override
    public Subject get(String name) throws SQLException {
        return null;
    }

    @Override
    public HashMap<Integer, Subject> getAll() throws SQLException {
        return null;
    }

    @Override
    public HashMap<Integer, Subject> getMultiple(int... keys) throws SQLException {
        return null;
    }

    @Override
    public void updateMultiple(Subject... objects) throws SQLException {

    }

    @Override
    public void deleteMultiple(int... keys) throws SQLException {

    }
}
