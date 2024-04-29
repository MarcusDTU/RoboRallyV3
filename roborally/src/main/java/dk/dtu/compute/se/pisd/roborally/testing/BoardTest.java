package testing;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dk.dtu.compute.se.pisd.roborally.model.*;

class BoardTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board(8, 8);
    }

    @Test
    void testBoardInitialization() {
       assertEquals(8, board.getWidth(), "Board width should be initialized to 8.");
       assertEquals(8, board.getHeight(), "Board height should be initialized to 8.");
        assertNotNull(board.getSpace(0, 0), "Space at (0,0) should not be null.");
    }

    @Test
    void testAddAndGetPlayer() {
        Player player = new Player(board, 1, "Player1");
        board.addPlayer(player);
        assertEquals(1, board.getPlayersNumber(), "Board should have 1 player after adding.");
        assertSame(player, board.getPlayer(0), "Should retrieve the same player added to the board.");
    }

    @Test
    void testGetNeighbour() {
        Space origin = board.getSpace(0, 0);
        Space eastNeighbor = board.getNeighbour(origin, Heading.EAST);
        Space expectedEast = board.getSpace(1, 0);
        assertSame(expectedEast, eastNeighbor, "East neighbor of (0,0) should be (1,0).");

        Space wrapAroundNeighbor = board.getNeighbour(origin, Heading.NORTH);
        Space expectedWrapAround = board.getSpace(0, 7);
        assertSame(expectedWrapAround, wrapAroundNeighbor, "North neighbor of (0,0) should wrap around to (0,7).");
    }

    @Test
    void testPhaseAndStep() {
        board.setPhase(Phase.PROGRAMMING);
        assertEquals(Phase.PROGRAMMING, board.getPhase(), "Phase should be set to PROGRAMMING.");
        board.setStep(5);
        assertEquals(5, board.getStep(), "Step should be set to 5.");
    }
}