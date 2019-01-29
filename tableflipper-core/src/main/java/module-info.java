module dan.langford.tableflipper {

    // export what you want a user or a DI framework may need to access
    exports dan.langford.tableflipper;
    exports dan.langford.tableflipper.plugin;

//    requires static lombok;

    requires dice;
    requires org.yaml.snakeyaml;
    requires org.slf4j;
    requires javax.inject;

    requires io.github.classgraph;

    exports dan.langford.tableflipper.tom to org.yaml.snakeyaml;

    // someday maybe play with "provides" and SPI stuff for "plugins"

}
