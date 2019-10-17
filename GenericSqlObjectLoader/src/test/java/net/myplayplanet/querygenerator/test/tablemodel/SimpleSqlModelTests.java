package net.myplayplanet.querygenerator.test.tablemodel;

import lombok.Getter;
import net.myplayplanet.querygenerator.api.ClassTranslator;
import net.myplayplanet.querygenerator.api.model.SqlDataTypes;
import net.myplayplanet.querygenerator.api.model.SqlModel;
import net.myplayplanet.querygenerator.api.model.table.TableObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class SimpleSqlModelTests {

    @Test
    public void test_simple_table_creation_with_no_primary_auto_search() {
        SqlModel model  = new SqlModel();
        ClassTranslator classTranslator = new ClassTranslator(model, Person.class);
        TableObject table = classTranslator.createTableObject(false);

        Assert.assertEquals("person", table.getName());

        Assert.assertEquals(3, table.getFields().size());

        Assert.assertNotNull(table.getObjectFromName("name"));
        Assert.assertEquals("name",table.getObjectFromName("name").getName());
        Assert.assertEquals(SqlDataTypes.VARCHAR,table.getFields().get(1).getObjectType().getType());

        Assert.assertNotNull(table.getObjectFromName("uuid"));
        Assert.assertEquals("uuid",table.getObjectFromName("uuid").getName());
        Assert.assertEquals(SqlDataTypes.CHAR,table.getObjectFromName("uuid").getObjectType().getType());

        Assert.assertNotNull(table.getObjectFromName("abc"));
        Assert.assertEquals("abc",table.getObjectFromName("abc").getName());
        Assert.assertEquals(SqlDataTypes.BOOLEAN,table.getObjectFromName("abc").getObjectType().getType());
    }



    @Getter
    public class Person {
        private UUID uuid;
        private String name;
        public boolean isAbc;
    }
}
