package dan.langford.tableflipper.core.plugin;

import dan.langford.tableflipper.core.TomService;
import dan.langford.tableflipper.core.tom.TableObjectModel.Table;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TablePlugin implements TomPlugin {

  private final Logger log = LoggerFactory.getLogger(getClass());
  private final TomService toms;
  private final RollPlugin roll;

  @Inject
  public TablePlugin(TomService toms, RollPlugin roll) {
    this.toms = toms;
    this.roll = roll;
  }

  @Override
  public Optional<String> resolve(String tableName, boolean verbose) {

    final String[] tableResult = new String[1];
    Table table = toms.getModel().getTables().get(tableName);
    if (table == null) {
      log.debug("table {} not found", tableName);
      tableResult[0] = null;
    } else {
      if (table.getRoll() != null && table.getResults() != null) {
        String expr = table.getRoll();
        roll.resolve(expr, false)
            .ifPresentOrElse(
                rollResult -> {
                  log.debug("rolled {}={} for table {}", expr, rollResult, tableName);
                  String rollGroup = findRollGroup(table, Integer.parseInt(rollResult));
                  tableResult[0] = table.getResults().get(rollGroup);
                },
                () -> {
                  log.debug("problem rolling {}", expr);
                  tableResult[0] = null;
                });
      } else if (table.getOneOf() != null) {
        String expr = "1d" + table.getOneOf().size();
        roll.resolve(expr, false)
            .ifPresentOrElse(
                rollResult -> {
                  log.debug("rolled {}={} for table {}", expr, rollResult, tableName);
                  tableResult[0] = table.getOneOf().get(Integer.parseInt(rollResult) - 1);
                },
                () -> {
                  log.debug("problem rolling {}", expr);
                  tableResult[0] = null;
                });
      } else {
        log.debug("not enough info to roll table");
        tableResult[0] = null;
      }
    }
    return Optional.ofNullable(tableResult[0]);
  }

  private boolean isGroupMatch(int roll, Map.Entry<String, String> e) {
    String[] parts = e.getKey().split("\\D"); // \D = not digit
    int min = Integer.parseInt(parts[0].equals("00") ? "100" : parts[0]);
    int max = parts.length == 1 ? min : Integer.parseInt(parts[1].equals("00") ? "100" : parts[1]);
    return roll >= min && roll <= max;
  }

  private String findRollGroup(Table table, int roll) {
    return table.getResults().entrySet().stream()
        .filter(e -> isGroupMatch(roll, e))
        .findFirst()
        .map(Map.Entry::getKey)
        .orElseThrow(RuntimeException::new);
  }

  public Collection<String> getTableNames() {
    return toms.getModel().getTables().keySet();
  }
}
