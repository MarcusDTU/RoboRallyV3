package dk.dtu.compute.se.pisd.roborally.model;

/**
 * Enum representing different types of damage.
 * @author Haleef Abu Talib, s224523@dtu.dk
 */
public enum Damage {

    SPAM("SPAM"),
    TROJAN_HORSE("Trojan Horse"),
    WORM("Worm"),
    VIRUS("Virus");

    final public String damaageName;

    /**
     * Constructs a Damage enum with the specified name.
     *
     * @param damaageName The name of the damage type.
     */
    Damage(String damaageName){
        this.damaageName = damaageName;
    }


}
