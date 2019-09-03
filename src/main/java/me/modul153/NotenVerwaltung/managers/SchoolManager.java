package me.modul153.NotenVerwaltung.managers;

import me.modul153.NotenVerwaltung.api.AbstractManager;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractSchool;
import me.modul153.NotenVerwaltung.data.complex.SchoolComplex;
import me.modul153.NotenVerwaltung.data.model.School;
import net.myplayplanet.services.connection.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SchoolManager extends AbstractManager<AbstractSchool, School, SchoolComplex> {
    private static SchoolManager schoolManager = null;
    public static SchoolManager getInstance() {
        if (schoolManager == null) {
            schoolManager = new SchoolManager();
        }
        return schoolManager;
    }

    @Override
    public AbstractSchool loadIDataObjectComplex(Integer key) {
        PreparedStatement statement = null;
        try {
            statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement("select `schoolname`,`adress_id` from `school` where `school_id`=?");
            statement.setInt(1, key);

            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new School(key, set.getString("schoolname"),set.getInt("adress_id"));
            }else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean saveIDataObjectComplex(Integer key, AbstractSchool value) {
        int adressId;

        if (value instanceof School) {
            School student = (School) value;
            adressId = student.getAdressId();

            if (AdressManager.getInstance().getSqlType(student.getAdressId()) == null) {
                System.out.println("could not save object with id " + key + ", user not found!");
                return false;
            }
        } else if (value instanceof SchoolComplex) {
            SchoolComplex school = (SchoolComplex) value;

            if (school.getAdress() == null) {
                System.out.println("could not save object with id " + key + ", user not found!");
                return false;
            } else if (AdressManager.getInstance().getSqlType(school.getAdress().getAdressId()) == null) {
                AdressManager.getInstance().save(key, school.getAdress());
            }

            adressId = school.getAdress().getAdressId();
        } else {
            System.out.println("invalid object in " + getManagerName() + "-cache found.");
            return false;
        }

        try {
            PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement(
                    "INSERT INTO `school` (`school_id`, `schoolname`,`adress_id`) VALUES (?, ?, ?) " +
                            "ON DUPLICATE KEY UPDATE `schoolname`=?, `adress_id`=?");
            statement.setInt(1, key);
            statement.setString(2, value.getSchoolName());
            statement.setInt(3, adressId);

            statement.setString(4, value.getSchoolName());
            statement.setInt(5, adressId);


            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getManagerName() {
        return "school";
    }
}
