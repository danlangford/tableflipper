package dan.langford.tableflipper.plugin;


import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RollPluginTest {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Test
    public void testExpressions() {
        TomPlugin p = new RollPlugin();

        // unfortunately invalid expressions will not always throw exceptions
        //

        List.of("78", "1d20", "2d6", "3d12+3", "4d4-4", "6d6y").forEach(expr -> {
            assertDoesNotThrow(() -> {
                log.info("attempting expr [{}]",expr);
                String result = p.resolve(expr).orElseThrow();
                log.info("result of expr [{}] is [{}]", expr, result);
                Integer.parseInt(result);
            });
        });

        assertThrows(IllegalStateException.class, () -> p.resolve("5dz"));

    }
}
