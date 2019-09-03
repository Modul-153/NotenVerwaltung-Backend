package me.modul153.NotenVerwaltung.managers;


import me.modul153.NotenVerwaltung.api.AbstractManager;
import me.modul153.NotenVerwaltung.data.abstracts.Credentials;
import net.myplayplanet.services.connection.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CredentialManager extends AbstractManager<Credentials, Credentials, Credentials> {
    private static CredentialManager credentialManager = null;
    public static CredentialManager getInstance() {
        if (credentialManager == null) {
            credentialManager = new CredentialManager();
        }
        return credentialManager;
    }
    @Override
    public Credentials loadIDataObjectComplex(Integer key) {
        try {
            PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement("select `password` from usercredentials where user_id=?");
            statement.setInt(1, key);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new Credentials(key, set.getString("password"));
            }else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean saveIDataObjectComplex(Integer key, Credentials value) {
        try {
            PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement(
                    "insert into `usercredentials` (`user_id`, `password`) values (? ,?)  ON DUPLICATE KEY UPDATE `password`=?");
            statement.setInt(1, value.getUserId());
            statement.setString(2, value.getPassword());
            statement.setString(3, value.getPassword());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getManagerName() {
        return "credentials";
    }
}
