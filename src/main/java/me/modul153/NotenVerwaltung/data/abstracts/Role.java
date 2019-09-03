package me.modul153.NotenVerwaltung.data.abstracts;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.IAbstract;
import me.modul153.NotenVerwaltung.api.IComplexType;
import me.modul153.NotenVerwaltung.api.ISqlType;

@Data
@AllArgsConstructor
public class Role implements IAbstract, ISqlType, IComplexType {
    int roleId;
    String name;

    @Override
    public AbstractionType getType() {
        return AbstractionType.SQL_TYPE;
    }

    @Override
    public ISqlType toSqlType() {
        return this;
    }

    @Override
    public IComplexType toComplexType() {
        return this;
    }
}
