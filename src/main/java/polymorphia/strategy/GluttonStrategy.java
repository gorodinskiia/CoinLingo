package polymorphia.strategy;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import polymorphia.characters.Character;
import polymorphia.maze.Room;
import polymorphia.observers.EventBus;
import polymorphia.observers.EventType;

public class GluttonStrategy extends FighterStrategy {
    private static final Logger logger = LoggerFactory.getLogger(GluttonStrategy.class);

    @Override
    public void doAction(Character myself, Room currentRoom) {


        if (currentRoom.hasFood()) {
            eatFood(myself, currentRoom);
            return;
        }

        if(currentRoom.getStudentLoan().isPresent()) {
            PlayStrategy.pickUpStudentLoan(myself, currentRoom);
        }

        Optional<Character> creature = currentRoom.getCreature();
        if (shouldFight(currentRoom) && creature.isPresent()) {
            boolean iWonFight = myself.fight(creature.get());
            if (iWonFight) {
                myself.setStrategy(new FighterStrategy());
                String eventMessage = "Glutton won fight! Changing strategy to fighter!";
                logger.info(eventMessage);
                EventBus.INSTANCE.broadcast(EventType.StrategyChange, eventMessage);
            }
            return;
        }

        super.doAction(myself, currentRoom);
    }

    boolean shouldFight(Room room) {
        return !room.hasFood() && room.hasLivingCreatures();
    }
}
