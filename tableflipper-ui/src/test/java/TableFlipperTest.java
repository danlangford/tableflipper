import dan.langford.tableflipper.ui.TableFlipperApp;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.hamcrest.CoreMatchers.not;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.ListViewMatchers.isEmpty;

@ExtendWith(ApplicationExtension.class)
public class TableFlipperTest {

    @Start
    void onStart(Stage stage) throws Exception {
        new TableFlipperApp().start(stage);
    }

    @Test
    void should_contain_list_items() {

        // expect:
        verifyThat("#tableList",  not(isEmpty()));
    }

    @Test
    void should_filter_away_items(FxRobot robot) {

        // when:
        robot.clickOn("#filter");
        robot.write("asdfasdfasdfasdf");

        verifyThat("#tableList",  isEmpty());


    }

}
