package dan.langford.tableflipper.core.service;

import com.bernardomg.tabletop.dice.parser.DefaultDiceNotationExpressionParser;
import com.bernardomg.tabletop.dice.parser.DiceNotationExpressionParser;
import com.bernardomg.tabletop.dice.roller.DefaultRoller;
import dan.langford.tableflipper.core.model.RollResult;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DiceService {

    @Inject
    public DiceService() {
    }

    private final DiceNotationExpressionParser diceNotation = new DefaultDiceNotationExpressionParser(new DefaultRoller());

    public RollResult roll(String expression) {
        return new RollResult(expression, diceNotation.parse(expression).roll());
    }

}
