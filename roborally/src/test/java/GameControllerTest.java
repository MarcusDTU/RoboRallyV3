import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.*;

import java.lang.reflect.Method;

class GameControllerTest {

    private GameController gameController;
    private Board board;

    @BeforeEach
    void setUp() {
      this.board = new Board(10, 10); // Create a 10x10 board
        this.gameController = new GameController(board);
    }
    /**
     * @author Asma Maryam, s230716@dtu.dk
     * @author Turan Talayhan, s224746@student.dtu.dk
     */

    @Test
    void testMoveForward() {
        Player player = new Player(board, 1, "Alice");
        Space startingSpace = board.getSpace(5, 5);
        player.setSpace(startingSpace);
        player.setHeading(Heading.NORTH);

        gameController.moveForward(player);
        assertEquals(board.getSpace(5, 4), player.getSpace(), "Player should move one space North.");
    }
    /**
     * @author Asma Maryam, s230716@dtu.dk
     * @author Turan Talayhan, s224746@student.dtu.dk
     */

    @Test
    void testFastForward() {
        Player player = new Player(board, 1, "Alice");
        Space startingSpace = board.getSpace(5, 5);
        player.setSpace(startingSpace);
        player.setHeading(Heading.SOUTH);

        gameController.fastForward(player);
        assertEquals(board.getSpace(5, 7), player.getSpace(), "Player should move two spaces South.");
    }
    /**
     * @author Asma Maryam, s230716@dtu.dk
     * @author Turan Talayhan, s224746@student.dtu.dk
     */
    @Test
    void testTurnRight() {
        Player player = new Player(board, 1, "Alice");
        player.setHeading(Heading.NORTH);

        gameController.turnRight(player);
        assertEquals(Heading.EAST, player.getHeading(), "Player should now face East.");
    }
    /**
     * @author Asma Maryam, s230716@dtu.dk
     * @author Turan Talayhan, s224746@student.dtu.dk
     */

    @Test
    void testTurnLeft() {
        Player player = new Player(board, 1, "Alice");
        player.setHeading(Heading.NORTH);

        gameController.turnLeft(player);
        assertEquals(Heading.WEST, player.getHeading(), "Player should now face west.");
    }

    /**
     * @author Asma Maryam, s230716@dtu.dk
     * @author Turan Talayhan, s224746@student.dtu.dk
     */
    @Test
    void testPhaseTransition() {
        gameController.startProgrammingPhase();
        assertEquals(Phase.PROGRAMMING, board.getPhase(), "Board phase should be set to PROGRAMMING.");

        gameController.startActivationPhase(0);
        assertEquals(Phase.ACTIVATION, board.getPhase(), "Board phase should be set to ACTIVATION.");
    }
    /**
     * @author Marcus Langkilde, s195080
     */
    @Test
    void testExecuteCommand() throws Exception {
        // Create a new player and set their initial position and heading
        Player player = new Player(board, 1, "Alice");
        Space startingSpace = board.getSpace(5, 5);
        player.setSpace(startingSpace);
        player.setHeading(Heading.NORTH);

        // Use reflection to get the executeCommand method
        Method executeCommand = GameController.class.getDeclaredMethod("executeCommand", Player.class, Command.class);

        // Set the method to be accessible
        executeCommand.setAccessible(true);

        // Invoke the method on the gameController instance
        executeCommand.invoke(gameController, player, Command.FORWARD);

        // Assert that the player's new space is the expected space
        assertEquals(board.getSpace(5, 4), player.getSpace(), "Player should move one space North.");
    }
    /**
     * @author Marcus Langkilde, s195080
     */
    @Test
    void testExecuteCommandOptionAndContinue() {
        // Create a new player and set their initial position and heading
        Player player = new Player(board, 1, "Alice");
        Space startingSpace = board.getSpace(5, 5);
        player.setSpace(startingSpace);
        player.setHeading(Heading.NORTH);

        // Set the player's command to FORWARD
        player.getProgramField(0).setCard(new CommandCard(Command.FORWARD));

        board.addPlayer(player);
        // Set the current player and phase
        board.setCurrentPlayer(player);
        board.setPhase(Phase.ACTIVATION);

        // Execute the command option and continue
        gameController.executeCommandOptionAndContinue(Command.FORWARD);

        // Assert that the player's new space is the expected space
        assertEquals(board.getSpace(5, 4), player.getSpace(), "Player should move one space North.");

        // Assert that the current player is the next player
        assertEquals(board.getPlayer(1), null, "Current player should be the next player.");
    }
    /**
     * @author Marcus Langkilde, s195080
     */
    @Test
    void testNotImplemented() {
        assertThrows(AssertionError.class, () -> gameController.notImplemented());
    }
    /**
     * @author Marcus Langkilde, s195080
     */
    @Test
    void testGenerateRandomCommandCard() throws Exception {
        // Use reflection to get the generateRandomCommandCard method
        Method generateRandomCommandCard = GameController.class.getDeclaredMethod("generateRandomCommandCard");

        // Set the method to be accessible
        generateRandomCommandCard.setAccessible(true);

        // Invoke the method on the gameController instance
        CommandCard commandCard = (CommandCard) generateRandomCommandCard.invoke(gameController);

        assertNotNull(commandCard, "Generated CommandCard should not be null");
        assertTrue(commandCard instanceof CommandCard, "Generated object should be an instance of CommandCard");
        assertNotNull(commandCard.command, "Command in CommandCard should not be null");
        assertTrue(commandCard.command instanceof Command, "Command in CommandCard should be an instance of Command");
    }
    /**
     * @author Marcus Langkilde, s195080
     */
    @Test
    void testMoveCards() {
        // Create a new player and set their initial position and heading
        Player player = new Player(board, 1, "Alice");
        Space startingSpace = board.getSpace(5, 5);
        player.setSpace(startingSpace);
        player.setHeading(Heading.NORTH);

        // Add the player to the game
        board.addPlayer(player);

        // Set the current player and phase
        board.setCurrentPlayer(player);
        board.setPhase(Phase.ACTIVATION);

        // Create two CommandCardFields
        CommandCardField source = new CommandCardField(player);
        CommandCardField target = new CommandCardField(player);

        // Set a card in the source field
        source.setCard(new CommandCard(Command.FORWARD));

        // Move the card from source to target
        boolean result = gameController.moveCards(source, target);

        // Assert that the move was successful
        assertTrue(result, "Move should be successful");

        // Assert that the source field is now empty
        assertNull(source.getCard(), "Source field should be empty after the move");

        // Assert that the target field now contains the card
        assertNotNull(target.getCard(), "Target field should contain the card after the move");
        assertEquals(Command.FORWARD, target.getCard().command, "Target field should contain the FORWARD command card");
    }
    /**
     * @author Marcus Langkilde, s195080
     */
    @Test
    void testFinishProgrammingPhase() {
        // Set the current phase to PROGRAMMING
        board.setPhase(Phase.PROGRAMMING);

        // Call the method to be tested
        gameController.finishProgrammingPhase();

        // Assert that the phase has been changed to ACTIVATION
        assertEquals(Phase.ACTIVATION, board.getPhase(), "Board phase should be set to ACTIVATION");

        // Assert that the current player is the first player
        assertEquals(board.getPlayer(0), board.getCurrentPlayer(), "Current player should be the first player");

        // Assert that the step is 0
        assertEquals(0, board.getStep(), "Step should be 0");

        // Assert that the first program field of each player is visible
        for (Player player : board.getPlayers()) {
            assertTrue(player.getProgramField(0).isVisible(), "First program field of each player should be visible");
        }

        // Assert that all other program fields of each player are invisible
        for (Player player : board.getPlayers()) {
            for (int i = 1; i < Player.NO_REGISTERS; i++) {
                assertFalse(player.getProgramField(i).isVisible(), "All other program fields of each player should be invisible");
            }
        }
    }
    /**
     * @author Marcus Langkilde, s195080
     */
    @Test
    void testMakeProgramFieldsInvisible() throws Exception {
        // Use reflection to get the makeProgramFieldsInvisible method
        Method makeProgramFieldsInvisible = GameController.class.getDeclaredMethod("makeProgramFieldsInvisible");

        // Set the method to be accessible
        makeProgramFieldsInvisible.setAccessible(true);

        // Invoke the method on the gameController instance
        makeProgramFieldsInvisible.invoke(gameController);

        // Assert that all program fields of each player are invisible
        for (Player player : board.getPlayers()) {
            for (int i = 0; i < Player.NO_REGISTERS; i++) {
                CommandCardField field = player.getProgramField(i);
                assertFalse(field.isVisible(), "All program fields of each player should be invisible");
            }
        }
    }
    /**
     * @author Marcus Langkilde, s195080
     */
    @Test
    void testMakeProgramFieldsVisible() throws Exception {
        // Use reflection to get the makeProgramFieldsVisible method
        Method makeProgramFieldsVisible = GameController.class.getDeclaredMethod("makeProgramFieldsVisible", int.class);

        // Set the method to be accessible
        makeProgramFieldsVisible.setAccessible(true);

        // Invoke the method on the gameController instance
        makeProgramFieldsVisible.invoke(gameController, 1);

        // Assert that all program fields of each player at register 1 are visible
        for (Player player : board.getPlayers()) {
            CommandCardField field = player.getProgramField(1);
            assertTrue(field.isVisible(), "Program field at register 1 of each player should be visible");
        }
    }
    /**
     * @author Marcus Langkilde, s195080
     */
    @Test
    void testMoveCurrentPlayerToSpace() {
        // Create a new player and set their initial position and heading
        Player player = new Player(board, 1, "Alice");
        Space startingSpace = board.getSpace(5, 5);
        player.setSpace(startingSpace);
        player.setHeading(Heading.NORTH);

        // Add the player to the game
        board.addPlayer(player);

        // Set the current player
        board.setCurrentPlayer(player);

        // Create a target space
        Space targetSpace = board.getSpace(6, 6);

        // Move the current player to the target space
        gameController.moveCurrentPlayerToSpace(targetSpace);

        // Assert that the player's space is now the target space
        assertEquals(targetSpace, player.getSpace(), "Player's space should be the target space after the move");

        // Assert that the target space's player is now the player
        assertEquals(player, targetSpace.getPlayer(), "Target space's player should be the player after the move");
    }
    /**
     * @author Marcus Langkilde, s195080
     */
    @Test
    void testPowerUp() {
        // Create a new player
        Player player = new Player(board, 1, "Alice");

        // Add the player to the game
        board.addPlayer(player);

        // Get the initial power-up count of the player
        int initialPowerUpCount = player.getPowerUpCnt();

        // Call the method to be tested
        gameController.powerUp(player);

        // Assert that the power-up count of the player has increased by 1
        assertEquals(initialPowerUpCount + 1, player.getPowerUpCnt(), "Power-up count should have increased by 1");
    }
    /**
     * @author Marcus Langkilde, s195080
     */
    @Test
    void testMoveToSpace() throws Exception {
        // Create a new player and set their initial position and heading
        Player player = new Player(board, 1, "Alice");
        Space startingSpace = board.getSpace(5, 5);
        player.setSpace(startingSpace);
        player.setHeading(Heading.NORTH);

        // Add the player to the game
        board.addPlayer(player);

        // Create a target space
        Space targetSpace = board.getNeighbour(startingSpace, Heading.NORTH); // Ensure the target space is the neighbor of the starting space in the direction of the player's heading

        // Use reflection to get the moveToSpace method
        Method moveToSpace = GameController.class.getDeclaredMethod("moveToSpace", Player.class, Space.class, Heading.class);

        // Set the method to be accessible
        moveToSpace.setAccessible(true);

        // Invoke the method on the gameController instance
        moveToSpace.invoke(gameController, player, targetSpace, Heading.NORTH);

        // Assert that the player's space is now the target space
        assertEquals(targetSpace, player.getSpace(), "Player's space should be the target space after the move");

        // Assert that the target space's player is now the player
        assertEquals(player, targetSpace.getPlayer(), "Target space's player should be the player after the move");
    }
    /**
     * @author Marcus Langkilde, s195080
     */
    @Test
    void testBackup() {
        // Create a new player and set their initial position and heading
        Player player = new Player(board, 1, "Alice");
        Space startingSpace = board.getSpace(5, 5);
        player.setSpace(startingSpace);
        player.setHeading(Heading.NORTH);

        // Call the backup method on the gameController instance
        gameController.backup(player);

        // Assert that the player's new space is the expected space
        assertEquals(board.getSpace(5, 6), player.getSpace(), "Player should move one space South.");
    }

}
