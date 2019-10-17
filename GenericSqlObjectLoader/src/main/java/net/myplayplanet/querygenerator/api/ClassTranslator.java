package net.myplayplanet.querygenerator.api;

import com.google.common.base.CaseFormat;
import com.google.common.base.Function;
import lombok.Getter;
import lombok.experimental.Delegate;
import net.myplayplanet.querygenerator.api.exceptions.NoNameFoundException;
import net.myplayplanet.querygenerator.api.model.SqlDataType;
import net.myplayplanet.querygenerator.api.model.SqlModel;
import net.myplayplanet.querygenerator.api.model.table.TableObject;
import net.myplayplanet.querygenerator.api.model.table.TypeObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ClassTranslator { //todo test coverage to at least 90%
    @Delegate
    ReverseFieldTranslator translator;
    @Getter
    private SqlModel model;
    @Getter
    private Class clazz;
    private Function<Class, List<String>> getFieldListFunction;
    private String primaryKeyFieldName = null;

    public ClassTranslator(@NotNull SqlModel model, @NotNull Class clazz, @NotNull FieldTranslator.FallbackType type) {
        assert model != null : "SqlModel is not allowed to be null.";
        assert clazz != null : "Class is not allowed to be null.";
        assert type != null : "FallbackType is not allowed to be null.";

        this.model = model;
        this.clazz = clazz;
        this.translator = new ReverseFieldTranslator(type);
        translator.setCustomNameProcessorClass(input -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, input.getSimpleName()));
        translator.setCustomNameProcessorField(input -> CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, input.getName()));
        translator.setCustomNameProcessorMethod(input -> {
            assert input != null : "input Method can not be null.";
            if (input.getName().startsWith("get") ||
                    input.getName().startsWith("set") ||
                    input.getName().startsWith("is") ||
                    clazz.isEnum() && input.getName().equals("name")) {
                String replace = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,
                        input.getName().replace("get", "")
                                .replace("set", "")
                                .replace("is", ""));
                return replace;
            } else {
                String to = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, input.getName());
                return to;
            }
        });

        getFieldListFunction = ClassTranslator::getFieldList;
    }

    /**
     * by default the FallbackType is {@link net.myplayplanet.querygenerator.api.FieldTranslator.FallbackType#AUTO}, this can be changed with using {@link #ClassTranslator(SqlModel, Class, FieldTranslator.FallbackType)}
     *
     * @param clazz the class object for what the table Object should be created.
     */
    public ClassTranslator(@NotNull SqlModel model, @NotNull Class clazz) {
        this(model, clazz, FieldTranslator.FallbackType.AUTO);
    }

    public void setGetFieldListFunction(@NotNull Function<Class, List<String>> func) {
        assert func != null : "Function can not be null.";
        getFieldListFunction = func;
    }

    /**
     * @param method the method that represents the Primary key in a Given Object
     */
    public void setPrimary(@NotNull Method method)  {
        assert method != null : "Method can not be NUll";
        this.primaryKeyFieldName = method.getName();
    }

    /**
     * @param field the field that represents the Primary key in a Given Object
     */
    public void setPrimary(@NotNull Field field)  {
        assert field != null : "Method can not be NUll";
        this.primaryKeyFieldName = field.getName();
    }

    public static List<String> getFieldList(Class clazz) {
        assert clazz != null : "input class can not be null.";

        List<String> result = new ArrayList<>();

        for (Method method : clazz.getMethods()) {
            if (method.getName().startsWith("get") ||
                    method.getName().startsWith("set") ||
                    method.getName().startsWith("is") ||
                    clazz.isEnum() && method.getName().equals("name")) {
                if (method.getName().equals("getClass") && method.getReturnType() == Class.class) {
                    continue;
                }
                if (result.contains(method.getName())) {
                    continue;
                }

                result.add(method.getName());
            }
        }
        for (Field field : clazz.getFields()) {

            if (result.contains(field.getName())) {
                continue;
            }

            result.add(field.getName());
        }
        result.sort(String::compareToIgnoreCase);
        return result;
    }

    /**
     * This Method creates a tableObject and add it to the in the  costructor defined {@link SqlModel}
     *
     * in the Table the Values will most of the time not be in the Order you would expect, to find the index for a field use
     *
     * @return the created TableObject.
     */
    public TableObject createTableObject(boolean findPrimary) {
        String name = translator.getName(clazz);

        if (name.equalsIgnoreCase("")) {
            throw new NoNameFoundException("could not create table for class " + clazz.getSimpleName() + ", no name was defined in translator.");
        }

        TableObject table = new TableObject(name);

        List<String> fieldNames = this.getFieldListFunction.apply(clazz);
        assert fieldNames != null && fieldNames.size() > 0 : "Fieldnames can not be null or Empty";

        if (findPrimary && this.primaryKeyFieldName == null) {
            this.primaryKeyFieldName = this.findPrimary(fieldNames);
        }

        int id = 0;
        for (String fieldName : fieldNames) {
            id++;

            if (primaryKeyFieldName != null && primaryKeyFieldName.equalsIgnoreCase(fieldName)) {
                table.setPrimaryKey(id);
            }

            Method method = FieldTranslator.getMethod(clazz, fieldName);

            if (method != null) {
                String newName = translator.getName(method);
                handleFieldCreation(id, table, newName, method.getReturnType());
            } else {
                Field field = FieldTranslator.getField(clazz, fieldName);
                if (field == null) {
                    throw new IllegalStateException("no method or field found with name " + fieldName + " in class " + clazz.getSimpleName());
                }
                String newName = translator.getName(field);
                handleFieldCreation(id, table, newName, field.getType());
            }
        }
        this.model.addTable(table);
        return table;
    }

    /**
     * @param list the list in where the primary key should be found.
     * @return the first parameter in that list containing "id", null if there was nothing.
     */
    private String findPrimary(@NotNull List<String> list) {
        for (String s : list) {
            if (s.toLowerCase().contains("id")) {
                return s;
            }
        }
        return null;
    }

    private void handleFieldCreation(int index, @NotNull TableObject table, @NotNull String newName, @NotNull Class returnType) {
        assert newName != null && !newName.equalsIgnoreCase("") : "Name can not be null or Empty.";
        SqlDataType typeObject = SqlDataTypeMapper.getInstance().map(returnType);
        if (typeObject == null) {
            throw new RuntimeException("not registered type found " + returnType.getSimpleName() + ", foreign keys are not supported.");
        }
        table.addField(index, new TypeObject(newName, typeObject));
    }

}
