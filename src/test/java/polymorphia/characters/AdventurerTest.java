package polymorphia.characters;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdventurerTest {
    CharacterFactory characterFactory = new CharacterFactory();

    @Test
    public void testConstructor() {
        Character hero = characterFactory.createKnight("Hero");
        assertEquals("Hero", hero.getName());
        assertEquals(CharacterFactory.DEFAULT_FIGHTER_INITIAL_HEALTH, hero.getHealth());
    }

}