package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;

import java.util.Collections;

public class DeckField extends Subject {

    final public Player player;

    private Deck deck;

    private boolean visible;


    public DeckField(Player player) {
        this.player = player;
        this.deck = new Deck();
        this.visible = true;
    }

    public Deck getDeck() {
        return deck;
    }

    public void shuffleDeck(Deck deck){
        Collections.shuffle(deck.initDeck);
    }

    public void setDeck(Deck deck) {
        if (deck != this.deck) {
            this.deck = deck;
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
