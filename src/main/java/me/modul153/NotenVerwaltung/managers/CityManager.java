package me.modul153.NotenVerwaltung.managers;

import me.modul153.NotenVerwaltung.api.AbstractManager;
import me.modul153.NotenVerwaltung.data.abstracts.City;
import me.modul153.NotenVerwaltung.helper.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class CityManager extends AbstractManager<City, City, City> {
    private static CityManager cityManager = null;

    public static CityManager getInstance() {
        if (cityManager == null) {
            cityManager = new CityManager();
        }
        return cityManager;
    }

    @Override
    public HashMap<Integer, City> getAllComplex() throws SQLException {
        HashMap<Integer, City> map = new HashMap<>();
        Connection conn = SqlHelper.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement("select `city_id`,`zipcode`,`name` from `notenverwaltung`.`city`");

            ResultSet set = statement.executeQuery();

            while (set.next()) {
                int key = set.getInt("city_id");
                map.put(key, new City(key, set.getInt("zipcode"), set.getString("name")));
            }
            return map;
        }finally {
            conn.close();
        }
    }

    @Override
    public HashMap<Integer, City> getAllSimple()  throws SQLException {
        return getAllComplex();
    }

    @Override
    public City getComplex(int key)  throws SQLException{
        Connection conn = SqlHelper.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement("select `zipcode`,`name` from `notenverwaltung`.`city` where `city_id` = ?");
            statement.setInt(1, key);
            ResultSet r = statement.executeQuery();
            if (r.next()) {
                return new City(key, r.getInt("zipcode"), r.getString("name"));
            } else {
                return null;
            }
        }finally {
            conn.close();
        }
    }

    @Override
    public City getSimple(int key) throws SQLException {
        return getComplex(key);
    }

    @Override
    public boolean updateComplex(City value)  throws SQLException {
        Connection conn = SqlHelper.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO `city` (`city_id`, `zipcode`, `name`) VALUES (?, ?, ?) " +
                            "ON DUPLICATE KEY UPDATE `zipcode`=VALUES(zipcode),`name`=VALUES(name)");
            statement.setInt(1, value.getCityId());
            statement.setInt(2, value.getZipCode());
            statement.setString(3, value.getName());
            statement.executeUpdate();
            return true;
        }finally {
            conn.close();
        }
    }

    @Override
    public boolean updateSimple(City simple) throws SQLException {
        return updateComplex(simple);
    }
}
