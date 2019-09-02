package me.modul153.NotenVerwaltung.managers;

import me.modul153.NotenVerwaltung.api.AbstractManager;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractUser;
import me.modul153.NotenVerwaltung.data.complex.UserComplex;
import me.modul153.NotenVerwaltung.data.model.User;
import net.myplayplanet.services.cache.Cache;
import net.myplayplanet.services.connection.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserManager extends AbstractManager<AbstractUser, User, UserComplex> {

    Cache<Integer, ArrayList<Integer>> listCache;

    public UserManager() {
        super();
        listCache = new net.myplayplanet.services.cache.Cache<>("list-user-cache", -1L, k -> {
            ArrayList<Integer> result = new ArrayList<>();

            try {
                PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement("select `user_id` from `notenverwaltung`.`user`");
                ResultSet set = statement.executeQuery();

                while (set.next()) {
                    result.add(set.getInt("user_id"));
                }
                return result;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    @Override
    public void add(Integer key, AbstractUser value) {
        super.add(key, value);
        listCache.get(0).add(key);
    }

    public AbstractUser getUser(String username) {
        for (Integer integer : listCache.get(0)) {
            AbstractUser abstractUser = this.get(integer);
            if (abstractUser.getUserName().equalsIgnoreCase(username)) {
                return abstractUser;
            }
        }
        return null;
    }

    private static UserManager userManager = null;

    public static UserManager getInstance() {
        if (userManager == null) {
            userManager = new UserManager();
        }
        return userManager;
    }

    @Override
    public User loadIDataObjectComplex(Integer key) {
        try {
            PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement("select `vorname`,`nachname`,`username`,`adress_id` from `notenverwaltung`.`user` where `user_id` = ?");
            statement.setInt(1, key);
            ResultSet r = statement.executeQuery();
            if (r.next()) {
                User user = new User(key,
                        r.getString("vorname"),
                        r.getString("nachname"),
                        r.getString("username"),
                        r.getInt("adress_id"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean saveIDataObjectComplex(Integer key, AbstractUser value) {
        int adresseId;

        if (value instanceof User) {
            User user = (User) value;
            adresseId = user.getAdresseId();

            if (AdressManager.getInstance().getSqlType(user.getAdresseId()) == null) {
                System.out.println("could not save object with id " + key + ", adress not found!");
                return false;
            }
        } else if (value instanceof UserComplex) {
            UserComplex user = (UserComplex) value;

            if (user.getAdresse() == null) {
                System.out.println("could not save object with id " + key + ", adress not found!");
                return false;
            } else if (AdressManager.getInstance().getSqlType(user.getAdresse().getAdressId()) == null) {
                AdressManager.getInstance().save(key, user.getAdresse());
            }
            adresseId = user.getAdresse().getAdressId();
        } else {
            System.out.println("invalid object in " + getManagerName() + "-cache found.");
            return false;
        }

        try {
            PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement(
                    "INSERT INTO `user` (`user_id`, `vorname`, `nachname`, `username`, `adress_id`) VALUES (?, ?, ?, ?, ?) " +
                            "ON DUPLICATE KEY UPDATE `vorname`=?,`nachname`=?,`username`=?,`adress_id`=?");
            statement.setInt(1, value.getUserId());
            statement.setString(2, value.getName());
            statement.setString(3, value.getNachname());
            statement.setString(4, value.getUserName());
            statement.setInt(5, adresseId);

            statement.setString(6, value.getName());
            statement.setString(7, value.getNachname());
            statement.setString(8, value.getUserName());
            statement.setInt(9, adresseId);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean validate(AbstractUser value) {
        switch (value.getType()) {
            case COMPLEX_TYPE:
                return ((UserComplex) value).getAdresse() != null;
            case SQL_TYPE:
                return AdressManager.getInstance().contains(((User) value).getAdresseId());
            default:
                return false;
        }
    }



    @Override
    public String getManagerName() {
        return "user";
    }
}
