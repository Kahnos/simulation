package simulation;

/**
 * Created by Kahnos - libcorrales.
 * Contains information about a specific client.
 */
public class Client {
    private double relativeArrivalTime; // Arrival time assigned by probability.
    private double realArrivalTime;     // Time the client arrives into the system.
    private double departureTime;       // Time the client leaves the system.
    private double serviceTime;         // Service time assigned by probability.
    private double serveTime;           // Time when a client is served.
    private double waitTime;            // Total time the client waits in line.

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

    // Getters

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

    // Setters

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

    // Other functions

    public void calculateWaitTime(){
        this.waitTime = departureTime - realArrivalTime - serviceTime;
    }

    public void calculateDepartureTime(){
        this.departureTime = serveTime + serviceTime;
    }
}
