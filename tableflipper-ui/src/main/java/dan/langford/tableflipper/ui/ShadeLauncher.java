package dan.langford.tableflipper.ui;

public class ShadeLauncher {

    // i once saw something that explained an error i was seeing
    // there were some javafx issues and the resolution was to have a seperate class' main method
    // call over into the primary Application
    // the workaround worked
    // and then later i noticed i did not need this
    // so something changed. i dont know what
    // thats why this class remains for now

    // edit: i found it and it had to do with SHADE plugin (possibly assembly plugin as well would have this problem)
    // https://stackoverflow.com/questions/52653836/maven-shade-javafx-runtime-components-are-missing

    public static void main(String[] args) {
        TableFlipperApp.main(args);
    }

}
