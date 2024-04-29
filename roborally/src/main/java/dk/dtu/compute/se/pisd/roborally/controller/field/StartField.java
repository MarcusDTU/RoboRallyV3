package dk.dtu.compute.se.pisd.roborally.controller.field;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Space;

/**
 * Represents the start field in the game.
 * @author Daniel Overballe Lerche, s235095@dtu.dk
 * @author Nikolaj Schæbel, s220471@dtu.dk
 */
public class StartField extends FieldAction {

    @Override
    public boolean doAction(GameController gameController, Space space) {
        return false;
    }
}
