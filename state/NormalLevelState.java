package state;

public class NormalLevelState implements MusicState {

    public void Determine(MusicContext mc) {
        mc.sound.stop();
        mc.TransitionToState(new HighLevelState());
    }

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
    public String toString(){
        return "Normallevel State";
    }
}

