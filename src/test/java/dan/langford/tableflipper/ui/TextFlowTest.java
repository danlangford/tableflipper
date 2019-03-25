package dan.langford.tableflipper.ui;

import com.vladsch.flexmark.util.ast.Document;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.TextFlowMatchers;
import org.testfx.util.DebugUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
public class TextFlowTest {

    private Logger log = LoggerFactory.getLogger(TextFlowTest.class);
    TextFlow textFlow;

    @Start
    public void onStart(Stage stage) {
        textFlow = new TextFlow();
        textFlow.setId("textFlow");
        Scene scene = new Scene(textFlow, 640, 480);
        scene.getStylesheets().add(getClass().getResource("/markdown.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void parseMarkdownTest(FxRobot robot) {

        Document doc = VladschUtils.jfxParser().parse("Hello *World* Nice **Picnic**");
        List<Text> texts = new TextFlowRenderContext().render(doc);

        robot.interact(() -> {
            textFlow.getChildren().setAll(texts);
        });

        assertEquals(4, texts.size());
        assertTrue(texts.get(0).getStyleClass().isEmpty());
        assertTrue(texts.get(1).getStyleClass().contains("emphasis"));
        assertTrue(texts.get(2).getStyleClass().isEmpty());
        assertTrue(texts.get(3).getStyleClass().contains("strongEmphasis"));

        FxAssert.verifyThat(textFlow, TextFlowMatchers.hasText("Hello World Nice Picnic"));

    }

    @Test
    public void headerMarkdownTest(FxRobot robot) throws InterruptedException {



        Document doc = VladschUtils.jfxParser().parse("A First Level Header\n" +
                "====================\n" +
                "\n" +
                "A Second Level Header\n" +
                "---------------------\n" +
                "\n" +
                "Now is the time for all good men to come to\n" +
                "the aid of their country. This is just a\n" +
                "regular paragraph.\n" +
                "\n" +
                "The quick brown fox jumped over the lazy\n" +
                "dog's back.\n" +
                "\n" +
                "### Header 3\n" +
                "\n" +
                "> This is a blockquote.\n" +
                "> \n" +
                "> This is the second paragraph in the blockquote.\n" +
                ">\n" +
                "> ## This is an H2 in a blockquote" +
                "\n\n\n\n" +
                "Some of these words *are emphasized*.\n" +
                "Some of these words _are emphasized also_.\n" +
                "\n" +
                "Use two asterisks for **strong emphasis**.\n" +
                "Or, if you prefer, __use two underscores instead__.");
        List<Text> texts = new TextFlowRenderContext().render(doc);

        robot.interact(() -> {
            textFlow.getChildren().setAll(texts);
        });

        StringBuilder sb = new StringBuilder();
        DebugUtils.saveWindow().apply(sb);
        log.info("screenshot? {}",sb);

        Thread.sleep(5_000L);

        FxAssert.verifyThat(textFlow, TextFlowMatchers.hasText("Hello World Nice Picnic"));

    }

}
