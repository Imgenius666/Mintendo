package state;

public class HighLevelState implements MusicState {

    public void Determine(MusicContext mc) {
        System.out.println("Highlevel music state");
    }
    //If the s in mc is true, play the high level sound.
    //If false, then stop the sound.
    @Override
    public void EnterState(MusicContext mc, int theme_idx) {
        if (mc.s) {
            mc.sound.setThemesFile(theme_idx*2);
            mc.sound.play();
            mc.sound.loop();
        } else {
            mc.sound.stop();
        }
    }
    //return the string "Highlevel State"
    public String toString(){
        return "Highlevel State";
    }
}
