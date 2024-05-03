package dk.dtu.compute.se.pisd.roborally.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

/**
 * Represents a discard pile of command cards.
 * @author Marcus Langkilde, s195080@DTU.dk
 * @author Haleef Abu Talib, s224523@dtu.dk
 */
public class DiscardPile {

    @Expose
    public ArrayList<Command> pile = new ArrayList<>();

    //Add card to discard pile


}
