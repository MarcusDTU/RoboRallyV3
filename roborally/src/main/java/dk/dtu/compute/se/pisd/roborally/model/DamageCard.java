package dk.dtu.compute.se.pisd.roborally.model;

import org.jetbrains.annotations.NotNull;

public class DamageCard {

    final public Damage damage;

    public DamageCard(@NotNull Damage damage) {
        this.damage = damage;
    }

    public String getName() {
        return damage.damaageName;
    }

}
