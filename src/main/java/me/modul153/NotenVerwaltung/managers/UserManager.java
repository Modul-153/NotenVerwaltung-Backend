package me.modul153.NotenVerwaltung.managers;

import me.modul153.NotenVerwaltung.api.AbstractManager;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractUser;
import me.modul153.NotenVerwaltung.data.abstracts.City;
import me.modul153.NotenVerwaltung.data.complex.UserComplex;
import me.modul153.NotenVerwaltung.data.model.User;
import me.modul153.NotenVerwaltung.helper.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class UserManager extends AbstractManager<AbstractUser, User, UserComplex> {
    @Override
    public HashMap<Integer, UserComplex> getAllComplex() {
        HashMap<Integer, UserComplex> result = new HashMap<>();
        Connection conn = SqlHelper.getConnection();
        try {
            ResultSet set = conn.prepareStatement("select user_id, firstname, lastname, username, street, number, city.name as city_name, city.zipcode as zipcode from `user` join city on `user`.city_id = city.city_id order by user_id;").executeQuery();

            while (set.next()) {
                int user_id = set.getInt("user_id");
                result.put(user_id,
                        new UserComplex(
                                user_id,
                                set.getString("firstname"),
                                set.getString("lastname"),
                                set.getString("username"),
                                set.getString("street"),
                                set.getInt("number"),
                                new City(
                                        set.getInt("city_id"),
                                        set.getInt("zipcode"),
                                        set.getString("city_name")
                                )
                        )
                );
            }
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public HashMap<Integer, User> getAllSimple() {
        HashMap<Integer, User> result = new HashMap<>();
        Connection conn = SqlHelper.getConnection();
        try {
            ResultSet set = conn.prepareStatement("select user_id, firstname, lastname, username, street, number,city.city_id as city_id, city.name as city_name, city.zipcode as zipcode from `user` join city on `user`.city_id = city.city_id order by user_id;").executeQuery();
            while (set.next()) {
                int user_id = set.getInt("user_id");
                result.put(user_id,
                        new User(
                                user_id,
                                set.getString("firstname"),
                                set.getString("lastname"),
                                set.getString("username"),
                                set.getString("street"),
                                set.getInt("number"),
                                set.getInt("city_id")
                        )
                );
            }
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public UserComplex getComplex(int key) {
        Connection conn = SqlHelper.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement("select user_id," +
                    "       firstname," +
                    "       lastname," +
                    "       username," +
                    "       street," +
                    "       number," +
                    "       city.city_id as city_id," +
                    "       city.name    as city_name," +
                    "       city.zipcode as zipcode " +
                    "from `user`" +
                    "         join city on `user`.city_id = city.city_id " +
                    "where user_id = ?;");
            statement.setInt(1, key);
            ResultSet set = statement.executeQuery();

            if (set.next()) {
                return new UserComplex(
                        set.getInt("user_id"),
                        set.getString("firstname"),
                        set.getString("lastname"),
                        set.getString("username"),
                        set.getString("street"),
                        set.getInt("number"),
                        new City(
                                set.getInt("city_id"),
                                set.getInt("zipcode"),
                                set.getString("city_name")
                        )
                );
            } else {
                return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public User getSimple(int key) {
        Connection conn = SqlHelper.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement("select user_id, firstname, lastname, username, street, number, city_id " +
                    "from `user`" +
                    "where user_id = ?;");
            statement.setInt(1, key);
            ResultSet set = statement.executeQuery();

            if (set.next()) {
                return new User(
                        set.getInt("user_id"),
                        set.getString("firstname"),
                        set.getString("lastname"),
                        set.getString("username"),
                        set.getString("street"),
                        set.getInt("number"),
                        set.getInt("city_id")
                );
            } else {
                return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean updateComplex(UserComplex complex) {
        Connection conn = SqlHelper.getConnection();
        try {
            PreparedStatement s1 = conn.prepareStatement(
                    "insert into city (city_id, name, zipcode)" +
                            "VALUES (?,?,?)" +
                            "on duplicate KEY UPDATE name=VALUES(name)," +
                            "                        zipcode=VALUES(zipcode);" +
                            ";");
            PreparedStatement s2 = conn.prepareStatement(
                    "insert into user (user_id, firstname, lastname, username, number, street, city_id)" +
                            "values (?,?,?,?,?,?,?)" +
                            "on duplicate key update firstname=VALUES(firstname)," +
                            "                        lastname=VALUES(lastname)," +
                            "                        username=VALUES(username)," +
                            "                        number=VALUES(number)," +
                            "                        street=VALUES(street)," +
                            "                        city_id=VALUES(city_id) " +
                            ";");

            if (complex.getCity() == null) {
                System.out.println("City in user can not be null.");
                return false;
            }

            s1.setInt(1, complex.getCity().getCityId());
            s1.setInt(2, complex.getCity().getZipCode());
            s1.setString(3, complex.getCity().getName());

            s2.setInt(1, complex.getUserId());
            s2.setString(2, complex.getFirstname());
            s2.setString(3, complex.getLastname());
            s2.setString(4, complex.getUsername());
            s2.setInt(5, complex.getNumber());
            s2.setString(6, complex.getStreet());
            s2.setInt(7, complex.getCity().getCityId());
            s2.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean updateSimple(User simple) {
        Connection conn = SqlHelper.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement("insert into user (user_id, firstname, lastname, username, number, street, city_id)\n" +
                    "values (?,?,?,?,?,?,?)\n" +
                    "on duplicate key update firstname=VALUES(firstname),\n" +
                    "                        lastname=VALUES(lastname),\n" +
                    "                        username=VALUES(username),\n" +
                    "                        number=VALUES(number),\n" +
                    "                        street=VALUES(street),\n" +
                    "                        city_id=VALUES(city_id)\n" +
                    ";");

            statement.setInt(1, simple.getUserId());
            statement.setString(2, simple.getFirstname());
            statement.setString(3, simple.getLastname());
            statement.setString(4, simple.getUsername());
            statement.setInt(5, simple.getNumber());
            statement.setString(6, simple.getStreet());
            statement.setInt(7, simple.getCityId());
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
