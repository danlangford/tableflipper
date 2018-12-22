package dan.langford.tableflipper.core.service;

import dan.langford.tableflipper.core.model.RollResult;
import dan.langford.tableflipper.core.model.Table;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;

import static java.text.MessageFormat.format;
import static org.slf4j.LoggerFactory.getLogger;

@Singleton
public class TableService {

    private final Logger log = getLogger(this.getClass());

    private final DirectoryService dir;
    private final DiceService dice;

    @Inject
    public TableService(DirectoryService dir, DiceService dice) {
        this.dir = dir;
        this.dice = dice;
    }


    public String roll(String tableName) {

        String rollResult;
        Table table = dir.getTable(tableName);
        if (table==null) {
            rollResult = format("[table {0} not found 404_NOT_FOUND]", tableName);
        } else {
            String expr = table.getRoll();
            RollResult roll = dice.roll(expr);
            log.debug("rolled {} for table {}", roll, tableName);
            String rollGroup = findRollGroup(table, roll.getResult());

            rollResult = table.getResults().get(rollGroup);
        }

        return rollResult;
    }

    private boolean isGroupMatch(int roll, Map.Entry<String,String> e) {
        String[] parts = e.getKey().split("\\D"); // \D = not digit
        int min = Integer.parseInt(parts[0].equals("00")?"100":parts[0]);
        int max = parts.length == 1 ? min : Integer.parseInt(parts[1].equals("00")?"100":parts[1]);
        return roll >= min && roll <= max;
    }

    private String findRollGroup(Table table, int roll) {
        return table.getResults().entrySet().stream()
                .filter(e->isGroupMatch(roll,e))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElseThrow(RuntimeException::new);
    }

}
