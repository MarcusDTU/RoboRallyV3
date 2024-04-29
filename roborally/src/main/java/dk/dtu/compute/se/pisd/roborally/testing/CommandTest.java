package dk.dtu.compute.se.pisd.roborally.testing;

import static org.junit.jupiter.api.Assertions.*;

import dk.dtu.compute.se.pisd.roborally.model.Command;
import org.junit.jupiter.api.Test;
import java.util.List;

class CommandTest {

    @Test
    void testDisplayName() {
        assertEquals("Fwd", Command.FORWARD.displayName, "FORWARD should have display name 'Fwd'.");
        assertEquals("Turn Right", Command.RIGHT.displayName, "RIGHT should have display name 'Turn Right'.");
        assertEquals("Turn Left", Command.LEFT.displayName, "LEFT should have display name 'Turn Left'.");
        assertEquals("Fast Fwd", Command.FAST_FORWARD.displayName, "FAST_FORWARD should have display name 'Fast Fwd'.");
        assertEquals("Again", Command.AGAIN.displayName, "AGAIN should have display name 'Again'.");
        assertEquals("Power Up", Command.POWER_UP.displayName, "POWER_UP should have display name 'Power Up'.");
        assertEquals("Fast Fast Fwd", Command.FAST_FAST_FORWARD.displayName, "FAST_FAST_FORWARD should have display name 'Fast Fast Fwd'.");
        assertEquals("U-Turn", Command.U_TURN.displayName, "U_TURN should have display name 'U-Turn'.");
        assertEquals("Left OR Right", Command.OPTION_LEFT_RIGHT.displayName, "OPTION_LEFT_RIGHT should have display name 'Left OR Right'.");
    }

    @Test
    void testOptions() {
        assertTrue(Command.OPTION_LEFT_RIGHT.getOptions().containsAll(List.of(Command.LEFT, Command.RIGHT)),
                "OPTION_LEFT_RIGHT should have options LEFT and RIGHT.");
        assertTrue(Command.FORWARD.getOptions().isEmpty(), "FORWARD should have no options.");
    }

    @Test
    void testIsInteractive() {
        assertFalse(Command.FORWARD.isInteractive(), "FORWARD should not be interactive.");
        assertFalse(Command.FAST_FORWARD.isInteractive(), "FAST_FORWARD should not be interactive.");
        assertTrue(Command.OPTION_LEFT_RIGHT.isInteractive(), "OPTION_LEFT_RIGHT should be interactive.");
    }
}