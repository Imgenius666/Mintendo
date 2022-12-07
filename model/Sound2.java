package model;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;

import java.net.URL;
import java.util.ArrayList;
import java.io.File;

public class Sound2 {

    public Clip clip;
    ArrayList<URL> effect_urls = new ArrayList<>();
    ArrayList<URL> theme_urls = new ArrayList<>();
    public boolean det;

    public Sound2() {
        try{
            File music_dir = new File("./assets/sounds/effects/");
            File[] sound_files = music_dir.listFiles();
            if (sound_files != null) {
                for (File music : sound_files) {
                    effect_urls.add(music.toURI().toURL());
                }
            } else {
                throw new RuntimeException("Effect sound files not found");
            }
            music_dir = new File("./assets/sounds/themes/");
            sound_files = music_dir.listFiles();
            if (sound_files != null) {
                for (File music : sound_files) {
                    theme_urls.add(music.toURI().toURL());
                }
            } else {
                throw new RuntimeException("Theme sound files not found");
            }
            this.det = true;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setEffectsFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(effect_urls.get(i));
            clip = AudioSystem.getClip();
            clip.open(ais);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setThemesFile(int i) {
        try {
            System.out.println(theme_urls.get(i));
            AudioInputStream ais = AudioSystem.getAudioInputStream(theme_urls.get(i));
            clip = AudioSystem.getClip();
            clip.open(ais);

        } catch (Exception e) {
            e.printStackTrace();
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
