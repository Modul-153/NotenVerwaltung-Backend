package net.myplayplanet.querygenerator.api;

import com.google.common.base.Function;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

public class FieldTranslator {
    private HashMap<Field, String> fieldMap;
    private HashMap<Method, String> methodMap;
    private HashMap<Class, String> classMap;

    private HashMap<Field, Function<Field, String>> fallbackMethodField;
    private HashMap<Method, Function<Method, String>> fallbackMethodMethod;
    private HashMap<Class, Function<Class, String>> fallbackMethodClass;

    private Function<Field, String> fallbackCustomFieldNameProcessor;
    private Function<Method, String> fallbackCustomMethodNameProcessor;
    private Function<Class, String> fallbackCustomClassNameProcessor;

    private static final String stringNotNullOrEmptyErrorMessage = "String can not be null, Empty or Whitespace!";
    private static final String fieldNotNullErrorMessage = "Field can not be null!";
    private static final String classNotNullErrorMessage = "Class can not be null!";
    private static final String methodNotNullErrorMessage = "Method can not be null!";
    private static final String functionNotNullErrorMessage = "Function can not be null!";
    private static final String functionReturnNullErrorMessage = "Function is not allowed to retrun null!";

    /**
     * this value determines if, when the name searched for was not set, it should try to take the Name Method for that type.
     */
    private FallbackType fallbackType;

    /**
     * @param fallbackType how this class should handle values that are not found. For more information see {@link FallbackType}
     */
    public FieldTranslator(FallbackType fallbackType) {
        assert fallbackType != null;
        this.fallbackType = fallbackType;
        fieldMap = new HashMap<>();
        methodMap = new HashMap<>();
        classMap = new HashMap<>();
        fallbackMethodField = new HashMap<>();
        fallbackMethodMethod = new HashMap<>();
        fallbackMethodClass = new HashMap<>();
    }

    /**
     * Calls the Other Constructor {@link #FieldTranslator(FallbackType)} with the Fallback type {@link FallbackType#EMPTY}
     */
    public FieldTranslator() {
        this(FallbackType.EMPTY);
    }

    /**
     * @param clazz the class for what the name should be searched
     * @return the new Name given for the Class, Empty String if nothing was found.
     */
    public String getName(@NotNull Class clazz) {
        assert clazz != null : "Class in getName(Class) should not be null!";

        return classMap.getOrDefault(clazz, getDefault(clazz));
    }

    /**
     * @param field the field for what the new Name should be Set.
     * @return the new Name given for the Field, Empty String if nothing was found.
     */
    public String getName(@NotNull Field field) {
        assert field != null : fieldNotNullErrorMessage;

        return fieldMap.getOrDefault(field, getDefault(field));
    }

    /**
     * @param method the method for what the new Name should be Set.
     * @return the new Name given for the Field, Empty String if nothing was found.
     */
    public String getName(@NotNull Method method) {
        assert method != null : methodNotNullErrorMessage;

        return methodMap.getOrDefault(method, getDefault(method));
    }


    /**
     * @param clazz   the class for what the new name should be set.
     * @param newName the new name for the given field.
     */
    public void setName(@NotNull Class clazz, @NotNull String newName) {
        assert clazz != null : classNotNullErrorMessage;
        assert newName != null && !newName.trim().isEmpty() : stringNotNullOrEmptyErrorMessage;

        classMap.put(clazz, newName);
    }

    /**
     * @param field   the field for what the new name should be set.
     * @param newName the new name for the given field.
     */
    public void setName(@NotNull Field field, @NotNull String newName) {
        assert newName != null && !newName.trim().isEmpty() : stringNotNullOrEmptyErrorMessage;
        assert field != null : fieldNotNullErrorMessage;

        fieldMap.put(field, newName);
    }

    /**
     * @param method  the field for what the new name should be set.
     * @param newName the new name for the given field.
     */
    public void setName(@NotNull Method method, @NotNull String newName) {
        assert newName != null && !newName.trim().isEmpty() : stringNotNullOrEmptyErrorMessage;
        assert method != null : methodNotNullErrorMessage;

        methodMap.put(method, newName);
    }

    /**
     * This Function will be called if the fallback type is set to {@link FallbackType#NAME_METHOD}
     *
     * @param method   the Method for what the name should be stored.
     * @param function the function that will be called.
     */
    public void addFallback(Method method, Function<Method, String> function) {
        assert method != null : methodNotNullErrorMessage;
        assert function != null : functionNotNullErrorMessage;

        fallbackMethodMethod.put(method, function);
    }

    /**
     * This Function will be called if the fallback type is set to {@link FallbackType#NAME_METHOD}
     *
     * @param field    the Method for what the name should be stored.
     * @param function the function that will be called.
     */
    public void addFallback(Field field, Function<Field, String> function) {
        assert field != null : fieldNotNullErrorMessage;
        assert function != null : functionNotNullErrorMessage;

        fallbackMethodField.put(field, function);
    }

    /**
     * This Function will be called if the fallback type is set to {@link FallbackType#NAME_METHOD}
     *
     * @param clazz    the Method for what the name should be stored.
     * @param function the function that will be called.
     */
    public void addFallback(Class clazz, Function<Class, String> function) {
        assert clazz != null : methodNotNullErrorMessage;
        assert function != null : functionNotNullErrorMessage;

        fallbackMethodClass.put(clazz, function);
    }

    /**
     * This method will be active if the fallback type is set to {@link FallbackType#CUSTOM_FIELD_PROCESSOR} or {@link FallbackType#AUTO}.
     * If this Method is set, it will be called to customise the messages that will be returned as a fallback.
     *
     * This is useful for example if you want to format classNames to a specific typing from UpperCamel to lower Snake etc.
     * @param func the function that will be called.
     */
    public void setCustomNameProcessorClass(@NotNull Function<Class, String> func) {
        assert func != null : functionNotNullErrorMessage;
        this.fallbackCustomClassNameProcessor  = func;
    }

    /**
     * This method will be active if the fallback type is set to {@link FallbackType#CUSTOM_FIELD_PROCESSOR} or {@link FallbackType#AUTO}.
     * If this Method is set, it will be called to customise the messages that will be returned as a fallback.
     *
     * This is useful for example if you want to format classNames to a specific typing from UpperCamel to lower Snake etc.
     * @param func the function that will be called.
     */
    public void setCustomNameProcessorField(@NotNull Function<Field, String> func) {
        assert func != null : functionNotNullErrorMessage;
        this.fallbackCustomFieldNameProcessor  = func;

    }

    /**
     * This method will be active if the fallback type is set to {@link FallbackType#CUSTOM_FIELD_PROCESSOR} or {@link FallbackType#AUTO}.
     * If this Method is set, it will be called to customise the messages that will be returned as a fallback.
     *
     * This is useful for example if you want to format classNames to a specific typing from UpperCamel to lower Snake etc.
     * @param func the function that will be called.
     */
    public void setCustomNameProcessorMethod(@NotNull Function<Method, String> func) {
        assert func != null : functionNotNullErrorMessage;
        this.fallbackCustomMethodNameProcessor = func;
    }

    private String getDefault(Field field) {
        switch (fallbackType) {
            case EMPTY:
                return "";
            case NAME_METHOD:
                String result1 = fallbackMethodField.getOrDefault(field, input -> "").apply(field);
                assert result1 != null : functionReturnNullErrorMessage;
                return result1;
            case GET_NAME:
                return field.getName();
            case CUSTOM_FIELD_PROCESSOR:
                if (fallbackCustomFieldNameProcessor == null) {
                    return "";
                }
                return fallbackCustomFieldNameProcessor.apply(field);
            case AUTO:
                String result2 = fallbackMethodField.getOrDefault(field, input -> "").apply(field);
                assert result2 != null : functionReturnNullErrorMessage + " (fallbackMethodField)";

                if (result2.equalsIgnoreCase("") && fallbackCustomFieldNameProcessor != null) {
                    result2 = fallbackCustomFieldNameProcessor.apply(field);
                }
                assert result2 != null : functionReturnNullErrorMessage + " (fallbackCustomFieldNameProcessor)";

                if (result2.equalsIgnoreCase("")) {
                    result2 = field.getName();
                }
                return result2;
            default:
                throw new RuntimeException("Fallback Type can not be null.");
        }
    }

    private String getDefault(Method method) {
        switch (fallbackType) {
            case EMPTY:
                return "";
            case NAME_METHOD:
                String result1 = fallbackMethodMethod.getOrDefault(method, input -> "").apply(method);
                assert result1 != null : functionReturnNullErrorMessage;
                return result1;
            case GET_NAME:
                return method.getName();
            case CUSTOM_FIELD_PROCESSOR:
                if (fallbackCustomMethodNameProcessor == null) {
                    return "";
                }
                return fallbackCustomMethodNameProcessor.apply(method);
            case AUTO:
                String result2 = fallbackMethodMethod.getOrDefault(method, input -> "").apply(method);
                assert result2 != null : functionReturnNullErrorMessage + " (fallbackMethodMethod)";

                if (result2.equalsIgnoreCase("") && fallbackCustomMethodNameProcessor != null) {
                    result2 = fallbackCustomMethodNameProcessor.apply(method);
                }
                assert result2 != null : functionReturnNullErrorMessage + " (fallbackCustomMethodNameProcessor)";

                if (result2.equalsIgnoreCase("")) {
                    result2 = method.getName();
                }
                return result2;
            default:
                throw new RuntimeException("Fallback Type can not be null.");
        }
    }

    private String getDefault(Class clazz) {
        switch (fallbackType) {
            case EMPTY:
                return "";
            case NAME_METHOD:
                String result1 = fallbackMethodClass.getOrDefault(clazz, input -> "").apply(clazz);
                assert result1 != null : functionReturnNullErrorMessage;
                return result1;
            case GET_NAME:
                return clazz.getSimpleName();
            case CUSTOM_FIELD_PROCESSOR:
                if (fallbackCustomClassNameProcessor == null) {
                    return "";
                }
                return fallbackCustomClassNameProcessor.apply(clazz);
            case AUTO:
                String result2 = fallbackMethodClass.getOrDefault(clazz, input -> "").apply(clazz);

                assert result2 != null : functionReturnNullErrorMessage + " (fallbackMethodClass)";

                if (result2.equalsIgnoreCase("") && fallbackCustomClassNameProcessor != null) {
                    result2 = fallbackCustomClassNameProcessor.apply(clazz);
                }
                assert result2 != null : functionReturnNullErrorMessage + " (fallbackCustomClassNameProcessor)";

                if (result2.equalsIgnoreCase("")) {
                    result2 = clazz.getSimpleName();
                }
                return result2;
            default:
                throw new RuntimeException("Fallback Type can not be null.");
        }

    }

    public static Field getField(Class clazz, String fieldName) {
        try {
            return clazz.getField(fieldName);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }
    public static Method getMethod(Class clazz, String methodName, Class... arguments) {
        try {
            return clazz.getMethod(methodName, arguments);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
    /**
     * Type of fallbacks if name was not found in the {@link FieldTranslator} class.
     */
    public enum FallbackType {
        /**
         * EMPTY will allways return a Empty String.
         */
        EMPTY,
        /**
         * will take the given Method in the {@link FieldTranslator} Class and if there is none, it will return null
         */
        NAME_METHOD,
        /**
         * This will call the getName bzw. getSimpleName classes on the Field Method or Class Objects.
         */
        GET_NAME,
        /**
         * This will try to find a custom method for processing the name of the field, method or class. If no method is set it will return a Empty string.
         */
        CUSTOM_FIELD_PROCESSOR,
        /**
         * This will try to get it via {@link FallbackType#NAME_METHOD}, if that fails it will try {@link FallbackType#CUSTOM_FIELD_PROCESSOR} and that fails aswell, it will take {@link FallbackType#GET_NAME}.
         */
        AUTO
    }
}
