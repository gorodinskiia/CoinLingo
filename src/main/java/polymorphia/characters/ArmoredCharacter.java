package polymorphia.characters;

import polymorphia.artifacts.Armor;
import polymorphia.maze.Room;
import polymorphia.observers.EventBus;
import polymorphia.observers.EventType;

public class ArmoredCharacter extends Character {
    private final Character decoratedCharacter;
    private final Armor armor;

    public ArmoredCharacter(Character character, Armor armor) {
        super(character.getName() + "Armor", character.getHealth(), character.die, character.strategy);
        this.decoratedCharacter = character;
        this.armor = armor;
    }

    @Override
    public String getName() {
        return decoratedCharacter.getName() + " wearing the armor " + armor.getName();
    }

    @Override
    public Double getHealth() {
        return decoratedCharacter.getHealth();
    }

    @Override
    public Room getCurrentLocation() {
        return super.getCurrentLocation();
    }

    @Override
    public void enterRoom(Room room) {
        super.enterRoom(room);
        decoratedCharacter.enterRoom(room);
    }

    @Override
    public void move(Room destinationRoom) {
        Room current = getCurrentLocation();
        if (current == null || !current.hasNeighbor(destinationRoom)) {
            throw new AssertionError("Invalid move: neighbor not present");
        }

        String eventMessage = String.format("%s moved from %s to %s", getName(), current.getName(), destinationRoom.getName());
        logger.info(eventMessage);
        EventBus.INSTANCE.broadcast(EventType.MovedRooms, eventMessage);

        current.remove(this);
        destinationRoom.enter(this);

        double totalMovingCost = Character.HEALTH_LOST_IN_MOVING_ROOMS + armor.getMovingCost();
        decoratedCharacter.loseHealth(totalMovingCost);
    }

    @Override
    public void eat(polymorphia.artifacts.IArtifact food) {
        decoratedCharacter.eat(food);
    }

    @Override
    public Boolean isAlive() {
        return decoratedCharacter.isAlive();
    }

    @Override
    public void setDie(polymorphia.Die die) {
        decoratedCharacter.setDie(die);
        super.setDie(die);
    }

    @Override
    public Boolean fight(Character foe) {
        return super.fight(foe);
    }

    @Override
    void loseFightDamage(double fightDamage) {
        double armorStrength = armor.getDefenseValue();
        double remaining = fightDamage - armorStrength;
        if (remaining > 0) {
            decoratedCharacter.loseFightDamage(remaining);
        }
    }

    @Override
    void loseHealth(Double healthPoints) {
        decoratedCharacter.loseHealth(healthPoints);
    }

    @Override
    public void doAction() {
        super.doAction();
    }

    @Override
    public void setStrategy(polymorphia.strategy.PlayStrategy strategy) {
        decoratedCharacter.setStrategy(strategy);
        super.setStrategy(strategy);
    }
}
