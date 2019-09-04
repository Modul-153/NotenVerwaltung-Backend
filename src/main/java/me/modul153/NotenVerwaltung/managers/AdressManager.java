package me.modul153.NotenVerwaltung.managers;

import me.modul153.NotenVerwaltung.api.AbstractManager;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractAdress;
import me.modul153.NotenVerwaltung.data.model.Adress;
import me.modul153.NotenVerwaltung.data.complex.AdressComplex;
import net.myplayplanet.services.connection.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdressManager extends AbstractManager<AbstractAdress, Adress, AdressComplex> {
    private static AdressManager adressManager = null;
    public static AdressManager getInstance() {
        if (adressManager == null) {
            adressManager = new AdressManager();
        }
        return adressManager;
    }

    @Override
    public Adress loadIDataObjectComplex(Integer key) {
        try {
            PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement("select `street`,`number`,`city_id` from `notenverwaltung`.`adress` where `adress_id` = ?");
            statement.setInt(1, key);
            ResultSet r = statement.executeQuery();
            if (r.next()) {
                Adress adress = new Adress(key,
                        r.getString("street"),
                        r.getInt("number"),
                        r.getInt("city_id"));
                return adress;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean saveIDataObjectComplex(Integer key, AbstractAdress value) {
        int ortId;

        if (value instanceof Adress) {
            Adress adress = (Adress) value;
            ortId = adress.getCityId();

            if (CityManager.getInstance().get(adress.getCityId()) == null) {
                System.out.println("could not save object with id " + key + ", adress not found!");
                return false;
            }
        }else if (value instanceof AdressComplex) {
            AdressComplex adresse = (AdressComplex) value;

            if (adresse.getCity() == null) {
                System.out.println("could not save object with id " + key + ", city not found!");
                return false;
            }else if (CityManager.getInstance().getSqlType(adresse.getCity().getCityId()) == null) {
                CityManager.getInstance().save(key, adresse.getCity());
            }
            ortId = adresse.getCity().getCityId();
        }else {
            System.out.println("invalid object in " + getManagerName() + "-cache found.");
            return false;
        }

        try {
            PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement(
                    "INSERT INTO `adresse` (`adress_id`, `strasse`, `nummer`, `ort_id`) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE `strasse`=?,`nummer`=?,`ort_id`=?");
            statement.setInt(1, value.getAdressId());
            statement.setString(2, value.getStreet());
            statement.setInt(3, value.getNumber());
            statement.setInt(4, ortId);

            statement.setString(5, value.getStreet());
            statement.setInt(6, value.getNumber());
            statement.setInt(7, ortId);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean validate(AbstractAdress value) {
        switch (value.getType()) {
            case COMPLEX_TYPE:
                return ((AdressComplex) value).getCity() != null;
            case SQL_TYPE:
                return CityManager.getInstance().contains(((Adress) value).getCityId());
            default:
                return false;
        }
    }

    @Override
    public String getManagerName() {
        return "adress";
    }
}