package me.modul153.NotenVerwaltung.managers;

import me.modul153.NotenVerwaltung.api.AbstractManager;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractStudent;
import me.modul153.NotenVerwaltung.data.complex.StudentComplex;
import me.modul153.NotenVerwaltung.data.model.Student;
import net.myplayplanet.services.connection.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentManager extends AbstractManager<AbstractStudent, Student, StudentComplex> {
    private static StudentManager studentManager = null;
    public static StudentManager getInstance() {
        if (studentManager == null) {
            studentManager = new StudentManager();
        }
        return studentManager;
    }

    @Override
    public AbstractStudent loadIDataObjectComplex(Integer key) {
        try {
            PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement("select `user_id` from `student` where `student_id`=?");
            statement.setInt(1, key);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new Student(key, set.getInt("user_id"));
            }else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean saveIDataObjectComplex(Integer key, AbstractStudent value){
        int userId;

        if (value instanceof Student) {
            Student student = (Student) value;
            userId = student.getUserId();

            if (UserManager.getInstance().getSqlType(student.getUserId()) == null) {
                System.out.println("could not save object with id " + key + ", user not found!");
                return false;
            }
        } else if (value instanceof StudentComplex) {
            StudentComplex student = (StudentComplex) value;

            if (student.getUser() == null) {
                System.out.println("could not save object with id " + key + ", user not found!");
                return false;
            } else if (UserManager.getInstance().getSqlType(student.getUser().getUserId()) == null) {
                UserManager.getInstance().save(key, student.getUser());
            }

            userId = student.getUser().getUserId();
        } else {
            System.out.println("invalid object in " + getManagerName() + "-cache found.");
            return false;
        }

        try {
            PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement(
                    "INSERT INTO `student` (`student_id`, `user_id`) VALUES (?, ?) " +
                            "ON DUPLICATE KEY UPDATE `user_id`=?");
            statement.setInt(1, value.getStudentId());
            statement.setInt(2, userId);
            statement.setInt(3, userId);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getManagerName() {
        return "student";
    }
}