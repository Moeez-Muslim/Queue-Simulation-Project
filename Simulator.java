import java.util.Comparator;
import java.util.function.Supplier;

public class Simulator {

    private final ImList<Server> servers;
    private final PQ<Event> events;
    private final Statistics stats;

    /**
     * Initializes Simulator with servers and customer times.
     *
     * <p>input: serversCount, number of servers.
     * qmax, maximum queue length.
     * arrivalTimes, customer arrival times.
     * stSupplier, service time supplier.
     */
    public Simulator(int serversCount, int qmax, ImList<Double> arrivalTimes, 
        Supplier<Double> stSupplier) {
        this.servers = initServers(serversCount, qmax);
        this.events = initEvents(arrivalTimes, stSupplier);
        this.stats = new Statistics(0, 0, 0);
    }

    /**
     * Initializes servers.
     *
     * <p>input: count, number of servers.
     * qmax, maximum queue length.
     *
     * <p>output: List of initialized servers.
     */
    private ImList<Server> initServers(int count, int qmax) {
        ImList<Server> tempServers = new ImList<>();
        for (int i = 1; i <= count; i++) {
            tempServers = tempServers.add(new Server(i, qmax));
        }
        return tempServers;
    }

    /**
     * Initializes events based on customer arrivals and service times.
     *
     * <p>input: arrivalTimes, customer arrival times.
     * stSupplier, service time supplier.
     *
     * <p>output: Priority queue of events.
     */
    private PQ<Event> initEvents(ImList<Double> arrivalTimes, Supplier<Double> stSupplier) {
        ImList<Customer> customers = new ImList<>();
        int id = 1;
        for (double arrivalTime : arrivalTimes) {
            customers = customers.add(new Customer(id++, arrivalTime, stSupplier));
        }

        Comparator<Event> eventComparator = (e1, e2) -> {
            int comparison = Double.compare(e1.getTime(), e2.getTime());
            
            if (comparison != 0) {
                return comparison;
            } else {
                int customerId1 = e1.getCustomer().getId();
                int customerId2 = e2.getCustomer().getId();
                return Integer.compare(customerId1, customerId2);
            }
        };

        PQ<Event> queue = new PQ<>(eventComparator);
        for (Customer customer : customers) {
            queue = queue.add(new Arrive(customer.getArrivalTime(), customer));
        }
        return queue;
    }

    /**
     * Determines the rank of an event.
     *
     * <p>input: e, event to rank.
     *
     * <p>output: Rank of the event.
     */


    /**
     * Simulates events based on customer actions.
     *
     * <p>output: String representation of results.
     */
    public String simulate() {
        StringBuilder result = new StringBuilder();
        ImList<Server> currentServers = servers;
        PQ<Event> localEvents = this.events;
        Statistics currrentstats = stats;
        Event prevEvent = null; // Variable to keep track of the previous event

        while (!localEvents.isEmpty()) {
            Pair<Event, PQ<Event>> pair = localEvents.poll();
            Event e = pair.first();
            localEvents = pair.second();
            Pair<Event, Pair<ImList<Server>, Statistics>> processedEvent = 
                e.nextEvent(currrentstats, currentServers);

            if (prevEvent == null || !processedEvent.first().equals(prevEvent)) {
                localEvents = localEvents.add(processedEvent.first());

            }


            prevEvent = processedEvent.first(); // Updating the previous event
            currrentstats = processedEvent.second().second();
            currentServers = processedEvent.second().first();
            result.append(e).append("\n");
        }

        result.append(String.format("[%.3f %d %d]",
            currrentstats.getAverageWaitingTime(),
            currrentstats.getCustomersServed(), currrentstats.getCustomersLeft()));

        return result.toString();
    }
}