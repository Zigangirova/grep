import org.junit.Test;

import static org.junit.Assert.*;

public class GrepTest {

    @Test
    public void testConfig() throws Exception{
        String[] args = {"^[а-яa-z0-9_-]{3,15}$ ", "-i", "input.txt"};
        Grep.config(args);

        assertTrue(Grep.param_i);
        assertFalse(Grep.param_r);
        assertFalse(Grep.param_v);
        assertTrue(Grep.word.length() > 0);
    }

    @Test
    public void testFilter() throws Exception{
        Grep.param_i = false;
        assertNotNull(Grep.filter("old monkey", "monkey"));

        Grep.param_i = true;
        assertNotNull(Grep.filter("old MonKeY", "monkey"));

        Grep.param_i = false;
        Grep.param_v = true;
        assertNull(Grep.filter("old monkey", "monkey"));

        Grep.param_v = false;
        Grep.param_r = true;
        assertNotNull(Grep.filter("asd", "^[а-яa-z0-9_-]{3,15}$"));
    }
}