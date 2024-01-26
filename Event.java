public abstract class Event {
    private final double time;
    private final Customer customer;

    public Event(double time, Customer cust) {
        this.time = time;
        this.customer = cust;
    }

    public double getTime() {
        return time;
    }

    public Customer getCustomer() {
        return customer;
    }

    public abstract Pair<Event, Pair<ImList<Server>, Statistics>> nextEvent(
        Statistics stats,
        ImList<Server> currentServers
    );

    @Override
    public abstract String toString();

    public abstract String getEventType();
}
