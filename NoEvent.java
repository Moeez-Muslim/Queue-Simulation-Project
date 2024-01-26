public class NoEvent extends Event {
    public NoEvent() {
        super(-1.0, new EmptyCustomer()); 
    }

    @Override
    public Pair<Event, Pair<ImList<Server>, Statistics>> nextEvent(
        Statistics stats, ImList<Server> currentServers) {
        return new Pair<>(this, new Pair<>(currentServers, stats));
    }

    @Override
    public String getEventType() {
        return "NoEvent";
    }

    @Override
    public String toString() {
        return "No event";
    }
}
