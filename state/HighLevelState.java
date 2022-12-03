package state;

public class HighLevelState implements MusicState {

    public void Determine(MusicContext mc) {
        System.out.println("Highlevel music state");
//        mc.sound.stop();
//        mc.TransitionToState(new NormalLevelState());
    }

    @Override
    public void EnterState(MusicContext mc) {
        if (mc.s) {
            mc.sound.setFile(6);
            mc.sound.play();
            mc.sound.loop();
        } else {
            mc.sound.stop();
        }
    }
}
