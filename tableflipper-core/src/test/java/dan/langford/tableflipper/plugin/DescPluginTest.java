package dan.langford.tableflipper.plugin;


import dan.langford.tableflipper.TomService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DescPluginTest {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Test
    public void testExpressions() {

        TomService mockTom = new TomService();
        mockTom.load(new StringReader("descriptions:\n  one: this is one\n  two: this is two"));

        TomPlugin p = new DescPlugin(mockTom);

        assertEquals("this is one", p.resolve("one").orElseThrow());
        assertEquals("this is two", p.resolve("two").orElseThrow());
        assertThrows(NoSuchElementException.class, () -> p.resolve("three").orElseThrow());

    }
}
