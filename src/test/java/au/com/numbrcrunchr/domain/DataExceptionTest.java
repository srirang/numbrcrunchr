package au.com.numbrcrunchr.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DataExceptionTest {
    @Test
    public void checkException() {
        DataException exception = new DataException("message");
        assertEquals("message", exception.getMessage());

        Exception e = new RuntimeException("message 1");
        exception = new DataException("message", e);
        assertEquals("message", exception.getMessage());
        assertEquals(e, exception.getCause());

        e = new RuntimeException("message 2");
        exception = new DataException(e);
        assertEquals(e.getClass().getName() + ": message 2",
                exception.getMessage());
        assertEquals(e, exception.getCause());
    }
}
