package simulation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    // -------------------- Other functions -------------------- //

    /**
     * Sets the minimum and maximum probability based on the probability total.
     */
    public void setAllMinMax() {
        double minAux;
        double maxAux;

        for (int i = 0; i < arrivalDistribution.size(); i++) {
            minAux = TimeDistribution.probabilityPreviousSummary(arrivalDistribution, i);
            arrivalDistribution.get(i).setProbabilityMin(minAux);

            maxAux = minAux + arrivalDistribution.get(i).getProbabilityTotal() - 0.01;
            arrivalDistribution.get(i).setProbabilityMax(maxAux);
        }

        for (int i = 0; i < serviceDistribution.size(); i++) {
            minAux = TimeDistribution.probabilityPreviousSummary(serviceDistribution, i);
            serviceDistribution.get(i).setProbabilityMin(minAux);

            maxAux = minAux + serviceDistribution.get(i).getProbabilityTotal() - 0.01;
            serviceDistribution.get(i).setProbabilityMax(maxAux);
        }
    }

    /**
     * Reads the configuration (in JSON format) from a file and parses it to an object using Gson.
     * @param filePath contains the path to the file containing the JSON configuration.
     * @return the parsed Config or null if an error occurs.
     */
    public static Config readConfigFromFile(String filePath) throws IOException{
        Config config = null;
        Gson gson = new GsonBuilder().create();

        try {
            // Reading file into String using UTF8 encoding.
            String configString = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);

            // Parsing the JSON string to Config.
            config = gson.fromJson(configString, Config.class);
        } catch (IOException e) {
            System.err.println("Error reading from file " + filePath);
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }

        return config;
    }

    // TODO: 14/06/2016 SAVE CONFIG TO FILE
    
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