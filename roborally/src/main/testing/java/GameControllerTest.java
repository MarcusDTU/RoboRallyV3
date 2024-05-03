package dk.dtu.compute.se.pisd.roborally.testing;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.*;

class GameControllerTest {

    private GameController gameController;
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board(10, 10); // Create a 10x10 board
        gameController = new GameController(board);
    }

    @Test
    void testMoveForward() {
        Player player = new Player(board, 1, "Alice");
        Space startingSpace = board.getSpace(5, 5);
        player.setSpace(startingSpace);
        player.setHeading(Heading.NORTH);

        gameController.moveForward(player);
        assertEquals(board.getSpace(5, 4), player.getSpace(), "Player should move one space North.");
    }


    @Test
    void testFastForward() {
        Player player = new Player(board, 1, "Alice");
        Space startingSpace = board.getSpace(5, 5);
        player.setSpace(startingSpace);
        player.setHeading(Heading.SOUTH);

        gameController.fastForward(player);
        assertEquals(board.getSpace(5, 7), player.getSpace(), "Player should move two spaces South.");
    }

    @Test
    void testTurnRight() {
        Player player = new Player(board, 1, "Alice");
        player.setHeading(Heading.NORTH);

        gameController.turnRight(player);
        assertEquals(Heading.EAST, player.getHeading(), "Player should now face East.");
    }

    @Test
    void testTurnLeft() {
        Player player = new Player(board, 1, "Alice");
        player.setHeading(Heading.NORTH);

        gameController.turnLeft(player);
        assertEquals(Heading.WEST, player.getHeading(), "Player should now face west.");
    }


    @Test
    void testPhaseTransition() {
        gameController.startProgrammingPhase();
        assertEquals(Phase.PROGRAMMING, board.getPhase(), "Board phase should be set to PROGRAMMING.");

        gameController.startActivationPhase(0);
        assertEquals(Phase.ACTIVATION, board.getPhase(), "Board phase should be set to ACTIVATION.");
    }

}