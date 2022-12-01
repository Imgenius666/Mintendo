package state;

import model.Sound2;
import model.TetrisModel;

public class MusicContext {
    public Sound2 sound;
    public  int score;
    public MusicState current;
    //image
    //time

    public MusicContext(int score) {
        this.sound = new Sound2();
        this.score = score;
    }
    public void TransitionToState(MusicState state){
        current = state;
        current.EnterState(this);
    }

}
