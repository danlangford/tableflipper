package dan.langford.tableflipper.core;

import dan.langford.tableflipper.cli.TableFlipperCli;
import dan.langford.tableflipper.ui.TableFlipperApp;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class TableFlipperLauncher {

  // i once saw something that explained an error i was seeing
  // there were some javafx issues and the resolution was to have a seperate class' main method
  // call over into the primary Application
  // the workaround worked
  // and then later i noticed i did not need this
  // so something changed. i dont know what
  // thats why this class remains for now

  // edit: i found it and it had to do with SHADE plugin (possibly assembly plugin as well would
  // have this problem)
  // https://stackoverflow.com/questions/52653836/maven-shade-javafx-runtime-components-are-missing

  // in addition to accomplishing the above mentioned workaround... this class is being enhanced to
  // also serve as a primary entry point for parsing command line options since we merged the CLI
  // and UI

  public static void main(String[] args) throws ParseException {

    // create Options object
    Options options = new Options();

    options.addOption(
        Option.builder("cli").hasArg(false).desc("run as command line interface").build());
    options.addOption(
        Option.builder("ui").hasArg(false).desc("run with graphical (windowed) interface").build());

    CommandLineParser parser = new DefaultParser();
    CommandLine cmd = parser.parse(options, args);

    if (cmd.hasOption("cli")) {
      TableFlipperCli.main(args);
    } else {
      TableFlipperApp.main(args);
    }
  }
}
