package simulation;

/**
 * Created by Kahnos - libcorrales.
 * Contains information about a specific client.
 */
public class Client {
    private double relativeArrivalTime = -1; // Arrival time assigned by probability.
    private double realArrivalTime = -1;     // Time the client arrives into the system.
    private double departureTime = -1;       // Time the client leaves the system.
    private double serviceTime = -1;         // Service time assigned by probability.
    private double serveTime = -1;           // Time when a client is served.
    private double waitTime = -1;            // Total time the client waits in line.

    public Client() {
    }

    public Client(double relativeArrivalTime, double realArrivalTime, double departureTime, double serviceTime, double serveTime, double waitTime) {
        this.relativeArrivalTime = relativeArrivalTime;
        this.realArrivalTime = realArrivalTime;
        this.departureTime = departureTime;
        this.serviceTime = serviceTime;
        this.serveTime = serveTime;
        this.waitTime = waitTime;
    }

    public Client(double relativeArrivalTime, double serviceTime) {
        this.relativeArrivalTime = relativeArrivalTime;
        this.serviceTime = serviceTime;
    }

    public Client(double relativeArrivalTime, double realArrivalTime, double serviceTime) {
        this.relativeArrivalTime = relativeArrivalTime;
        this.realArrivalTime = realArrivalTime;
        this.serviceTime = serviceTime;
    }

    // -------------------- Getters -------------------- //

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

    public double getServeTime() {
        return serveTime;
    }

    public double getWaitTime() {
        return waitTime;
    }

    // -------------------- Setters -------------------- //

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

    public void setServeTime(double serveTime) {
        this.serveTime = serveTime;
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
        departureTime = serveTime + serviceTime;
        return departureTime;
    }
}
