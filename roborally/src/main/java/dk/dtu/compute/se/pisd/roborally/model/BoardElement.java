package dk.dtu.compute.se.pisd.roborally.model;

public abstract class BoardElement {

    Orientation orientation;

    public BoardElement(Orientation orientation) {
        this.orientation = orientation;
    }

    abstract public void activate();

    enum Orientation {
        NORTH, EAST, SOUTH, WEST
    }

}
