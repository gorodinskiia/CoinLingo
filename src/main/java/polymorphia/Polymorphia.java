package polymorphia;

import org.slf4j.Logger;
import polymorphia.characters.Character;
import polymorphia.maze.Maze;
import polymorphia.observers.EventBus;
import polymorphia.observers.EventIssuingObservable;
import polymorphia.observers.EventObserver;
import polymorphia.observers.EventType;

import java.util.List;
import java.util.Random;

public class Polymorphia implements EventIssuingObservable {
    static Logger logger = org.slf4j.LoggerFactory.getLogger(Polymorphia.class);

    Maze maze;
    Integer turnCount = 0;
    final Random rand = new Random();

    public Polymorphia(Maze maze) {
        this.maze = maze;
    }

    // *********** EventIssuingObservable required methods ***********
    @Override
    public void attach(EventObserver observer) {
        attach(observer, EventType.All);
    }

    @Override
    public void attach(EventObserver observer, EventType type) {
        EventBus.INSTANCE.attach(observer, type);
    }

    @Override
    public void attach(EventObserver observer, List<EventType> types) {
        EventBus.INSTANCE.attach(observer, types);
    }

    @Override
    public void detach(EventObserver observer) {
        EventBus.INSTANCE.detach(observer);
    }
    // *********** EventIssuingObservable required methods ***********

    public String toString() {
        return "Polymorphia MAZE: turn " + turnCount + "\n" + maze.toString();
    }

    public Boolean isOver() {
        return !hasLivingCreatures() || !hasLivingAdventurers();
    }

    public Boolean hasLivingCreatures() {
        return maze.hasLivingCreatures();
    }

    public Boolean hasLivingAdventurers() {
        return maze.hasLivingAdventurers();
    }

    public void playTurn() {
        if (turnCount == 0) {
            logger.info("\nStarting play...\n");
        }
        turnCount += 1;

        // Process all the characters in random order
        List<Character> characters = getLivingCharacters();
        while (!characters.isEmpty()) {
            int index = rand.nextInt(characters.size());
            Character character = characters.get(index);
            if (character.isAlive()) {
                character.doAction();
            }
            characters.remove(index);
        }
        String eventMessage = String.format("Turn %s ended and there are %s living adventurers", turnCount, maze.getLivingAdventurers().size());
        logger.info(eventMessage);
        EventBus.INSTANCE.broadcast(EventType.TurnEnded, eventMessage);
    }

    private List<Character> getLivingCharacters() {
        return maze.getLivingCharacters();
    }

    public void play() {
        EventBus.INSTANCE.broadcast(EventType.GameStart, "The game just started");
        while (!isOver()) {
            playTurn();
            logger.info(this.toString());
        }
        String eventMessage = String.format("The game ended after %s turns", turnCount);
        EventBus.INSTANCE.broadcast(EventType.GameOver, eventMessage);

        logger.info("The game ended after {} turns.\n", turnCount);
        String eventDescription;
        if (hasLivingAdventurers()) {
            eventDescription = "The adventurers won! Left standing are: " + getAdventurerNames() + "\n";
        } else if (hasLivingCreatures()) {
            eventDescription = "The creatures won. Boo! Left standing are: " + getCreatureNames() + "\n";
        } else {
            eventDescription = "No team won! Everyone died!\n";
        }
        logger.info(eventDescription);
    }

    private String getAdventurerNames() {
        return String.join(", ", getLivingCharacters().stream().map(Object::toString).toList());
    }

    private String getCreatureNames() {
        return String.join(", ", getLivingCreatures().stream().map(Object::toString).toList());
    }

    private List<Character> getLivingCreatures() {
        return maze.getLivingCreatures();
    }

    public Character getWinner() {
        if (!isOver() || !hasLivingCharacters()) {
            // No one has won yet, or no one won -- all died
            return null;
        }
        return getLivingCharacters().getFirst();
    }

    private boolean hasLivingCharacters() {
        return maze.hasLivingCharacters();
    }
}
