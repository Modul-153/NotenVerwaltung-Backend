package me.modul153.NotenVerwaltung.managers;

import me.modul153.NotenVerwaltung.api.AbstractManager;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractTeacher;
import me.modul153.NotenVerwaltung.data.complex.TeacherComplex;
import me.modul153.NotenVerwaltung.data.model.Teacher;
import me.modul153.NotenVerwaltung.data.model.User;
import me.modul153.NotenVerwaltung.helper.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class TeacherManager extends AbstractManager<AbstractTeacher, Teacher, TeacherComplex> {
    private static TeacherManager teacherManager = null;

    public static TeacherManager getInstance() {
        if (teacherManager == null) {
            teacherManager = new TeacherManager();
        }
        return teacherManager;
    }

    @Override
    public HashMap<Integer, TeacherComplex> getAllComplex() throws SQLException {

        Connection conn = SqlHelper.getConnection();
        try {
            HashMap<Integer, TeacherComplex> result = new HashMap<>();
            ResultSet set = conn.prepareStatement(
                    "select teacher_id,user_id, firstname, lastname, username, number, street, city_id " +
                            "from teacher " +
                            "         join user using(user_id) " +
                            "order by teacher_id;").executeQuery();
            while (set.next()) {
                int teacherId = set.getInt("teacher_id");
                result.put(teacherId,
                        new TeacherComplex(
                                teacherId,
                                new User(
                                        set.getInt("user_id"),
                                        set.getString("firstname"),
                                        set.getString("lastname"),
                                        set.getString("username"),
                                        set.getString("street"),
                                        set.getInt("number"),
                                        set.getInt("city_id")
                                )
                        )
                );
            }
            return result;
        }finally {
            conn.close();
        }
    }

    @Override
    public HashMap<Integer, Teacher> getAllSimple() throws SQLException {

        Connection conn = SqlHelper.getConnection();
        try {
            HashMap<Integer, Teacher> result = new HashMap<>();
            ResultSet set = conn.prepareStatement(
                    "select teacher_id, user_id from teacher order by teacher_id;").executeQuery();

            while (set.next()) {
                int teacherId = set.getInt("teacher_id");
                result.put(teacherId,
                        new Teacher(
                                teacherId,
                                set.getInt("user_id")
                        )
                );
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }finally {
            conn.close();
        }
    }

    @Override
    public TeacherComplex getComplex(int key) throws SQLException {
        Connection conn = SqlHelper.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(
                    "select user_id, firstname, lastname, username, number, street, city_id " +
                            "from teacher " +
                            "         join user u on teacher.user_id = u.user_id " +
                            "where teacher_id = ?;");
            statement.setInt(1, key);
            ResultSet set = statement.executeQuery();

            if (set.next()) {
                return new TeacherComplex(
                        key,
                        new User(
                                set.getInt("user_id"),
                                set.getString("firstname"),
                                set.getString("lastname"),
                                set.getString("username"),
                                set.getString("street"),
                                set.getInt("number"),
                                set.getInt("city_id")

                        )
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            conn.close();
        }
    }

    @Override
    public Teacher getSimple(int key) throws SQLException {

        Connection conn = SqlHelper.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(
                    "select teacher_id, user_id from teacher where teacher_id = ?;");
            statement.setInt(1, key);
            ResultSet set = statement.executeQuery();

            if (set.next()) {
                int teacherId = set.getInt("teacher_id");
                return new Teacher(
                        teacherId,
                        set.getInt("user_id")
                );
            } else {
                return null;
            }
        }finally {
            conn.close();
        }
    }

    @Override
    public boolean updateComplex(TeacherComplex complex) throws SQLException {
        Connection conn = SqlHelper.getConnection();
        try {
            conn.setAutoCommit(false);
            PreparedStatement s2 = conn.prepareStatement("insert into user (user_id, firstname, lastname, username, number, street, city_id)" +
                    "values (?,?,?,?,?,?,?)\n" +
                    "on duplicate key update firstname=VALUES(firstname)," +
                    "                        lastname=VALUES(lastname)," +
                    "                        username=VALUES(username)," +
                    "                        number=VALUES(number)," +
                    "                        street=VALUES(street)," +
                    "                        city_id=VALUES(city_id)" +
                    ";");

            s2.setInt(1, complex.getUser().getUserId());
            s2.setString(2, complex.getUser().getFirstname());
            s2.setString(3, complex.getUser().getLastname());
            s2.setString(4, complex.getUser().getUsername());
            s2.setInt(5, complex.getUser().getNumber());
            s2.setString(6, complex.getUser().getStreet());
            s2.setInt(7, complex.getUser().getCityId());
            s2.executeUpdate();

            PreparedStatement s1 = conn.prepareStatement("insert into teacher (teacher_id, user_id) " +
                    "VALUES (?, ?) " +
                    "on duplicate key update user_id=VALUES(user_id)");

            s1.setInt(1, complex.getTeacherId());
            s1.setInt(2, complex.getUser().getUserId());
            conn.commit();
            return true;
        } catch (SQLException ex) {
            conn.rollback();
            throw ex;
        }finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    @Override
    public boolean updateSimple(Teacher simple) throws SQLException {
        Connection conn = SqlHelper.getConnection();
        try {
            PreparedStatement s1 = conn.prepareStatement("insert into teacher (teacher_id, user_id) " +
                    "VALUES (?, ?) " +
                    "on duplicate key update user_id=VALUES(user_id)");

            s1.setInt(1, simple.getTeacherId());
            s1.setInt(2, simple.getUserId());
            return true;
        }finally {
            conn.close();
        }
    }
}
