package polymorphia.observers;

import org.junit.jupiter.api.Disabled;
import polymorphia.audible.AudiblePlayer;

class AudibleObserverTest {

    @Disabled
    void testAudibleObserver() {
        AudiblePlayer audiblePlayer = new AudiblePlayer();
        AudibleObserver audibleObserver = new AudibleObserver(audiblePlayer);
        audibleObserver.update(EventType.Death, "Bilbo just died");
    }

}