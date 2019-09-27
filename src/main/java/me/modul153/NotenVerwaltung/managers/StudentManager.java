package me.modul153.NotenVerwaltung.managers;

import me.modul153.NotenVerwaltung.api.AbstractManager;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractStudent;
import me.modul153.NotenVerwaltung.data.complex.StudentComplex;
import me.modul153.NotenVerwaltung.data.model.Student;
import me.modul153.NotenVerwaltung.data.model.User;
import me.modul153.NotenVerwaltung.helper.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class StudentManager extends AbstractManager<AbstractStudent, Student, StudentComplex> {
    private static StudentManager studentManager = null;

    public static StudentManager getInstance() {
        if (studentManager == null) {
            studentManager = new StudentManager();
        }
        return studentManager;
    }

    @Override
    public HashMap<Integer, StudentComplex> getAllComplex() throws SQLException {

        Connection conn = SqlHelper.getConnection();
        try {
            HashMap<Integer, StudentComplex> result = new HashMap<>();
            ResultSet set = conn.prepareStatement(
                    "select student_id,user_id, firstname, lastname, username, number, street, city_id " +
                            "from student " +
                            "         join user using(user_id) " +
                            "order by student_id;").executeQuery();
            while (set.next()) {
                int studentId = set.getInt("student_id");
                result.put(studentId,
                        new StudentComplex(
                                studentId,
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
    public HashMap<Integer, Student> getAllSimple() throws SQLException {

        Connection conn = SqlHelper.getConnection();
        try {
            HashMap<Integer, Student> result = new HashMap<>();
            ResultSet set = conn.prepareStatement(
                    "select student_id, user_id from student order by student_id;").executeQuery();

            while (set.next()) {
                int studentId = set.getInt("student_id");
                result.put(studentId,
                        new Student(
                                studentId,
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
    public StudentComplex getComplex(int key) throws SQLException {
        Connection conn = SqlHelper.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(
                    "select user_id, firstname, lastname, username, number, street, city_id " +
                            "from student " +
                            "         join user u on student.user_id = u.user_id " +
                            "where student_id = ?;");
            statement.setInt(1, key);
            ResultSet set = statement.executeQuery();

            if (set.next()) {
                return new StudentComplex(
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
    public Student getSimple(int key) throws SQLException {

        Connection conn = SqlHelper.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(
                    "select student_id, user_id from student where student_id = ?;");
            statement.setInt(1, key);
            ResultSet set = statement.executeQuery();

            if (set.next()) {
                int studentId = set.getInt("student_id");
                return new Student(
                        studentId,
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
    public boolean updateComplex(StudentComplex complex) throws SQLException {
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

            PreparedStatement s1 = conn.prepareStatement("insert into student (student_id, user_id) " +
                    "VALUES (?, ?) " +
                    "on duplicate key update user_id=VALUES(user_id)");

            s1.setInt(1, complex.getStudentId());
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
    public boolean updateSimple(Student simple) throws SQLException {
        Connection conn = SqlHelper.getConnection();
        try {
            PreparedStatement s1 = conn.prepareStatement("insert into student (student_id, user_id) " +
                    "VALUES (?, ?) " +
                    "on duplicate key update user_id=VALUES(user_id)");

            s1.setInt(1, simple.getStudentId());
            s1.setInt(2, simple.getUserId());
            return true;
        }finally {
            conn.close();
        }
    }
}
