package state;

public class NormalLevelState implements MusicState {
    @Override
    public void Determine(MusicContext mc) {
        if (mc.score >= 50){
            mc.sound.stop();
        }
    }

    @Override
    public void EnterState(MusicContext mc) {
        mc.sound.setFile(2);
        mc.sound.play();
    }
}

