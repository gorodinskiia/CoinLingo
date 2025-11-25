package polymorphia.strategy;

import polymorphia.maze.Room;

public class CowardStrategy extends FighterStrategy {

    @Override
    boolean shouldFight(Room room) {
        return false;
    }

    @Override
    boolean shouldMove(Room room) {
        return room.hasLivingCreatures() || (room.hasNeighbors() && !room.hasFood());
    }

    @Override
    boolean shouldEat(Room room) {
        return room.hasFood() && !room.hasLivingCreatures();
    }
}
