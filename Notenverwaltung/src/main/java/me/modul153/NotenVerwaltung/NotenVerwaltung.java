package me.modul153.NotenVerwaltung;

import me.modul153.NotenVerwaltung.managers.city.ICityManager;
import me.modul153.NotenVerwaltung.managers.city.MockCityManager;
import me.modul153.NotenVerwaltung.managers.school.ISchoolManager;
import me.modul153.NotenVerwaltung.managers.school.MockSchoolManager;
import me.modul153.NotenVerwaltung.managers.subject.ISubjectManager;
import me.modul153.NotenVerwaltung.managers.subject.MockSubjectManager;
import me.modul153.NotenVerwaltung.managers.user.IUserManager;
import me.modul153.NotenVerwaltung.managers.user.MockUserManager;
import net.myplayplanet.services.connection.ConnectionManager;

import java.sql.Connection;

public class NotenVerwaltung {
    public static boolean useMock = false;

    public static Connection getConnection() {
        return ConnectionManager.getInstance().getMySQLConnection();
    }
    ICityManager cityManager;
    ISchoolManager schoolManager;
    IUserManager userManager;
    ISubjectManager subjectManager;

    public NotenVerwaltung() {
        if (useMock) {
            loadMockManagers();
        }else {
            loadNormalManagers();
        }
    }



    private void loadNormalManagers() {


    }

    private void loadMockManagers() {
        cityManager = new MockCityManager();
        schoolManager = new MockSchoolManager();
        userManager = new MockUserManager();
        subjectManager = new MockSubjectManager();
    }
}
