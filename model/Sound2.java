package model;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;

import java.net.URL;

public class Sound2 {

    public Clip clip;
    URL soundURL[] = new URL[30];
    public boolean det;

    public Sound2() {

        soundURL[0] = getClass().getResource("/soundfile/hitsound.wav");
        soundURL[1] = getClass().getResource("/soundfile/remove.wav");
        soundURL[2] = getClass().getResource("/soundfile/tetrisnormal1.wav");
        soundURL[3] = getClass().getResource("/soundfile/good.wav");
        soundURL[4] = getClass().getResource("/soundfile/excellent.wav");
        soundURL[5] = getClass().getResource("/soundfile/unbelievable.wav");
        soundURL[6] = getClass().getResource("/soundfile/tetrishighlevel1.wav");
        soundURL[7] = getClass().getResource("/soundfile/gameover.wav");
        this.det = true;
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        } catch (Exception e) {

        }
    }

    public void play() {
        try {
            clip.start();

        } catch (java.lang.NullPointerException e) {
            det = false;

        }


    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        try {
            clip.stop();
            det = false;
        } catch (java.lang.NullPointerException e) {
            det = false;

        }

    }
}
