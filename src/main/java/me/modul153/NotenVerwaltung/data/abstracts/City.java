package me.modul153.NotenVerwaltung.data.abstracts;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.IAbstract;
import me.modul153.NotenVerwaltung.api.IComplexType;
import me.modul153.NotenVerwaltung.api.ISqlType;

@AllArgsConstructor
@Data
public class City implements IAbstract, ISqlType, IComplexType {
    private int cityId;
    private int zipCode;
    private String name;

    @Override
    public AbstractionType getType() {
        return AbstractionType.SQL_TYPE;
    }

    @Override
    public IComplexType toComplexType() {
        return this;
    }

    @Override
    public ISqlType toSqlType() {
        return this;
    }
}
