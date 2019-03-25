package dan.langford.tableflipper;

import dan.langford.tableflipper.plugin.DescPlugin;
import dan.langford.tableflipper.plugin.RollPlugin;
import dan.langford.tableflipper.plugin.TablePlugin;
import dan.langford.tableflipper.plugin.TomPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.Reader;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.text.MessageFormat.format;
import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static java.util.stream.Collectors.toList;

/**
 * the "engine" that makes this work is the recursive templating. this class finds fragment tokens and resolves them with the necessary services. those services may someday be "plugins"
 */
public class TableFlipper {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final DescPlugin desc;
    private final TablePlugin table;
    private final RollPlugin roll;

    private Map<String, TomPlugin> plugins;

    private static final String ERR="[ERR problem with {0} {1}]";

    // match inside of < & > alphanumeric, whitespace, +, -, /, *, x, X
    // ya i know x, X, _ is captured in \w (word) but i like to be very declarative on what we support
    // tries to guarantee word-space-expression and as such that when split on a space it will only have two parts
    private final Pattern pattern = Pattern.compile("<(\\w+\\s+[\\w+\\-/*xX_]+)>");

    @Inject
    public TableFlipper(DescPlugin desc, TablePlugin table, RollPlugin roll) {
        this.desc = desc;
        this.table = table;
        this.roll = roll;
        plugins = Map.of("desc",desc,"table",table,"roll",roll);
    }

    public String rollOnTable(String tableName){
//        return recursiveTemplate(format("<table {0}>", tableName));
        // to avoid creating a string only to parse and split a string lets call the plugins directly
        return recursiveTemplate(table.resolve(tableName).orElse(format(ERR, "table", tableName)));
    }

    public String buildDesc(String descName) {
//        return recursiveTemplate(format("<desc {0}>", descName));
        // to avoid creating a string only to parse and split a string lets call the plugins directly
        return recursiveTemplate(desc.resolve(descName).orElse(format(ERR, "desc", descName)));
    }

    public String rollDice(String diceExpr) {
//        return recursiveTemplate(format("<roll {0}>", diceExpr));
        // to avoid creating a string only to parse and split a string lets call the plugins directly
        return recursiveTemplate(roll.resolve(diceExpr).orElse(format(ERR, "roll", diceExpr)));
    }

    public void addModel(Reader reader) {
        throw new IllegalStateException("not yet implemented");
    }

    private String recursiveTemplate(String templateExpr){
        String fullyResolved;
        Matcher m = pattern.matcher(templateExpr);
        if (m.find()) {
            String resolved = resolve(m.group());
            String newTemplateExpr = m.replaceFirst(resolved);
            fullyResolved = recursiveTemplate(newTemplateExpr);
        } else {
            fullyResolved = templateExpr;
        }
        return fullyResolved;
    }

    // this should be everything inside the < & >
    private String resolve(String template) {
        String[] parts = template.replaceAll("[<>]","").split("\\s");
        log.debug("parts={}", asList(parts));
        String pluginName = parts[0], pluginExpr = parts[1];
        return plugins.getOrDefault(pluginName, (i, v) -> empty()).resolve(pluginExpr).orElse(format(ERR, pluginName, pluginExpr));
    }

    public Collection<String> getTableNames() {
        return table.getTableNames().stream().sorted().collect(toList());
    }
}
