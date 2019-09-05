package me.modul153.NotenVerwaltung.helper;

import net.myplayplanet.services.connection.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    public static PreparedStatement getStatement(String sql) throws SQLException {
        System.out.println("sql: " + sql);
        Counter.connectionCounter++;
        return ConnectionManager.getInstance().getMySQLConnection().prepareStatement(sql);
    }
}
