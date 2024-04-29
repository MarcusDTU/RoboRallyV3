package dk.dtu.compute.se.pisd.roborally.testing;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import dk.dtu.compute.se.pisd.roborally.model.Command;
import dk.dtu.compute.se.pisd.roborally.model.CommandCard;

class CommandCardTest {

    @Test
    void testConstructorInitialization() {
        CommandCard card = new CommandCard(Command.FORWARD);
        assertNotNull(card.command, "The command attribute should be initialized.");
        assertEquals(Command.FORWARD, card.command, "The command should be FORWARD.");
    }

    @Test
    void testGetName() {
        CommandCard card = new CommandCard(Command.FAST_FORWARD);
        assertEquals("Fast Fwd", card.getName(), "getName should return 'Fast Fwd' for FAST_FORWARD command.");
    }
}