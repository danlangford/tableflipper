package dan.langford.tableflipper.cli;

import dan.langford.tableflipper.TableFlipper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;

import static com.google.inject.Guice.createInjector;

public class TableFlipperCli {

    public static void main(String...args){
//        // maybe someday we can build a Provider that just passes through to ServiceLoader?
//        Injector injector = createInjector((Module) b -> ServiceLoader.load(TableFlipper.class).findFirst().ifPresent(tf -> b.bind(TableFlipper.class).toInstance(tf)));
//        injector.getInstance(TableFlipperCli.class).run();

        createInjector().getInstance(TableFlipperCli.class).run();

    }

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final TableFlipper flipper;
    private final DmInputService dm;

    @Inject
    public TableFlipperCli(TableFlipper flipper, DmInputService dm) {
        this.flipper = flipper;
        this.dm = dm;
    }

    private void run() {

        String doExit="";
        do {
            String decision = dm.promptFor(new ArrayList<>(flipper.getTableNames()));
            log.debug("you selected {}", decision);
            dm.say("\nRoll on Table {} is {}\n", decision, flipper.rollOnTable(decision));
            doExit = dm.ask("press [Enter] to continue, [x] to exit.");
        } while (!doExit.toLowerCase().contains("x"));

        log.debug("exiting due to response: {}", doExit);

    }
}
