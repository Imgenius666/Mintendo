import state.HighLevelState;
import state.MusicContext;

import org.junit.jupiter.api.Test;
import state.NormalLevelState;

import static org.junit.jupiter.api.Assertions.*;

public class StateTests {
    //Test the music is working correctly(play) ot not.
    @Test
    void testplay(){
        MusicContext mc1 = new MusicContext(true, 0);
        mc1.TransitionToState(new NormalLevelState());
        assertEquals(true, mc1.sound.det);
        mc1.s = false;
        mc1.TransitionToState(new NormalLevelState());
        assertEquals(false, mc1.sound.det);
        MusicContext mc2 = new MusicContext(true, 0);
        mc2.TransitionToState(new HighLevelState());
        assertEquals(true, mc2.sound.det);
        mc2.s = false;
        mc2.TransitionToState(new HighLevelState());
        assertEquals(false, mc2.sound.det);

    }
    //Test the music transfer is working correctly ot not.
    @Test
    void testStateTransfer(){
        MusicContext mc = new MusicContext(true, 0);
        mc.DetermineState();
        assertEquals("Highlevel State", mc.current.toString());
        mc.DetermineState();
        assertEquals("Highlevel State", mc.current.toString());
        mc.TransitionToState(new NormalLevelState());
        assertEquals("Normallevel State", mc.current.toString());

    }
}
