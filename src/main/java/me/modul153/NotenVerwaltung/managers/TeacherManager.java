package me.modul153.NotenVerwaltung.managers;

import me.modul153.NotenVerwaltung.api.AbstractManager;
import me.modul153.NotenVerwaltung.api.IComplexType;
import me.modul153.NotenVerwaltung.api.ISqlType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractStudent;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractTeacher;
import me.modul153.NotenVerwaltung.data.complex.StudentComplex;
import me.modul153.NotenVerwaltung.data.complex.TeacherComplex;
import me.modul153.NotenVerwaltung.data.model.Student;
import me.modul153.NotenVerwaltung.data.model.Teacher;
import net.myplayplanet.services.connection.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherManager extends AbstractManager<AbstractTeacher, Teacher, TeacherComplex> {
    private static TeacherManager teacherManager = null;
    public static TeacherManager getInstance() {
        if (teacherManager == null) {
            teacherManager = new TeacherManager();
        }
        return teacherManager;
    }
    @Override
    public AbstractTeacher loadIDataObjectComplex(Integer key) {
        try {
            PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement("select `user_id` from `teacher` where `teacher_id`=?");
            statement.setInt(1, key);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new Teacher(key, set.getInt("user_id"));
            }else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean saveIDataObjectComplex(Integer key, AbstractTeacher value) {
        {
            int userId;

            if (value instanceof Teacher) {
                Teacher teacher = (Teacher) value;
                userId = teacher.getUserId();

                if (UserManager.getInstance().getSqlType(teacher.getUserId()) == null) {
                    System.out.println("could not save object with id " + key + ", user not found!");
                    return false;
                }
            } else if (value instanceof TeacherComplex) {
                TeacherComplex student = (TeacherComplex) value;

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
                        "INSERT INTO `teacher` (`teacher_id`, `user_id`) VALUES (?, ?) " +
                                "ON DUPLICATE KEY UPDATE `user_id`=?");
                statement.setInt(1, value.getTeacherId());
                statement.setInt(2, userId);
                statement.setInt(3, userId);
                statement.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    @Override
    public boolean validate(AbstractTeacher value) {
        int userId;

        if (value instanceof ISqlType) {
            userId = ((Teacher)value).getUserId();
        }else if (value instanceof IComplexType) {
            TeacherComplex complex = (TeacherComplex) value;
            if (complex.getUser() == null) {
                return false;
            }
            userId = complex.getUser().getUserId();
        }else {
            return false;
        }

        return UserManager.getInstance().contains(userId);
    }

    @Override
    public String getManagerName() {
        return "teacher";
    }
}
