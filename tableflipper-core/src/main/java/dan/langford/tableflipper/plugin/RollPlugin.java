
package dan.langford.tableflipper.plugin;

import com.bernardomg.tabletop.dice.history.RollHistory;
import com.bernardomg.tabletop.dice.interpreter.DiceInterpreter;
import com.bernardomg.tabletop.dice.interpreter.DiceRoller;
import com.bernardomg.tabletop.dice.parser.DefaultDiceParser;
import com.bernardomg.tabletop.dice.parser.DiceParser;

import java.util.Optional;

public class RollPlugin implements TomPlugin {

    private final DiceParser                   diceNotation = new DefaultDiceParser();

    private final DiceInterpreter<RollHistory> roller       = new DiceRoller();

    @Override
    public Optional<String> resolve(final String expression,
            final boolean verbose) {
        final RollHistory rolled = diceNotation.parse(expression, roller);
        final Integer roll = rolled.getTotalRoll();
        return Optional.of(verbose ? expression + '=' + roll : roll.toString());
    }
}
