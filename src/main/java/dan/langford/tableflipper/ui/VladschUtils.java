package dan.langford.tableflipper.ui;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;
import java.util.ArrayList;

public class VladschUtils {

  private static HtmlRenderer RENDERER;
  private static Parser PARSER;
  private static MutableDataHolder OPTIONS;

  // set rendering options for JavaFX
  private static MutableDataHolder buildJfxOptions() {

    MutableDataSet dataSet = new MutableDataSet();
    ArrayList<Extension> extensions = new ArrayList<>();

    dataSet.set(Parser.PARSE_INNER_HTML_COMMENTS, true);
    dataSet.set(Parser.INDENTED_CODE_NO_TRAILING_BLANK_LINES, true);
    dataSet.set(HtmlRenderer.SUPPRESS_HTML_BLOCKS, false);
    dataSet.set(HtmlRenderer.SUPPRESS_INLINE_HTML, false);

    // Setup Block Quote Options
    dataSet.set(Parser.BLOCK_QUOTE_TO_BLANK_LINE, true);

    // Setup List Options for GitHub profile
    dataSet.set(Parser.LISTS_AUTO_LOOSE, false);
    dataSet.set(Parser.LISTS_AUTO_LOOSE, false);
    dataSet.set(Parser.LISTS_END_ON_DOUBLE_BLANK, false);

    dataSet.set(Parser.LISTS_BULLET_ITEM_INTERRUPTS_PARAGRAPH, false);
    dataSet.set(Parser.LISTS_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH, true);
    dataSet.set(Parser.LISTS_ORDERED_ITEM_DOT_ONLY, true);
    dataSet.set(Parser.LISTS_ORDERED_ITEM_INTERRUPTS_PARAGRAPH, false);
    dataSet.set(Parser.LISTS_ORDERED_ITEM_INTERRUPTS_ITEM_PARAGRAPH, true);
    dataSet.set(Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARAGRAPH, false);
    dataSet.set(Parser.LISTS_ORDERED_LIST_MANUAL_START, false);

    // disable fenced code blocks
    dataSet.set(Parser.MATCH_CLOSING_FENCE_CHARACTERS, false);
    dataSet.set(Parser.HEADING_NO_LEAD_SPACE, true);
    dataSet.set(Parser.THEMATIC_BREAK_RELAXED_START, true);
    dataSet.set(HtmlRenderer.INDENT_SIZE, 2);
    dataSet.set(HtmlRenderer.FENCED_CODE_LANGUAGE_CLASS_PREFIX, "");
    dataSet.set(HtmlRenderer.RENDER_HEADER_ID, true);
    dataSet.set(Parser.EXTENSIONS, extensions);

    // typically in markdown a single new line character is considered a soft break and doenst
    // produce a <br/>
    // but i want new lines to be a little easier.
    dataSet.set(HtmlRenderer.SOFT_BREAK, "<br />");

    return dataSet;
  }

  public static MutableDataHolder jfxOptions() {
    if (OPTIONS == null) {
      OPTIONS = buildJfxOptions();
    }
    return OPTIONS;
  }

  public static Parser jfxParser() {
    if (PARSER == null) {
      PARSER = Parser.builder(jfxOptions()).build();
    }
    return PARSER;
  }

  public static HtmlRenderer jfxRenderer() {
    if (RENDERER == null) {
      RENDERER = HtmlRenderer.builder(jfxOptions()).build();
    }
    return RENDERER;
  }
}
