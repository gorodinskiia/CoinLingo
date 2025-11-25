package polymorphia.strategy;


import java.util.Optional;

import polymorphia.characters.Character;
import polymorphia.maze.Room;

public class FighterStrategy extends PlayStrategy {
    public void doAction(Character myself, Room currentRoom) {
        
        if (shouldFight(currentRoom)) {
            fightCreature(myself, currentRoom);
            return;
        }

        if (currentRoom.getArmor().isPresent() && currentRoom.getCreature().isEmpty()) {
            PlayStrategy.putOnArmor(myself, currentRoom);
            return;
        }

        if (shouldEat(currentRoom)) {
            eatFood(myself, currentRoom);
            return;
        }
        if (shouldMove(currentRoom)) {
            myself.move(currentRoom.getRandomNeighbor());
        }
    }

    boolean shouldEat(Room currentRoom) {
        return currentRoom.hasFood();
    }

    private static void fightCreature(Character myself, Room currentRoom) {
        Optional<Character> creature = currentRoom.getCreature();
        creature.ifPresent(myself::fight);
    }

    boolean shouldFight(Room currentRoom) {
        return currentRoom.hasLivingCreatures();
    }

}
