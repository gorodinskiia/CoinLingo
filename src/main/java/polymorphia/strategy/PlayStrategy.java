package polymorphia.strategy;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import polymorphia.artifacts.Armor;
import polymorphia.artifacts.IArtifact;
import polymorphia.artifacts.StudentLoan;
import polymorphia.characters.Character;
import polymorphia.characters.CharacterFactory;
import polymorphia.maze.Room;

abstract public class PlayStrategy {
    private static final Logger logger = LoggerFactory.getLogger(PlayStrategy.class);
    private static final CharacterFactory characterFactory = new CharacterFactory();


    static void eatFood(Character myself, Room currentRoom) {
        Optional<IArtifact> food = currentRoom.getFood();
        if (food.isPresent()) {
            myself.eat(food.get());
        } else {
            logger.error("No food in room");
        }
    }

    static void putOnArmor(Character myself, Room currentRoom) {
        Optional<IArtifact> armorOpt = currentRoom.getArmor();
        if (armorOpt.isPresent()) {
            IArtifact artifact = armorOpt.get();
            if (!(artifact instanceof Armor)) {
                logger.error("Artifact found is not Armor: {}", artifact);
                return;
            }
            Armor armor = (Armor) artifact;
            Character armored = myself.putOnArmor(armor);
            currentRoom.remove(artifact);
            currentRoom.replaceCharacter(myself, armored);
        } else {
            logger.error("No armor in room");
        }
    }

    abstract public void doAction(Character character, Room currentRoom);

    boolean shouldMove(Room room) {
        return room != null && room.hasNeighbors();

    }

    static void pickUpStudentLoan(Character myself, Room currentRoom) {
        Optional<IArtifact> loanOpt = currentRoom.getStudentLoan();
        if (loanOpt.isPresent()) {
            IArtifact artifact = loanOpt.get();
            if (!(artifact instanceof StudentLoan)) {
                logger.error("Artifact found is not StudentLoan: {}", artifact);
                return;
            }
            StudentLoan studentLoan = (StudentLoan) artifact;
            Character loanedCharacter = myself.pickUpStudentLoan(studentLoan);
            currentRoom.remove(artifact);
            currentRoom.replaceCharacter(myself, loanedCharacter);
        } else {
            logger.error("No student loan in room");
        }
    }

}
