package polymorphia.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import polymorphia.Polymorphia;
import polymorphia.audible.AudiblePlayer;
import polymorphia.observers.AudibleObserver;
import polymorphia.observers.EventType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PolymorphiaStepDefs {
    World world;

    public PolymorphiaStepDefs(World world) {
        this.world = world;
    }

    @When("the game is played in the created maze")
    public void theGameIsPlayedInTheCreatedMaze() {
        Polymorphia game = world.getGame();
        game.play();
    }

    @Then("all the adventurers or all of the creatures have died")
    public void allTheAdventurersOrAllOfTheCreaturesHaveDied() {
        Polymorphia game = world.getGame();
        assertTrue(!game.hasLivingAdventurers() || !game.hasLivingCreatures());
    }

    @Then("the game is over")
    public void theGameIsOver() {
        Polymorphia game = world.getGame();
        assertTrue(game.isOver());
    }

    @And("a fight has occurred")
    public void aFightHasOccurred() {
        assertTrue(world.observer.eventsReceived(EventType.FightOccurred));
    }

    @And("an audible observer is attached interested in the following events:")
    public void anAudibleObserverIsAttachedInterestedInTheFollowingEvents(List<String> events) {
        List<EventType> eventTypes = new ArrayList<>();
        for (String event : events) {
            eventTypes.add(EventType.valueOf(event.replaceAll(" ", "")));
        }
        AudibleObserver audibleObserver = new AudibleObserver(new AudiblePlayer());
        world.getGame().attach(audibleObserver, eventTypes);
    }

    @When("the game plays a turn")
    public void theGamePlaysATurn() {
        Polymorphia game = world.getGame();
        game.playTurn();
    }
}
