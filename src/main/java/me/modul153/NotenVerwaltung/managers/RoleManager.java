package me.modul153.NotenVerwaltung.managers;

import me.modul153.NotenVerwaltung.api.AbstractManager;
import me.modul153.NotenVerwaltung.data.abstracts.Role;
import net.myplayplanet.services.connection.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleManager extends AbstractManager<Role, Role, Role> {

    private static RoleManager roleManager = null;
    public static RoleManager getInstance() {
        if (roleManager == null) {
            roleManager = new RoleManager();
        }
        return roleManager;
    }

    @Override
    public Role loadIDataObjectComplex(Integer key) {
        try {
            PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement(
                    "select `rolename` from `notenverwaltung`.`role` where `role_id` = ?");
            statement.setInt(1, key);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return new Role(key, result.getString("rolename"));
            }else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean saveIDataObjectComplex(Integer key, Role value) {
        try {
            PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement(
                    "INSERT INTO `role` (`role_id`, `rolename`) VALUES (?, ?) ON DUPLICATE KEY UPDATE `rolename`=?");

            statement.setInt(1, key);
            statement.setString(2, value.getName());
            statement.setString(3, value.getName());

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getManagerName() {
        return "role";
    }
}
