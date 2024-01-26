public class Server {
    private final int id;
    private final double freeAt;
    private final ImList<Customer> queue;
    private final int qmax;
    private final boolean isReserved;
    private static final double DELTA = 1e-9;
    private static final Customer EMPTY_CUSTOMER = new Customer(-1, -1.0, () -> -1.0);
    private final double serverTime;


    /**
     * Initializes a Server with ID, freeAt time, queue, maximum queue size, and reserved status.
     * 
     * <p>input: id, Server ID.
     * freeAt, Time when server is free.
     * queue, Current queue of customers.
     * qmax, Maximum queue size.
     * isReserved, If the server is reserved.
     */
    public Server(int id, double freeAt, ImList<Customer> queue, int qmax, 
        boolean isReserved, double serverTime) {
        this.id = id;
        this.freeAt = freeAt;
        this.queue = queue;
        this.qmax = qmax;
        this.isReserved = isReserved;
        this.serverTime = serverTime;
    }

    /**
     * Initializes a new Server with ID and maximum queue size. Sets freeAt to 0.0.
     * 
     * <p>input: id, Server ID.
     * qmax, Maximum queue size.
     */
    public Server(int id, int qmax) {
        this(id, 0.0, new ImList<Customer>(), qmax, false, 0.0);
    }

    /** Retrieves maximum queue size. */
    public int getQmax() {
        return qmax;
    }

    /** Retrieves current queue size. */
    public int getQueueSize() {
        return queue.size();
    }

    /** Retrieves server ID. */
    public int getId() {
        return id;
    }

    /** Reserves the server. */
    public Server reserve() {
        return new Server(id, freeAt, queue, qmax, true, serverTime);
    }

    public double getServerTime() {
        return this.serverTime;
    }

    public Server setServerTime(double newServerTime) {
        return new Server(this.id, this.freeAt, this.queue, this.qmax, 
        this.isReserved, newServerTime);
    }

    /** Checks if the server is reserved. */
    public boolean isReserved() {
        return isReserved;
    }

    /**
     * Checks if server can serve a customer.
     * 
     * <p>input: customer to check.
     * 
     * <p>output: true if can serve, false otherwise.
     */
    public boolean canServe(Customer customer) {
        return !isReserved && customer.getArrivalTime() + DELTA >= freeAt;
    }

    /**
     * Serves a customer at a given service time.
     * 
     * <p>input: customer to serve.
     * serviceTime, Time taken to serve the customer.
     * 
     * <p>output: Updated Server instance.
     */
    public Server serve(Customer customer, double serviceTime) {
        return new Server(id, customer.getArrivalTime() + serviceTime, 
        queue, qmax, isReserved, serviceTime);
    }

    /** Checks if the server's queue is full. */
    public boolean isQueueFull() {
        return hasWaitingCustomer() || queue.size() >= qmax;
    }

    /** Checks if there's a waiting customer. */
    public boolean hasWaitingCustomer() {
        return !queue.isEmpty();
    }

    /**
     * Adds a customer to the queue.
     * 
     * <p>input: customer to add.
     * 
     * <p>output: Updated Server instance.
     */

    /**
     * Retrieves the next customer in the queue and updates the server state.
     * 
     * <p>output: Pair of next customer and updated Server instance.
     */
    public Pair<Customer, Server> getNextInQueue() {
        if (queue.isEmpty()) {
            return new Pair<>(EMPTY_CUSTOMER, this);
        }
        Customer nextCustomer = queue.get(0);
        return new Pair<>(nextCustomer,
                new Server(id, freeAt, queue.remove(0), qmax, isReserved, serverTime));
    }

    public Server addToQueue(Customer customer) {
        return new Server(id, freeAt, queue.add(customer), qmax, isReserved, serverTime);
    }

    
    /**
     * Provides a peek at the next customer in the queue
     *
     * <p>output: Pair containing the next customer and the current Server instance.
     */

    public Pair<Customer, Server> peekNextInQueue() {
        if (queue.isEmpty()) {
            return new Pair<>(EMPTY_CUSTOMER, this);
        }
        Customer nextCustomer = queue.get(0);
        return new Pair<>(nextCustomer, this);
    }


    /**
     * Removes the next customer in the queue
     *
     * <p>output: Updated Server instance with the next customer removed.
     */
    public Server removeNextInQueue() {
        if (queue.isEmpty()) {
            return this;
        }
        return new Server(id, freeAt, queue.remove(0), qmax, isReserved, serverTime);
    }

    /** Sets the server to idle. */
    public Server makeIdle() {
        return new Server(id, 0.0, queue, qmax, isReserved, serverTime);
    }


    /** Retrieves next free time of server. */
    public double getFreeTime() {
        return freeAt;
    }

    /** Returns server's string representation. */
    @Override
    public String toString() {
        return "server " + id;
    }

    /** Retrieves the delta value. */
    public static double getDelta() {
        return DELTA;
    }

    /**
     * Calculates the waiting time for a customer.
     * 
     * <p>input: customer to calculate waiting time for.
     * 
     * <p>output: Waiting time for the customer.
     */
    public double getWaitingTime(Customer customer) {
        if (customer.getArrivalTime() >= freeAt) {
            return 0.0;
        }
        return freeAt - customer.getArrivalTime();
    }

    /**
     * Checks if a customer is an empty customer.
     * 
     * <p>input: customer to check.
     * 
     * <p>output: true if it's an empty customer, false otherwise.
     */
    public boolean isEmptyCustomer(Customer customer) {
        return EMPTY_CUSTOMER.equals(customer);
    }

}
