package dan.langford.tableflipper.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.inject.Inject;

public class TableFlipperApp extends Application {

    @Inject
    private FXMLLoader fxmlLoader;

    @Override
    public void start(Stage primaryStage) throws Exception {

        new GuiceFx().init(this);

        Parent root = fxmlLoader.load(getClass().getResourceAsStream("scene.fxml"));
        primaryStage.setScene(new Scene(root));

        primaryStage.setTitle("Table Flipper");
        primaryStage.show();
    }

    public static void main(String...args){
        launch(args);
    }

}
