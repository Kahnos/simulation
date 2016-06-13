package simulation;

import javafx.collections.ObservableList;

import javax.smartcardio.ATR;
import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by Kahnos - libcorrales.
 * Contains the configurations for the simulation.
 */
public class Config {
    private int simulationDays = 300;   // Defines the amount of days to simulate.
    private int openTime = 480;         // Defines the amount of minutes a day the business receives clients.
    private int serverAmount = 1;       // Defines the amount of servers.
    private int maxClients = 2;        // Defines the maximum number of clients in the system. -1 = Infinite.
    private ArrayList<TimeDistribution> arrivalDistribution = new ArrayList<>();    // Contains the distribution of arrival times and probabilities.
    private ArrayList<TimeDistribution> serviceDistribution = new ArrayList<>();    // Contains the distribution of service times and probabilities.

    public Config() {
    }

    public Config(int simulationDays, int openTime, int serverAmount, int maxClients, ArrayList<TimeDistribution> arrivalDistribution, ArrayList<TimeDistribution> serviceDistribution) {
        this.simulationDays = simulationDays;
        this.openTime = openTime;
        this.serverAmount = serverAmount;
        this.maxClients = maxClients;
        this.arrivalDistribution = (ArrayList) arrivalDistribution.clone();
        this.serviceDistribution = (ArrayList) serviceDistribution.clone();
    }

    public Config(int simulationDays, int openTime, int serverAmount, int maxClients, ObservableList<TimeDistribution> arrivalDistribution, ObservableList<TimeDistribution> serviceDistribution) {
        this.simulationDays = simulationDays;
        this.openTime = openTime;
        this.serverAmount = serverAmount;
        this.maxClients = maxClients;
        this.arrivalDistribution.addAll(arrivalDistribution);
        this.serviceDistribution.addAll(serviceDistribution);
    }

    // -------------------- Getters -------------------- //

    public int getSimulationDays() {
        return simulationDays;
    }

    public int getOpenTime() {
        return openTime;
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

    public void setOpenTime(int openTime) {
        this.openTime = openTime;
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

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();

        string.append("simulationDays: ").append(simulationDays).append(" - ");
        string.append("openTime: ").append(openTime).append(" - ");
        string.append("serverAmount: ").append(serverAmount).append(" - ");
        string.append("maxClients: ").append(maxClients);
        string.append("\n\n");

        // Printing arrival distributions.
        string.append("Arrival distribution: \n");
        string.append(String.format("%10s%10s%10s\n","Time","Min","Max"));
        string.append("====================================\n");
        for (TimeDistribution AT : arrivalDistribution) {
            string.append(String.format("%10d%10.2f%10.2f\n", AT.getTime(), AT.getProbabilityMin(), AT.getProbabilityMax()));
        }
        string.append("\n\n");

        // Printing service distributions.
        string.append("Service distribution: \n");
        string.append(String.format("%10s%10s%10s\n","Time","Min","Max"));
        string.append("====================================\n");
        for (TimeDistribution ST : serviceDistribution) {
            string.append(String.format("%10d%10.2f%10.2f\n", ST.getTime(), ST.getProbabilityMin(), ST.getProbabilityMax()));
        }
        string.append("\n\n");

        return string.toString();
    }

}