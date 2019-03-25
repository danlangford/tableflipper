package dan.langford.tableflipper.core.plugin;

import com.bernardomg.tabletop.dice.parser.DefaultDiceNotationExpressionParser;
import com.bernardomg.tabletop.dice.parser.DiceNotationExpressionParser;
import com.bernardomg.tabletop.dice.roller.DefaultRoller;

import java.util.Optional;

public class RollPlugin implements TomPlugin {

    private final DiceNotationExpressionParser diceNotation = new DefaultDiceNotationExpressionParser(new DefaultRoller());

    @Override
    public Optional<String> resolve(String expression, boolean verbose) {
        Integer roll = diceNotation.parse(expression).roll();
        return Optional.of(verbose ? expression+'='+roll : roll.toString());
    }
}
