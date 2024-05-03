package dk.dtu.compute.se.pisd.roborally.test_temp;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.junit.Test;

public class GameControllerTest {
    Board board = new Board(4,4);
    Space spcae = new Space(board, 4, 4);
    Player player = new Player(board, 1, "Player 1");
    GameController gameController = new GameController(board);


    @Test
    public void moveForwardTest() {
        gameController.moveForward(player);
        assert player.getSpace().equals(board.getSpace(1, 0));
    }

    @Test
    public void PowerUpTest() {
        gameController.powerUp(player);
        assert player.getPowerUpCnt() == 1;
    }



}
