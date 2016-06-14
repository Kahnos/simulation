package simulation;

import java.util.ArrayList;

/**
 * Created by Kahnos - libcorrales.
 * Groups all the days in the simulation, the config and methods to perform statistical analysis.
 */
public class Simulation {

    private Config config;
    private ArrayList<Day> days = new ArrayList<>();

    /**
     * This constructor will simulate all the days based on the configuration. The configuration will be read from a file.
     * @param configPath contains the configurations' file path.
     */
    public Simulation(String configPath) throws Exception{
        this.config = Config.readConfigFromFile(configPath);
        for (int i = 0; i < config.getSimulationDays(); i++) {
            days.add(new Day(i + 1, config));
        }
    }

    /**
     * This constructor will simulate all the days based on the configuration.
     * @param config contains the configuration for the simulation.
     */
    public Simulation(Config config) {
        this.config = config;
        for (int i = 0; i < config.getSimulationDays(); i++) {
            days.add(new Day(i + 1, config));
        }
    }

    // -------------------- Getters -------------------- //

    public Config getConfig() {
        return config;
    }

    public ArrayList<Day> getDays() {
        return days;
    }

    // -------------------- Statistical functions -------------------- //

    /**
     * Counts the clients that didn't wait per day and returns the average.
     * @return an average of the number of clients that didn't wait.
     */
    public int countClientsWithoutWait(){
        int count = 0;

        

        return count;
    }

}



























