package me.modul153.NotenVerwaltung.managers.city;

import me.modul153.NotenVerwaltung.NotenVerwaltung;
import me.modul153.NotenVerwaltung.data.City;
import me.modul153.NotenVerwaltung.helper.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class SqlCityManager implements ICityManager {
    @Override
    public HashMap<Integer, City> getAll() throws SQLException {
        Connection connection = NotenVerwaltung.getConnection();

        ResultSet set = connection.prepareStatement("select city_id, name, zipcode from city").executeQuery();
        return handleResultSetOfMultiple(set);
    }

    @Override
    public HashMap<Integer, City> getMultiple(int... keys) throws SQLException {

        if (keys.length == 0) {
            System.out.println("u cant get 0 Objects...");
            return null;
        }

        Connection connection = NotenVerwaltung.getConnection();
        PreparedStatement statement = connection.prepareStatement("select city_id, name, zipcode from city where " + SqlHelper.buildMultipleDelete("city_id", keys.length));
        for (int i = 0; i < keys.length; i++) {
            statement.setInt(i + 1, keys[i]);
        }
        ResultSet set = statement.executeQuery();
        return handleResultSetOfMultiple(set);
    }

    @Override
    public void updateMultiple(City... objects) throws SQLException {

        if (objects.length == 0) {
            System.out.println("yo can't update 0 objects.");
            return;
        }

        Connection connection = NotenVerwaltung.getConnection();

        PreparedStatement statement = connection.prepareStatement("insert into city (city_id, name, zipcode) values " + SqlHelper.buildInsertMultipleString(objects.length, 3) +
                " on duplicate key update name=values(name),zipcode=values(zipcode);");

        int i = 1;
        for (City object : objects) {
            statement.setInt(i++, object.getCityId());
            statement.setString(i++, object.getName());
            statement.setInt(i++, object.getZipcode());
        }
        statement.executeQuery();
    }

    @Override
    public void deleteMultiple(int... keys) throws SQLException {
        if (keys.length == 0) {
            System.out.println("you can't delete 0 Objects...");
            return;
        }

        Connection connection = NotenVerwaltung.getConnection();

        PreparedStatement statement = connection.prepareStatement("delete from city where " + SqlHelper.buildMultipleDelete("city.city_id", keys.length));
        for (int i = 0; i < keys.length; i++) {
            statement.setInt(i + 1, keys[i]);
        }
    }

    @Override
    public HashMap<Integer, City> getMultiple(String... names) throws SQLException {

        if (names.length == 0) {
            System.out.println("you can't get 0 Objects...");
            return null;
        }

        Connection connection = NotenVerwaltung.getConnection();
        PreparedStatement statement = connection.prepareStatement("select city_id, name, zipcode from city where " + SqlHelper.buildMultipleDelete("name", names.length));
        for (int i = 0; i < names.length; i++) {
            statement.setString(i + 1, names[i]);
        }
        ResultSet set = statement.executeQuery();
        return handleResultSetOfMultiple(set);
    }

    private HashMap<Integer, City> handleResultSetOfMultiple(ResultSet set) throws SQLException {
        HashMap<Integer, City> result = new HashMap<>();
        while (set.next()) {
            int cityId = set.getInt("city_id");
            result.put(cityId, new City(cityId, set.getString("name"), set.getInt("zipcode")));
        }
        return result;
    }
}
