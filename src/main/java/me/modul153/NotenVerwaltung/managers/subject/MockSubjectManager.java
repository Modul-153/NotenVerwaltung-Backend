package me.modul153.NotenVerwaltung.managers.subject;

import me.modul153.NotenVerwaltung.data.Subject;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

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
    public Subject get(int id) throws SQLException {
        return null;
    }

    @Override
    public void update(Subject object) throws SQLException {

    }

    @Override
    public void updateAll(List<Subject> objects) throws SQLException {

    }

    @Override
    public void delete(int key) throws SQLException {

    }

    @Override
    public void deleteMultiple(List<Integer> keys) throws SQLException {

    }
}
