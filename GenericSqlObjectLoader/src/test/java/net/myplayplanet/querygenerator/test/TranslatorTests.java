package net.myplayplanet.querygenerator.test;

import net.myplayplanet.querygenerator.api.FieldTranslator;
import net.myplayplanet.querygenerator.test.testclasses.SimpleSample;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TranslatorTests {
    @Test
    public void test_simple_add_class_and_get_class() {
        //ARRANGE
        FieldTranslator translator = new FieldTranslator();
        translator.setName(SimpleSample.class, "some_simple_sample");

        //ACT
        String name = translator.getName(SimpleSample.class);

        //Assert
        Assert.assertEquals("some_simple_sample", name);
    }
    @Test
    public void test_simple_add_method_and_get_method() {
        //ARRANGE
        FieldTranslator translator = new FieldTranslator();
        Method method = FieldTranslator.getMethod(SimpleSample.class, "doSmth", int.class);
        translator.setName(method, "do_something");

        //ACT
        String name = translator.getName(method);

        //ASSERT
        Assert.assertEquals("do_something", name);
    }
    @Test
    public void test_simple_add_field_and_get_field() {
        //ARRANGE
        FieldTranslator translator = new FieldTranslator();
        Field field = FieldTranslator.getField(SimpleSample.class, "someString");
        translator.setName(field, "some_random_string");

        //ACT
        String name = translator.getName(field);

        //ASSERT
        Assert.assertEquals("some_random_string", name);
    }

}
