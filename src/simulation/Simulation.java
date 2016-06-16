package simulation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Created by Kahnos - libcorrales.
 * Groups all the days in the simulation, the config and methods to perform global statistical analysis.
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
     * Counts the clients in the system and returns the average.
     * @return an average of the number of clients in the system.
     */
    public double countClients() {
        double globalCount = 0;

        for (Day day : days) {
            globalCount += day.getClients().size();
        }

        return round(globalCount / config.getSimulationDays(), 2);
    }

    /**
     * Calculates the probability that a client waits.
     * @return an average of the probability a client waits.
     */
    public double getWaitProbability() {
        double count = 0;
        double globalCount = 0;

        for (Day day : days) {
            for (Client client : day.getClients()) {
                if (client.getWaitTime() != 0)
                    ++count;
            }
            globalCount += (count / day.getClients().size());
            count = 0;
        }

        return round(globalCount / config.getSimulationDays(), 2);
    }

    /**
     * Counts the clients that waited per day and returns the average.
     * @return an average of the number of clients that waited.
     */
    public double countClientsWithWait() {
        double count = 0;
        double globalCount = 0;

        for (Day day : days) {
            for (Client client : day.getClients()) {
                if (client.getWaitTime() != 0)
                    ++count;
            }
            globalCount += count;
            count = 0;
        }

        return round(globalCount / config.getSimulationDays(), 2);
    }

    /**
     * Counts the clients that didn't wait per day and returns the average.
     * @return an average of the number of clients that didn't wait.
     */
    public double countClientsWithoutWait() {
        double count = 0;
        double globalCount = 0;

        for (Day day : days) {
            for (Client client : day.getClients()) {
                if (client.getWaitTime() == 0)
                    ++count;
            }
            globalCount += count;
            count = 0;
        }

        return round(globalCount / config.getSimulationDays(), 2);
    }

    /**
     * Counts the clients that left per day and returns the average.
     * @return an average of the number of clients who left unattended.
     */
    public double countLostClients() {
        double globalCount = 0;

        for (Day day : days) {
            globalCount += day.getLostClients();
        }

        return round(globalCount / config.getSimulationDays(), 2);
    }

    /**
     * Calculates the average time a client spends inside the system.
     * @return the average time a client is inside the system.
     */
    public double calculateTotalClientTime() {
        double globalTime = 0;
        double clientTime = 0;

        for (Day day : days) {
            for (Client client : day.getClients()) {
                clientTime += client.getDepartureTime() - client.getRealArrivalTime();
            }
            globalTime += (clientTime / day.getClients().size());
        }

        return round(globalTime / config.getSimulationDays(), 2);
    }

    /**
     * Calculates the average time a client spends waiting.
     * @return the average time a client is waiting.
     */
    public double calculateClientWaitTime() {
        double globalTime = 0;
        double clientTime = 0;

        for (Day day : days) {
            for (Client client : day.getClients()) {
                if (client.getWaitTime() != 0)
                    clientTime += client.getWaitTime();
            }
            globalTime += (clientTime / day.getClients().size());
        }

        return round(globalTime / config.getSimulationDays(), 2);
    }

    /**
     * Calculates the average time a client that doesn't wait spends inside the system.
     * @return the average time a client that doesn't wait is inside the system.
     */
    public double calculateTotalNonWaitingClientTime() {
        double globalTime = 0;
        double clientTime = 0;

        for (Day day : days) {
            for (Client client : day.getClients()) {
                if (client.getWaitTime() == 0)
                    clientTime += client.getDepartureTime() - client.getRealArrivalTime();
            }
            globalTime += (clientTime / day.getClients().size());
        }

        return round(globalTime / config.getSimulationDays(), 2);
    }

    /**
     * Calculates the average time a client that waits spends inside the system.
     * @return the average time a client that waits is inside the system.
     */
    public double calculateTotalWaitingClientTime() {
        double globalTime = 0;
        double clientTime = 0;

        for (Day day : days) {
            for (Client client : day.getClients()) {
                if (client.getWaitTime() != 0)
                    clientTime += client.getDepartureTime() - client.getRealArrivalTime();
            }
            globalTime += (clientTime / day.getClients().size());
        }

        return round(globalTime / config.getSimulationDays(), 2);
    }

    /**
     * Calculates the total average time the business works after it closes to attend remaining clients.
     * @return the total average time the business overworks.
     */
    public double calculateExtraWorkTime() {
        double globalTime = 0;

        for (Day day : days) {
            if (day.getFinalTM() > config.getOpenTime())
                globalTime += day.getFinalTM() - config.getOpenTime();
        }

        return round(globalTime / config.getSimulationDays(), 2);
    }

    /**
     * Calculates the total average use time of a single server.
     * @param index contains the index of the server.
     * @return the average use time of server.
     */
    public double calculateServerUseTime(int index) {
        double serverTime = 0;
        double globalTime = 0;
        Event event;
        Event prevEvent;

        for (Day day : days) {
            for (int i = 1; i < day.getEvents().size(); i++) {
                event = day.getEvents().get(i);
                prevEvent = day.getEvents().get(i - 1);

                if (prevEvent.getServers().get(index).isBusy())
                    serverTime += event.getTM() - prevEvent.getTM();
            }
            globalTime += (serverTime / day.getFinalTM());
        }

        return round(globalTime / config.getSimulationDays(), 2);
    }

    /**
     * Calculates the total average use time of all the servers.
     * @return the average use time of all servers.
     */
    public double calculateAllServersUseTime() {
        double serverTime = 0;

        for (int i = 0; i < config.getServerAmount(); i++) {
            serverTime += calculateServerUseTime(i);
        }

        return round(serverTime / config.getServerAmount(), 2);
    }

    /**
     * Calls all the statistical functions and reunites the results in a single arraylist.
     * @return a double arraylist with all the statistics.
     */
    public ArrayList<Double> getAllStatistics() {
        ArrayList<Double> statistics = new ArrayList<>();

        statistics.add(countClients());
        statistics.add(countClientsWithWait());
        statistics.add(countClientsWithoutWait());
        statistics.add(getWaitProbability());
        statistics.add(countLostClients());
        statistics.add(calculateTotalClientTime());
        statistics.add(calculateClientWaitTime());
        statistics.add(calculateTotalWaitingClientTime());
        statistics.add(calculateTotalNonWaitingClientTime());
        statistics.add(calculateExtraWorkTime());

        for (int i = 0; i < config.getServerAmount(); i++) {
            statistics.add(calculateServerUseTime(i));
        }

        statistics.add(calculateAllServersUseTime());

        return statistics;
    }

    /**
     * Rounds a double number. Taken from: http://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
     * @param value is the double to round.
     * @param places is the number of decimals.
     * @return the rounded double.
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();

        // Printing configuration.
        string.append("Configuration: \n");
        string.append(config);

        for (Day day : days) {
            string.append(day);
        }

        string.append("Average clients: ").append(countClients()).append("\n");
        string.append("Average clients with wait: ").append(countClientsWithWait()).append("\n");
        string.append("Average clients without wait: ").append(countClientsWithoutWait()).append("\n");
        string.append("Probability that a client waits: ").append(getWaitProbability()).append("\n");
        string.append("Average lost clients: ").append(countLostClients()).append("\n");
        string.append("Average client system time: ").append(calculateTotalClientTime()).append("\n");
        string.append("Average client wait time: ").append(calculateClientWaitTime()).append("\n");
        string.append("Average waiting client system time: ").append(calculateTotalWaitingClientTime()).append("\n");
        string.append("Average non-waiting client system time: ").append(calculateTotalNonWaitingClientTime()).append("\n");
        string.append("Average overwork time: ").append(calculateExtraWorkTime()).append("\n");
        string.append("Total average use time of server 0: ").append(calculateServerUseTime(0)).append("\n");
        string.append("Total average use time of all servers: ").append(calculateAllServersUseTime()).append("\n");


        return string.toString();
    }

}



























