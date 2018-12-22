package dan.langford.tableflipper.ui;

import dan.langford.tableflipper.core.service.DirectoryService;
import dan.langford.tableflipper.core.service.TemplateService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import jdk.nashorn.internal.objects.NativeString;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

public class SceneController {

    @Inject
    private DirectoryService dir;

    @Inject
    private TemplateService tmplt;
    
    @FXML
    private Label jVersionLabel;

    @FXML
    private ListView<String> tableList;

//    @FXML
//    private TreeView<String> tableTree;

    @FXML
    private TextArea tableResults;

    @FXML
    private TextField filter;


    @FXML
    public void handleClick(MouseEvent event){
        String selected = tableList.getSelectionModel().getSelectedItem();
        if(selected!=null&&selected.trim().length()>0) {
            tableResults.setText(tmplt.rollTable(selected));
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
        jVersionLabel.setText("Hello, JavaFX " + javafxVersion + " Running on Java " + javaVersion + ".");

        entireList = new ArrayList<String>(dir.getTableNames());
        filteredList = entireList;

        tableList.setItems(FXCollections.observableList(filteredList));

//        TreeItem<String> root = new TreeItem<>("tables");
//        for (String name : filteredList) {
//            TreeItem<String> temp = root;
//            for (String part : name.split("/")) {
//                Optional<TreeItem<String>> child = temp.getChildren().stream().filter(s -> s.getValue().equals(part)).findAny();
//                if (child.isPresent()) {
//                    temp = child.get();
//                } else {
//                    TreeItem<String> tempChild = new TreeItem<>(part);
//                    temp.getChildren().add(tempChild);
//                    temp = tempChild;
//                }
//            }
//        }
//        tableTree.setRoot(root);

    }
}
