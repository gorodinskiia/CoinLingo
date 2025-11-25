package polymorphia.strategy;

import polymorphia.characters.Character;
import polymorphia.maze.Room;

public class DemonStrategy extends PlayStrategy {
    @Override
    public void doAction(Character character, Room currentRoom) {
        if (currentRoom.hasLivingAdventurers()) {
            character.fight(currentRoom.getRandomAdventurer());
            return;
        }
        character.move(currentRoom.getRandomNeighbor());
    }
}
