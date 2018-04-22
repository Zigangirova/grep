import org.junit.Test;

import static org.junit.Assert.*;

public class GrepTest {

    Grep grep = new Grep();

    @Test
    public void testConfig() throws Exception{
        String[] args = {"^[а-яa-z0-9_-]{3,15}$ ", "-i", "input.txt"};
        grep.config(args);

        assertTrue(grep.param_i);
        assertFalse(grep.param_r);
        assertFalse(grep.param_v);
        assertTrue(grep.word.length() > 0);
    }


    @Test
    public void testNullStrings() throws Exception {
        String[] args = {};
        Grep _grep =  new Grep();

        _grep.config(args);

        assertEquals("", grep.word);
    }

    @Test
    public void testFilter() throws Exception{
        grep.param_i = false;
        assertNotNull(grep.filter("old monkey", "monkey"));

        assertNull(grep.filter("", "monkey"));

        grep.param_i = true;
        assertNotNull(grep.filter("old MonKeY", "monkey"));

        grep.param_i = false;
        grep.param_v = true;
        assertNull(grep.filter("old monkey", "monkey"));

        grep.param_v = false;
        grep.param_r = true;
        assertNotNull(grep.filter("asd", "^[а-яa-z0-9_-]{3,15}$"));

        grep.param_i = true;
        assertNotNull(grep.filter("asd", "^[а-яa-z0-9_-]{3,15}$"));

        grep.param_i = false;
        grep.param_v = true;
        assertNull(grep.filter("asd", "^[а-яa-z0-9_-]{3,15}$"));

        grep.param_r = false;
        grep.param_i = true;
        assertNull(grep.filter("old MonKeY", "monkey"));
    }
}