public class Done extends Event {
    private final int serverId;

    public Done(double time, Customer cust, int sId) {
        super(time, cust);
        this.serverId = sId;
    }

    public int getServerId() {
        return serverId;
    }

    @Override
    public Pair<Event, Pair<ImList<Server>, Statistics>> nextEvent(
            Statistics stats, ImList<Server> currentServers) {

        return new Pair<>(this, new Pair<>(currentServers, stats));

    }

    @Override
    public String getEventType() {
        return "Done";
    }

    @Override
    public String toString() {
        return String.format("%.3f %d done serving by %d",
                             getTime(),
                             getCustomer().getId(),
                             serverId);
    }
}
