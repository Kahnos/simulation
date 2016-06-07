package simulation;

/**
 * Created by Kahnos - libcorrales.
 * Contains information about a specific client.
 */
public class Client {

    private int id = -1;
    private double relativeArrivalTime = -1; // Arrival time assigned by probability.
    private double realArrivalTime = -1;     // Time the client arrives into the system.
    private double departureTime = -1;       // Time the client leaves the system.
    private double serviceTime = -1;         // Service time assigned by probability.
    private double servedTime = -1;          // Time when a client is served.
    private double waitTime = -1;            // Total time the client waits in line.

    public Client() {
    }

    public Client(int id, double relativeArrivalTime, double realArrivalTime, double departureTime, double serviceTime, double servedTime, double waitTime) {
        this.id = id;
        this.relativeArrivalTime = relativeArrivalTime;
        this.realArrivalTime = realArrivalTime;
        this.departureTime = departureTime;
        this.serviceTime = serviceTime;
        this.servedTime = servedTime;
        this.waitTime = waitTime;
    }

    public Client(int id, double relativeArrivalTime, double serviceTime) {
        this.id = id;
        this.relativeArrivalTime = relativeArrivalTime;
        this.serviceTime = serviceTime;
    }

    public Client(int id, double relativeArrivalTime, double realArrivalTime, double serviceTime) {
        this.id = id;
        this.relativeArrivalTime = relativeArrivalTime;
        this.realArrivalTime = realArrivalTime;
        this.serviceTime = serviceTime;
    }

    // -------------------- Getters -------------------- //

    public int getId() {
        return id;
    }

    public double getRelativeArrivalTime() {
        return relativeArrivalTime;
    }

    public double getRealArrivalTime() {
        return realArrivalTime;
    }

    public double getDepartureTime() {
        return departureTime;
    }

    public double getServiceTime() {
        return serviceTime;
    }

    public double getServedTime() {
        return servedTime;
    }

    public double getWaitTime() {
        return waitTime;
    }

    // -------------------- Setters -------------------- //

    public void setId(int id) {
        this.id = id;
    }

    public void setRelativeArrivalTime(double relativeArrivalTime) {
        this.relativeArrivalTime = relativeArrivalTime;
    }

    public void setRealArrivalTime(double realArrivalTime) {
        this.realArrivalTime = realArrivalTime;
    }

    public void setDepartureTime(double departureTime) {
        this.departureTime = departureTime;
    }

    public void setServiceTime(double serviceTime) {
        this.serviceTime = serviceTime;
    }

    public void setServedTime(double servedTime) {
        this.servedTime = servedTime;
    }

    public void setWaitTime(double waitTime) {
        this.waitTime = waitTime;
    }

    // -------------------- Other functions -------------------- //

    // Calculates the wait time of the client and returns it.
    public double calculateWaitTime(){
        waitTime = departureTime - realArrivalTime - serviceTime;
        return waitTime;
    }

    // Calculates the departure time of the client and returns it.
    public double calculateDepartureTime(){
        departureTime = servedTime + serviceTime;
        return departureTime;
    }

}
