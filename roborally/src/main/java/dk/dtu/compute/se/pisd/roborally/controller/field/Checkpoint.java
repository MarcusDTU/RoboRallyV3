package dk.dtu.compute.se.pisd.roborally.controller.field;

import com.google.gson.annotations.Expose;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.jetbrains.annotations.NotNull;

public class Checkpoint extends FieldAction {
    @Expose
    private int orderNumber;

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    /**
     * This method checks if the player is on the checkpoint and have collected the previous checkpoint
     * @param gameController the game controller
     * @param space the space the player is on
     * @return true if the player is on the checkpoint and have collected the previous checkpoint
     * @author Nikolaj Schæbel, s220471@dtu.dk
     */
    @Override
    public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {
        //update the player's checkpoint if the player is on the checkpoint and have collected the previous checkpoint
        for(Player p: gameController.board.getPlayers()){
            if(p.getSpace().equals(space) && p.getCheckpointCollected() == (orderNumber - 1)){
                p.setCheckpoint(orderNumber);
                return true;
            }
        }
        return false;
    }
}
