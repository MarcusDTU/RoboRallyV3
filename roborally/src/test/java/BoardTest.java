import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dk.dtu.compute.se.pisd.roborally.model.*;

import java.util.ArrayList;
import java.util.List;

class BoardTest {
    private Board board;
    private Player player;

    @BeforeEach
    void setUp() {
        board = new Board(8, 8);
        player = new Player(board, 1, "Alice");
        board.addPlayer(player);
    }

    /**
    * @author Asma Maryam, s230716@dtu.dk
    * @author Turan Talayhan, s224746@student.dtu.dk
     */

    @Test
    void testBoardInitialization() {
        assertEquals(8, board.getWidth(), "Board width should be initialized to 8.");
        assertEquals(8, board.getHeight(), "Board height should be initialized to 8.");
        assertNotNull(board.getSpace(0, 0), "Space at (0,0) should not be null.");
    }

    /**
     * @author Asma Maryam, s230716@dtu.dk
     * @author Turan Talayhan, s224746@student.dtu.dk
     */

    @Test
    void testAddAndGetPlayer() {
        assertEquals(1, board.getPlayersNumber(), "Board should have 1 player after adding.");
        assertSame(player, board.getPlayer(0), "Should retrieve the same player added to the board.");
    }

    /**
     * @author Asma Maryam, s230716@dtu.dk
     * @author Turan Talayhan, s224746@student.dtu.dk
     */
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

    /**
     * @author Asma Maryam, s230716@dtu.dk
     * @author Turan Talayhan, s224746@student.dtu.dk
     */

    @Test
    void testPhaseAndStep() {
        board.setPhase(Phase.PROGRAMMING);
        assertEquals(Phase.PROGRAMMING, board.getPhase(), "Phase should be set to PROGRAMMING.");
        board.setStep(5);
        assertEquals(5, board.getStep(), "Step should be set to 5.");
    }
    @Test
    void testSetPlayers() {
        // Create a list of players
        List<Player> players = new ArrayList<>();
        Player player1 = new Player(board, 1, "Alice");
        Player player2 = new Player(board, 2, "Bob");
        players.add(player1);
        players.add(player2);

        // Set the players to the board
        board.setPlayers(players);

        // Retrieve the players from the board
        Player[] retrievedPlayers = board.getPlayers();

        // Assert that the retrieved players are the ones that were set
        assertArrayEquals(players.toArray(), retrievedPlayers, "Retrieved players should be the ones that were set");
    }
    @Test
    void testSetStepMode() {
        // Set the step mode of the Board
        board.setStepMode(true);

        // Retrieve the step mode from the Board
        boolean isStepMode = board.isStepMode();

        // Assert that the retrieved step mode is true
        assertTrue(isStepMode, "Retrieved step mode should be true");

        // Set the step mode of the Board to false
        board.setStepMode(false);

        // Retrieve the step mode from the Board
        isStepMode = board.isStepMode();

        // Assert that the retrieved step mode is false
        assertFalse(isStepMode, "Retrieved step mode should be false");
    }
    @Test
    void testIsStepMode() {
        // Set the step mode of the Board
        board.setStepMode(true);

        // Retrieve the step mode from the Board
        boolean isStepMode = board.isStepMode();

        // Assert that the retrieved step mode is true
        assertTrue(isStepMode, "Retrieved step mode should be true");

        // Set the step mode of the Board to false
        board.setStepMode(false);

        // Retrieve the step mode from the Board
        isStepMode = board.isStepMode();

        // Assert that the retrieved step mode is false
        assertFalse(isStepMode, "Retrieved step mode should be false");
    }
    @Test
    void testGetStatusMessage() {
        // Set the phase, current player, step, and total moves of the Board
        board.setPhase(Phase.ACTIVATION);
        board.setCurrentPlayer(player);
        board.setStep(3);
        board.setTotalMoves(10);

        // Retrieve the status message from the Board
        String statusMessage = board.getStatusMessage();

        // Construct the expected status message
        String expectedStatusMessage = "Phase: " + board.getPhase().name() +
                ", Player = " + board.getCurrentPlayer().getName() +
                ", Step: " + board.getStep() +
                ", Total Moves:" + board.getTotalMoves();

        // Assert that the retrieved status message is the expected one
        assertEquals(expectedStatusMessage, statusMessage, "Retrieved status message should be the expected one");
    }
    @Test
    void testGetSpace() {
        // Retrieve the space from the Board at coordinates (3, 4)
        Space space = board.getSpace(3, 4);

        // Assert that the retrieved space's x and y coordinates are as expected
        assertEquals(3, space.x, "Retrieved space's x coordinate should be 3");
        assertEquals(4, space.y, "Retrieved space's y coordinate should be 4");
    }
    @Test
    void testSetGameId() {
        // Set the game ID of the Board
        board.setGameId(12345);

        // Retrieve the game ID from the Board
        int gameId = board.getGameId();

        // Assert that the retrieved game ID is the one that was set
        assertEquals(12345, gameId, "Retrieved game ID should be the one that was set");
    }
    @Test
    void testGetSpaces() {
        // Retrieve the spaces from the Board
        Space[][] spaces = board.getSpaces();

        // Assert that the retrieved spaces are not null
        assertNotNull(spaces, "Retrieved spaces should not be null");

        // Assert that each space in the retrieved spaces is not null
        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                assertNotNull(spaces[i][j], "Space at (" + i + "," + j + ") should not be null");
            }
        }
    }

}