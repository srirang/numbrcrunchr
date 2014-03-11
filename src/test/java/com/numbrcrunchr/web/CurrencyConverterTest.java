package com.numbrcrunchr.web;

import static org.junit.Assert.assertEquals;

import javax.faces.convert.ConverterException;

import org.junit.Test;

import com.numbrcrunchr.web.CurrencyConverter;

public class CurrencyConverterTest {
    private CurrencyConverter converter = new CurrencyConverter();

    @Test
    public void numberToString() {
        assertEquals("$0", converter.getAsString(null, null, (Number) 0));
        assertEquals("$0", converter.getAsString(null, null, (Number) null));
        assertEquals("$100,000", converter.getAsString(null, null, 100000));
        assertEquals("$10,000", converter.getAsString(null, null, 10000));
        assertEquals("$1", converter.getAsString(null, null, 1.232));
        assertEquals("$1", converter.getAsString(null, null, "1"));
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
    public void stringToNumber() {
        assertEquals(100000l,
                converter.getAsObject(null, null, "$100,000.12312"));
        assertEquals(100000l,
                converter.getAsObject(null, null, "AUD 100,000.12312"));
        assertEquals(100000l,
                converter.getAsObject(null, null, "100,000.12312"));
        assertEquals(1100000l,
                converter.getAsObject(null, null, "1100,000.12312"));
        assertEquals(0l, converter.getAsObject(null, null, ""));
        assertEquals(0l, converter.getAsObject(null, null, null));
        try {
            assertEquals(0l, converter.getAsObject(null, null, "abcd"));
        } catch (ConverterException e) {
            // Expected
        }

        try {
            assertEquals(0l, converter.getAsObject(null, null, "$123.456.789"));
        } catch (ConverterException e) {
            // Expected
        }
    }

    @Test
    public void errorsFromProduction() {
        assertEquals(0l, converter.getAsObject(null, null, "   "));
    }
}
