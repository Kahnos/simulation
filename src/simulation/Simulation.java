package simulation;

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
    // TODO: 14/06/2016 Recomendaciones cuando no se cumple el objetivo

    /**
     * Counts the clients in the system and returns the average.
     * @return an average of the number of clients in the system.
     */
    public double countClients() {
        double globalCount = 0;

        for (Day day : days) {
            globalCount += day.getClients().size();
        }

        return globalCount / config.getSimulationDays();
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

        return globalCount / config.getSimulationDays();
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

        return globalCount / config.getSimulationDays();
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

        return globalCount / config.getSimulationDays();
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

        return globalCount / config.getSimulationDays();
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

        return globalTime / config.getSimulationDays();
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

        return globalTime / config.getSimulationDays();
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

        return globalTime / config.getSimulationDays();
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

        return globalTime / config.getSimulationDays();
    }

    /**
     * Calculates the total average time the business works after it closes to attend remaining clients.
     * @return the total average time the business overworks.
     */
    public double calculateExtraWorkTime() {
        double globalTime = 0;

        for (Day day : days) {
            globalTime += day.getFinalTM() - config.getOpenTime();
        }

        return globalTime / config.getSimulationDays();
    }

    /**
     * Calculates the total average use time of a single server.
     * @param index contains the index of the server.
     * @return the average use time of server.
     */
    public double calculateServerUseTime(int index) {
        double serverTime = 0;
        Event event;
        Event prevEvent;

        for (Day day : days) {
            for (int i = 1; i < day.getEvents().size(); i++) {
                event = day.getEvents().get(i);
                prevEvent = day.getEvents().get(i - 1);

                if (prevEvent.getServers().get(index).isBusy())
                    serverTime += event.getTM() - prevEvent.getTM();
            }
        }

        return serverTime / config.getSimulationDays();
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

        return serverTime / config.getServerAmount();
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

        string.append("Average clients with wait: ").append(countClientsWithWait()).append("\n");
        string.append("Average clients without wait: ").append(countClientsWithoutWait()).append("\n");
        string.append("Probability that a client waits: ").append(getWaitProbability()).append("\n");
        string.append("Average lost clients: ").append(countLostClients()).append("\n");
        string.append("Average clients: ").append(countClients()).append("\n");
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



























