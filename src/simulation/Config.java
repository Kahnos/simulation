package simulation;

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

    public Config(int simulationDays, int serverAmount, int maxClients, ArrayList<TimeDistribution> arrivalDistribution, ArrayList<TimeDistribution> serviceDistribution) {
        this.simulationDays = simulationDays;
        this.serverAmount = serverAmount;
        this.maxClients = maxClients;
        this.arrivalDistribution = (ArrayList) arrivalDistribution.clone();
        this.serviceDistribution = (ArrayList) serviceDistribution.clone();
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
