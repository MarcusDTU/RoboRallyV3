import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dk.dtu.compute.se.pisd.roborally.model.*;

class PlayerTest {

    private Board board;
    private Player player;

    @BeforeEach
    void setUp() {
        board = new Board(10, 10);
        player = new Player(board, 1, "Alice");
    }
    /**
     * @author Asma Maryam, s230716@dtu.dk
     * @author Turan Talayhan, s224746@student.dtu.dk
     */
    @Test
    void testConstructorInitialization() {
        assertEquals("Alice", player.getName(), "Player name should be initialized to Alice.");
        assertEquals(1, player.getRobotId(), "Player robot ID should be initialized to 1.");
        assertNull(player.getSpace(), "Player space should initially be null.");
        assertEquals(Heading.SOUTH, player.getHeading(), "Player heading should initially be SOUTH.");
        assertNotNull(player.getProgram(), "Program should not be null.");
        assertNotNull(player.getCards(), "Cards should not be null.");
    }
    /**
     * @author Asma Maryam, s230716@dtu.dk
     * @author Turan Talayhan, s224746@student.dtu.dk
     */
    @Test
    void testSpaceManagement() {
        Space space = new Space(board, 5, 5);
        player.setSpace(space);
        assertEquals(space, player.getSpace(), "Player space should be set to the provided space.");
        assertEquals(player, space.getPlayer(), "Space should reference back to the player.");
    }
    /**
     * @author Asma Maryam, s230716@dtu.dk
     * @author Turan Talayhan, s224746@student.dtu.dk
     */
    @Test
    void testHeadingManagement() {
        player.setHeading(Heading.NORTH);
        assertEquals(Heading.NORTH, player.getHeading(), "Player heading should be updated to NORTH.");
    }
    /**
     * @author Asma Maryam, s230716@dtu.dk
     * @author Turan Talayhan, s224746@student.dtu.dk
     */
    @Test
    void testCardAndProgramManagement() {
        CommandCardField programField = new CommandCardField(player);
        CommandCardField cardField = new CommandCardField(player);

        player.getProgram()[0] = programField;
        player.getCards()[0] = cardField;

        assertEquals(programField, player.getProgramField(0), "Program field should be retrievable.");
        assertEquals(cardField, player.getCardField(0), "Card field should be retrievable.");
    }
}