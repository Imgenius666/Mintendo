package views;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import model.TetrisModel;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ChangeColorMode extends TetrisView implements Command{
    TetrisView tetrisView;
    //check if change color mode has been called
    boolean called;
    Button HighContrastModeButton, StandardModeButton, EyeProtectionModeButton, CustomizeColorSchemeButton;
    
    public ChangeColorMode(TetrisView tetrisview){
        super();
        this.tetrisView = tetrisview;
        called = false;

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
        EyeProtectionModeButton.setOnAction(e -> EyeProtecionMode());

        CustomizeColorSchemeButton = new Button("Customize Color Scheme");
        CustomizeColorSchemeButton.setId("Customize Color Scheme"); // DO NOT MODIFY ID
        CustomizeColorSchemeButton.setStyle("-fx-background-color: #688aed; -fx-text-fill: white;");
        CustomizeColorSchemeButton.setPrefSize(250, 50);
        CustomizeColorSchemeButton.setFont(new Font(16));
//        CustomizeColorSchemeButton.setOnAction(e -> customizeColorScheme());

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
        StandardModeButton.setOnAction(e -> {
            standardMode();
        });

        HighContrastModeButton.setOnAction(e -> {
            model.stopGame();
        });

        EyeProtectionModeButton.setOnAction(e -> {
            model.stopGame();
        });

        CustomizeColorSchemeButton.setOnAction(e -> {
            model.stopGame();
        });

    }


    @Override
    public void execute() {

    }

    private void highContrastMode() {

    }

    private void standardMode() {

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
    }


    private void EyeProtecionMode(){

    }
//
//    private void customizeColorScheme;(){
//
//           }

        }

