package dk.dtu.compute.se.pisd.roborally.test_temp;
import dk.dtu.compute.se.pisd.roborally.model.Deck;
import dk.dtu.compute.se.pisd.roborally.model.Command;
import java.util.ArrayList;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotEquals;

public class DeckTest {

    Deck deck = new Deck();

    @Test
    public void shuffleDeckTest() {
        Deck deck = new Deck();
        // Create a copy of the initial deck
        ArrayList<Command> initialDeck = new ArrayList<>(deck.initDeck);
        // Shuffle the deck
        deck.shuffleDeck();
        // Check if the deck has been shuffled by comparing it with the initial deck
        assertNotEquals(initialDeck, deck.initDeck);
    }

}
