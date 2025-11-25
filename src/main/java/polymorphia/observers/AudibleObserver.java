package polymorphia.observers;

import polymorphia.audible.AudiblePlayer;

public class AudibleObserver implements EventObserver {
    AudiblePlayer player;

    public AudibleObserver(AudiblePlayer player) {
        this.player = player;
    }

    @Override
    public void update(EventType event, String message) {
        player.say(message);
    }

}
