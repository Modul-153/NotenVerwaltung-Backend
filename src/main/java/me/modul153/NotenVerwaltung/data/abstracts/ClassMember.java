package me.modul153.NotenVerwaltung.data.abstracts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.IAbstract;
import me.modul153.NotenVerwaltung.api.IComplexType;
import me.modul153.NotenVerwaltung.api.ISqlType;

@Getter
@AllArgsConstructor
public class ClassMember implements IAbstract, ISqlType, IComplexType {
    int userId;
    int classId;

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
