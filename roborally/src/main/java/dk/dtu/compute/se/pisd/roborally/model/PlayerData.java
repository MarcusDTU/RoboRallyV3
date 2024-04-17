package dk.dtu.compute.se.pisd.roborally.model;

public class PlayerData {

    private String name;
    private int x;
    private int y;
    private String color;

    // Default constructor needed for Gson
    public PlayerData() {
    }

    public PlayerData(String name, int x, int y, String color) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.color = color;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }



}
