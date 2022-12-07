package views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangeThemeController implements Initializable {
    @FXML
    private ListView<String> theme_view;
    TetrisView view;
    String[] themes = {"8-bit", "Piano", "Orchestra","Western"};
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        theme_view.getItems().addAll(themes);
        theme_view.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
    public void setView(TetrisView view) {
        this.view = view;
    }
    @FXML
    public void confirm(){
        int val = theme_view.getSelectionModel().getSelectedIndex();
        view.setMC(val);
        Stage stage = (Stage)(theme_view).getScene().getWindow();
        stage.close();
    }
}
