package dk.dtu.compute.se.pisd.roborally.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Represents a deck of command cards.
 * @author Marcus Langkilde, s195080@DTU.dk
 * @author Haleef Abu Talib, s224523@dtu.dk
 */
public class Deck {
    @Expose
    public ArrayList<Command> initDeck;
    /**
     * Constructs a new deck with the initial set of command cards.
     * @author Marcus Langkilde, s195080@DTU.dk
     * @author Haleef Abu Talib, s224523@dtu.dk
     */
    public Deck(){
        this.initDeck = new ArrayList<Command>(Arrays.asList(Command.FORWARD,Command.FORWARD,Command.FORWARD,
                Command.RIGHT,Command.RIGHT,Command.RIGHT,Command.LEFT,Command.LEFT,Command.LEFT,Command.FAST_FORWARD,Command.FAST_FORWARD,Command.FAST_FORWARD,
                Command.AGAIN,Command.AGAIN,Command.AGAIN,Command.POWER_UP,Command.POWER_UP,Command.POWER_UP,Command.FAST_FAST_FORWARD,Command.FAST_FAST_FORWARD,
                Command.FAST_FAST_FORWARD,Command.U_TURN,Command.U_TURN,Command.U_TURN,Command.OPTION_LEFT_RIGHT,Command.OPTION_LEFT_RIGHT,Command.OPTION_LEFT_RIGHT,Command.BACK_UP,Command.BACK_UP,Command.BACK_UP));
    }
    /**
     * Shuffles the deck.
     * @author Marcus Langkilde, s195080@DTU.dk
     * @author Haleef Abu Talib, s224523@dtu.dk
     */
    public void shuffleDeck() {
        Collections.shuffle(this.initDeck);
    }

    //Add cards to deck
    //Add a shuffle method
    //Make a constructor
    //Give it to the player
    //Draw card
    //Add discard pile to deck


}
