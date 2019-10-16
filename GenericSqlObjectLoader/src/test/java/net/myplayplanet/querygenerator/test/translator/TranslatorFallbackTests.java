package net.myplayplanet.querygenerator.test.translator;

import com.google.common.base.CaseFormat;
import net.myplayplanet.querygenerator.api.FieldTranslator;
import net.myplayplanet.querygenerator.test.testclasses.SimpleSample;
import net.myplayplanet.querygenerator.test.testclasses.SimpleSample2;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TranslatorFallbackTests {

    @Test
    public void test_fallback_function_name_method_for_type_class() {
        //ARANGE
        FieldTranslator translator = new FieldTranslator(FieldTranslator.FallbackType.NAME_METHOD);
        Class<SimpleSample> clazz = SimpleSample.class;
        translator.addFallback(clazz, input -> input.getSimpleName());

        //ACT
        String result = translator.getName(SimpleSample.class);

        //ASSERT
        Assert.assertEquals("SimpleSample", result);
    }

    @Test
    public void test_fallback_function_name_method_for_type_method() {
        //ARANGE
        FieldTranslator translator = new FieldTranslator(FieldTranslator.FallbackType.NAME_METHOD);
        Method method = FieldTranslator.getMethod(SimpleSample.class, "doSmth", int.class);
        translator.addFallback(method, input -> CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, input.getName()));

        //ACT
        String result = translator.getName(method);

        //ASSERT
        Assert.assertEquals("do_smth", result);
    }

    @Test
    public void test_fallback_function_name_method_field() {
        //ARANGE
        FieldTranslator translator = new FieldTranslator(FieldTranslator.FallbackType.NAME_METHOD);
        Field field =FieldTranslator.getField(SimpleSample.class, "someString");
        translator.addFallback(field, input -> CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, input.getName()));

        //ACT
        String result = translator.getName(field);

        //ASSERT
        Assert.assertEquals("some_string", result);
    }

    @Test
    public void test_fallback_function_name_method_class_other_class() {
        //ARANGE
        FieldTranslator translator = new FieldTranslator(FieldTranslator.FallbackType.NAME_METHOD);
        Class<SimpleSample> clazz = SimpleSample.class;
        translator.addFallback(clazz, input -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, input.getSimpleName()));

        //ACT
        String result = translator.getName(SimpleSample.class);

        //ASSERT
        Assert.assertEquals("simple_sample", result);
    }

    @Test
    public void test_fallback_get_name_class() {
        //ARANGE
        FieldTranslator translator = new FieldTranslator(FieldTranslator.FallbackType.GET_NAME);

        //ACT
        String result = translator.getName(SimpleSample.class);

        //ASSERT
        Assert.assertEquals("SimpleSample", result);
    }
    @Test
    public void test_fallback_get_name_field() {
        //ARANGE
        FieldTranslator translator = new FieldTranslator(FieldTranslator.FallbackType.GET_NAME);

        //ACT
        String result = translator.getName(FieldTranslator.getField(SimpleSample.class, "someString"));

        //ASSERT
        Assert.assertEquals("someString", result);
    }
    @Test
    public void test_fallback_get_name_method() {
        //ARANGE
        FieldTranslator translator = new FieldTranslator(FieldTranslator.FallbackType.GET_NAME);

        //ACT
        String result = translator.getName(FieldTranslator.getMethod(SimpleSample.class, "doSmth", int.class));

        //ASSERT
        Assert.assertEquals("doSmth", result);
    }


    @Test
    public void test_fallback_auto_class() {
        //ARANGE
        FieldTranslator translator = new FieldTranslator(FieldTranslator.FallbackType.AUTO);
        translator.addFallback(SimpleSample2.class, input -> input.getSimpleName() + "-abc");
        //ACT
        String r1 = translator.getName(SimpleSample.class);
        String r2 = translator.getName(SimpleSample2.class);

        //ASSERT
        Assert.assertEquals("SimpleSample", r1);
        Assert.assertEquals("SimpleSample2-abc", r2);
    }

    @Test
    public void test_fallback_auto_method() {
        //ARANGE
        FieldTranslator translator = new FieldTranslator(FieldTranslator.FallbackType.AUTO);
        Method someSimpleMethod = FieldTranslator.getMethod(SimpleSample2.class, "someSimpleMethod");
        translator.addFallback(someSimpleMethod, input -> input.getName() + "-baum");
        //ACT
        String r1 = translator.getName(FieldTranslator.getMethod(SimpleSample2.class, "someSimpleMethod"));
        String r2 = translator.getName(FieldTranslator.getMethod(SimpleSample.class, "doSmth", int.class));

        //ASSERT
        Assert.assertEquals("someSimpleMethod-baum", r1);
        Assert.assertEquals("doSmth", r2);
    }


    @Test
    public void test_fallback_auto_field() {
        //ARANGE
        FieldTranslator translator = new FieldTranslator(FieldTranslator.FallbackType.AUTO);
        Field field = FieldTranslator.getField(SimpleSample2.class, "someString");
        translator.addFallback(field, input -> input.getName() + "-baum");
        //ACT
        String r1 = translator.getName(FieldTranslator.getField(SimpleSample2.class, "someString"));
        String r2 = translator.getName(FieldTranslator.getField(SimpleSample.class, "someString2"));

        //ASSERT
        Assert.assertEquals("someString-baum", r1);
        Assert.assertEquals("someString2", r2);
    }

    @Test
    public void test_custom_names_fallback_auto() {
        //ARANGE
        FieldTranslator translator = new FieldTranslator(FieldTranslator.FallbackType.AUTO);

        translator.setCustomNameProcessorClass(input -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, input.getSimpleName()));
        translator.setCustomNameProcessorMethod(input -> CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, input.getName()));

        Field field = FieldTranslator.getField(SimpleSample2.class, "someString");
        Method someSimpleMethod = FieldTranslator.getMethod(SimpleSample2.class, "someSimpleMethod");
        Class clazz = SimpleSample.class;

        //ACT
        String r1 = translator.getName(field);
        String r2 = translator.getName(someSimpleMethod);
        String r3 = translator.getName(clazz);
        String r4 = translator.getName(String.class);

        //ASSERT
        Assert.assertEquals("someString", r1);
        Assert.assertEquals("some_simple_method", r2);
        Assert.assertEquals("simple_sample", r3);
        Assert.assertEquals("string", r4);
    }
    @Test
    public void test_custom_names_fallback_not_auto() {
        //ARANGE
        FieldTranslator translator = new FieldTranslator(FieldTranslator.FallbackType.CUSTOM_FIELD_PROCESSOR);

        translator.setCustomNameProcessorClass(input -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, input.getSimpleName()));
        translator.setCustomNameProcessorMethod(input -> CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, input.getName()));

        Field field = FieldTranslator.getField(SimpleSample2.class, "someString");
        Method someSimpleMethod = FieldTranslator.getMethod(SimpleSample2.class, "someSimpleMethod");
        Class clazz = SimpleSample.class;

        //ACT
        String r1 = translator.getName(field);
        String r2 = translator.getName(someSimpleMethod);
        String r3 = translator.getName(clazz);

        //ASSERT
        Assert.assertEquals("", r1);
        Assert.assertEquals("some_simple_method", r2);
        Assert.assertEquals("simple_sample", r3);
    }
}
