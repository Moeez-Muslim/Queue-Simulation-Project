public class Statistics {

    private final int customersServed;
    private final int customersLeft;
    private final double totalWaitingTime;

    /**
     * Initializes Statistics with the given values.
     *
     * <p>input: customersServed, number of customers served.
     * customersLeft, number of customers left.
     * totalWaitingTime, total waiting time.
     */
    public Statistics(int customersServed, int customersLeft, double totalWaitingTime) 
    {
        this.customersServed = customersServed;
        this.customersLeft = customersLeft;
        this.totalWaitingTime = totalWaitingTime;
    }

    /** Retrieves the number of customers served. */
    public int getCustomersServed() 
    {
        return customersServed;
    }

    /** Retrieves the number of customers left. */
    public int getCustomersLeft() 
    {
        return customersLeft;
    }

    /** Retrieves the total waiting time. */
    public double getTotalWaitingTime() 
    {
        return totalWaitingTime;
    }

    /**
     * Calculates and retrieves the average waiting time.
     *
     * <p>output: Average waiting time.
     */
    public double getAverageWaitingTime() {
        if (customersServed == 0) {
            return 0.0;
        }
        return totalWaitingTime / customersServed;
    }

    /**
     * Increments the number of customers served.
     *
     * <p>output: Updated Statistics object.
     */
    public Statistics incrementCustomersServed() 
    {
        return new Statistics(customersServed + 1, customersLeft, totalWaitingTime);
    }

    /**
     * Increments the number of customers left.
     *
     * <p>output: Updated Statistics object.
     */
    public Statistics incrementCustomersLeft() 
    {
        return new Statistics(customersServed, customersLeft + 1, totalWaitingTime);
    }

    /**
     * Adds waiting time.
     *
     * <p>input: waitingTime, time to be added.
     *
     * <p>output: Updated Statistics object.
     */
    public Statistics addWaitingTime(double waitingTime) 
    {
    	System.out.println("Customers Served: "+ customersServed + "	Wait time added: " + waitingTime+ "	Total Waiting time: "+totalWaitingTime);
    	
        return new Statistics(customersServed, customersLeft, totalWaitingTime + waitingTime);
    }
}
