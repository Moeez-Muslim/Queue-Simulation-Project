public class Arrive extends Event {
    public Arrive(double time, Customer cust) { 
        super(time, cust);
    }

    @Override
    public Pair<Event, Pair<ImList<Server>, Statistics>> nextEvent(
            Statistics stats, ImList<Server> currentServers) {
        
        Customer customer = this.getCustomer();
        for (int i = 0; i < currentServers.size(); i++) {
            Server server = currentServers.get(i);
            if (server.canServe(customer)) {
                Statistics newstats = stats.incrementCustomersServed();
                return new Pair<>(new Serve(customer.getArrivalTime(), customer, server.getId()), 
                                new Pair<>(currentServers, newstats));
            }
        }

        for (int i = 0; i < currentServers.size(); i++) {
            Server server = currentServers.get(i);
            
            if (server.getQueueSize() < server.getQmax()) {
                Statistics newstats = stats.incrementCustomersServed();
                return new Pair<>(new Wait(this.getTime(), customer, server.getId()), 
                                  new Pair<>(currentServers, newstats));
            }
        }
        
        Statistics newstats = stats.incrementCustomersLeft();
        return new Pair<>(new Leave(this.getTime(), customer), 
                        new Pair<>(currentServers, newstats));
    }

    @Override
    public String getEventType() {
        return "Arrive";
    }

    @Override
    public String toString() {
        return String.format("%.3f %d arrives", getTime(), getCustomer().getId());
    }
}
