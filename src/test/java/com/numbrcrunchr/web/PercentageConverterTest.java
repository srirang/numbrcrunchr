package com.numbrcrunchr.web;

import static org.junit.Assert.assertEquals;

import javax.faces.convert.ConverterException;

import org.junit.Test;

import com.numbrcrunchr.web.PercentageConverter;

public class PercentageConverterTest {

    private PercentageConverter converter = new PercentageConverter();

    @Test
    public void checkObjectToString() {
        assertEquals("", converter.getAsString(null, null, null));
        assertEquals("0%", converter.getAsString(null, null, 0));
        assertEquals("9.99%", converter.getAsString(null, null, 9.99));
        assertEquals("0.01%", converter.getAsString(null, null, 0.01));
        assertEquals("0%", converter.getAsString(null, null, 0.002));
        try {
            converter.getAsString(null, null, new Object());
        } catch (ConverterException e) {
            // Expected
        }
        try {
            converter.getAsString(null, null, "aasfd");
        } catch (ConverterException e) {
            // Expected
        }
        try {
            converter.getAsString(null, null, "123 52");
        } catch (ConverterException e) {
            // Expected
        }
    }

    @Test
    public void checkStringToPercentage() {
        assertEquals(0, converter.getAsObject(null, null, ""));
        assertEquals(9.99, converter.getAsObject(null, null, "9.99%"));
        assertEquals(0.01, converter.getAsObject(null, null, "0.01%"));
        assertEquals(0.002, converter.getAsObject(null, null, "0.002%"));
        try {
            converter.getAsObject(null, null, null);
        } catch (ConverterException e) {
            // Expected
        }
        try {
            converter.getAsObject(null, null, "abcd");
        } catch (ConverterException e) {
            // Expected
        }
    }

    @Test
    public void errorsFromProduction() {
        assertEquals(0, converter.getAsObject(null, null, "   "));
    }
}
