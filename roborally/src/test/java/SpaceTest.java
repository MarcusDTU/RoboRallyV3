import static org.junit.jupiter.api.Assertions.*;

import dk.dtu.compute.se.pisd.roborally.controller.field.FieldAction;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

class SpaceTest {

    private Board board;
    private Space space;

    @BeforeEach
    void setUp() {
        board = new Board(10, 10); // Assuming this creates a 10x10 board
        space = new Space(board, 5, 5); // Place the space at position (5, 5)
    }
    /**
     * @author Asma Maryam, s230716@dtu.dk
     * @author Turan Talayhan, s224746@student.dtu.dk
     */
    @Test
    void testConstructorInitialization() {
        assertEquals(5, space.x, "Space x-coordinate should be initialized to 5.");
        assertEquals(5, space.y, "Space y-coordinate should be initialized to 5.");
        assertNull(space.getPlayer(), "Initially, no player should be in the space.");
    }
    /**
     * @author Asma Maryam, s230716@dtu.dk
     * @author Turan Talayhan, s224746@student.dtu.dk
     */
    @Test
    void testSetAndGetPlayer() {
        Player player = new Player(board, 1, "Test Player");
        space.setPlayer(player);
        assertSame(player, space.getPlayer(), "The player should be set and retrieved correctly.");

        // Verify that setting a new player updates correctly
        Player newPlayer = new Player(board, 2, "New Test Player");
        space.setPlayer(newPlayer);
        assertSame(newPlayer, space.getPlayer(), "New player should replace the old one.");
        assertNull(player.getSpace(), "The old player's space should be null after moving out.");
    }
}
