package simulation;

/**
 * Created by Kahnos - libcorrales.
 * Contains information about a specific client.
 */
public class Client {

    private int id = -1;
    private int relativeArrivalTime = -1; // Arrival time assigned by probability.
    private int realArrivalTime = -1;     // Time the client arrives into the system.
    private int serviceTime = -1;         // Service time assigned by probability.
    private int servedTime = -1;          // Time when a client is served.
    private int departureTime = -1;       // Time the client leaves the system.
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
     * Sets realArrivalTime, servedTime, departureTime and waitTime.
     * @param realArrivalTime contains the real arrival time.
     * @param servedTime contains the time the client is served.
     */
    public void setAll(int realArrivalTime, int servedTime) {
        this.realArrivalTime = realArrivalTime;
        this.servedTime = servedTime;
        calculateDepartureTime();
        calculateWaitTime();
    }

    public void setAll(int servedTime) {
        calculateDepartureTime(servedTime);
        calculateWaitTime();
    }

    /**
     * Sets the served time and calculates the departure time of the client and returns it. Requires servedTime.
     * @param servedTime contains the time the client is served.
     * @return the calculated departure time of the Client.
     */
    public int calculateDepartureTime(int servedTime) {
        this.servedTime = servedTime;
        departureTime = servedTime + serviceTime;
        return departureTime;
    }

    public int calculateDepartureTime() {
        departureTime = servedTime + serviceTime;
        return departureTime;
    }

    /**
     * Sets the departure time and calculates the wait time of the client and returns it. Requires realArrivalTime.
     * @param departureTime contains the time of departure of the client.
     * @return the calculated waitTime of the Client.
     */
    public int calculateWaitTime(int departureTime) {
        this.departureTime = departureTime;
        waitTime = departureTime - realArrivalTime - serviceTime;
        return waitTime;
    }

    public int calculateWaitTime(){
        waitTime = departureTime - realArrivalTime - serviceTime;
        return waitTime;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();

        string.append("ID: ").append(id).append(" - ");
        string.append("relativeArrival: ").append(relativeArrivalTime).append(" - ");
        string.append("realArrival: ").append(realArrivalTime).append(" - ");
        string.append("service: ").append(serviceTime).append(" - ");
        string.append("served: ").append(servedTime).append(" - ");
        string.append("departure: ").append(departureTime).append(" - ");
        string.append("wait: ").append(waitTime).append("\n");

        return string.toString();
    }

}
