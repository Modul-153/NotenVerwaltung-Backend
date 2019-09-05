package me.modul153.NotenVerwaltung.managers;

import me.modul153.NotenVerwaltung.api.AbstractManager;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractUser;
import me.modul153.NotenVerwaltung.data.complex.UserComplex;
import me.modul153.NotenVerwaltung.data.model.User;
import me.modul153.NotenVerwaltung.services.SqlSetup;
import net.myplayplanet.services.cache.Cache;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class UserManager extends AbstractManager<AbstractUser, User, UserComplex> {

    private static UserManager userManager = null;
    private Cache<Integer, ArrayList<Integer>> listCache;

    public UserManager() {
        super();
        listCache = new net.myplayplanet.services.cache.Cache<>("list-user-cache", -1L, k -> {
            ArrayList<Integer> result = new ArrayList<>();

            try {
                PreparedStatement statement = SqlSetup.getStatement("select `user_id` from `notenverwaltung`.`user`");
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
    public HashMap<Integer, AbstractUser> loadAllObjects() {
        HashMap<Integer, AbstractUser> map = new HashMap<>();
        try {
            PreparedStatement statement = SqlSetup.getStatement("select `user_id`,`firstname`,`lastname`,`username`,`adress_id` from `notenverwaltung`.`user`");
            ResultSet r = statement.executeQuery();

            while (r.next()) {
                int user_id = r.getInt("user_id");
                User user = new User(user_id,
                        r.getString("firstname"),
                        r.getString("lastname"),
                        r.getString("username"),
                        r.getInt("adress_id"));
                map.put(user_id, user);
            }
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static UserManager getInstance() {
        if (userManager == null) {
            userManager = new UserManager();
        }
        return userManager;
    }

    @Override
    public void add(Integer key, AbstractUser value) {
        super.add(key, value);
        listCache.get(0).add(key);
    }

    public AbstractUser getUser(String username) {
        for (Integer integer : listCache.get(0)) {
            AbstractUser abstractUser = this.get(integer);
            if (abstractUser.getUsername().equalsIgnoreCase(username)) {
                return abstractUser;
            }
        }
        return null;
    }

    @Override
    public User loadIDataObjectComplex(Integer key) {
        try {
            PreparedStatement statement = SqlSetup.getStatement("select `firstname`,`lastname`,`username`,`adress_id` from `notenverwaltung`.`user` where `user_id` = ?");
            statement.setInt(1, key);
            ResultSet r = statement.executeQuery();
            if (r.next()) {
                User user = new User(key,
                        r.getString("firstname"),
                        r.getString("lastname"),
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
            adresseId = user.getAdressId();

            if (AdressManager.getInstance().getSqlType(user.getAdressId()) == null) {
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
            PreparedStatement statement = SqlSetup.getStatement(
                    "INSERT INTO `user` (`user_id`, `firstname`, `lastname`, `username`, `adress_id`) VALUES (?, ?, ?, ?, ?) " +
                            "ON DUPLICATE KEY UPDATE `firstname`=?,`lastname`=?,`username`=?,`adress_id`=?");
            statement.setInt(1, value.getUserId());
            statement.setString(2, value.getFirstname());
            statement.setString(3, value.getLastname());
            statement.setString(4, value.getUsername());
            statement.setInt(5, adresseId);

            statement.setString(6, value.getFirstname());
            statement.setString(7, value.getLastname());
            statement.setString(8, value.getUsername());
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
                return AdressManager.getInstance().contains(((User) value).getAdressId());
            default:
                return false;
        }
    }

    @Override
    public String getManagerName() {
        return "user";
    }
}
