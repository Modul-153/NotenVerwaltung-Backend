package net.myplayplanet.querygenerator.test;

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
        translator.addFallbackForClass(clazz, input -> input.getSimpleName());

        //ACT
        String result = translator.getName(SimpleSample.class);

        //ASSERT
        Assert.assertEquals("SimpleSample", result);
    }

    @Test
    public void test_fallback_function_name_method_for_type_method() {
        //ARANGE
        FieldTranslator translator = new FieldTranslator(FieldTranslator.FallbackType.NAME_METHOD);
        Method method = FieldTranslator.getMethod(SimpleSample.class, "doSmth", Integer.class);
        translator.addFallbackForMethod(method, input -> CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, input.getName()));

        //ACT
        String result = translator.getName(SimpleSample.class);

        //ASSERT
        Assert.assertEquals("do_smth", result);
    }

    @Test
    public void test_fallback_function_name_method_field() {
        //ARANGE
        FieldTranslator translator = new FieldTranslator(FieldTranslator.FallbackType.NAME_METHOD);
        Field field =FieldTranslator.getField(SimpleSample.class, "someString");
        translator.addFallbackForField(field, input -> CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, input.getName()));

        //ACT
        String result = translator.getName(SimpleSample.class);

        //ASSERT
        Assert.assertEquals("some_string", result);
    }

    @Test
    public void test_fallback_function_name_method_class_other_class() {
        //ARANGE
        FieldTranslator translator = new FieldTranslator(FieldTranslator.FallbackType.NAME_METHOD);
        Class<SimpleSample> clazz = SimpleSample.class;
        translator.addFallbackForClass(clazz, input -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, input.getSimpleName()));

        //ACT
        String result = translator.getName(SimpleSample2.class);

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
        String result = translator.getName(FieldTranslator.getMethod(SimpleSample.class, "doSmth"));

        //ASSERT
        Assert.assertEquals("doSmth", result);
    }


    @Test
    public void test_fallback_auto_method() {
        //ARANGE
        FieldTranslator translator = new FieldTranslator(FieldTranslator.FallbackType.AUTO);
        translator.addFallbackForClass(SimpleSample2.class, input -> input.getSimpleName() + "-abc");
        //ACT
        String result = translator.getName(SimpleSample.class);

        //ASSERT
        Assert.assertEquals("doSmth", result);
        Assert.assertEquals("SimpleSample2-abc", result);
    }

}
