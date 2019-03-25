module dan.langford.tableflipper {

    // export what you want a user or a DI framework may need to access
//    exports dan.langford.tableflipper;
//    exports dan.langford.tableflipper.core.plugin;

    requires dice;
    requires org.yaml.snakeyaml;
    requires javax.inject;
    requires io.github.classgraph;
    requires org.slf4j;
    opens dan.langford.tableflipper.core.tom to org.yaml.snakeyaml;

    // someday maybe play with "provides" and SPI stuff for "plugins"




    requires com.google.guice;
    opens dan.langford.tableflipper.cli to com.google.guice;




    requires javafx.controls;
    requires javafx.fxml;
    requires flexmark;
    requires flexmark.util;
    opens dan.langford.tableflipper.ui to com.google.guice, javafx.graphics, javafx.fxml;

}
