package me.modul153.NotenVerwaltung.managers;


import me.modul153.NotenVerwaltung.api.AbstractManager;
import me.modul153.NotenVerwaltung.data.abstracts.Credential;
import me.modul153.NotenVerwaltung.helper.SqlHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class CredentialManager extends AbstractManager<Credential, Credential, Credential> {
    private static CredentialManager credentialManager = null;

    public static CredentialManager getInstance() {
        if (credentialManager == null) {
            credentialManager = new CredentialManager();
        }
        return credentialManager;
    }

    @Override
    public HashMap<Integer, Credential> loadAllObjects() {
        HashMap<Integer, Credential> map = new HashMap<>();
        try {
            PreparedStatement statement = SqlHelper.getStatement("select `user_id`,`password` from usercredentials");
            ResultSet set = statement.executeQuery();

            while (set.next()) {
                int key = set.getInt("user_id");
                map.put(key, new Credential(key, set.getString("password")));
            }

            return map;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Credential loadIDataObjectComplex(Integer key) {
        try {
            PreparedStatement statement = SqlHelper.getStatement("select `password` from usercredentials where user_id=?");
            statement.setInt(1, key);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new Credential(key, set.getString("password"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean saveIDataObjectComplex(Integer key, Credential value) {
        try {
            PreparedStatement statement = SqlHelper.getStatement(
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
