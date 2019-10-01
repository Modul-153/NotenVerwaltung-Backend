package me.modul153.NotenVerwaltung.managers.neww;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface IManager<M extends Serializable> {
    HashMap<Integer, M> getAll() throws SQLException;

    M get(int id) throws SQLException;

    void update(M object) throws SQLException;

    void updateAll(List<M> objects) throws SQLException;

    void delete(int key) throws SQLException;

    void deleteMultiple(List<M> keys) throws SQLException;
}
