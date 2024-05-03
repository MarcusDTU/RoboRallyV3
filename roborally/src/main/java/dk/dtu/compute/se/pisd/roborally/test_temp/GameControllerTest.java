package dk.dtu.compute.se.pisd.roborally.test_temp;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import org.junit.Before;
import org.junit.Test;

public class GameControllerTest {
    Board board = new Board(4,4);
    Player player = new Player(board, 1, "Player 1");
    GameController gameController = new GameController(board);
    @Before
    public void setUp() {
        // Create a new board
        board = new Board(4,4);

        // Add spaces to the board
        for (int x = 0; x < board.width; x++) {
            for (int y = 0; y < board.height; y++) {
                Space space = new Space(board, x, y);
                board.spaces[x][y] = space;
            }
        }

        // Add a player to the board
        player = new Player(board, 1, "Player 1");
        board.addPlayer(player);

        // Set the initial position of the player
        player.setSpace(board.getSpace(0, 0));
        player.setHeading(Heading.SOUTH);
        // Create a new game controller
        gameController = new GameController(board);
    }



    @Test
    public void moveForwardTest() {
        gameController.moveForward(player);
        assert player.getSpace().equals(board.getSpace(0, 1));
    }

    @Test
    public void PowerUpTest() {
        gameController.powerUp(player);
        assert player.getPowerUpCnt() == 1;
    }



}
