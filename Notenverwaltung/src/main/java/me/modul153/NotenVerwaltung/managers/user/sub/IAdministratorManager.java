package me.modul153.NotenVerwaltung.managers.user.sub;
import me.modul153.NotenVerwaltung.data.Administrator;

import java.sql.SQLException;
import java.util.HashMap;

public interface IAdministratorManager {
    HashMap<Integer, Administrator> getAllAdministrators() throws SQLException;

    HashMap<Integer, Administrator> getMultipleAdministrators(int... keys) throws SQLException;

    default Administrator getAdministrator(int id) throws SQLException {
        HashMap<Integer, Administrator> r = this.getMultipleAdministrators(id);
        if (r.size() != 1) {
            return null;
        }
        return r.get(id);
    }

    default void updateAdministrator(Administrator object) throws SQLException {
        updateMultipleAdministrators(object);
    }

    void updateMultipleAdministrators(Administrator... objects) throws SQLException;

    default void deleteAdministrator(int key) throws SQLException {
        deleteMultipleAdministrators(key);
    }

    void deleteMultipleAdministrators(int... keys) throws SQLException;

    void removeAdministrators(int... id);

    default void removeAdministrator(int id) {
        removeAdministrators(id);
    }

}
