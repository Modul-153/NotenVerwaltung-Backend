package me.modul153.NotenVerwaltung.managers.school;

import me.modul153.NotenVerwaltung.data.School;
import me.modul153.NotenVerwaltung.managers.IManager;

import java.sql.SQLException;
import java.util.HashMap;


public interface ISchoolManager extends IManager<School> {
    default School get(String name) throws SQLException {
        HashMap<Integer, School> s =  getMultiple(name);
        if (s.size() != 1) {
            return null;
        }
        return s.get(0);
    }
    HashMap<Integer, School> getMultiple(String... names) throws SQLException;
}
