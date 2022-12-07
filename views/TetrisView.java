package views;
import java.awt.*;
import java.awt.image.BufferedImage;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;
import model.TetrisModel;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.TetrisPiece;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static model.TetrisPiece.SQUARE_STR;


/**
 * Tetris View
 *
 * Based on the Tetris assignment in the Nifty Assignments Database, authored by Nick Parlante
 */
public class TetrisView implements Initializable {
    @FXML
    Button Left_movement, Right_movement, Rotate_movement, Down_movement;
    TetrisModel model; //reference to model
    Stage stage;
    Color color = Color.RED;
    @FXML
    MenuItem startButton, stopButton, loadButton, saveButton, newButton; //menu items for some functions
    @FXML
    Label scoreLabel, gameModeLabel;
    @FXML
    Slider slider;
    @FXML
    BorderPane borderPane, secondBorderPane;
    @FXML
    AnchorPane anchorPane;
    @FXML
    BufferedImage image;
    @FXML
    Canvas canvas, secondCanvas;
    @FXML
    HBox controls;
    @FXML
    VBox vBox, scoreBox;
    @FXML
    GraphicsContext gc;
    @FXML
    GraphicsContext bx;
    @FXML
    GraphicsContext g; //the graphics context will be linked to the canvas
    @FXML
    RadioButton pilotButtonHuman, pilotButtonComputer;

    Boolean paused;
    Timeline timeline;

    private state.MusicContext mc;

    private boolean state;



    int pieceWidth = 25; //width of block on display

    int i = 0;
    private double width; //height and width of canvas
    private double height;
    private TetrisPiece piece;

    public TetrisView() {
        this.model = new TetrisModel();
        this.mc = new state.MusicContext(true, 0);
        this.state = true;

    }

    public void setMC(int val){
        System.out.println(val);
        this.mc = new state.MusicContext(true, val);
        this.mc.s = true;
        transState();
        model.resume();
    }

    /**
     * Initialize interface
     */
    private void initUI(){
    }

    /**
     * Get user selection of "autopilot" or human player
     *
     * @param value toggle selector on UI
     */
    private void swapPilot(Toggle value) {
        RadioButton chk = (RadioButton)value.getToggleGroup().getSelectedToggle();
        String strVal = chk.getText();
        if (strVal.equals("Computer (Default)")){
            this.model.setAutoPilotMode();
            gameModeLabel.setText("Player is: Computer (Default)");
        } else if (strVal.equals("Human")) {
            this.model.setHumanPilotMode();
            gameModeLabel.setText("Player is: Human");
        }
        borderPane.requestFocus(); //give the focus back to the pane with the blocks.
    }

    /**
     * Update board (paint pieces and score info)
     */
    private void updateBoard() {
        if (!this.paused) {
            paintBoard();
            this.model.modelTick(TetrisModel.MoveType.DOWN);
            updateScore();
        }
    }

    /**
     * Update score on UI
     */
    private void updateScore() {
        if (i != model.getScore() + + model.getCount()){
            clearBoard();
            printSecondBoard();
        }
        if (!this.paused) {
            scoreLabel.setText("Score is: " + model.getScore() + "\nPieces placed:" + model.getCount());

        }

        if (!this.paused && model.getScore() >= 30) {
            if (state) {
                mc.DetermineState();
                state = false;
            }
        }
    }

    public void transState(){
        mc.TransitionToState(mc.current);
    }



    /**
     * Methods to calibrate sizes of pixels relative to board size
     */
    private final int yPixel(int y) {
        return (int) Math.round(this.height -1 - (y+1)*dY());
    }
    private final int xPixel(int x) {
        return Math.round((x)*dX());
    }
    private final float dX() {
        return( ((float)(this.width-2)) / this.model.getBoard().getWidth() );
    }
    private final float dY() {
        return( ((float)(this.height-2)) / this.model.getBoard().getHeight() );
    }

    /**
     * Draw the board
     */
    @FXML
    public void paintBoard() {
        // Draw a rectangle around the whole screen
        gc.setStroke(Color.GREEN);
        gc.setFill(Color.GREEN);
        gc.fillRect(0, 0, this.width-1, this.height-1);

        // Draw the line separating the top area on the screen
        gc.setStroke(Color.BLACK);
        int spacerY = yPixel(this.model.getBoard().getHeight() - TetrisModel.BUFFERZONE - 1);
        gc.strokeLine(0, spacerY, this.width-1, spacerY);


        // Factor a few things out to help the optimizer
        final int dx = Math.round(dX()-2);
        final int dy = Math.round(dY()-2);
        final int bWidth = this.model.getBoard().getWidth();
        int x, y;
        // Loop through and draw all the blocks; sizes of blocks are calibrated relative to screen size
        for (x=0; x<bWidth; x++) {
            int left = xPixel(x);	// the left pixel
            // draw from 0 up to the col height
            final int yHeight = this.model.getBoard().getColumnHeight(x);
            for (y=0; y<yHeight; y++) {
                if (this.model.getBoard().getGrid(x, y)) {
                    gc.setFill(Color.RED);
                    gc.fillRect(left+1, yPixel(y)+1, dx, dy);
                    gc.setFill(Color.GREEN);
                }
            }
        }
    }
    //clear the second board
    public void clearBoard() {
        bx.setFill(Color.BLACK);
        bx.fillRect(50, 0, this.width - 1, this.height - 1);
    }

    // print the second board
    public void printSecondBoard(){
        final int dx = Math.round(dX()-2);
        final int dy = Math.round(dY()-2);
        int bWidth = 30;
        int x, y;
        // Loop through and draw all the blocks; sizes of blocks are calibrated relative to screen size
        for (x=0; x<bWidth; x++) {
            int left = xPixel(x);	// the left pixel
            // draw from 0 up to the col height
            int yHeight = 30;
            for (y=0; y<yHeight; y++) {
                if (this.model.getBoard().getGrid(x, y)) {
                    bx.setFill(this.color);
                    bx.fillRect(left+1, yPixel(y)+1, dx, dy);;
                    bx.setFill(Color.GREEN);
                }
            }
        }
    }

    /**
     * Create the view to save a board to a file
     */
    @FXML
    private void createSaveView(){
        SaveView saveView = new SaveView(this);
    }

    /**
     * Create the view to select a board to load
     */
    @FXML
    private void createLoadView(){
        LoadView loadView = new LoadView(this);
    }
    //Create a new setting page
    @FXML
    private void openAudioSettingsPage(){
        this.model.stopGame();
        try{
            this.mc.s = false;
            transState();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ChangeTheme.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Theme settings");
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setOnCloseRequest(event -> {
                Platform.exit();
                System.exit(0);
            });
            stage.show();
            ChangeThemeController controller = loader.getController();
            controller.view = this;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void openColorSettingsPage(){
        this.model.stopGame();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.paused = false;
        this.width = this.model.getWidth() * pieceWidth + 2;
        this.height = this.model.getHeight() * pieceWidth + 2;

        //add canvas
        canvas.setWidth(this.width);
        canvas.setHeight(this.height);
        gc = canvas.getGraphicsContext2D();

        secondCanvas.setHeight(90);
        secondCanvas.setWidth(170);
        bx = secondCanvas.getGraphicsContext2D();
        g = secondCanvas.getGraphicsContext2D();

        final ToggleGroup toggleGroup = new ToggleGroup();

        pilotButtonHuman.setToggleGroup(toggleGroup);
        pilotButtonHuman.setUserData(Color.SALMON);

        pilotButtonComputer.setToggleGroup(toggleGroup);
        pilotButtonComputer.setUserData(Color.SALMON);

        toggleGroup.selectedToggleProperty().addListener((observable, oldVal, newVal) -> swapPilot(newVal));

        //timeline structures the animation, and speed between application "ticks"
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.25), e -> updateBoard()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        //configure this such that you start a new game when the user hits the newButton
        //Make sure to return the focus to the borderPane once you're done!
        newButton.setOnAction(e -> {
            mc.current = new state.NormalLevelState();
            if(!mc.s){
                mc.s = true;
                transState();
            }else{
                mc.sound.stop();
                transState();
            }
            this.state = true;
            model.newGame();
        });

        //configure this such that you restart the game when the user hits the startButton
        //Make sure to return the focus to the borderPane once you're done!
        startButton.setOnAction(e -> {
            if(!mc.s){
                mc.s = true;
                transState();
            }
            model.resume();
        });

        //configure this such that you pause the game when the user hits the stopButton
        //Make sure to return the focus to the borderPane once you're done!
        stopButton.setOnAction(e -> {
            mc.s = false;
            transState();
            model.stopGame();
        });

        //configure this such that the save view pops up when the saveButton is pressed.
        //Make sure to return the focus to the borderPane once you're done!
        saveButton.setOnAction(e -> {
            createSaveView();
        });

        //configure this such that the load view pops up when the loadButton is pressed.
        //Make sure to return the focus to the borderPane once you're done!
        loadButton.setOnAction(e -> {
            createLoadView();
        });

        Left_movement.setOnAction(e -> {
            model.modelTick(TetrisModel.MoveType.LEFT);
        });

        Right_movement.setOnAction(e -> {
            model.modelTick(TetrisModel.MoveType.RIGHT);
        });
        Down_movement.setOnAction(e -> {
            model.modelTick(TetrisModel.MoveType.DROP);
        });
        Rotate_movement.setOnAction(e -> {
            model.modelTick(TetrisModel.MoveType.ROTATE);
        });
        //configure this such that you adjust the speed of the timeline to a value that
        //ranges between 0 and 3 times the default rate per model tick.  Make sure to return the
        //focus to the borderPane once you're done!
        slider.setOnMouseReleased(e -> {
            double val = slider.getValue();
            timeline.setRate(val * 3 / 100);
        });
        //configure this such that you can use controls to rotate and place pieces as you like!!
        //You'll want to respond to tie key presses to these moves:
        // TetrisModel.MoveType.DROP, TetrisModel.MoveType.ROTATE, TetrisModel.MoveType.LEFT
        //and TetrisModel.MoveType.RIGHT
        //make sure that you don't let the human control the board
        //if the autopilot is on, however.
        borderPane.setOnKeyReleased(k -> {
            switch (k.getCode()) {
                case S -> model.modelTick(TetrisModel.MoveType.DROP);
                case A -> model.modelTick(TetrisModel.MoveType.LEFT);
                case D -> model.modelTick(TetrisModel.MoveType.RIGHT);
                case R -> model.modelTick(TetrisModel.MoveType.ROTATE);
            }
        });

        transState();
        this.model.startGame(); //begin
    }
}