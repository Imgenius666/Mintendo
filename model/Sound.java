package model;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
// import
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    Long currentFrame;
    Clip clip;
    // The instruction of Tetris game's rule
    String txt = "Tetris is a puzzle game that is played on a grid called the Matrix. " +
            "Tetrimino shapes drop one-by-one from the top of the Matrix to the bottom. " +
            "These Tetriminos come in seven different shapes, each represented by a " +
            "different letter of the alphabet. To excel at Tetris, you must arrange these " +
            "Tetriminos in such a way that they form solid horizontal lines, whereupon which " +
            "these lines disappear from the Matrix. Eliminate four lines at once and you " +
            "officially achieve a Tetris Line Clear. Before a Tetrimino reaches the bottom of" +
            " the Matrix, you can move and rotate this shape to fit your desired position. ";

    // check the audio is ON or OFF
    String status;
    //  Input Stream
    AudioInputStream audioInputStream;
    // file path
    static String filePath;
    public Sound()
            throws UnsupportedAudioFileException,
            IOException, LineUnavailableException
    {
        audioInputStream =
                AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        // open input stream in the clip
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    // Main class
    public static void main(String[] args)
    {
        try
        {
            filePath = "C:\\Users\\xiang\\Desktop\\Project\\Mintendo\\introAudio.wav";
            Sound audioPlayer = new Sound();
            audioPlayer.play();
            Scanner sc = new Scanner(System.in);

            while (true)
            {
                System.out.println("1. pause");
                System.out.println("2. resume");
                System.out.println("3. restart");
                System.out.println("4. stop");
                int c = sc.nextInt();
                audioPlayer.gotoChoice(c);
                if (c == 4)
                    break;
            }
            sc.close();
        }

        catch (Exception ex)
        {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    // Control the Audio setting
    private void gotoChoice(int c)
            throws IOException, LineUnavailableException, UnsupportedAudioFileException
    {
        switch (c)
        {
            case 1:
                pause();
                break;
            case 2:
                resumeAudio();
                break;
            case 3:
                restart();
                break;
            case 4:
                stop();
                break;
        }
    }
    // Play the Audio
    public void play()
    {
        clip.start();

        status = "play";
    }

    // Pause the Audio
    public void pause()
    {
        if (status.equals("paused"))
        {
            System.out.println("audio is already paused");
            return;
        }
        this.currentFrame =
                this.clip.getMicrosecondPosition();
        clip.stop();
        status = "paused";
    }

    // Resume Audio
    public void resumeAudio() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException
    {
        if (status.equals("play"))
        {
            System.out.println("Audio is already "+
                    "being played");
            return;
        }
        clip.close();
        resetAudioStream();
        clip.setMicrosecondPosition(currentFrame);
        this.play();
    }

    //Restart Audio
    public void restart() throws IOException, LineUnavailableException,
            UnsupportedAudioFileException
    {
        clip.stop();
        clip.close();
        resetAudioStream();
        currentFrame = 0L;
        clip.setMicrosecondPosition(0);
        this.play();
    }

    // Stop the Audio
    public void stop() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException
    {
        currentFrame = 0L;
        clip.stop();
        clip.close();
    }


    // Reset Audio
    public void resetAudioStream() throws UnsupportedAudioFileException, IOException,
            LineUnavailableException
    {
        audioInputStream = AudioSystem.getAudioInputStream(
                new File(filePath).getAbsoluteFile());
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
