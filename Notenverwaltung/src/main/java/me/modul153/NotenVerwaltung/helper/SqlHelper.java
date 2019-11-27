package me.modul153.NotenVerwaltung.helper;

import net.myplayplanet.services.connection.ConnectionManager;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class SqlHelper {

    public static String buildInsertMultipleString(int amount, int parameterAmount) {
        String parameterString = getParameterString(parameterAmount);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            list.add(parameterString);
        }
        return String.join(",", list);
    }

    private static String getParameterString(int parameterAmount) {
        List<String> r = new ArrayList<>();
        for (int i = 0; i < parameterAmount; i++) {
            r.add("?");
        }
        return "("+String.join(",", r)+")";
    }

    public static String buildMultipleDelete(String fieldName, int amount){
        List<String> r = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            r.add(fieldName + " = ?");
        }
        return String.join(" OR ", r);
    }
}
