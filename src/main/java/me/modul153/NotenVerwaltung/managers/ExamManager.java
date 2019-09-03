package me.modul153.NotenVerwaltung.managers;

import me.modul153.NotenVerwaltung.api.AbstractManager;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractExam;
import me.modul153.NotenVerwaltung.data.complex.ExamComplex;
import me.modul153.NotenVerwaltung.data.model.Exam;
import net.myplayplanet.services.connection.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ExamManager extends AbstractManager<AbstractExam, Exam, ExamComplex> {
    private static ExamManager examManager = null;
    public static ExamManager getInstance() {
        if (examManager == null) {
            examManager = new ExamManager();
        }
        return examManager;
    }

    @Override
    public AbstractExam loadIDataObjectComplex(Integer key) {
        try {
            PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement("select `mark`,`date`,`user_id` from `exams` where exam_id=?");
            statement.setInt(1, key);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new Exam(key,set.getInt("mark"),
                        new Date(set.getDate("date").getTime()),
                                set.getInt("user_id"));
            }else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean saveIDataObjectComplex(Integer key, AbstractExam value) {
        int userId;

        if (value instanceof Exam) {
            Exam exam = (Exam) value;
            userId = exam.getUserId();

            if (AdressManager.getInstance().getSqlType(exam.getUserId()) == null) {
                System.out.println("could not save object with id " + key + ", user not found!");
                return false;
            }
        } else if (value instanceof ExamComplex) {
            ExamComplex exam = (ExamComplex) value;

            if (exam.getUser() == null) {
                System.out.println("could not save object with id " + key + ", user not found!");
                return false;
            } else if (AdressManager.getInstance().getSqlType(exam.getUser().getUserId()) == null) {
                UserManager.getInstance().save(key, exam.getUser());
            }

            userId = exam.getUser().getUserId();
        } else {
            System.out.println("invalid object in " + getManagerName() + "-cache found.");
            return false;
        }

        try {
            PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement(
                    "INSERT INTO `exams` (`exam_id`, `mark`, `date`, `user_id`) VALUES (?, ?, ?, ?) " +
                            "ON DUPLICATE KEY UPDATE `mark`=?, `date`=?,`user_id`=?");
            statement.setInt(1, key);
            statement.setInt(2, value.getMark());
            statement.setDate(3, new java.sql.Date(value.getDate().getTime()));
            statement.setInt(4, userId);

            statement.setInt(5, value.getMark());
            statement.setDate(6, new java.sql.Date(value.getDate().getTime()));
            statement.setInt(7, userId);

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getManagerName() {
        return "exam";
    }
}
