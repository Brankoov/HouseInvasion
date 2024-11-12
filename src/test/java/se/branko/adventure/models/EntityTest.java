package se.branko.adventure.models;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;



@DisplayName("Testning av fight metoder och isConsious metoder.")
class EntityTest {
   Resident resident;
   Burglar burglar;

    @BeforeEach
    void setUp() {
        resident = new Resident("Resident", 12, 3);
        burglar = new Burglar("Burglar", 12, 4);
    }
    @AfterEach
    void tearDown() {
        resident = null;
        burglar = null;
    }

    @Test
    void testGetDamageResident() {
        assertEquals(3, resident.getDamage());
    }
    @Test
    void testGetDamageBurglar() {
        assertEquals(4, burglar.getDamage());
    }

    @Test
    void residentAttackBurglar() {
        resident.attack(burglar);
        assertEquals(9, burglar.getHealth());
    }
    @Test
    void burglarAttackResident(){
        burglar.attack(resident);
        assertEquals(8, resident.getHealth());
    }

    @Test
    void BurglarTakeDamage() {
        burglar.takeDamage(resident.getDamage());
        assertEquals(9, burglar.getHealth());
    }

    @Test
    void isConsious() {
        burglar.takeDamage(5);
        assertTrue(burglar.isConsious());
    }
    @Test
    void isNotConsious() {
        burglar.takeDamage(13);
        assertFalse(burglar.isConsious());
    }



}