import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dk.dtu.compute.se.pisd.roborally.model.*;

import java.util.Arrays;

class PriorityAntennaTest {

    private Board board;
    private PriorityAntenna antenna;

    @BeforeEach
    void setUp() {
        // Initialize the board and place the antenna in the center
        board = new Board(10, 10);
        antenna = new PriorityAntenna(BoardElement.Orientation.NORTH, 5, 5); // Assuming NORTH is a valid Orientation enum value
    }

    @Test
    void testSimpleDistanceSorting() {
        Player player1 = new Player(board, 1, "Player 1");
        player1.setSpace(new Space(board, 5, 3)); // Closer to the antenna
        Player player2 = new Player(board, 2, "Player 2");
        player2.setSpace(new Space(board, 5, 8)); // Farther from the antenna

        board.addPlayer(player1);
        board.addPlayer(player2);

        Player[] sortedPlayers = antenna.determinePriority(board);
        assertEquals(player1, sortedPlayers[0], "Player1 should be first as it is closer.");
        assertEquals(player2, sortedPlayers[1], "Player2 should be second as it is farther.");
    }

    @Test
    void testTieBreakingProximity() {
        Player player1 = new Player(board, 1, "Player 1");
        player1.setSpace(new Space(board, 5, 5)); // Same as antenna
        Player player2 = new Player(board, 2, "Player 2");
        player2.setSpace(new Space(board, 5, 5)); // Same as antenna

        board.addPlayer(player1);
        board.addPlayer(player2);

        Player[] sortedPlayers = antenna.determinePriority(board);
        // Assuming order determination by additional attributes like ID or name if needed
        assertEquals(player1, sortedPlayers[0], "Player1 should be first due to additional criteria.");
        assertEquals(player2, sortedPlayers[1], "Player2 should be second.");
    }

    @Test
    void testEdgeCasesAllSamePosition() {
        Player player1 = new Player(board, 1, "Player 1");
        player1.setSpace(new Space(board, 5, 5));
        Player player2 = new Player(board, 2, "Player 2");
        player2.setSpace(new Space(board, 5, 5));

        board.addPlayer(player1);
        board.addPlayer(player2);

        Player[] sortedPlayers = antenna.determinePriority(board);
        // Expecting no changes since all are at the same position
        assertEquals(player1, sortedPlayers[0], "Player1 should remain first.");
        assertEquals(player2, sortedPlayers[1], "Player2 should remain second.");
    }
}