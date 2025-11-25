package polymorphia.characters;

import polymorphia.artifacts.StudentLoan;
import polymorphia.maze.Room;

public class LoanedCharacter extends Character {
    private final Character decoratedCharacter;
    private final StudentLoan loan;

    public LoanedCharacter(Character character, StudentLoan loan) {
        super(
                character.getName() + " Loan ",
                character.getHealth(),
                character.die,
                character.strategy
        );

        this.decoratedCharacter = character;
        this.loan = loan;
    }

    @Override
    public String getName() {
        return decoratedCharacter.getName() + " with student loans" + loan.getName();
    }

    @Override
    public Double getHealth() {
        return decoratedCharacter.getHealth();
    }

    @Override
    public void move(Room destinationRoom) {
        decoratedCharacter.move(destinationRoom);
        decoratedCharacter.loseHealth(loan.getMovingCost());
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
        double debtAmount = loan.getDefenseValue();
        double remaining = fightDamage + debtAmount;
        decoratedCharacter.loseFightDamage(remaining);
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

    @Override
    public Room getCurrentLocation() {
        return decoratedCharacter.getCurrentLocation();
    }

    @Override
    public void enterRoom(Room room) {
        decoratedCharacter.enterRoom(room);
    }
}
