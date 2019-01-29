package dan.langford.tableflipper.plugin;


import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RollPluginTest {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Test
    public void testExpressions() {
        TomPlugin p = new RollPlugin();

        // valid expressions should run well and return a valid int
        List.of("78", "1d20", "2d6", "3d12+3", "4d4-4", "7d2*6", "8d5/2").forEach(expr -> {
            assertDoesNotThrow(() -> {
                log.info("attempting expr [{}]",expr);
                String result = p.resolve(expr).orElseThrow();
                log.info("result of expr [{}] is [{}]", expr, result);
                int x = parseInt(result);
                log.info("result [{}] parsed to int [{}]", result, x);

            });
        });

        // invalid expressions should throw exceptions
        List.of("5dz","6d6y", "9d12x3").forEach(expr -> {
            assertThrows(IllegalStateException.class, () -> {
                log.info("attempting expr [{}]",expr);
                String result = p.resolve(expr).orElseThrow();
            });
        });

    }
}
