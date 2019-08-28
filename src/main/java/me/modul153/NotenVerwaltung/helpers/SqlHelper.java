package me.modul153.NotenVerwaltung.helpers;

import me.modul153.NotenVerwaltung.dao.adresse.Adresse;
import me.modul153.NotenVerwaltung.dao.adresse.Ort;
import me.modul153.NotenVerwaltung.dao.user.User;
import net.myplayplanet.services.connection.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SqlHelper {
    public static boolean saveOrt(Ort ort) {
        try {
            PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement(
                    "INSERT INTO `ort` (`ort_id`, `zipcode`, `name`) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE `zipcode`=?,`name`=?");
            statement.setInt(1, ort.getOrtId());
            statement.setInt(2, ort.getZipCode());
            statement.setString(3, ort.getName());
            statement.setInt(4, ort.getZipCode());
            statement.setString(5, ort.getName());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static void saveAdresse(Adresse adresse) {
        saveAdresse(adresse, false);
    }

    public static boolean saveAdresse(Adresse adresse, boolean saveSub) {
        try {
            if (saveSub) {
                saveOrt(adresse.getOrt());
            }

            PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement(
                    "INSERT INTO `adresse` (`adress_id`, `strasse`, `nummer`, `ort_id`) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE `strasse`=?,`nummer`=?,`ort_id`=?");
            statement.setInt(1, adresse.getAdressId());
            statement.setString(2, adresse.getStrasse());
            statement.setInt(3, adresse.getNummer());
            statement.setInt(4, adresse.getOrt().getOrtId());

            statement.setString(5, adresse.getStrasse());
            statement.setInt(6, adresse.getNummer());
            statement.setInt(7, adresse.getOrt().getOrtId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static void saveUser(User user) {
        saveUser(user, false);
    }

    public static boolean   saveUser(User user, boolean saveSub) {
        try {
            System.out.println("update user in sql " + user.getUserId());
            if (saveSub) {
                saveAdresse(user.getAdresse(), true);
            }

            PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement(
                    "INSERT INTO `user` (`user_id`, `vorname`, `nachname`, `username`, `geburtsdatum`, `adress_id`) VALUES (?, ?, ?, ?, ?, ?) " +
                            "ON DUPLICATE KEY UPDATE `vorname`=?,`nachname`=?,`username`=?,`geburtsdatum`=?,`adress_id`=?");
            statement.setInt(1, user.getUserId());
            statement.setString(2, user.getName());
            statement.setString(3, user.getNachname());
            statement.setString(4, user.getUserName());
            statement.setTimestamp(5, new Timestamp(user.getBirthday().getTime()));
            statement.setInt(6, user.getAdresse().getAdressId());

            statement.setString(7, user.getName());
            statement.setString(8, user.getNachname());
            statement.setString(9, user.getUserName());
            statement.setTimestamp(10, new Timestamp(user.getBirthday().getTime()));
            statement.setInt(11, user.getAdresse().getAdressId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        //todo implement
    }
}
