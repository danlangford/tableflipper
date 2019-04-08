package dan.langford.tableflipper.ui;

import com.vladsch.flexmark.ast.BlockQuote;
import com.vladsch.flexmark.ast.Emphasis;
import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ast.SoftLineBreak;
import com.vladsch.flexmark.ast.StrongEmphasis;
import com.vladsch.flexmark.util.ast.BlankLine;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextFlowRenderContext {

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public List<Text> render(Node node) {
    return render(node, List.of());
  }

  private List<Text> render(Node node, List<String> psuedoClasses) {

    List<Text> results = new ArrayList<>();

    log.debug("spotted {}", node.getClass());

    if (node instanceof Emphasis) {
      psuedoClasses = copyWith(psuedoClasses, "emphasis");
    } else if (node instanceof StrongEmphasis) {
      psuedoClasses = copyWith(psuedoClasses, "strongEmphasis");
    } else if (node instanceof BlockQuote) {
      psuedoClasses = copyWith(psuedoClasses, "blockQuote");
    } else if (node instanceof com.vladsch.flexmark.ast.Text) {
      return List.of(styledText(node.getChars(), psuedoClasses));
    } else if (node instanceof Heading) {
      int level = ((Heading) node).getLevel();
      psuedoClasses = copyWith(psuedoClasses, "heading" + level);
      return List.of(styledText(node.getChildChars(), psuedoClasses));
    } else if (node instanceof Paragraph) {
      results.add(styledText("\n", psuedoClasses));
    } else if (node instanceof SoftLineBreak) {
      results.add(styledText("\n", psuedoClasses));
    } else if (node instanceof BlankLine) {
      results.add(styledText("\n\n", psuedoClasses));
    } else {
      log.info("not handling {}", node.getClass());
    }

    List<String> p = psuedoClasses;
    node.getChildren()
        .forEach(
            n -> {
              results.addAll(render(n, p));
            });
    return results;
  }

  private Text styledText(BasedSequence chars, List<String> psuedoClasses) {
    return styledText(chars.toString(), psuedoClasses);
  }

  private Text styledText(String text, List<String> psuedoClasses) {
    Text t = new Text(text);
    t.getStyleClass().addAll(psuedoClasses);
    return t;
  }

  private <T> List<T> copyWith(List<T> list, T element) {
    List<T> temp = new ArrayList<>(list);
    temp.add(element);
    return temp;
  }
}
