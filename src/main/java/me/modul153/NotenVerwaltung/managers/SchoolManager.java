package me.modul153.NotenVerwaltung.managers;

import me.modul153.NotenVerwaltung.api.AbstractManager;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractSchool;
import me.modul153.NotenVerwaltung.data.abstracts.City;
import me.modul153.NotenVerwaltung.data.complex.SchoolComplex;
import me.modul153.NotenVerwaltung.data.model.School;
import me.modul153.NotenVerwaltung.helper.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class SchoolManager extends AbstractManager<AbstractSchool, School, SchoolComplex> {
    private static SchoolManager schoolManager = null;

    public static SchoolManager getInstance() {
        if (schoolManager == null) {
            schoolManager = new SchoolManager();
        }
        return schoolManager;
    }

    @Override
    public HashMap<Integer, SchoolComplex> getAllComplex() throws SQLException {
        HashMap<Integer, SchoolComplex> result = new HashMap<>();
        Connection conn = SqlHelper.getConnection();
        try {
            ResultSet set = conn.prepareStatement("select school_id, schoolname, street, number,city.name as city_name, city.zipcode as zipcode from `school` join city using(city_id) order by school_id;").executeQuery();

            while (set.next()) {
                int school_id = set.getInt("school_id");
                result.put(school_id,
                        new SchoolComplex(
                                school_id,
                                set.getString("schoolname"),
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
        } finally {
            conn.close();
        }
    }

    @Override
    public HashMap<Integer, School> getAllSimple() throws SQLException {
        HashMap<Integer, School> result = new HashMap<>();
        Connection conn = SqlHelper.getConnection();
        try {
            ResultSet set = conn.prepareStatement("select school_id, schoolname, city_id, street,number from school order by school_id;").executeQuery();
            while (set.next()) {
                int school_id = set.getInt("school_id");
                result.put(school_id,
                        new School(
                                school_id,
                                set.getString("schoolname"),
                                set.getString("street"),
                                set.getInt("number"),
                                set.getInt("city_id")
                        )
                );
            }
            return result;
        } finally {
            conn.close();
        }
    }

    @Override
    public SchoolComplex getComplex(int key) throws SQLException {
        HashMap<Integer, SchoolComplex> result = new HashMap<>();
        Connection conn = SqlHelper.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement("select school_id, schoolname, street, number ,city.name as city_name, city.zipcode as zipcode from `school` join city using(city_id) where school_id = ?;");
            statement.setInt(1, key);
            ResultSet set = statement.executeQuery();

            if (set.next()) {
                return new SchoolComplex(
                        set.getInt("school_id"),
                        set.getString("schoolname"),
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
        } finally {
            conn.close();
        }
    }

    @Override
    public School getSimple(int key) throws SQLException {
        Connection conn = SqlHelper.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement("select school_id, schoolname, city_id,street, number from school where school_id = ?;");
            statement.setInt(1, key);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new School(
                        set.getInt("school_id"),
                        set.getString("schoolname"),
                        set.getString("street"),
                        set.getInt("number"),
                        set.getInt("city_id")
                );
            } else {
                return null;
            }
        } finally {
            conn.close();
        }
    }

    @Override
    public boolean updateComplex(SchoolComplex complex) throws SQLException {
        Connection conn = SqlHelper.getConnection();
        try {
            conn.setAutoCommit(false);
            PreparedStatement s1 = conn.prepareStatement(
                    "insert into city (city_id, name, zipcode)" +
                            "VALUES (?,?,?)" +
                            "on duplicate KEY UPDATE name=VALUES(name)," +
                            "                        zipcode=VALUES(zipcode);" +
                            ";");

            if (complex.getCity() == null) {
                System.out.println("City in school can not be null.");
                return false;
            }

            s1.setInt(1, complex.getCity().getCityId());
            s1.setInt(2, complex.getCity().getZipCode());
            s1.setString(3, complex.getCity().getName());
            s1.executeUpdate();

            PreparedStatement s2 = conn.prepareStatement(
                    "insert into school (school_id, schoolname, number, street, city_id) values (?,?,?,?,?)" +
                            "on duplicate key update " +
                            "schoolname=values(schoolname)," +
                            "number=values(number)," +
                            "street=values(street)," +
                            "city_id=values(city_id);");

            s2.setInt(1, complex.getSchoolId());
            s2.setString(2, complex.getSchoolName());
            s2.setInt(3, complex.getNumber());
            s2.setString(4, complex.getStreet());
            s2.setInt(5, complex.getCity().getCityId());
            s2.executeUpdate();
            conn.commit();
            return true;
        } catch (SQLException ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.close();
        }
    }

    @Override
    public boolean updateSimple(School simple) throws SQLException {
        Connection conn = SqlHelper.getConnection();
        try {
            PreparedStatement s2 = conn.prepareStatement(
                    "insert into school (school_id, schoolname, number, street, city_id) values (?,?,?,?,?)" +
                            "on duplicate key update " +
                            "schoolname=values(schoolname)," +
                            "number=values(number)," +
                            "street=values(street)," +
                            "city_id=values(city_id);");
            s2.setInt(1, simple.getSchoolId());
            s2.setString(2, simple.getSchoolName());
            s2.setInt(3, simple.getNumber());
            s2.setString(4, simple.getStreet());
            s2.setInt(5, simple.getCityId());
            s2.executeUpdate();
            return true;
        } finally {
            conn.close();
        }
    }
}
