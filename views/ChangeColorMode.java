package views;

import javafx.scene.text.Text;
import javafx.stage.Modality;
import model.TetrisModel;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ChangeColorMode extends TetrisView implements Command{
    TetrisView tetrisView;
    //check if change color mode has been called
    boolean HighContrastMode, StandardMode, EyeProtectionMode, CustomizeColorScheme;
    Button HighContrastModeButton, StandardModeButton, EyeProtectionModeButton;
    public ChangeColorMode(TetrisView tetrisview){
        super();
        this.tetrisView = tetrisview;


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
        HighContrastModeButton.setStyle("-fx-background-color: #688aed; -fx-text-fill: white;");
        HighContrastModeButton.setPrefSize(250, 50);
        HighContrastModeButton.setFont(new Font(16));
        HighContrastModeButton.setOnAction(e -> highContrastMode());

        StandardModeButton = new Button("Standard Mode");
        StandardModeButton.setId("Standard Mode"); // DO NOT MODIFY ID
        StandardModeButton.setStyle("-fx-background-color: #688aed; -fx-text-fill: white;");
        StandardModeButton.setPrefSize(250, 50);
        StandardModeButton.setFont(new Font(16));
        StandardModeButton.setOnAction(e -> standardMode());

        EyeProtectionModeButton = new Button("Eye Protection Mode");
        EyeProtectionModeButton.setId("Eye Protection Mode"); // DO NOT MODIFY ID
        EyeProtectionModeButton.setStyle("-fx-background-color: #688aed; -fx-text-fill: white;");
        EyeProtectionModeButton.setPrefSize(250, 50);
        EyeProtectionModeButton.setFont(new Font(16));
        EyeProtectionModeButton.setOnAction(e -> eyeProtectionMode());


        //Creating VBox, adding buttons to the VBox. Creating a new scene, adding buttons to the scene
        VBox changeModeBox = new VBox(10, HighContrastModeButton, StandardModeButton,
                EyeProtectionModeButton);
        dialogVbox.getChildren().add(changeModeBox);
        Scene dialogScene = new Scene(dialogVbox, 400, 300);
        dialog.setScene(dialogScene);
        dialog.show();

        //Set on action for these button
        StandardModeButton.setOnAction(e -> {
            standardMode();
        });

        HighContrastModeButton.setOnAction(e -> {
            highContrastMode();
        });

        EyeProtectionModeButton.setOnAction(e -> {
            eyeProtectionMode();
        });

        execute();

        dialog.setOnCloseRequest(event -> {
            tetrisView.paused = false;
        });

    }


    @Override
    public void execute() {
        //Deciding 
        if(this.tetrisView.highContrast){
            highContrastMode();
        }
        if(this.tetrisView.eyeProtection){
            eyeProtectionMode();
        }
        if(this.tetrisView.standard){
            standardMode();
        }
    }


        private void highContrastMode () {
            this.tetrisView.paused = true;
            this.tetrisView.changeColor = true;
            this.tetrisView.highContrast = true;
            this.tetrisView.eyeProtection = false;
            this.tetrisView.standard = false;
            this.tetrisView.customized = false;
            // Draw a rectangle around the whole screen
            tetrisView.gc.setStroke(Color.GREEN);
            tetrisView.gc.setFill(Color.GREEN);
            tetrisView.gc.fillRect(0, 0, tetrisView.getWidth() - 1, tetrisView.getHeight() - 1);

            // Draw the line separating the top area on the screen
            tetrisView.gc.setStroke(Color.BLACK);
            int spacerY = tetrisView.yPixel(tetrisView.model.getBoard().getHeight() - TetrisModel.BUFFERZONE - 1);
            tetrisView.gc.strokeLine(0, spacerY, tetrisView.getWidth() - 1, spacerY);

            // Factor a few things out to help the optimizer
            final int dx = Math.round(tetrisView.dX() - 2);
            final int dy = Math.round(tetrisView.dY() - 2);
            final int bWidth = tetrisView.model.getBoard().getWidth();
            int x, y;
            // Loop through and draw all the blocks; sizes of blocks are calibrated relative to screen size
            for (x = 0; x < bWidth; x++) {
                int left = tetrisView.xPixel(x);    // the left pixel
                // draw from 0 up to the col height
                final int yHeight = tetrisView.model.getBoard().getColumnHeight(x);
                for (y = 0; y < yHeight; y++) {
                    if (tetrisView.model.getBoard().getGrid(x, y)) {
                        tetrisView.gc.setFill(Color.RED);
                        tetrisView.gc.fillRect(left + 1, tetrisView.yPixel(y) + 1, dx, dy);
                        tetrisView.gc.setFill(Color.GREEN);
                    }
                }
            }
            this.tetrisView.paused = false;
        }

        private void standardMode () {
            this.tetrisView.paused = true;
            this.tetrisView.changeColor = true;
            this.tetrisView.standard = true;
            this.tetrisView.highContrast = false;
            this.tetrisView.eyeProtection = false;
            this.tetrisView.customized = false;
            // Draw a rectangle around the whole screen
            tetrisView.gc.setStroke(Color.rgb(242, 244, 250));
            tetrisView.gc.setFill(Color.rgb(242, 244, 250));
            tetrisView.gc.fillRect(0, 0, tetrisView.getWidth() - 1, tetrisView.getHeight() - 1);

            // Draw the line separating the top area on the screen
            tetrisView.gc.setStroke(Color.BLACK);
            int spacerY = tetrisView.yPixel(tetrisView.model.getBoard().getHeight() - TetrisModel.BUFFERZONE - 1);
            tetrisView.gc.strokeLine(0, spacerY, tetrisView.getWidth() - 1, spacerY);

            // Factor a few things out to help the optimizer
            final int dx = Math.round(tetrisView.dX() - 2);
            final int dy = Math.round(tetrisView.dY() - 2);
            final int bWidth = tetrisView.model.getBoard().getWidth();
            int x, y;
            // Loop through and draw all the blocks; sizes of blocks are calibrated relative to screen size
            for (x = 0; x < bWidth; x++) {
                int left = tetrisView.xPixel(x);    // the left pixel
                // draw from 0 up to the col height
                final int yHeight = tetrisView.model.getBoard().getColumnHeight(x);
                for (y = 0; y < yHeight; y++) {
                    if (tetrisView.model.getBoard().getGrid(x, y)) {
                        tetrisView.gc.setFill(Color.rgb(104, 138, 237));
                        tetrisView.gc.fillRect(left + 1, tetrisView.yPixel(y) + 1, dx, dy);
                        tetrisView.gc.setFill(Color.rgb(242, 244, 250));
                    }
                }
            }
            this.tetrisView.paused = false;
        }


        private void eyeProtectionMode () {
            this.tetrisView.paused = true;
            this.tetrisView.changeColor = true;
            this.tetrisView.eyeProtection = true;
            this.tetrisView.highContrast = false;
            this.tetrisView.standard = false;
            this.tetrisView.customized = false;
            tetrisView.gc.setStroke(Color.rgb(213, 217, 193));
            tetrisView.gc.setFill(Color.rgb(213, 217, 193));
            tetrisView.gc.fillRect(0, 0, tetrisView.getWidth() - 1, tetrisView.getHeight() - 1);

            // Draw the line separating the top area on the screen
            tetrisView.gc.setStroke(Color.BLACK);
            int spacerY = tetrisView.yPixel(tetrisView.model.getBoard().getHeight() - TetrisModel.BUFFERZONE - 1);
            tetrisView.gc.strokeLine(0, spacerY, tetrisView.getWidth() - 1, spacerY);

            // Factor a few things out to help the optimizer
            final int dx = Math.round(tetrisView.dX() - 2);
            final int dy = Math.round(tetrisView.dY() - 2);
            final int bWidth = tetrisView.model.getBoard().getWidth();
            int x, y;
            // Loop through and draw all the blocks; sizes of blocks are calibrated relative to screen size
            for (x = 0; x < bWidth; x++) {
                int left = tetrisView.xPixel(x);    // the left pixel
                // draw from 0 up to the col height
                final int yHeight = tetrisView.model.getBoard().getColumnHeight(x);
                for (y = 0; y < yHeight; y++) {
                    if (tetrisView.model.getBoard().getGrid(x, y)) {
                        tetrisView.gc.setFill(Color.rgb(110, 153, 107));
                        tetrisView.gc.fillRect(left + 1, tetrisView.yPixel(y) + 1, dx, dy);
                        tetrisView.gc.setFill(Color.rgb(213, 217, 193));
                    }
                }
            }
            this.tetrisView.paused = false;
        }


}

