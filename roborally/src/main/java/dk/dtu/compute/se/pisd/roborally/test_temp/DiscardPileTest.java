package dk.dtu.compute.se.pisd.roborally.test_temp;

import dk.dtu.compute.se.pisd.roborally.model.*;
import org.junit.Test;

import static junit.framework.Assert.*;

public class DiscardPileTest {
    @Test
    public void discardCardTest() {
        Board board = new Board(8, 8);
        Player player = new Player(board, 0, "Test Player");

        DiscardPileField discardPile = new DiscardPileField(player);

        assertNotNull(player.getDiscardedPile());

    }
}
