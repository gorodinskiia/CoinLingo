package polymorphia.observers;

public interface EventObserver {
    void update(EventType event, String message);
}
