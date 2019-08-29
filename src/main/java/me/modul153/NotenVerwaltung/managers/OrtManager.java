package me.modul153.NotenVerwaltung.managers;

import me.modul153.NotenVerwaltung.dao.adresse.Ort;
import net.myplayplanet.services.cache.AbstractSaveProvider;
import net.myplayplanet.services.cache.Cache;
import net.myplayplanet.services.connection.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrtManager {
    private Cache<Integer, Ort> ortCache;

    private static OrtManager userManager = null;
    public static OrtManager getInstance() {
        if (userManager == null) {
            userManager = new OrtManager();
        }
        return userManager;
    }

    public OrtManager() {
        ortCache = new Cache<>("ort-cache", integer -> {
            try {
                PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement("select `zipcode`,`name` from `notenverwaltung`.`ort` where `ort_id` = ?");
                statement.setInt(1, integer);
                ResultSet r = statement.executeQuery();
                if (r.next()) {
                    return new Ort(integer, r.getInt("zipcode"), r.getString("name"));
                } else {
                    return null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }, new AbstractSaveProvider<Integer, Ort>() {
            @Override
            public boolean save(Integer integer, Ort ort) {
                return saveOrt(ort);
            }
        });
    }

    public Ort getOrt(int id) {
        return ortCache.get(id);
    }

    public boolean addOrt(Ort ort) {
        ortCache.update(ort.getOrtId(), ort);
        return true;
    }

    public void clearCache() {
        ortCache.clearCache();
    }

    protected boolean saveOrt(Ort ort) {
        try {
            PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement(
                    "INSERT INTO `ort` (`ort_id`, `zipcode`, `name`) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE `zipcode`=?,`name`=?");
            statement.setInt(1, ort.getOrtId());
            statement.setInt(2, ort.getZipCode());
            statement.setString(3, ort.getName());
            statement.setInt(4, ort.getZipCode());
            statement.setString(5, ort.getName());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
