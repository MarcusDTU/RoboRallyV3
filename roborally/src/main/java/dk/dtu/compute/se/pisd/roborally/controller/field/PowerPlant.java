package dk.dtu.compute.se.pisd.roborally.controller.field;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Space;

/**
 * Represents the power plant field in the game
 * @author Nikolaj Schæbel, s220471@dtu.dk
 */
public class PowerPlant extends FieldAction {

    @Override
    public boolean doAction(GameController gameController, Space space) {
        return false;
    }
}
