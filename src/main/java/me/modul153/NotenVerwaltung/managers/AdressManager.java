package me.modul153.NotenVerwaltung.managers;

import me.modul153.NotenVerwaltung.dao.AdresseOrt;
import me.modul153.NotenVerwaltung.dao.adresse.Adresse;
import net.myplayplanet.services.cache.AbstractSaveProvider;
import net.myplayplanet.services.cache.Cache;
import net.myplayplanet.services.connection.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdressManager {
    private Cache<Integer, AdresseOrt> adressCache;

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
                    Adresse adresse = new Adresse(integer,
                            r.getString("strasse"),
                            r.getInt("nummer"),
                            r.getInt("ort_id"));
                    return new AdresseOrt(adresse, null);
                } else {
                    return null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }, new AbstractSaveProvider<Integer, AdresseOrt>() {
            @Override
            public boolean save(Integer integer, AdresseOrt adresse) {
                return saveAdresse(adresse);
            }
        });
    }

    public Adresse getAdresse(int id) {
        return adressCache.get(id).getAdresse();
    }

    public boolean addAdresse(Adresse adresse) {
        adressCache.update(adresse.getAdressId(), new AdresseOrt(adresse, null));
        return true;
    }

    public void clearCache() {
        adressCache.clearCache();
    }

    protected boolean saveAdresse(AdresseOrt adresseOrt) {

        Adresse adresse = adresseOrt.getAdresse();

        if (OrtManager.getInstance().getOrt(adresse.getOrtId()) == null) {
            if (adresseOrt.getOrt() != null) {
                if (!OrtManager.getInstance().saveOrt(adresseOrt.getOrt())) {
                    return false;
                }
            }else {
                return false;
            }
        }

        try {
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
