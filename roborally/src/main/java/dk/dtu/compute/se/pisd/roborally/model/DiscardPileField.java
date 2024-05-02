package dk.dtu.compute.se.pisd.roborally.model;

import com.google.gson.annotations.Expose;
import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;

public class DiscardPileField extends Subject {

    public Player player;

    @Expose
    private DiscardPile pile;

    @Expose
    private boolean visible;


    public DiscardPileField(Player player) {
        this.player = player;
        this.pile = new DiscardPile();
        this.visible = true;
    }

    public DiscardPile getPile() {
        return pile;
    }

    public void setPile(DiscardPile pile) {
        if (pile != this.pile) {
            this.pile = pile;
            notifyChange();
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        if (visible != this.visible) {
            this.visible = visible;
            notifyChange();
        }
    }
}
