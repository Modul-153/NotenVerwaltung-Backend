package me.modul153.NotenVerwaltung.managers;

import me.modul153.NotenVerwaltung.api.AbstractManager;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractAdresse;
import me.modul153.NotenVerwaltung.data.model.Adresse;
import me.modul153.NotenVerwaltung.data.complex.AdresseComplex;
import net.myplayplanet.services.connection.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdressManager extends AbstractManager<AbstractAdresse, Adresse, AdresseComplex> {
    private static AdressManager adressManager = null;
    public static AdressManager getInstance() {
        if (adressManager == null) {
            adressManager = new AdressManager();
        }
        return adressManager;
    }

    @Override
    public Adresse loadIDataObjectComplex(Integer key) {
        try {
            PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement("select `strasse`,`nummer`,`ort_id` from `notenverwaltung`.`adresse` where `adress_id` = ?");
            statement.setInt(1, key);
            ResultSet r = statement.executeQuery();
            if (r.next()) {
                Adresse adresse = new Adresse(key,
                        r.getString("strasse"),
                        r.getInt("nummer"),
                        r.getInt("ort_id"));
                return adresse;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean saveIDataObjectComplex(Integer key, AbstractAdresse value) {
        int ortId;

        if (value instanceof Adresse) {
            Adresse adresse = (Adresse) value;
            ortId = adresse.getOrtId();

            if (OrtManager.getInstance().get(adresse.getOrtId()) == null) {
                System.out.println("could not save object with id " + key + ", adress not found!");
                return false;
            }
        }else if (value instanceof AdresseComplex) {
            AdresseComplex adresse = (AdresseComplex) value;

            if (adresse.getOrt() == null) {
                System.out.println("could not save object with id " + key + ", ort not found!");
                return false;
            }else if (OrtManager.getInstance().getSqlType(adresse.getOrt().getOrtId()) == null) {
                OrtManager.getInstance().save(key, adresse.getOrt());
            }
            ortId = adresse.getOrt().getOrtId();
        }else {
            System.out.println("invalid object in " + getManagerName() + "-cache found.");
            return false;
        }

        try {
            PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement(
                    "INSERT INTO `adresse` (`adress_id`, `strasse`, `nummer`, `ort_id`) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE `strasse`=?,`nummer`=?,`ort_id`=?");
            statement.setInt(1, value.getAdressId());
            statement.setString(2, value.getStrasse());
            statement.setInt(3, value.getNummer());
            statement.setInt(4, ortId);

            statement.setString(5, value.getStrasse());
            statement.setInt(6, value.getNummer());
            statement.setInt(7, ortId);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean validate(AbstractAdresse value) {
        switch (value.getType()) {
            case COMPLEX_TYPE:
                return ((AdresseComplex) value).getOrt() != null;
            case SQL_TYPE:
                return OrtManager.getInstance().contains(((Adresse) value).getOrtId());
            default:
                return false;
        }
    }

    @Override
    public String getManagerName() {
        return "adresse";
    }
}