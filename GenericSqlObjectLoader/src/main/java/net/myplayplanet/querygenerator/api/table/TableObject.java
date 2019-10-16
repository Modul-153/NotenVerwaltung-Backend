package net.myplayplanet.querygenerator.api.table;

import lombok.Getter;
import net.myplayplanet.querygenerator.api.NotNull;
import net.myplayplanet.querygenerator.api.Nullable;

import java.util.HashMap;

/**
 * this objects represents a Table in SQL and a Class in Java.
 * This Object only makes sens in the context of a {@link TableModel}.
 */
@Getter
public class TableObject {
    private String name;
    private HashMap<Integer, TypeObject> fields;
    private Integer primaryKeyIndex;

    public TableObject(@NotNull String name) {
        assert name != null : "name is not allowed to be null.";

        this.name = name;
        this.fields = new HashMap<>();
    }

    /**
     * @param primaryKeyIndex the index of the field that is the Primary key. This may be null. This index must exist as a registered field.
     */
    public void setPrimaryKey(@Nullable Integer primaryKeyIndex) {
        if (!fields.containsKey(primaryKeyIndex)) {
            throw new IllegalStateException("PrimaryKeyIndex must be a registered field! (" + primaryKeyIndex + ")");
        }

        this.primaryKeyIndex = primaryKeyIndex;
    }

    /**
     * @param id         the Id, this should not be given already, if it is it will throw a exception if override is set to false (default).
     * @param typeObject the field object that should be added.
     * @param override   if this value is set to true it will override fields already added and not throw a exception if that position is already given.
     */
    public void addField(int id, TypeObject typeObject, boolean override) {
        assert typeObject != null : "TypeObject is not allowed to be null";

        if (fields.containsKey(id) && !override) {
            throw new IllegalStateException("there is already a field with id " + id + ".");
        }
        fields.put(id, typeObject);
    }

    /**
     * by default it will throw a exception if the object on that index already exists. If this should be avoided use
     * {@link #addField(int, TypeObject, boolean)} and set override to true.
     *
     * @param id         the internal id of the field.
     * @param typeObject the typeObject that should be added.
     */
    public void addField(int id, TypeObject typeObject) {
        addField(id, typeObject, false);
    }
}
