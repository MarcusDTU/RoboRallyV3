package dk.dtu.compute.se.pisd.roborally.controller.field;

import com.google.gson.annotations.Expose;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;

/**
 * Represents the reboot field in the game.
 * @author Daniel Overballe Lerche, s235095@dtu.dk
 * @author Nikolaj Schæbel, s220471@dtu.dk
 */
public class RebootField extends FieldAction {
    @Expose
    Heading heading;

    /**
     * Get the heading of the reboot field
     * @return The heading of the reboot field
     * @author Daniel Overballe Lerche, s235095@dtu.dk
     * @author Nikolaj Schæbel, s220471@dtu.dk
     */
    public Heading getHeading() {
        return heading;
    }

    /**
     * Set the heading of the reboot field
     * @param heading The heading of the reboot field
     * @author Daniel Overballe Lerche, s235095@dtu.dk
     * @author Nikolaj Schæbel, s220471@dtu.dk
     */
    public void setHeading(Heading heading) {
        this.heading = heading;
    }

    @Override
    public boolean doAction(GameController gameController, Space space) {
        return false;
    }
}