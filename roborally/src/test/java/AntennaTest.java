

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.controller.field.Antenna;
import dk.dtu.compute.se.pisd.roborally.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AntennaTest {

    private Antenna antenna;
    private GameController gameController;
    private Board board;
    private Space antennaSpace;
    private Player player1, player2, player3;

    @BeforeEach
    void setUp() {
        board = new Board(10, 10);
        gameController = new GameController(board);

        antenna = new Antenna();
        antenna.setHeading(Heading.NORTH);

        player1 = new Player(board, 1, "Alice");
        player2 = new Player(board, 2, "Bob");
        player3 = new Player(board, 3, "Charlie");

        // Ensure each player has a valid space
        board.getSpace(5, 4).setPlayer(player1); // North of antenna
        player1.setSpace(board.getSpace(5, 4));
        board.getSpace(5, 6).setPlayer(player2); // South of antenna
        player2.setSpace(board.getSpace(5, 6));
        board.getSpace(4, 5).setPlayer(player3); // West of antenna
        player3.setSpace(board.getSpace(4, 5));

        antennaSpace = board.getSpace(5, 5);
    }
    /**
     * @author Asma Maryam, s230716@dtu.dk
     * @author Turan Talayhan, s224746@student.dtu.dk
     */

    @Test
    void testSetAndGetHeading() {
        assertEquals(Heading.NORTH, antenna.getHeading(), "Heading should be NORTH initially");
        antenna.setHeading(Heading.EAST);
        assertEquals(Heading.EAST, antenna.getHeading(), "Heading should be changed to EAST");
    }

    /**
     * @author Asma Maryam, s230716@dtu.dk
     * @author Turan Talayhan, s224746@student.dtu.dk
     */
    @Test
    void testCalculateDistance() {
        assertEquals(1, antenna.calculateDistance(player1, antennaSpace), "Distance should be 1 for player1");
        assertEquals(1, antenna.calculateDistance(player3, antennaSpace), "Distance should be 1 for player3");
        assertEquals(1, antenna.calculateDistance(player2, antennaSpace), "Distance should be 1 for player2");
    }

    /**
     * @author Asma Maryam, s230716@dtu.dk
     * @author Turan Talayhan, s224746@student.dtu.dk
     */
    @Test
    void testSortPlayers() {
        Player[] players = {player1, player2, player3};
        Player[] sortedPlayers = antenna.sortPlayers(players, antennaSpace);
        assertNotNull(sortedPlayers);
        assertEquals(3, sortedPlayers.length);
        System.out.println("Sorted players: " + Arrays.toString(sortedPlayers));  // Output sorted player names
    }
    /**
     * @author Asma Maryam, s230716@dtu.dk
     * @author Turan Talayhan, s224746@student.dtu.dk
     */

    @Test
    void testSortSameDistance() {
        Player[] players = {player1, player2, player3};
        Player[] sortedPlayers = antenna.sortPlayers(players, antennaSpace);
        assertNotNull(sortedPlayers);
        assertEquals(3, sortedPlayers.length);
        System.out.println("Sorted players: " + Arrays.toString(sortedPlayers));  // Output sorted player names
    }
}