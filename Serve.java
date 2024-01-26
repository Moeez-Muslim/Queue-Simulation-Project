public class Serve extends Event {
    private final int serverId;

    public Serve(double time, Customer cust, int sId) {
        super(time, cust);
        this.serverId = sId;
    }

    public int getServerId() {
        return serverId;
    }

    @Override
    public Pair<Event, Pair<ImList<Server>, Statistics>> nextEvent(
            Statistics stats, ImList<Server> currentServers) {
        
        Customer customer = this.getCustomer();
        Server server = currentServers.get(this.getServerId() - 1);
    
        double serviceTime = customer.getServiceTime();
        
        Server newServer = server.serve(customer, serviceTime
            + this.getTime() - customer.getArrivalTime());
                    newServer = newServer.removeNextInQueue();
        
        newServer = newServer.setServerTime(serviceTime);
        
        ImList<Server> updatedServers = currentServers.set(this.getServerId() - 1, newServer);
        
        return new Pair<>(new Done(newServer.getFreeTime(), customer, newServer.getId()), 
                          new Pair<>(updatedServers, stats));
    }

    
    
    
    
    


    @Override
    public String getEventType() {
        return "Serve";
    }

    @Override
    public String toString() {
        return String.format("%.3f %d serves by %d", 
                             getTime(), 
                             getCustomer().getId(), 
                             serverId);
    }
}
