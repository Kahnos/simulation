package simulation;

/**
 * Created by Kahnos - libcorrales.
 * Contains information about a specific client.
 */
public class Client {

    private int id = -1;
    private int relativeArrivalTime = -1; // Arrival time assigned by probability.
    private int realArrivalTime = -1;     // Time the client arrives into the system.
    private int departureTime = -1;       // Time the client leaves the system.
    private int serviceTime = -1;         // Service time assigned by probability.
    private int servedTime = -1;          // Time when a client is served.
    private int waitTime = -1;            // Total time the client waits in line.

    public Client() {
    }

    public Client(int id, int relativeArrivalTime, int realArrivalTime, int departureTime, int serviceTime, int servedTime, int waitTime) {
        this.id = id;
        this.relativeArrivalTime = relativeArrivalTime;
        this.realArrivalTime = realArrivalTime;
        this.departureTime = departureTime;
        this.serviceTime = serviceTime;
        this.servedTime = servedTime;
        this.waitTime = waitTime;
    }

    public Client(int id, int relativeArrivalTime, int serviceTime) {
        this.id = id;
        this.relativeArrivalTime = relativeArrivalTime;
        this.serviceTime = serviceTime;
    }

    public Client(int id, int relativeArrivalTime, int realArrivalTime, int serviceTime) {
        this.id = id;
        this.relativeArrivalTime = relativeArrivalTime;
        this.realArrivalTime = realArrivalTime;
        this.serviceTime = serviceTime;
    }

    // -------------------- Getters -------------------- //

    public int getId() {
        return id;
    }

    public int getRelativeArrivalTime() {
        return relativeArrivalTime;
    }

    public int getRealArrivalTime() {
        return realArrivalTime;
    }

    public int getDepartureTime() {
        return departureTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public int getServedTime() {
        return servedTime;
    }

    public int getWaitTime() {
        return waitTime;
    }

    // -------------------- Setters -------------------- //

    public void setId(int id) {
        this.id = id;
    }

    public void setRelativeArrivalTime(int relativeArrivalTime) {
        this.relativeArrivalTime = relativeArrivalTime;
    }

    public void setRealArrivalTime(int realArrivalTime) {
        this.realArrivalTime = realArrivalTime;
    }

    public void setDepartureTime(int departureTime) {
        this.departureTime = departureTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public void setServedTime(int servedTime) {
        this.servedTime = servedTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    // -------------------- Other functions -------------------- //

    /**
     * Calculates the wait time of the client and returns it. Requires realArrivalTime.
     * @return the calculated waitTime of the Client.
     */
    public int calculateWaitTime(){
        waitTime = departureTime - realArrivalTime - serviceTime;
        return waitTime;
    }

    /**
     * Calculates the departure time of the client and returns it. Requires servedTime.
     * @return the calculated departure time of the Client.
     */
    public int calculateDepartureTime(){
        departureTime = servedTime + serviceTime;
        return departureTime;
    }

}
