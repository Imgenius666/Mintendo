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

public class CustomizeColor extends TetrisView implements Command {
    TetrisView tetrisView;
    ColorPicker colorPicker = new ColorPicker();
    ColorPicker colorPicker2 = new ColorPicker();

    Color pieceColor;
    Color backgroundColor;


    public CustomizeColor(TetrisView tetrisView) {
        super();
        this.tetrisView = tetrisView;
        this.tetrisView.customized = true;
        this.tetrisView.changeColor = true;
        this.tetrisView.highContrast = false;
        this.tetrisView.eyeProtection = false;
        this.tetrisView.standard = false;
        Text text = new Text("Please pick a color for the piece ↑\n" +
                " And for the background ↓ ");

        final Stage priStage = new Stage();
        BorderPane root = new BorderPane();
        root.setTop(this.colorPicker);
        root.setCenter(text);
        root.setBottom(this.colorPicker2);
        Scene scene = new Scene(root, 270, 150);
        priStage.setScene(scene);
        priStage.setTitle("Color Picker");
        priStage.show();
    }

    @Override
    public void execute() {
        this.colorPicker.setOnAction(e -> {
            this.colorPicker2.setOnAction(f -> {
                tetrisView.gc.setStroke(this.colorPicker.getValue());
                tetrisView.gc.setFill(this.colorPicker.getValue());
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
                            tetrisView.gc.setFill(this.colorPicker2.getValue());
                            tetrisView.gc.fillRect(left + 1, tetrisView.yPixel(y) + 1, dx, dy);
                            tetrisView.gc.setFill(this.colorPicker.getValue());
                        }
                    }
                }
            });
        });
        this.tetrisView.model.modelTick(TetrisModel.MoveType.DOWN);
        this.tetrisView.updateScore();
    }
}



