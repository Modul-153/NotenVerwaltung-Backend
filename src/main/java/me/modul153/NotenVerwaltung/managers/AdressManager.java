package me.modul153.NotenVerwaltung.managers;

import me.modul153.NotenVerwaltung.dao.adresse.Adresse;
import net.myplayplanet.services.cache.AbstractSaveProvider;
import net.myplayplanet.services.cache.Cache;
import net.myplayplanet.services.connection.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdressManager {
    private Cache<Integer, Adresse> adressCache;

    private static AdressManager userManager = null;
    public static AdressManager getInstance() {
        if (userManager == null) {
            userManager = new AdressManager();
        }
        return userManager;
    }

    public AdressManager() {
        adressCache = new Cache<>("adress-cache", integer -> {
            try {
                PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement("select `strasse`,`nummer`,`ort_id` from `notenverwaltung`.`adresse` where `adress_id` = ?");
                statement.setInt(1, integer);
                ResultSet r = statement.executeQuery();
                if (r.next()) {
                    return new Adresse(integer,
                            r.getString("strasse"),
                            r.getInt("nummer"),
                            r.getInt("ort_id"));
                } else {
                    return null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }, new AbstractSaveProvider<Integer, Adresse>() {
            @Override
            public boolean save(Integer integer, Adresse adresse) {
                return saveAdresse(adresse, true);
            }
        });
    }

    public Adresse getAdresse(int id) {
        return adressCache.get(id);
    }

    public boolean addAdresse(Adresse adresse) {
        adressCache.update(adresse.getAdressId(), adresse);
        return true;
    }

    public void clearCache() {
        adressCache.clearCache();
    }

    protected boolean saveAdresse(Adresse adresse, boolean saveSub) {
        try {
            if (saveSub) {
                OrtManager.getInstance().saveOrt(adresse.getOrt());
            }

            PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement(
                    "INSERT INTO `adresse` (`adress_id`, `strasse`, `nummer`, `ort_id`) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE `strasse`=?,`nummer`=?,`ort_id`=?");
            statement.setInt(1, adresse.getAdressId());
            statement.setString(2, adresse.getStrasse());
            statement.setInt(3, adresse.getNummer());
            statement.setInt(4, adresse.getOrt().getOrtId());

            statement.setString(5, adresse.getStrasse());
            statement.setInt(6, adresse.getNummer());
            statement.setInt(7, adresse.getOrt().getOrtId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
