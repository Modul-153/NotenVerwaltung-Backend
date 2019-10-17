package net.myplayplanet.querygenerator.api.model;

import net.myplayplanet.querygenerator.api.model.table.TableObject;

import java.util.HashMap;

public class SqlModel {
    HashMap<String, TableObject> tables;

    public SqlModel() {
        tables = new HashMap<>();
    }

    public void addTable(TableObject tableObject) {
        this.tables.put(tableObject.getName(), tableObject);
    }
}
