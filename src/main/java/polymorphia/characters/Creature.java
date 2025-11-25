package polymorphia.characters;

import polymorphia.strategy.PlayStrategy;

public class Creature extends Character {

    public Creature(String name, Double defaultHealth, PlayStrategy strategy) {
        super(name, defaultHealth, strategy);
    }

    @Override
    public Boolean isCreature() {
        return true;
    }

    @Override
    public Boolean isAdventurer() {
        return false;
    }

}
