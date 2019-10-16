package net.myplayplanet.querygenerator.api;

import com.google.common.base.CaseFormat;
import com.google.common.base.Function;
import lombok.Getter;
import lombok.experimental.Delegate;
import net.myplayplanet.querygenerator.api.exceptions.NoNameFoundException;
import net.myplayplanet.querygenerator.api.table.SqlDataType;
import net.myplayplanet.querygenerator.api.table.TableModel;
import net.myplayplanet.querygenerator.api.table.TableObject;
import net.myplayplanet.querygenerator.api.table.TypeObject;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ClassTranslator {
    @Delegate
    ReverseFieldTranslator translator;
    @Getter
    private TableModel model;
    @Getter
    private Class clazz;
    private Function<Class, List<String>> getFieldListFunction;
    private String primaryKeyFieldName = null;

    public ClassTranslator(@NotNull TableModel model, @NotNull Class clazz, @NotNull FieldTranslator.FallbackType type) {
        assert model != null : "TableModel is not allowed to be null.";
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
                return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,
                        input.getName())
                        .replace("get", "")
                        .replace("set", "")
                        .replace("is", "");
            } else {
                return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, input.getName());
            }
        });

        getFieldListFunction = ClassTranslator::getFieldList;
    }

    /**
     * by default the FallbackType is {@link net.myplayplanet.querygenerator.api.FieldTranslator.FallbackType#AUTO}, this can be changed with using {@link #ClassTranslator(TableModel, Class, FieldTranslator.FallbackType)}
     *
     * @param clazz the class object for what the table Object should be created.
     */
    public ClassTranslator(@NotNull TableModel model, @NotNull Class clazz) {
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
                result.add(method.getName());
            }
        }
        for (Field field : clazz.getFields()) {
            result.add(field.getName());
        }
        return result;
    }

    /**
     * This Method creates a tableObject and add it to the in the costructor defined {@link TableModel}
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

        int id = 0;
        for (String fieldName : fieldNames) {
            id++;
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

        if (findPrimary) {
            //todo implement automatic finding of primary key from the name etc.
        }

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
        assert newName != null && newName.equalsIgnoreCase("") : "Name can not be null or Empty.";
        SqlDataType typeObject = SqlDataTypeMapper.getInstance().map(returnType);
        if (typeObject == null) {
            System.out.println("not registered type found " + returnType.getSimpleName() + ", foreign keys are not supported.");
            throw new NotImplementedException();
        }
        table.addField(index, new TypeObject(newName, typeObject));
    }

}
