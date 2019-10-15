package net.myplayplanet.querygenerator.test;

import net.myplayplanet.querygenerator.api.FieldTranslator;
import net.myplayplanet.querygenerator.test.testclasses.SimpleSample;
import net.myplayplanet.querygenerator.test.testclasses.SimpleSample2;
import org.junit.Assert;
import org.junit.Test;

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
    }@Test
    public void test_fallback_function_name_method_method() {
        //ARANGE
        FieldTranslator translator = new FieldTranslator(FieldTranslator.FallbackType.NAME_METHOD);
        Method method = null;
        try {
            method = SimpleSample.class.getMethod("");
        } catch (NoSuchMethodException e) {
            Assert.fail("NoSuchMethodException");
        }
        translator.addFallbackForMethod(method, input -> input.getName());

        //ACT
        String result = translator.getName(SimpleSample.class);

        //ASSERT
        Assert.assertEquals("SimpleSample", result);
    }@Test
    public void test_fallback_function_name_method_field() {
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
    public void test_fallback_function_name_method_class_other_class() {
        //ARANGE
        FieldTranslator translator = new FieldTranslator(FieldTranslator.FallbackType.NAME_METHOD);
        Class<SimpleSample> clazz = SimpleSample.class;
        translator.addFallbackForClass(clazz, input -> input.getSimpleName());

        //ACT
        String result = translator.getName(SimpleSample2.class);

        //ASSERT
        Assert.assertEquals("", result);
    }

    @Test
    public void test_fallback_get_name() {
        //ARANGE
        FieldTranslator translator = new FieldTranslator(FieldTranslator.FallbackType.GET_NAME);
        Class<SimpleSample> clazz = SimpleSample.class;
        translator.addFallbackForClass(clazz, input -> input.getSimpleName());

        //ACT
        String result = translator.getName(SimpleSample.class);

        //ASSERT
        Assert.assertEquals("SimpleSample", result);
    }
}
