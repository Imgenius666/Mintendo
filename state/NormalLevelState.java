package state;

public class NormalLevelState implements MusicState {
    //Assigns MusicContext ms variable.
    public void Determine(MusicContext mc) {
        mc.sound.stop();
        mc.TransitionToState(new HighLevelState());
    }
    //If s in mc is true, then play the normal level sound.
    //if false, then stop the sound.
    @Override
    public void EnterState(MusicContext mc, int theme_idx) {
        if (mc.s) {
            mc.sound.setThemesFile(theme_idx*2 + 1);
            mc.sound.play();
            mc.sound.loop();
        } else {
            mc.sound.stop();
        }
    }
    //return the string "Normallevel State".
    public String toString(){
        return "Normallevel State";
    }
}

