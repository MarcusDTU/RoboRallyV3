package dk.dtu.compute.se.pisd.roborally.model;

public enum Damage {

    SPAM("SPAM"),
    TROJAN_HORSE("Trojan Horse"),
    WORM("Worm"),
    VIRUS("Virus");

    final public String damaageName;

    Damage(String damaageName){
        this.damaageName = damaageName;
    }


}
