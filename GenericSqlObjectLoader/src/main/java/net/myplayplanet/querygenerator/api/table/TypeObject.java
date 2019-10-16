package net.myplayplanet.querygenerator.api.table;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
