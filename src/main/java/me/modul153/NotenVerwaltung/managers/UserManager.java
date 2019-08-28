package me.modul153.NotenVerwaltung.managers;

import lombok.Getter;
import me.modul153.NotenVerwaltung.dao.adresse.Adresse;
import me.modul153.NotenVerwaltung.dao.adresse.Ort;
import me.modul153.NotenVerwaltung.dao.user.User;
import me.modul153.NotenVerwaltung.helpers.SqlHelper;
import net.myplayplanet.services.cache.AbstractSaveProvider;
import net.myplayplanet.services.cache.Cache;
import net.myplayplanet.services.connection.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
                return SqlHelper.saveOrt(ort);
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
                return SqlHelper.saveAdresse(adresse, true);
            }
        });

        userCache = new Cache<>("user-cache", integer -> {
            try {
                System.out.println("getUser user from sql " + integer);
                PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement("select `vorname`,`nachname`,`username`,`geburtsdatum`,`adress_id` from `notenverwaltung`.`user` where `user_id` = ?");
                statement.setInt(1, integer);
                ResultSet r = statement.executeQuery();
                if (r.next()) {
                    return new User(integer,
                            r.getString("vorname"),
                            r.getString("nachname"),
                            r.getString("username"),
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
                return SqlHelper.saveUser(user, true);
            }
        });
    }

    public User getUser(int id) {
        return userCache.get(id);
    }
    public boolean addUser(User user) {
        userCache.update(user.getUserId(), user);
        return true;
    }
}
