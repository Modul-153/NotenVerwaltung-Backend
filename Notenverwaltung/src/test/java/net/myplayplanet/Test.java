package net.myplayplanet;

import ch.qos.logback.core.db.dialect.DBUtil;
import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        DBConfigurationBuilder config = DBConfigurationBuilder.newBuilder();
        config.setPort(0); // 0 => autom. detect free port
        Connection conn = null;
        DB database = null;
        try {
            database = DB.newEmbeddedDB(config.build());
            database.start();
            database.createDB("notenverwaltung");


            conn = DriverManager.getConnection(config.getURL("notenverwaltung"), "root", "");
            QueryRunner qr = new QueryRunner();
            database.source("dump_04_10_2019_09_50.sql");
            List<String> result = qr.query(conn, "select * from city",new ColumnListHandler<>());

            for (String s : result) {
                System.out.println(s);
            }
            database.stop();
        } catch (ManagedProcessException | SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                DbUtils.close(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
