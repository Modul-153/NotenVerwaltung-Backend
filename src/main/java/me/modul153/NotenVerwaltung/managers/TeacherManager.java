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
    private static TeacherManager cityManager = null;

    public static TeacherManager getInstance() {
        if (cityManager == null) {
            cityManager = new TeacherManager();
        }
        return cityManager;
    }

    @Override
    public HashMap<Integer, TeacherComplex> getAllComplex() {

        Connection conn = SqlHelper.getConnection();
        try {
            HashMap<Integer, TeacherComplex> result = new HashMap<>();
            ResultSet set = conn.prepareStatement(
                    "select teacher_id,user_id, firstname, lastname, username, number, street, city_id " +
                            "from teacher " +
                            "         join user u on teacher.user_id = u.user_id " +
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
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public HashMap<Integer, Teacher> getAllSimple() {

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
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public TeacherComplex getComplex(int key) {
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
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Teacher getSimple(int key) {

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
        } catch (SQLException e) {
            e.printStackTrace();
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
    public boolean updateComplex(TeacherComplex complex) {
        return false;
    }

    @Override
    public boolean updateSimple(Teacher simple) {
        return false;
    }
}
