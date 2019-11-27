package me.modul153.NotenVerwaltung.managers.city;

import me.modul153.NotenVerwaltung.data.City;
import me.modul153.NotenVerwaltung.managers.IManager;

import java.sql.SQLException;
import java.util.HashMap;

public interface ICityManager extends IManager<City> {
    default City get(String name) throws SQLException {
        HashMap<Integer, City> s =  getMultiple(name);
        if (s.size() != 1) {
            return null;
        }
        return s.get(0);
    }
    HashMap<Integer, City> getMultiple(String... names) throws SQLException;
}
