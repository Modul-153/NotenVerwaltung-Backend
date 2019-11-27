package me.modul153.NotenVerwaltung.managers.user;

import me.modul153.NotenVerwaltung.data.User;
import me.modul153.NotenVerwaltung.managers.IManager;
import me.modul153.NotenVerwaltung.managers.user.sub.IAdministratorManager;
import me.modul153.NotenVerwaltung.managers.user.sub.IStudentManager;
import me.modul153.NotenVerwaltung.managers.user.sub.ITeacherManager;

import java.sql.SQLException;

public interface IUserManager extends IManager<User> {
    void toStudent(int userId) throws SQLException;

    void toTeacher(int userId) throws SQLException;

    void toAdministrator(int userId) throws SQLException;
}

