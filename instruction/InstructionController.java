package instruction;
import javafx.fxml.FXML;
import model.Sound;
import javafx.scene.control.Button;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class InstructionController {
    @FXML
    //Buttons to control the audio
    Button Start_Audio, Pause_Audio, Resume_Audio, Stop_Audio, Restart_Audio;
    // sound
    Sound sound;
    // Constructor for Sound

    // initialize
    public void initialize(){
        try {
            this.sound = new Sound();
            Start_Audio.setOnAction(e -> {
                sound.play();
            });
            Pause_Audio.setOnAction(e -> {
                sound.pause();
            });
            Restart_Audio.setOnAction(e -> {
                try {
                    sound.restart();
                } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
                    throw new RuntimeException(ex);
                }
            });
            Resume_Audio.setOnAction(e -> {
                try {
                    sound.resumeAudio();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            });
            Stop_Audio.setOnAction(e -> {
                try {
                    sound.stop();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

   // public void
}
