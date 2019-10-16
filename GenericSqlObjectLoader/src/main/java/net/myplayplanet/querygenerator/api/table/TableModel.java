package net.myplayplanet.querygenerator.api.table;

import java.util.HashMap;

public class TableModel {
    HashMap<String, TableObject> tables;

    public TableModel() {
        tables = new HashMap<>();
    }

    public void addTable(TableObject tableObject) {
        this.tables.put(tableObject.getName(), tableObject);
    }
}
