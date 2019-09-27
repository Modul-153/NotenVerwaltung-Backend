package me.modul153.NotenVerwaltung.managers;

import me.modul153.NotenVerwaltung.api.AbstractManager;
import me.modul153.NotenVerwaltung.data.abstracts.Subject;
import me.modul153.NotenVerwaltung.helper.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class SubjectManager extends AbstractManager<Subject, Subject, Subject> {
    private static SubjectManager subjectManager = null;

    public static SubjectManager getInstance() {
        if (subjectManager == null) {
            subjectManager = new SubjectManager();
        }
        return subjectManager;
    }

    @Override
    public HashMap<Integer, Subject> getAllComplex() throws SQLException {
        HashMap<Integer, Subject> map = new HashMap<>();
        Connection conn = SqlHelper.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement("select `subject_id`,`name` from `notenverwaltung`.`subject`");

            ResultSet set = statement.executeQuery();

            while (set.next()) {
                int key = set.getInt("subject_id");
                map.put(key, new Subject(key, set.getString("name")));
            }
            return map;
        } finally {
            conn.close();
        }
    }

    @Override
    public HashMap<Integer, Subject> getAllSimple() throws SQLException {
        return getAllComplex();
    }

    @Override
    public Subject getComplex(int key) throws SQLException {
        Connection conn = SqlHelper.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement("select `name` from `notenverwaltung`.`subject` where `subject_id` = ?");
            statement.setInt(1, key);
            ResultSet r = statement.executeQuery();
            if (r.next()) {
                return new Subject(key, r.getString("name"));
            } else {
                return null;
            }
        } finally {
            conn.close();
        }
    }

    @Override
    public Subject getSimple(int key) throws SQLException {
        return getComplex(key);
    }

    @Override
    public boolean updateComplex(Subject value) throws SQLException {
        Connection conn = SqlHelper.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO `subject` (`subject_id`, `name`) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE `name`=VALUES(name)");
            statement.setInt(1, value.getSubjectId());
            statement.setString(2, value.getName());
            statement.executeUpdate();
            return true;
        } finally {
            conn.close();
        }
    }

    @Override
    public boolean updateSimple(Subject simple) throws SQLException {
        return updateComplex(simple);
    }
}
