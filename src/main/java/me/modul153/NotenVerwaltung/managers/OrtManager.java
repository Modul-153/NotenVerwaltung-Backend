package me.modul153.NotenVerwaltung.managers;

import me.modul153.NotenVerwaltung.api.AbstractManager;
import me.modul153.NotenVerwaltung.data.abstracts.Ort;
import net.myplayplanet.services.connection.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrtManager extends AbstractManager<Ort, Ort, Ort> {
    private static OrtManager ortManager = null;
    public static OrtManager getInstance() {
        if (ortManager == null) {
            ortManager = new OrtManager();
        }
        return ortManager;
    }

    @Override
    public Ort loadIDataObjectComplex(Integer key) {
        try {
            PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement("select `zipcode`,`name` from `notenverwaltung`.`ort` where `ort_id` = ?");
            statement.setInt(1, key);
            ResultSet r = statement.executeQuery();
            if (r.next()) {
                return new Ort(key, r.getInt("zipcode"), r.getString("name"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean saveIDataObjectComplex(Integer key, Ort value) {
        try {
            PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement(
                    "INSERT INTO `ort` (`ort_id`, `zipcode`, `name`) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE `zipcode`=?,`name`=?");
            statement.setInt(1, value.getOrtId());
            statement.setInt(2, value.getZipCode());
            statement.setString(3, value.getName());
            statement.setInt(4, value.getZipCode());
            statement.setString(5, value.getName());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    @Override
    public String getManagerName() {
        return "ort";
    }
}
