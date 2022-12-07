package state;

import model.Sound2;
import model.TetrisModel;

public class MusicContext {
    public Sound2 sound;
    //public  int score;
    public MusicState current;

    public boolean s;
    public int theme_idx;
    public MusicContext(boolean s, int theme_idx) {
        this.sound = new Sound2();
        this.s = s;
        this.theme_idx = theme_idx;
        this.current = new NormalLevelState();
    }

    public void TransitionToState(MusicState state){
        current = state;
        current.EnterState(this, theme_idx);
    }

    public void DetermineState(){
        current.Determine(this);
    }



}
