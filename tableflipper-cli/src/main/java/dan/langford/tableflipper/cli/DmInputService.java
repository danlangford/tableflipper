package dan.langford.tableflipper.cli;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Scanner;

import static java.util.stream.Collectors.toList;

@Singleton
// try to decide if we still need to ask for input on command line.
// i think we do need some easy way to select a table to be rolled
public class DmInputService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final Scanner scanner = new Scanner(System.in);

    @Inject
    public DmInputService(){}

    @Deprecated
    private <T extends Enum<T>> T enumMenu(Class<T> enumType) {
        for (T c : enumType.getEnumConstants()) {
            log.info("{0}: {1}", c.ordinal()+1, c.name());
        }
        int response = Integer.valueOf(scanner.nextLine().trim());
        return enumType.getEnumConstants()[response-1];
    }

    public String promptFor(List<String> items){

        List<String> filteredItems = items;
        String input = "";

        // continue the loop if we didnt get a number, or if the number is too large
        while (!NumberUtils.isDigits(input) || NumberUtils.toInt(input)>filteredItems.size()-1) {
            if(StringUtils.isBlank(input)) {
                say("clearing all filters");
                filteredItems = items;
            } else if (StringUtils.isAlphaSpace(input)) {
                say("applying filter: "+input);
                String $input = input;
                filteredItems = items.stream()
                        .filter(s -> StringUtils.containsIgnoreCase(s, $input))
                        .collect(toList());
                if(filteredItems.size()==0) {
                    say("no items match the filter of "+input);
                    say("clearing all filters");
                    filteredItems = items;
                }
            } else {
                say("invalid input");
            }
            say("\nEnter a Number to select an Item. Enter Text to filter your options.\n");
            for (int i = 0; i < filteredItems.size(); i++) {
                say(i+". "+filteredItems.get(i));
            }
            input = ask("please select an item");
        }
        String selection = filteredItems.get(NumberUtils.toInt(input));
        say("your selection: "+selection);
        return selection;
    }

    public void say(String msg, Object... args) {
        // TODO could we be more inefficient?
        String trueMsg = msg;
        for(Object o : args) {
            trueMsg = trueMsg.replaceFirst("\\{\\}", o.toString());
        }
        System.out.println(trueMsg);
    }

    public String ask(String prompt) {
        say(prompt);
        return scanner.nextLine();
    }
}
