package me.modul153.NotenVerwaltung.managers;

import me.modul153.NotenVerwaltung.dao.AdresseOrt;
import me.modul153.NotenVerwaltung.dao.UserAdresseOrt;
import me.modul153.NotenVerwaltung.dao.user.User;
import net.myplayplanet.services.cache.AbstractSaveProvider;
import net.myplayplanet.services.cache.Cache;
import net.myplayplanet.services.connection.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManager {
    private Cache<Integer, UserAdresseOrt> userCache;

    private static UserManager userManager = null;
    public static UserManager getInstance() {
        if (userManager == null) {
            userManager = new UserManager();
        }
        return userManager;
    }

    private UserManager() {
        userCache = new Cache<>("user-cache", integer -> {
            try {
                PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement("select `vorname`,`nachname`,`username`,`adress_id` from `notenverwaltung`.`user` where `user_id` = ?");
                statement.setInt(1, integer);
                ResultSet r = statement.executeQuery();
                if (r.next()) {
                    User user = new User(integer,
                            r.getString("vorname"),
                            r.getString("nachname"),
                            r.getString("username"),
                            r.getInt("adress_id"));
                    return new UserAdresseOrt(user, null, null);
                } else {
                    return null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }, new AbstractSaveProvider<Integer, UserAdresseOrt>() {
            @Override
            public boolean save(Integer integer, UserAdresseOrt user) {
                return saveUser(user);
            }
        });
    }

    public User getUser(int id) {
        return userCache.get(id).getUser();
    }

    public boolean addUser(User user) {
        userCache.update(user.getUserId(), new UserAdresseOrt(user, null, null));
        return true;
    }

    public void clearCache() {
        userCache.clearCache();
    }

    protected boolean saveUser(UserAdresseOrt userAdresseOrt) {
        User user = userAdresseOrt.getUser();

        if (AdressManager.getInstance().getAdresse(user.getAdresseId()) == null) {
            if (userAdresseOrt.getAdresse() != null) {
                if (!AdressManager.getInstance().saveAdresse(new AdresseOrt(userAdresseOrt.getAdresse(), userAdresseOrt.getOrt()))) {
                    return false;
                }
            }else {
                return false;
            }
        }

        try {
            PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement(
                    "INSERT INTO `user` (`user_id`, `vorname`, `nachname`, `username`, `adress_id`) VALUES (?, ?, ?, ?, ?) " +
                            "ON DUPLICATE KEY UPDATE `vorname`=?,`nachname`=?,`username`=?,`adress_id`=?");
            statement.setInt(1, user.getUserId());
            statement.setString(2, user.getName());
            statement.setString(3, user.getNachname());
            statement.setString(4, user.getUserName());
            statement.setInt(5, user.getAdresse().getAdressId());

            statement.setString(6, user.getName());
            statement.setString(7, user.getNachname());
            statement.setString(8, user.getUserName());
            statement.setInt(9, user.getAdresse().getAdressId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
