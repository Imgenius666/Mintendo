package state;

public class HighLevelState implements MusicState {

    public void Determine(MusicContext mc) {
        System.out.println("Highlevel music state");
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
    public String toString(){
        return "Highlevel State";
    }
}
