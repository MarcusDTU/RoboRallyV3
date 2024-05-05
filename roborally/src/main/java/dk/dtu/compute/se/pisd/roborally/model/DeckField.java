package dk.dtu.compute.se.pisd.roborally.model;

import com.google.gson.annotations.Expose;
import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;

import java.util.Collections;

/**
 * Represents a field containing a player's deck.
 * @author Marcus Langkilde, s195080@DTU.dk
 * @author Haleef Abu Talib, s224523@dtu.dk
 */
public class DeckField extends Subject {

    final public Player player;
    @Expose
    private Deck deck;
    @Expose
    private boolean visible;


    /**
     * Constructs a DeckField for the specified player with a new deck.
     *
     * @param player The player to whom the deck belongs.
     * @author Marcus Langkilde, s195080@DTU.dk
     * @author Haleef Abu Talib, s224523@dtu.dk
     */
    public DeckField(Player player) {
        this.player = player;
        this.deck = new Deck();
        this.visible = true;
    }

    /**
     * Retrieves the deck in the field.
     *
     * @return The deck in the field.
     * @author Marcus Langkilde, s195080@DTU.dk
     * @author Haleef Abu Talib, s224523@dtu.dk
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * Shuffles the specified deck.
     *
     * @param deck The deck to shuffle.
     * @author Marcus Langkilde, s195080@DTU.dk
     * @author Haleef Abu Talib, s224523@dtu.dk
     */
    public void shuffleDeck(Deck deck){
        Collections.shuffle(deck.initDeck);
    }

    /**
     * Sets the deck in the field.
     *
     * @param deck The new deck for the field.
     * @author Marcus Langkilde, s195080@DTU.dk
     * @author Haleef Abu Talib, s224523@dtu.dk
     */
    public void setDeck(Deck deck) {
        if (deck != this.deck) {
            this.deck = deck;
            notifyChange();
        }
    }

    /**
     * Checks if the deck is visible.
     *
     * @return {@code true} if the deck is visible, {@code false} otherwise.
     * @author Marcus Langkilde, s195080@DTU.dk
     * @author Haleef Abu Talib, s224523@dtu.dk
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Sets the visibility of the deck.
     *
     * @param visible {@code true} to make the deck visible, {@code false} to hide it.
     * @author Marcus Langkilde, s195080@DTU.dk
     * @author Haleef Abu Talib, s224523@dtu.dk
     */
    public void setVisible(boolean visible) {
        if (visible != this.visible) {
            this.visible = visible;
            notifyChange();
        }
    }
}
