public class Leave extends Event {
    public Leave(double time, Customer cust) {
        super(time, cust);
    }

    @Override
    public Pair<Event, Pair<ImList<Server>, Statistics>> nextEvent(
            Statistics stats, ImList<Server> currentServers) {
        
        // Returning the same Leave event along with the unchanged servers and statistics

        return new Pair<>(this, new Pair<>(currentServers, stats));
    }
    
    
    @Override
    public String getEventType() {
        return "Leave";
    }

    @Override
    public String toString() {
        return String.format("%.3f %d leaves", getTime(), getCustomer().getId());
    }
}
