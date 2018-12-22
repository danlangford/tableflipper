package dan.langford.tableflipper.core.service;

import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.text.MessageFormat.format;
import static java.util.Arrays.asList;
import static org.slf4j.LoggerFactory.getLogger;

@Singleton
public class TemplateService {

    private static final Logger log = getLogger(TemplateService.class);
    private final DirectoryService dir;
    private final TableService tables;
    private final DiceService dice;

    private final Pattern pattern = Pattern.compile("(<[\\w\\d\\s+-/]+>)");

    @Inject
    public TemplateService(DirectoryService dir, TableService tables, DiceService dice) {
        this.dir = dir;
        this.tables = tables;
        this.dice = dice;
    }

    public String rollTable(String tableName, Map<String,String> vars){
        return processRaw(tables.roll(tableName),vars);
    }

    public String processNamed(String templateName, Map<String,String> vars){
        String template = dir.getTemplate(templateName);
        if(template==null) {
            throw new NullPointerException(format("cannot find {0} in templateDirectory 404_NOT_FOUND", templateName));
        }
        return processRaw(template, vars);
    }

    public String processRaw(String templateExpr, Map<String,String> vars){
        String fullyResolved;
        Matcher m = pattern.matcher(templateExpr);
        if (m.find()) {
            String fragment = m.group();
            String resolved = resolve(fragment, vars);
            String newTemplateExpr = m.replaceFirst(resolved);
            fullyResolved = processRaw(newTemplateExpr, vars);
        } else {
            fullyResolved = templateExpr;
        }
        return fullyResolved;
    }

    private String resolve(String fragment, Map<String, String> vars) {
        if (vars==null) {
            vars = new HashMap<>();
        }
        String[] parts = fragment.replaceAll("[<>]","").split("\\s");
        log.debug("fragment={}", asList(parts));
        String resolved;
        switch (parts[0]) {
            case "table":
                resolved = tables.roll(parts[1]);
                break;
            case "roll":
                resolved = format("({0})", dice.roll(parts[1]));
                break;
            case "template":
                resolved = dir.getTemplate(parts[1], format("[template {0} NOT FOUND 404_NOT_FOUND]", parts[1]));
                break;
            default:
                resolved = format("[{0} {1} CMD NOT FOUND 404_NOT_FOUND]", parts[0], parts[1]);
                break;
        }
        return resolved;
    }

    public String processNamed(String templateName) {
        return processNamed(templateName, Collections.EMPTY_MAP);
    }
    public String rollTable(String tableName) {
        return rollTable(tableName, Collections.EMPTY_MAP);
    }
}
