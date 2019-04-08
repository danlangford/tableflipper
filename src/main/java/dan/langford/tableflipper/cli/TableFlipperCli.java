package dan.langford.tableflipper.cli;

import static com.google.inject.Guice.createInjector;

import com.google.inject.Module;
import dan.langford.tableflipper.core.TableFlipper;
import java.util.ArrayList;
import java.util.Scanner;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableFlipperCli {

  public static void main(String... args) {
    //        // maybe someday we can build a Provider that just passes through to ServiceLoader?
    //        Injector injector = createInjector((Module) b ->
    // ServiceLoader.load(TableFlipper.class).findFirst().ifPresent(tf ->
    // b.bind(TableFlipper.class).toInstance(tf)));
    //        injector.getInstance(TableFlipperCli.class).run();

    createInjector(
            (Module)
                binder -> {
                  binder.bind(Scanner.class).toInstance(new Scanner(System.in));
                })
        .getInstance(TableFlipperCli.class)
        .run();
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

    String choice = "";
    String decision = "";
    do {
      if (!choice.toLowerCase().equals("r")) {
        decision = dm.promptFor(new ArrayList<>(flipper.getTableNames()));
        log.debug("you selected {}", decision);
      }
      dm.say("\nRoll on Table {} is {}\n", decision, flipper.rollOnTable(decision));
      choice = dm.ask("press [Enter] for another table, [r] to reroll the table, [x] to exit.");
    } while (!choice.toLowerCase().equals("x"));

    log.debug("exiting due to response: {}", choice);
  }
}
