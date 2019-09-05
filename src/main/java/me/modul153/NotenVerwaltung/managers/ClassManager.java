package me.modul153.NotenVerwaltung.managers;

import me.modul153.NotenVerwaltung.api.AbstractManager;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractClass;
import me.modul153.NotenVerwaltung.data.complex.ClassComplex;
import me.modul153.NotenVerwaltung.data.model.Class;
import me.modul153.NotenVerwaltung.helper.SqlHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ClassManager extends AbstractManager<AbstractClass, Class, ClassComplex> {
    private static ClassManager clsManager = null;

    public static ClassManager getInstance() {
        if (clsManager == null) {
            clsManager = new ClassManager();
        }
        return clsManager;
    }

    @Override
    public HashMap<Integer, AbstractClass> loadAllObjects() {
        HashMap<Integer, AbstractClass> map = new HashMap<>();
        try {
            PreparedStatement statement = SqlHelper.getStatement("select `class_id`,`name`,`school_id` from `notenverwaltung`.`class`");

            ResultSet set = statement.executeQuery();
            while (set.next()) {
                int key = set.getInt("class_id");
                Class cls = new Class(key,
                        set.getString("name"),
                        set.getInt("school_id"));
                map.put(key, cls);
            }
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Class loadIDataObjectComplex(Integer key) {
        try {
            PreparedStatement statement = SqlHelper.getStatement("select `name`,`school_id` from `notenverwaltung`.`class` where `class_id` = ?");
            statement.setInt(1, key);
            ResultSet r = statement.executeQuery();
            if (r.next()) {
                Class cls = new Class(key,
                        r.getString("name"),
                        r.getInt("school_id"));
                return cls;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean saveIDataObjectComplex(Integer key, AbstractClass value) {
        int schoolId;

        if (value instanceof Class) {
            Class cls = (Class) value;
            schoolId = cls.getSchoolId();

            if (CityManager.getInstance().get(cls.getSchoolId()) == null) {
                System.out.println("could not save object with id " + key + ", class not found!");
                return false;
            }
        } else if (value instanceof ClassComplex) {
            ClassComplex clse = (ClassComplex) value;

            if (clse.getSchool() == null) {
                System.out.println("could not save object with id " + key + ", School not found!");
                return false;
            } else if (CityManager.getInstance().getSqlType(clse.getSchool().getAdressId()) == null) {
                SchoolManager.getInstance().save(key, clse.getSchool());
            }
            schoolId = clse.getSchool().getSchoolId();
        } else {
            System.out.println("invalid object in " + getManagerName() + "-cache found.");
            return false;
        }

        try {
            PreparedStatement statement = SqlHelper.getStatement(
                    "INSERT INTO `class` (`class_id`, `name`, `school_id`) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE `name`=?,`school_id`=?");
            statement.setInt(1, value.getClassId());
            statement.setString(2, value.getName());
            statement.setInt(3, schoolId);

            statement.setString(4, value.getName());
            statement.setInt(5, schoolId);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean validate(AbstractClass value) {
        switch (value.getType()) {
            case COMPLEX_TYPE:
                return ((ClassComplex) value).getSchool() != null;
            case SQL_TYPE:
                return CityManager.getInstance().contains(((Class) value).getSchoolId());
            default:
                return false;
        }
    }

    @Override
    public String getManagerName() {
        return "class";
    }
}