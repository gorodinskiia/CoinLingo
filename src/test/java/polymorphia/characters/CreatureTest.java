package polymorphia.characters;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreatureTest {
    CharacterFactory characterFactory = new CharacterFactory();

    @Test
    public void testConstructor() {
        Character orc = characterFactory.createCreature("Orc");
        assertEquals("Orc", orc.getName());
        assertEquals(CharacterFactory.DEFAULT_CREATURE_INITIAL_HEALTH, orc.getHealth());
    }
}