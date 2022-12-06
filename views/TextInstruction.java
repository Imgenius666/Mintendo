package views;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TextInstruction extends TetrisView implements Command {
    TetrisView tetrisView;

    //check if change color mode has been called
    public TextInstruction(TetrisView tetrisview) {
        super();
        this.tetrisView = tetrisview;


        //Pausing the current scene, creating new stage
        tetrisView.paused = true;
        Text text = new Text("Tetris has very simple rules: \n" +
                "You can only move the pieces in specific ways; \n " +
                "Your game is over if your pieces reach the top of the screen;\n " +
                "You can only remove pieces from the screen by filling all the blank space in a line.");
        text.setFont(Font.font(20));

        final Stage priStage = new Stage();
        BorderPane root = new BorderPane();
        root.setCenter(text);
        Scene scene = new Scene(root, 750, 200);
        priStage.setScene(scene);
        priStage.setTitle("Game Instruction");
        priStage.show();
    }

    @Override
    public void execute() {

    }
}