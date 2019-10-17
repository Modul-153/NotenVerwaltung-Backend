package net.myplayplanet.querygenerator.api.model.table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.myplayplanet.querygenerator.api.model.SqlDataType;

/**
 * this Object represents a Field or a method in a Java class or a column in SQL.
 * This Object only makes sens in the context of {@link TableObject}
 */
@Getter
@AllArgsConstructor
public class TypeObject {
    String name;
    SqlDataType objectType;
}
