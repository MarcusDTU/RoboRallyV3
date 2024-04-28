package dk.dtu.compute.se.pisd.roborally.model;

import java.util.List;

public class PlayerData {
    private String name;
    private int x;
    private int y;
    private int robotId;

    private String heading; // For storing the player's heading
    private List<String> programmingCards; // For storing the player's programming cards
    private List<String> commandCards; // For storing the player's command cards

    private String phase;  // For storing the board phase
    private int step; // For storing the board step

    // Default constructor needed for Gson
    public PlayerData() {
    }
    public PlayerData(String name, int x, int y, int robotId, String heading,
                      List<String> programmingCards, List<String> commandCards, String phase, int step) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.robotId = robotId;
        this.heading = heading;
        this.programmingCards = programmingCards;
        this.commandCards = commandCards;
        this.phase = phase;
        this.step = step;
    }

    public String getPhase() {
        return phase;
    }

    public int getStep() {
        return step;
    }

    public List<String> getCommandCards() {
        return commandCards;
    }

    public void setCommandCards(List<String> commandCards) {
        this.commandCards = commandCards;
    }

    public List<String> getProgrammingCards() {
        return programmingCards;
    }

    public void setProgrammingCards(List<String> programmingCards) {
        this.programmingCards = programmingCards;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRobotId() {
        return robotId;
    }

    public String getHeading() {
        return heading;
    }

    public void setRobotId(int robotId) {
        this.robotId = robotId;
    }
}
