package state;

public class NormalLevelState implements MusicState {

    public void Determine(MusicContext mc) {
        mc.sound.stop();
        mc.TransitionToState(new HighLevelState());

    }

    @Override
    public void EnterState(MusicContext mc) {
        if (mc.s) {
            mc.sound.setFile(2);
            mc.sound.play();
            mc.sound.loop();
        } else {
            mc.sound.stop();
        }
    }
}

