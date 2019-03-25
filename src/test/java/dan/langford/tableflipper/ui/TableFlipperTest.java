package dan.langford.tableflipper.ui;

import javafx.stage.Stage;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.ListViewMatchers;

@ExtendWith(ApplicationExtension.class)
public class TableFlipperTest {

    @Start
    void onStart(Stage stage) throws Exception {
        new TableFlipperApp().start(stage);
    }

    @Test
    void should_contain_list_items() {

        // expect:
        FxAssert.verifyThat("#tableList",  CoreMatchers.not(ListViewMatchers.isEmpty()));
    }

    @Test
    void should_filter_away_items(FxRobot robot) {

        // when:
        robot.clickOn("#filter");
        robot.write("asdfasdfasdfasdf");

        FxAssert.verifyThat("#tableList",  ListViewMatchers.isEmpty());


    }

}
