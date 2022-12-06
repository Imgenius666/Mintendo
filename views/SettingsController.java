package views;


import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController {
    private Scene scene;
    private Parent root;
    @FXML
    private ImageView close_button;
    @FXML
    private BorderPane Settings_Pane;

    public void change_color_mode(){

    }

    public void close_stage() {
        Stage stage = (Stage) Settings_Pane.getScene().getWindow();
        stage.close();
    }
}
