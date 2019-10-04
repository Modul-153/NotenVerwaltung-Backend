package me.modul153.NotenVerwaltung.managers.subject;

import me.modul153.NotenVerwaltung.data.Subject;
import me.modul153.NotenVerwaltung.managers.IManager;

import java.sql.SQLException;

public interface ISubjectManager extends IManager<Subject> {
    Subject get(String name) throws SQLException;
}
