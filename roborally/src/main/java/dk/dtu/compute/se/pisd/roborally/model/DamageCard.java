package dk.dtu.compute.se.pisd.roborally.model;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a damage card.
 * * @author Haleef Abu Talib, s224523@dtu.dk
 */
public class DamageCard {

    final public Damage damage;

    /**
     * Constructs a DamageCard with the specified damage type.
     *
     * @param damage The type of damage on the card.
     */
    public DamageCard(@NotNull Damage damage) {
        this.damage = damage;
    }

    /**
     * Retrieves the name of the damage on the card.
     *
     * @return The name of the damage.
     */
    public String getName() {
        return damage.damaageName;
    }

}
