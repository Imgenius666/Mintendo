package state;

public class NormalLevelState implements MusicState {

    //Assigns MusicContext ms variable.
    public void Determine(MusicContext mc) {
        mc.sound.stop();
        mc.soundeffect.setFile(8);
        mc.soundeffect.play();
        mc.TransitionToState(new HighLevelState());

    }
    //If s in mc is true, then play the normal level sound.
    //if false, then stop the sound.
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

    //return the string "Normallevel State".
    public String toString(){
        return "Normallevel State";
    }

}


