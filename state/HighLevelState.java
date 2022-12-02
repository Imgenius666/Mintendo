package state;

public class HighLevelState implements MusicState{

    public void Determine(MusicContext mc) {
        if (mc.score < 50){
            mc.sound.stop();
        }
    }

    @Override
    public void EnterState(MusicContext mc) {
        mc.sound.setFile(6);
        mc.sound.play();
    }


}
