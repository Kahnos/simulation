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

    /*
    *   Estadísticas a calcular:

        Configuración de simulación

        Cantidad promedio de clientes en el sistema

        Cantidad promedio de clientes en cola

        Probabilidad de esperar -> # Clientes sin espera / # Total de clientes DONE

        PIECHART {
            # Clientes con espera DONE

            # Clientes sin espera DONE

            # Clientes que se van sin ser atendidos DONE
        }

        BAR CHART {
            Tiempo promedio de cliente en el sistema

            Tiempo promedio que un cliente está en cola

            Tiempo promedio en el sistema de un cliente que hace cola

            Tiempo promedio en el sistema de un cliente que no hace cola

            Tiempo promedio adicional de trabajo del negocio
        }

        BAR CHART {
            Porcentaje de utilización de cada servidor

            Porcentaje de utilización de todos los servidores
        }

    * */

    /**
     * Counts the clients that didn't wait per day and returns the average.
     * @return an average of the number of clients that didn't wait.
     */
    public double countClientsWithoutWait(){
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
     * Counts the clients that waited per day and returns the average.
     * @return an average of the number of clients that waited.
     */
    public double countClientsWithWait(){
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
     * Calculates the probability that a client waits.
     * @return an average of the probability a client waits.
     */
    public double getWaitProbability(){
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
     * Counts the clients that left per day and returns the average.
     * @return an average of the number of clients who left unattended.
     */
    public double countLostClients(){
        double globalCount = 0;

        for (Day day : days) {
            globalCount += day.getLostClients();
        }

        return globalCount / config.getSimulationDays();
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

        string.append("Average clients without wait: ").append(countClientsWithoutWait()).append("\n");
        string.append("Average clients with wait: ").append(countClientsWithWait()).append("\n");
        string.append("Probability that a client waits: ").append(getWaitProbability()).append("\n");

        return string.toString();
    }

}



























