package net.myplayplanet.querygenerator.test;

import net.myplayplanet.querygenerator.api.FieldTranslator;
import net.myplayplanet.querygenerator.test.testclasses.SimpleSample;
import net.myplayplanet.querygenerator.test.testclasses.SimpleSample2;
import org.junit.Assert;
import org.junit.Test;

public class TranslatorTests {
    @Test
    public void test_simple_addClass_and_getClass() {
        FieldTranslator translator = new FieldTranslator();

        translator.setName(SimpleSample.class, "some_simple_sample");

        Assert.assertEquals("some_simple_sample", "some_simple_sample");
    }

    @Test
    public void test_simple_fallback() {
        FieldTranslator translator = new FieldTranslator(FieldTranslator.FallbackType.NAME_METHOD);

        Class<SimpleSample> clazz = SimpleSample.class;
        translator.setName(clazz, "some_simple_sample");

        translator.addFallbackForClass(clazz, input -> input.getSimpleName());

        Assert.assertEquals("some_simple_sample", "some_simple_sample");
    }
    @Test
    public void a() {
        try {
            if (SimpleSample.class.getField("someString") == SimpleSample2.class.getField("someString")) {
                System.out.println("true");
            }else {
                System.out.println("false");
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
