package me.modul153.NotenVerwaltung.managers;

import me.modul153.NotenVerwaltung.api.AbstractManager;
import me.modul153.NotenVerwaltung.data.abstracts.City;
import me.modul153.NotenVerwaltung.services.Counter;
import net.myplayplanet.services.connection.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CityManager extends AbstractManager<City, City, City> {
    private static CityManager cityManager = null;
    public static CityManager getInstance() {
        if (cityManager == null) {
            cityManager = new CityManager();
        }
        return cityManager;
    }

    @Override
    public City loadIDataObjectComplex(Integer key) {
        try {
            Counter.connectionCounter++;
        PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement("select `zipcode`,`name` from `notenverwaltung`.`city` where `city_id` = ?");
            statement.setInt(1, key);
            ResultSet r = statement.executeQuery();
            if (r.next()) {
                return new City(key, r.getInt("zipcode"), r.getString("name"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean saveIDataObjectComplex(Integer key, City value) {
        try {
            Counter.connectionCounter++;
        PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement(
                    "INSERT INTO `city` (`city_id`, `zipcode`, `name`) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE `zipcode`=?,`name`=?");
            statement.setInt(1, value.getCityId());
            statement.setInt(2, value.getZipCode());
            statement.setString(3, value.getName());
            statement.setInt(4, value.getZipCode());
            statement.setString(5, value.getName());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    @Override
    public String getManagerName() {
        return "city";
    }
}
