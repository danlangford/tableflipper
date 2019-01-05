package dan.langford.tableflipper.ui;

import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.options.MutableDataSet;
import dan.langford.tableflipper.TableFlipper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebView;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.text.MessageFormat.format;
import static java.util.stream.Collectors.toList;

public class SceneController {

    @Inject
    private TableFlipper flipper;
    
    @FXML
    private Label jVersionLabel;

    @FXML
    private ListView<String> tableList;

    @FXML
    private WebView webView;

    @FXML
    private TextField filter;

    @FXML
    private TextFlow textFlow;

    @FXML
    private MenuBar menuBar;

    MutableDataSet options = new MutableDataSet();

    @FXML
    public void handleClick(MouseEvent event){
        String selected = tableList.getSelectionModel().getSelectedItem();
        if(selected!=null&&selected.trim().length()>0) {
            String rollResult = flipper.rollOnTable(selected);
            Document node = VladschUtils.jfxParser().parse(rollResult);
            String html = VladschUtils.jfxRenderer().render(node);
            webView.getEngine().loadContent(html);


            Collection<? extends Node> children = new TextFlowRenderContext().render(node);
            textFlow.getChildren().setAll(children);

        }
    }

    @FXML
    public void handleAction(ActionEvent action) {
        doFilter();
    }

    @FXML
    public void handleKeyTyped(KeyEvent key) {
        doFilter();
    }

    private List<String> entireList;
    private List<String> filteredList;

    private void doFilter(){
        String filterText = filter.getText();
        if (filterText.trim().length()==0) {
            filteredList=entireList;
        } else {
            filteredList=entireList.stream()
                    .filter(s -> s.toLowerCase().contains(filterText.toLowerCase()))
                    .collect(toList());
        }
        tableList.setItems(FXCollections.observableList(filteredList));
    }
    
    public void initialize() {

        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        jVersionLabel.setText(format("JavaFX {0} Running on Java {1}.", javafxVersion, javaVersion));

        entireList = new ArrayList<>(flipper.getTableNames());
        filteredList = entireList;

        tableList.setItems(FXCollections.observableList(filteredList));

    }
}
