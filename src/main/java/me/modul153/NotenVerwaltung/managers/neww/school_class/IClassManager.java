package me.modul153.NotenVerwaltung.managers.neww.school_class;

import me.modul153.NotenVerwaltung.data.Class;
import me.modul153.NotenVerwaltung.data.TeacherClassSubject;
import me.modul153.NotenVerwaltung.data.User;
import me.modul153.NotenVerwaltung.managers.neww.IManager;

import java.sql.SQLException;
import java.util.List;

public interface IClassManager extends IManager<Class> {
    List<User> getMembers(int classId) throws SQLException;
    List<TeacherClassSubject> getTeachers(int classId) throws SQLException;
}
