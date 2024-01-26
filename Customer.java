import java.util.function.Supplier;

/**
 * Represents a customer with an ID, arrival time, and service time.
 */
public class Customer {

    private final int id;          
    private final double arrivalTime;  
    private final Supplier<Double> serviceTimeSupplier;

    /**
     * Initializes a new Customer object.
     *
     * <p>input: id, ID of customer;
     * arrivalTime, time customer arrives;
     * st, supplier providing service time for the customer.
     */
    public Customer(int id, double arrivalTime, Supplier<Double> st) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTimeSupplier = st;
    }

    /**
     * Retrieves ID of customer.
     *
     * <p>output: ID of customer.
     */
    public int getId() {
        return id; 
    }

    /**
     * Retrieves arrival time of customer.
     *
     * <p>output: Arrival time of customer.
     */
    public double getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Retrieves service time for customer.
     *
     * <p>output: Service time of customer.
     */
    public double getServiceTime() {
        return serviceTimeSupplier.get();
    }

    /**
     * Returns string representation of customer.
     *
     * <p>output: String format of customer.
     */
    @Override
    public String toString() {
        return String.format("customer %d", id);
    }
}
