module dan.langford.tableflipper.ui {

    // do i need to export anything of mine?

    requires javafx.controls;
    requires javafx.fxml;
    requires javax.inject;
    requires com.google.guice;
    requires dan.langford.tableflipper;

    opens dan.langford.tableflipper.ui to com.google.guice, javafx.graphics, javafx.fxml;

}
