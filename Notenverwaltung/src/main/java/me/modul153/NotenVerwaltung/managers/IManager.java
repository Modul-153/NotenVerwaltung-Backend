package me.modul153.NotenVerwaltung.managers;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;

public interface IManager<M extends Serializable> {
    HashMap<Integer, M> getAll() throws SQLException;

    HashMap<Integer, M> getMultiple(int... keys) throws SQLException;

    default M get(int id) throws SQLException {
        HashMap<Integer, M> r = this.getMultiple(id);
        if (r.size() != 1) {
            return null;
        }
        return r.get(id);
    }

    default void update(M object) throws SQLException {
        updateMultiple(object);
    }

    void updateMultiple(M... objects) throws SQLException;

    default void delete(int key) throws SQLException {
        deleteMultiple(key);
    }

    void deleteMultiple(int... keys) throws SQLException;
}
