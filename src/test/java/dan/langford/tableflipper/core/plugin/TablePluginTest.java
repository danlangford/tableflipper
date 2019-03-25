package dan.langford.tableflipper.core.plugin;


import dan.langford.tableflipper.core.TomService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TablePluginTest {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Test
    public void testExpressions() {

        TomService mockTom = new TomService();
        mockTom.load(new StringReader("tables:\n  one:\n    roll: 1d4\n    results:\n      1-4: testing1234"));

        TomPlugin p = new TablePlugin(mockTom, new RollPlugin());

        assertEquals("testing1234", p.resolve("one").orElseThrow());
        assertThrows(NoSuchElementException.class, () -> p.resolve("three").orElseThrow());

    }
}
