package views;

import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.TetrisModel;
import views.TetrisView;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;


public class ChangeColorMode extends TetrisView implements Command{
    TetrisView tetrisView;
    Button HighContrastModeButton, StandardModeButton, EyeProtectionModeButton, CustomizeColorSchemeButton;
    /**
     * Constructor
     *
     * @param model reference to tetris model
     * @param stage application stage
     */
    public ChangeColorMode(TetrisModel model, Stage stage, TetrisView tetrisView) {
        //super(model, stage);
        this.tetrisView = tetrisView;

        //Pausing the current scene, creating new stage
        tetrisView.paused = true;
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(tetrisView.stage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.setPadding(new Insets(20, 20, 20, 20));
        dialogVbox.setStyle("-fx-background-color: #121212;");

        //Creating buttons
        HighContrastModeButton = new Button("High Contrast Mode");
        HighContrastModeButton.setId("High Contrast Mode"); // DO NOT MODIFY ID
        HighContrastModeButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        HighContrastModeButton.setPrefSize(250, 50);
        HighContrastModeButton.setFont(new Font(16));
        HighContrastModeButton.setOnAction(e -> highContrastMode());

        StandardModeButton = new Button("Standard Mode");
        StandardModeButton.setId("Standard Mode"); // DO NOT MODIFY ID
        StandardModeButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        StandardModeButton.setPrefSize(250, 50);
        StandardModeButton.setFont(new Font(16));
        StandardModeButton.setOnAction(e -> standardMode());

        EyeProtectionModeButton = new Button("Eye Protection Mode");
        EyeProtectionModeButton.setId("Eye Protection Mode"); // DO NOT MODIFY ID
        EyeProtectionModeButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        EyeProtectionModeButton.setPrefSize(250, 50);
        EyeProtectionModeButton.setFont(new Font(16));
        EyeProtectionModeButton.setOnAction(e -> eyeProtecionMode());

        CustomizeColorSchemeButton = new Button("Customize Color Scheme");
        CustomizeColorSchemeButton.setId("Customize Color Scheme"); // DO NOT MODIFY ID
        CustomizeColorSchemeButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        CustomizeColorSchemeButton.setPrefSize(250, 50);
        CustomizeColorSchemeButton.setFont(new Font(16));
        CustomizeColorSchemeButton.setOnAction(e -> customizeColorScheme());

        //Creating VBox, adding buttons to the VBox. Creating a new scene, adding buttons to the scene
        VBox changeModeBox = new VBox(10, HighContrastModeButton, StandardModeButton,
                EyeProtectionModeButton, CustomizeColorSchemeButton);
        dialogVbox.getChildren().add(changeModeBox);
        Scene dialogScene = new Scene(dialogVbox, 400, 300);
        dialog.setScene(dialogScene);
        dialog.show();
        dialog.setOnCloseRequest(event -> {
            tetrisView.paused = false;
        });
    }

    @Override
    public void execute() {

    }

    private void highContrastMode(){

    }

    private void standardMode(){

        // Draw a rectangle around the whole screen
        tetrisView.gc.setStroke(Color.rgb(168, 50, 168));
        tetrisView.gc.setFill(Color.rgb(168, 50, 168));
        //tetrisView.gc.fillRect(0, 0, tetrisView.getWidth()-1, tetrisView.getHeight()-1);
    }


    private void eyeProtecionMode(){

    }

    private void customizeColorScheme(){

    }

}
