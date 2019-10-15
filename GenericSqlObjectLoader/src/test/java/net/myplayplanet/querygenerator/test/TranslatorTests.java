package net.myplayplanet.querygenerator.test;

import net.myplayplanet.querygenerator.api.FieldTranslator;
import net.myplayplanet.querygenerator.test.testclasses.SimpleSample;
import org.junit.Assert;
import org.junit.Test;

public class TranslatorTests {
    @Test
    public void test_simple_addClass_and_getClass() {
        FieldTranslator translator = new FieldTranslator();

        translator.setName(SimpleSample.class, "some_simple_sample");

        Assert.assertEquals("some_simple_sample", translator.getName(SimpleSample.class));
    }

}
