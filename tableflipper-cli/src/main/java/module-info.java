module dan.langford.tableflipper.cli {

    requires dan.langford.tableflipper;

    requires org.slf4j;
    requires com.google.guice;
    requires javax.inject;

    exports dan.langford.tableflipper.cli to com.google.guice;
}
