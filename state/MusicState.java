package state;

public interface MusicState {

    public default void EnterState(MusicContext mc, int theme_idx){

    }
    public default void Determine(MusicContext mc){

    }
}
