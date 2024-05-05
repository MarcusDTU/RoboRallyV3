import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import dk.dtu.compute.se.pisd.roborally.model.Command;
import dk.dtu.compute.se.pisd.roborally.model.DiscardPile;

import java.util.List;

class DiscardPileTest {
    /**
     * @author Asma Maryam, s230716@dtu.dk
     * @author Turan Talayhan, s224746@student.dtu.dk
     */

    @Test
    void testInitialization() {
        DiscardPile discardPile = new DiscardPile();
        assertNotNull(discardPile.pile, "Pile should not be null upon initialization.");
        assertTrue(discardPile.pile.isEmpty(), "Pile should be empty upon initialization.");
    }
    /**
     * @author Asma Maryam, s230716@dtu.dk
     * @author Turan Talayhan, s224746@student.dtu.dk
     */
    @Test
    void testAddingCommands() {
        DiscardPile discardPile = new DiscardPile();
        Command command1 = Command.FORWARD;
        Command command2 = Command.LEFT;

        discardPile.pile.add(command1);
        assertEquals(1, discardPile.pile.size(), "Pile should contain one command after adding.");
        assertEquals(command1, discardPile.pile.get(0), "The command in the pile should be the one that was added first.");

        discardPile.pile.add(command2);
        assertEquals(2, discardPile.pile.size(), "Pile should contain two commands after adding another.");
        assertEquals(command2, discardPile.pile.get(1), "The second command in the pile should be the one that was added second.");
    }
    /**
     * @author Asma Maryam, s230716@dtu.dk
     * @author Turan Talayhan, s224746@student.dtu.dk
     */
    @Test
    void testListBehavior() {
        DiscardPile discardPile = new DiscardPile();
        discardPile.pile.add(Command.FORWARD);
        discardPile.pile.add(Command.RIGHT);
        discardPile.pile.add(Command.LEFT);

        List<Command> expectedCommands = List.of(Command.FORWARD, Command.RIGHT, Command.LEFT);
        assertEquals(expectedCommands, discardPile.pile, "The pile should maintain the order of commands as they were added.");
    }
}