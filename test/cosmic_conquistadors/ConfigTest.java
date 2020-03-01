package cosmic_conquistadors;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import org.junit.Test;

public class ConfigTest {
    @Test
    public void testConfigParsing() throws ParseException {
        String stringValue = "a string value";
        int intValue = 42;
        double doubleValue = 3.141;
        String[] lines = {"# Test config", "myString = \"" + stringValue + "\"",
                          "myInt = " + Integer.toString(intValue),
                          "myDouble = " + Double.toString(doubleValue)};

        Config cfg = new Config();
        cfg.parseConfig(lines);

        assertEquals(stringValue, cfg.getString("myString"));
        assertEquals(intValue, cfg.getInt("myInt"));
        assertEquals(doubleValue, cfg.getDouble("myDouble"), 0);
    }

    @Test
    public void testConfigFlushing() throws ParseException {
        String stringValue = "a string value";
        int intValue = 42;
        double doubleValue = 3.141;
        String[] lines = {"# Test config", "myString = \"hello stringy world\"",
                          "myInt = 1234", "myDouble = 2.71"};

        Config cfg = new Config();
        cfg.setString("myString", stringValue);
        cfg.setInt("myInt", intValue);
        cfg.setDouble("myDouble", doubleValue);

        cfg.flushConfig(lines);
        assertEquals("# Test config", lines[0]);
        assertEquals("myString = \"" + stringValue + "\"", lines[1]);
        assertEquals("myInt = " + Integer.toString(intValue), lines[2]);
        assertEquals("myDouble = " + Double.toString(doubleValue), lines[3]);
    }
}
