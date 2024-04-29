package dk.dtu.compute.se.pisd.roborally.testing;


import static org.junit.jupiter.api.Assertions.*;

import dk.dtu.compute.se.pisd.roborally.model.Damage;
import org.junit.jupiter.api.Test;

class DamageTest {

    @Test
    void testDamageNames() {
        assertEquals("SPAM", Damage.SPAM.damaageName, "Damage name for SPAM should be 'SPAM'.");
        assertEquals("Trojan Horse", Damage.TROJAN_HORSE.damaageName, "Damage name for TROJAN_HORSE should be 'Trojan Horse'.");
        assertEquals("Worm", Damage.WORM.damaageName, "Damage name for WORM should be 'Worm'.");
        assertEquals("Virus", Damage.VIRUS.damaageName, "Damage name for VIRUS should be 'Virus'.");
    }
}