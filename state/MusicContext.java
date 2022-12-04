package state;

import model.Sound2;
import model.TetrisModel;

public class MusicContext {
    public Sound2 sound;
    //public  int score;
    public MusicState current;

    public boolean s;
    //image
    //time

    public MusicContext(boolean s) {
        this.sound = new Sound2();
        this.s = s;
        this.current = new NormalLevelState();
    }
    public void TransitionToState(MusicState state){
        current = state;
        current.EnterState(this);
    }

    public void DetermineState(){
        current.Determine(this);
    }

//    public void Changes(boolean s){
//        this.s = s;
//    }

}
