import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dk.dtu.compute.se.pisd.roborally.model.*;

class DiscardPileFieldTest {

    private DiscardPileField discardPileField;
    private Player player;

    @BeforeEach
    void setUp() {
        Board board = new Board(10, 10); // Create a 10x10 board
        this.player = new Player(board, 1, "Alice");
        this.discardPileField = new DiscardPileField(player);
    }
    /**
     * @author Marcus Langkilde, s195080
     */
    @Test
    void testSetPile() {
        // Create a new DiscardPile
        DiscardPile newPile = new DiscardPile();

        // Set the new DiscardPile to the DiscardPileField
        discardPileField.setPile(newPile);

        // Assert that the DiscardPile in the DiscardPileField is the new DiscardPile
        assertEquals(newPile, discardPileField.getPile(), "DiscardPile in DiscardPileField should be the one that was set");
    }
    /**
     * @author Marcus Langkilde, s195080
     */
    @Test
    void testGetPile() {
        // Create a new DiscardPile
        DiscardPile newPile = new DiscardPile();

        // Set the new DiscardPile to the DiscardPileField
        discardPileField.setPile(newPile);

        // Retrieve the DiscardPile from the DiscardPileField
        DiscardPile retrievedPile = discardPileField.getPile();

        // Assert that the retrieved DiscardPile is the new DiscardPile
        assertEquals(newPile, retrievedPile, "Retrieved DiscardPile should be the one that was set");
    }
    /**
     * @author Marcus Langkilde, s195080
     */
    @Test
    void testIsVisible() {
        // Set the visibility of the DiscardPileField
        discardPileField.setVisible(true);

        // Retrieve the visibility from the DiscardPileField
        boolean isVisible = discardPileField.isVisible();

        // Assert that the retrieved visibility is true
        assertTrue(isVisible, "Retrieved visibility should be true");

        // Set the visibility of the DiscardPileField to false
        discardPileField.setVisible(false);

        // Retrieve the visibility from the DiscardPileField
        isVisible = discardPileField.isVisible();

        // Assert that the retrieved visibility is false
        assertFalse(isVisible, "Retrieved visibility should be false");
    }
    /**
     * @author Marcus Langkilde, s195080
     */
    @Test
    void testSetVisible() {
        // Set the visibility of the DiscardPileField
        discardPileField.setVisible(true);

        // Retrieve the visibility from the DiscardPileField
        boolean isVisible = discardPileField.isVisible();

        // Assert that the retrieved visibility is true
        assertTrue(isVisible, "Retrieved visibility should be true");

        // Set the visibility of the DiscardPileField to false
        discardPileField.setVisible(false);

        // Retrieve the visibility from the DiscardPileField
        isVisible = discardPileField.isVisible();

        // Assert that the retrieved visibility is false
        assertFalse(isVisible, "Retrieved visibility should be false");
    }

}