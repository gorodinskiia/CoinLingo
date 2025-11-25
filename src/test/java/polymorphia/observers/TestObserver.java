package polymorphia.observers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestObserver implements EventObserver {
    Map<EventType, List<String>> eventsReceived = new HashMap<>();

    @Override
    public void update(EventType type, String message) {
        if (!eventsReceived.containsKey(type)) {
            eventsReceived.put(type, new ArrayList<>());
        }
        eventsReceived.get(type).add(message);
    }

    public boolean eventsReceived(EventType type) {
        return eventsReceived.containsKey(type);
    }

    public int numberOfEventsReceived(EventType eventType) {
        if (!eventsReceived.containsKey(eventType)) {
            return 0;
        }
        return eventsReceived.get(eventType).size();
    }
}
