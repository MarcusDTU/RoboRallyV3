package dk.dtu.compute.se.pisd.roborally.model;

import com.google.gson.annotations.Expose;
import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;

/**
 * Represents a field containing a player's discard pile.
 * @author Marcus Langkilde, s195080@DTU.dk
 * @author Haleef Abu Talib, s224523@dtu.dk
 */
public class DiscardPileField extends Subject {

    public Player player;

    @Expose
    private DiscardPile pile;

    @Expose
    private boolean visible;

    /**
     * Constructs a DiscardPileField for the specified player with a new discard pile.
     *
     * @param player The player to whom the discard pile belongs.
     * @author Marcus Langkilde, s195080@DTU.dk
     * @author Haleef Abu Talib, s224523@dtu.dk
     */
    public DiscardPileField(Player player) {
        this.player = player;
        this.pile = new DiscardPile();
        this.visible = true;
    }

    /**
     * Retrieves the discard pile in the field.
     *
     * @return The discard pile in the field.
     * @author Marcus Langkilde, s195080@DTU.dk
     * @author Haleef Abu Talib, s224523@dtu.dk
     */
    public DiscardPile getPile() {
        return pile;
    }

    /**
     * Sets the discard pile in the field.
     *
     * @param pile The new discard pile for the field.
     * @author Marcus Langkilde, s195080@DTU.dk
     * @author Haleef Abu Talib, s224523@dtu.dk
     */
    public void setPile(DiscardPile pile) {
        if (pile != this.pile) {
            this.pile = pile;
            notifyChange();
        }
    }

    /**
     * Checks if the discard pile is visible.
     *
     * @return {@code true} if the discard pile is visible, {@code false} otherwise.
     * @author Marcus Langkilde, s195080@DTU.dk
     * @author Haleef Abu Talib, s224523@dtu.dk
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Sets the visibility of the discard pile.
     *
     * @param visible {@code true} to make the discard pile visible, {@code false} to hide it.
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
