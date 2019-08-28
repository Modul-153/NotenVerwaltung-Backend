package me.modul153.NotenVerwaltung.managers;

import lombok.Getter;
import me.modul153.NotenVerwaltung.dao.adresse.Adresse;
import me.modul153.NotenVerwaltung.dao.adresse.Ort;
import me.modul153.NotenVerwaltung.dao.user.User;
import net.myplayplanet.services.cache.AbstractSaveProvider;
import net.myplayplanet.services.cache.Cache;
import net.myplayplanet.services.connection.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Getter
public class UserManager {
    private Cache<Integer, Ort> ortCache;
    private Cache<Integer, Adresse> adressCache;
    private Cache<Integer, User> userCache;

    private static UserManager userManager = null;
    public static UserManager getInstance() {
        if (userManager == null) {
            userManager = new UserManager();
        }
        return userManager;
    }

    private UserManager() {
        ortCache = new Cache<>("ort-cache", integer -> {
            try {
                PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement("select `zipcode`,`name` from `notenverwaltung`.`ort` where `ort_id` = ?");
                statement.setInt(1, integer);
                ResultSet r = statement.executeQuery();
                if (r.next()) {
                    return new Ort(integer, r.getInt("zipcode"), r.getString("name"));
                } else {
                    return null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }, new AbstractSaveProvider<Integer, Ort>() {
            @Override
            public boolean save(Integer integer, Ort ort) {
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
        });

        adressCache = new Cache<>("adress-cache", integer -> {
            try {
                PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement("select `strasse`,`nummer`,`ort_id` from `notenverwaltung`.`adresse` where `adress_id` = ?");
                statement.setInt(1, integer);
                ResultSet r = statement.executeQuery();
                if (r.next()) {
                    return new Adresse(integer,
                            r.getString("strasse"),
                            r.getInt("nummer"),
                            ortCache.get(r.getInt("ort_id")));
                } else {
                    return null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }, new AbstractSaveProvider<Integer, Adresse>() {
            @Override
            public boolean save(Integer integer, Adresse adresse) {
                try {
                    ortCache.update(integer, adresse.getOrt());

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
        });

        userCache = new Cache<>("adress-cache", integer -> {
            try {
                PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement("select `vorname`,`nachname`,`username`,`geburtsdatum`,`adress_id` from `notenverwaltung`.`user` where `user_id` = ?");
                statement.setInt(1, integer);
                ResultSet r = statement.executeQuery();
                if (r.next()) {
                    return new User(integer,
                            r.getString("vorname"),
                            r.getString("nachname"),
                            r.getString("username"),
                            r.getDate("geburtsdatum"),
                            adressCache.get(r.getInt("adress_id")));
                } else {
                    return null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }, new AbstractSaveProvider<Integer, User>() {
            @Override
            public boolean save(Integer integer, User user) {
                try {
                    adressCache.update(integer, user.getAdresse());

                    PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement(
                            "INSERT INTO `user` (`user_id`, `vorname`, `nachname`, `username`, `geburtsdatum`, `adress_id`) VALUES (?, ?, ?, ?, ?, ?) " +
                                    "ON DUPLICATE KEY UPDATE `vorname`=?,`nachname`=?,`username`=?,`geburtsdatum=?`,`adress_id`=?");
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
            }
        });
    }

    public User getUser(int id) {
        return userCache.get(id);
    }
}
