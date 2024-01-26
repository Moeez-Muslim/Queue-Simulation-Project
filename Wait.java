public class Wait extends Event {
    private final int serverId;

    public Wait(double time, Customer cust, int sId) {
        super(time, cust);
        this.serverId = sId;
    }

    public int getServerId() {
        return serverId;
    }

    @Override
    public Pair<Event, Pair<ImList<Server>, Statistics>> nextEvent(
            Statistics stats, ImList<Server> currentServers) {
        
        Server server = currentServers.get(this.getServerId() - 1);
        Customer customer = this.getCustomer();
        
        double calculatedTime = server.getFreeTime() 
            + server.getQueueSize() * server.getServerTime();
        double waitTime = calculatedTime - customer.getArrivalTime();
                
        Statistics newstats = stats.addWaitingTime(waitTime);
        
        Server newServer = server.addToQueue(customer);
        
        ImList<Server> updatedServers = currentServers.set(this.getServerId() - 1, newServer);
                
        return new Pair<>(new Serve(calculatedTime, customer, server.getId()), 
                          new Pair<>(updatedServers, newstats));
    }
    
    
    


      
    @Override
    public String getEventType() {
        return "Wait";
    }

    @Override
    public String toString() {
        return String.format("%.3f %d waits at %d", 
                             getTime(), 
                             getCustomer().getId(), 
                             serverId);
    }
}
