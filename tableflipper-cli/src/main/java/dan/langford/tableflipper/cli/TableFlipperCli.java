package dan.langford.tableflipper.cli;

import dan.langford.tableflipper.core.service.DirectoryService;
import dan.langford.tableflipper.core.service.TemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;

import static com.google.inject.Guice.*;

public class TableFlipperCli {

    public static void main(String...args){
        createInjector().getInstance(TableFlipperCli.class).run();
    }

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final TemplateService tmplt;
    private final DirectoryService dir;
    private final DmInputService dm;

    @Inject
    public TableFlipperCli(TemplateService tmplt, DirectoryService dir, DmInputService dm) {
        this.tmplt = tmplt;
        this.dir = dir;
        this.dm = dm;
    }

    private void run() {

        String doExit="";
        do {
            String decision = dm.promptFor(new ArrayList<>(dir.getTableNames()));
            log.debug("you selected {}", decision);
            dm.say("\nRoll on Table {} is {}\n", decision, tmplt.rollTable(decision));
            doExit = dm.ask("press [Enter] to continue, [x] to exit.");
        } while (!doExit.toLowerCase().contains("x"));

        log.debug("exiting due to response: {}", doExit);

    }
}
