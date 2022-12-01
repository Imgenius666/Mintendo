package views;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;


/** 
 * Save File View
 * 
 * Based on the Tetris assignment in the Nifty Assignments Database, authored by Nick Parlante
 */
public class SaveView {

    static String saveFileSuccess = "Saved board!!";
    static String saveFileExistsError = "Error: File already exists";
    static String saveFileNotSerError = "Error: File must end with .ser";
    private Label saveFileErrorLabel = new Label("");
    private Label saveBoardLabel = new Label(String.format("Enter name of file to save"));
    private TextField saveFileNameTextField = new TextField("");
    private Button saveBoardButton = new Button("Save board");
    TetrisView tetrisView;

     /**
         * Constructor
         * 
         * @param tetrisView master view
         */
    public SaveView(TetrisView tetrisView) {
        this.tetrisView = tetrisView;

        tetrisView.paused = true;
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(tetrisView.stage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.setPadding(new Insets(20, 20, 20, 20));
        dialogVbox.setStyle("-fx-background-color: #121212;");

        saveBoardLabel.setId("SaveBoard"); // DO NOT MODIFY ID
        saveFileErrorLabel.setId("SaveFileErrorLabel");
        saveFileNameTextField.setId("SaveFileNameTextField");
        saveBoardLabel.setStyle("-fx-text-fill: #e8e6e3;");
        saveBoardLabel.setFont(new Font(16));
        saveFileErrorLabel.setStyle("-fx-text-fill: #e8e6e3;");
        saveFileErrorLabel.setFont(new Font(16));
        saveFileNameTextField.setStyle("-fx-text-fill: #e8e6e3;");
        saveFileNameTextField.setFont(new Font(16));

        String boardName = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()) + ".ser";
        saveFileNameTextField.setText(boardName);

        saveBoardButton = new Button("Save board");
        saveBoardButton.setId("SaveBoard"); // DO NOT MODIFY ID
        saveBoardButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        saveBoardButton.setPrefSize(200, 50);
        saveBoardButton.setFont(new Font(16));
        saveBoardButton.setOnAction(e -> saveBoard());

        VBox saveBoardBox = new VBox(10, saveBoardLabel, saveFileNameTextField, saveBoardButton, saveFileErrorLabel);

        dialogVbox.getChildren().add(saveBoardBox);
        Scene dialogScene = new Scene(dialogVbox, 400, 400);
        dialog.setScene(dialogScene);
        dialog.show();
        dialog.setOnCloseRequest(event -> {
            tetrisView.paused = false;
        });

    }

    /**
     * Save the board to a file 
     */
    //TODO
    public void saveBoard() {
        String file_name = saveFileNameTextField.getText().trim();
        if (file_name.isEmpty() || file_name.equals(".ser")) {
            this.saveFileErrorLabel.setText("Need a file name.");
        }else if(!file_name.endsWith(".ser")) {
            this.saveFileErrorLabel.setText(saveFileNotSerError);
        }else{
            //String location = "./Assignment2/boards/" + saveFileNameTextField.getText();
            String location = "./boards/" + saveFileNameTextField.getText();
            File file = new File(location);
            System.out.println(file.getAbsolutePath());
            if (file.exists()) {
                this.saveFileErrorLabel.setText(saveFileExistsError);
            }else{
                this.tetrisView.model.saveModel(file);
                this.saveBoardLabel.setText(saveFileSuccess);
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("File saved");
//                alert.setContentText(saveFileSuccess);
//                Optional<ButtonType> result = alert.showAndWait();
//                if(result.isPresent() && result.get() == ButtonType.OK){
//                    alert.close();
//                }
            }
        }
    }

}
