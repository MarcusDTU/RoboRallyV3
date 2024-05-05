import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dk.dtu.compute.se.pisd.roborally.model.*;

class DeckFieldTest {

    private DeckField deckField;
    private Player player;

    @BeforeEach
    void setUp() {
        Board board = new Board(10, 10); // Create a 10x10 board
        this.player = new Player(board, 1, "Alice");
        this.deckField = new DeckField(player);
    }
    /**
     * @author Marcus Langkilde, s195080
     */
    @Test
    void testGetDeck() {
        // Create a new Deck
        Deck newDeck = new Deck();

        // Set the new Deck to the DeckField
        deckField.setDeck(newDeck);

        // Retrieve the Deck from the DeckField
        Deck retrievedDeck = deckField.getDeck();

        // Assert that the retrieved Deck is the new Deck
        assertEquals(newDeck, retrievedDeck, "Retrieved Deck should be the one that was set");
    }
    /**
     * @author Marcus Langkilde, s195080
     */
    @Test
    void testShuffleDeck() {
        // Get the initial deck
        Deck initialDeck = new Deck();

        // Shuffle the deck
        deckField.shuffleDeck(deckField.getDeck());

        // Assert that the deck has been shuffled
        assertNotEquals(initialDeck, deckField.getDeck(), "Deck should have been shuffled");
    }
    /**
     * @author Marcus Langkilde, s195080
     */
    @Test
    void testSetDeck() {
        // Create a new Deck
        Deck newDeck = new Deck();

        // Set the new Deck to the DeckField
        deckField.setDeck(newDeck);

        // Retrieve the Deck from the DeckField
        Deck retrievedDeck = deckField.getDeck();

        // Assert that the retrieved Deck is the new Deck
        assertEquals(newDeck, retrievedDeck, "Retrieved Deck should be the one that was set");
    }
    /**
     * @author Marcus Langkilde, s195080
     */
    @Test
    void testSetVisible() {
        // Set the visibility of the DeckField
        deckField.setVisible(true);

        // Retrieve the visibility from the DeckField
        boolean isVisible = deckField.isVisible();

        // Assert that the retrieved visibility is true
        assertTrue(isVisible, "Retrieved visibility should be true");

        // Set the visibility of the DeckField to false
        deckField.setVisible(false);

        // Retrieve the visibility from the DeckField
        isVisible = deckField.isVisible();

        // Assert that the retrieved visibility is false
        assertFalse(isVisible, "Retrieved visibility should be false");
    }
    /**
     * @author Marcus Langkilde, s195080
     */
    @Test
    void testIsVisible() {
        // Set the visibility of the DeckField
        deckField.setVisible(true);

        // Retrieve the visibility from the DeckField
        boolean isVisible = deckField.isVisible();

        // Assert that the retrieved visibility is true
        assertTrue(isVisible, "Retrieved visibility should be true");

        // Set the visibility of the DeckField to false
        deckField.setVisible(false);

        // Retrieve the visibility from the DeckField
        isVisible = deckField.isVisible();

        // Assert that the retrieved visibility is false
        assertFalse(isVisible, "Retrieved visibility should be false");
    }

}