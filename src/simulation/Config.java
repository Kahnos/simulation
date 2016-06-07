package simulation;

import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by Kahnos - libcorrales.
 * Contains the configurations for the simulation.
 */
public class Config {

    private int simulationDays = 300; // Defines the amount of days to simulate.
    private int serverAmount = 1;  // Defines the amount of servers.
    private int maxClients = -1;     // Defines the maximum number of clients in the system. -1 = Infinite.
    private ArrayList<TimeDistribution> arrivalDistribution = new ArrayList<>();    // Contains the distribution of arrival times and probabilities.
    private ArrayList<TimeDistribution> serviceDistribution = new ArrayList<>();    // Contains the distribution of service times and probabilities.

    public Config() {
    }

    public Config(int simulationDays, int serverAmount, int maxClients, ArrayList<TimeDistribution> arrivalDistribution, ArrayList<TimeDistribution> serviceDistribution) {
        this.simulationDays = simulationDays;
        this.serverAmount = serverAmount;
        this.maxClients = maxClients;
        this.arrivalDistribution = (ArrayList) arrivalDistribution.clone();
        this.serviceDistribution = (ArrayList) serviceDistribution.clone();
    }

    public Config(int simulationDays, int serverAmount, int maxClients, ObservableList<TimeDistribution> arrivalDistribution, ObservableList<TimeDistribution> serviceDistribution) {
        this.simulationDays = simulationDays;
        this.serverAmount = serverAmount;
        this.maxClients = maxClients;
        this.arrivalDistribution.addAll(arrivalDistribution);
        this.serviceDistribution.addAll(serviceDistribution);
    }

    // -------------------- Getters -------------------- //

    public int getSimulationDays() {
        return simulationDays;
    }

    public int getServerAmount() {
        return serverAmount;
    }

    public int getMaxClients() {
        return maxClients;
    }

    public ArrayList<TimeDistribution> getArrivalDistribution() {
        return arrivalDistribution;
    }

    public ArrayList<TimeDistribution> getServiceDistribution() {
        return serviceDistribution;
    }

    // -------------------- Setters -------------------- //

    public void setSimulationDays(int simulationDays) {
        this.simulationDays = simulationDays;
    }

    public void setServerAmount(int serverAmount) {
        this.serverAmount = serverAmount;
    }

    public void setMaxClients(int maxClients) {
        this.maxClients = maxClients;
    }

    public void setArrivalDistribution(ArrayList<TimeDistribution> arrivalDistribution) {
        this.arrivalDistribution = (ArrayList) arrivalDistribution.clone();
    }

    public void setArrivalDistribution(ObservableList<TimeDistribution> arrivalDistribution) {
        this.arrivalDistribution.clear();
        this.arrivalDistribution.addAll(arrivalDistribution);
    }

    public void setServiceDistribution(ArrayList<TimeDistribution> serviceDistribution) {
        this.serviceDistribution = (ArrayList) serviceDistribution.clone();
    }

    public void setServiceDistribution(ObservableList<TimeDistribution> serviceDistribution) {
        this.serviceDistribution.clear();
        this.serviceDistribution.addAll(serviceDistribution);
    }

    /**
     * Created by Kahnos - libcorrales.
     * Defines combinations of times and probability distributions. This will be used for the clients arrival and service times.
     */
    public class TimeDistribution {

        private int time;
        private double probability;

        public TimeDistribution(int time, double probability) {
            this.time = time;
            this.probability = probability;
        }

        // -------------------- Getters -------------------- //

        public int getTime() {
            return time;
        }

        public double getProbability() {
            return probability;
        }

        // -------------------- Setters -------------------- //

        public void setTime(int time) {
            this.time = time;
        }

        public void setProbability(double probability) {
            this.probability = probability;
        }

    }

}
