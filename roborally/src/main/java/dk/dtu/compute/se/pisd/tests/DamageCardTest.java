package dk.dtu.compute.se.pisd.roborally.testing;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import dk.dtu.compute.se.pisd.roborally.model.Damage;
import dk.dtu.compute.se.pisd.roborally.model.DamageCard;

class DamageCardTest {

    @Test
    void testDamageCardInitialization() {
        // Test initialization with each type of Damage
        for (Damage damageType : Damage.values()) {
            DamageCard card = new DamageCard(damageType);
            assertNotNull(card.damage, "Damage property should not be null.");
            assertEquals(damageType, card.damage, "DamageCard should be initialized with the correct Damage type.");
        }
    }

    @Test
    void testGetName() {
        // Test name retrieval for each type of Damage
        for (Damage damageType : Damage.values()) {
            DamageCard card = new DamageCard(damageType);
            assertEquals(damageType.damaageName, card.getName(), "getName() should return the correct damage name.");
        }
    }
}