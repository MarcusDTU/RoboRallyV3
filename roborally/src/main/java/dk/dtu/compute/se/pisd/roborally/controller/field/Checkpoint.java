package dk.dtu.compute.se.pisd.roborally.controller.field;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class Checkpoint extends FieldAction {

    private int orderNumber;

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    @Override
    public boolean doAction(GameController gameController, Space space) {
        return false;
    }
}
