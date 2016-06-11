package simulation;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Kahnos - libcorrales.
 * Contains information about all events in a simulation day. Setups and runs the simulation for a day.
 * Creates the clients based on a random number for arrival and service times.
 */
public class Day {

    private int dayID = -1;
    private Config config;
    private ArrayList<Event> events = new ArrayList<>();
    private ArrayList<Client> clients = new ArrayList<>();

    public Day() {
    }

    /**
     * This constructor calls simulate(), which runs the simulation.
     * @param dayID contains the ID of the day, for example, day 13 of 300.
     * @param config contains the configuration for the simulation.
     */
    public Day(int dayID, Config config) {
        this.dayID = dayID;
        this.config = config;
        simulate();
    }

    // -------------------- Getters -------------------- //

    public int getDayID() {
        return dayID;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    // -------------------- Setters -------------------- //

    public void setDayID(int dayID) {
        this.dayID = dayID;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = (ArrayList) events.clone();
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = (ArrayList) clients.clone();
    }

    // -------------------- Other functions -------------------- //

    /**
     * Creates a new client, assigning the arrival and service times based on random probability.
     * @param id contains the ID of the client.
     * @param TM contains the current TM.
     * @return the created client.
     */
    private Client createClient(int id, int TM){
        double randomNumber = Math.random();
        int arrivalTime = -1;
        int serviceTime = -1;

        for (TimeDistribution AD : config.getArrivalDistribution())
            if ((randomNumber >= AD.getProbabilityMin())
                    && (randomNumber <= AD.getProbabilityMax())) {
                arrivalTime = AD.getTime();
            }

        for (TimeDistribution SD : config.getServiceDistribution())
            if ((randomNumber >= SD.getProbabilityMin())
                    && (randomNumber <= SD.getProbabilityMax())) {
                serviceTime = SD.getTime();
            }

        return new Client(id, arrivalTime, TM + arrivalTime, serviceTime);
    }

    /**
     * Counts the number of clients currently in the system and returns it.
     * @param TM contains the current TM.
     * @return the current number of clients in the system.
     */
    private int checkCurrentClients(int TM){
        int clientCounter = 0;

        // Count the clients inside the system.
        for (Client client : clients) {
            if ((client.getRealArrivalTime() >= TM)
                    && ((client.getDepartureTime() < TM) || (client.getDepartureTime() == -1))) {
                clientCounter++;
            }
        }

        // Add 1 because the next arrival has not been accounted.
        return clientCounter + 1;
    }

    /**
     * Checks the servers availability and returns the index of a free server, if there is one, or -1 if there is not.
     * @param servers contains the servers to check.
     */
    private int checkServers(ArrayList<Server> servers){
        int availableServer = -1;

        for (Server server : servers) {
            if (!server.isBusy()) {
                availableServer = server.getId();
            }
        }

        return availableServer;
    }

    /**
     * Simulates the day, creating and adding the events and clients following a waiting line simulation algorithm.
     */
    private void simulate(){
        // Auxiliary variables.
        int eventCounter = 1;
        int clientCounter = 1;
        int availableServer;
        Client client;
        Client newClient;
        Event event;

        // Creating the servers based on the configuration.
        ArrayList<Server> servers = new ArrayList(config.getServerAmount());
        for (int i = 0; i < config.getServerAmount(); i++) {
            servers.add(new Server(i));
        }

        // Creating the wait line.
        ArrayList<Client> waitLine = new ArrayList<>();

        // Creating the first client.
        client = createClient(clientCounter, 0);
        client.setRelativeArrivalTime(0);
        client.setAll(0, 0);
        clients.add(client);

        // The first client occupies the first server.
        servers.get(0).setAll(client, true);

        // Creating the second client.
        newClient = createClient(++clientCounter, 0);
        clients.add(newClient);

        /* Creating the first event, the arrival of the first client.
        Event parameters: int eventID, String type, Client client,
        int TM, int nextArrivalTime, int nextDepartureTime, ArrayList<Server> servers, ArrayList<Client> waitLine*/
        events.add(new Event(eventCounter, "Llegada", client,
                0, newClient.getRealArrivalTime(), client.getDepartureTime(), servers));

        // Enter the cycle.
        while (true){
            event = events.get(eventCounter);
            client = clients.get(clientCounter);

            // Comparing next arrival time to next departure time. The lesser will be the next event.
            if (event.getNextArrivalTime() < event.getNextDepartureTime()) {
                // Next event is an arrival.
                // Checking availability of servers. If available, set the arriving client, else the client waits.
                availableServer = checkServers(servers);
                if (availableServer != -1) {
                    // A server is available.
                    servers.get(availableServer).setAll(client, true);
                    client.setAll(client.getRealArrivalTime());
                }
                else {
                    // No server is available, add client to wait line.
                    waitLine.add(client);
                }

                // Check max clients to see if a new client is possible.
                if ((config.getMaxClients() == -1)
                        || (checkCurrentClients(event.getTM()) < config.getMaxClients())) {
                    // A new client is possible. Creating a new client.
                    newClient = createClient(++clientCounter, event.getNextArrivalTime());
                    clients.add(newClient);

                    // Creating a new arrival event with the next clients arrival time.
                    events.add(new Event(++eventCounter, "Llegada", client,
                            client.getRealArrivalTime(), newClient.getRealArrivalTime(),
                            event.getNextDepartureTime(), servers, waitLine));
                }
                else {
                    // A new client is not possible, maximum reached.
                    // Creating a new arrival event with next arrival time of 99999, so next event is a departure.
                    events.add(new Event(++eventCounter, "Llegada", client,
                            client.getRealArrivalTime(), newClient.getRealArrivalTime(),
                            event.getNextDepartureTime(), servers, waitLine));
                }
            }
            else {
                // Next event is a departure.

            }
        }
    }

}





















