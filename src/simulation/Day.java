package simulation;

import java.util.ArrayList;

/**
 * Created by Kahnos - libcorrales.
 * Contains information about all events in a simulation day. Setups and runs the simulation for a day.
 * Creates the clients based on a random number for arrival and service times.
 */
public class Day {

    private int dayID = -1;
    private int lostClients = 0;
    private Config config;
    private ArrayList<Event> events = new ArrayList<>();
    private ArrayList<Client> clients = new ArrayList<>();

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

    public int getLostClients() {
        return lostClients;
    }

    public Config getConfig() {
        return config;
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
    private Client createClient(int id, int TM) {
        double randomArrivalNumber = Math.random();
        double randomServiceNumber = Math.random();
        int arrivalTime = -1;
        int serviceTime = -1;

        for (TimeDistribution AD : config.getArrivalDistribution())
            if ((randomArrivalNumber >= AD.getProbabilityMin())
                    && (randomArrivalNumber < (AD.getProbabilityMax() + 0.01))) {
                arrivalTime = AD.getTime();
            }

        for (TimeDistribution SD : config.getServiceDistribution())
            if ((randomServiceNumber >= SD.getProbabilityMin())
                    && (randomServiceNumber < (SD.getProbabilityMax() + 0.01))) {
                serviceTime = SD.getTime();
            }

        return new Client(id, arrivalTime, TM + arrivalTime, serviceTime);
    }

    /**
     * Counts the number of clients currently in the system and returns it.
     * @param servers contains the servers.
     * @param waitingLineLength contains the waiting line length.
     * @return the current number of clients in the system.
     */
    private int countCurrentClients(ArrayList<Server> servers, int waitingLineLength) {
        int clientCounter = 0;

        for (Server server : servers) {
            if (server.isBusy())
                ++clientCounter;
        }

        return clientCounter + waitingLineLength;
    }

    /**
     * Checks the servers availability and returns the index of a free server, if there is one, or -1 if there is not.
     * @param servers contains the servers to check.
     */
    private int getAvailableServer(ArrayList<Server> servers) {
        int availableServer = -1;

        for (Server server : servers)
            if (!server.isBusy())
                availableServer = server.getId();

        return availableServer;
    }

    /**
     * Checks the occupied servers and returns the ID of the server of the departing client.
     * @param servers contains all the servers.
     * @param TM contains the current TM.
     * @return the ID of the server that contains the departing client.
     */
    private int findDepartingClient(ArrayList<Server> servers, int TM) {
        int serverID = -1;

        for (Server server : servers)
            if (server.isBusy())
                if (server.getClient().getDepartureTime() == TM)
                    serverID = server.getId();

        return serverID;
    }

    /**
     * Checks the occupied servers and returns the ID of the server of the next departing client.
     * @param servers contains all the servers.
     * @return the ID of the server that contains the next departing client or -1 if there is none.
     */
    private int findNextDepartingClient(ArrayList<Server> servers) {
        int serverID = -1;
        int minAux = 9999;

        for (Server server : servers)
            if (server.isBusy())
                if (server.getClient().getDepartureTime() < minAux) {
                    minAux = server.getClient().getDepartureTime();
                    serverID = server.getId();
                }

        return serverID;
    }

    /**
     * Simulates the day, creating and adding the events and clients following a waiting line simulation algorithm.
     */
    private void simulate(){
        // Auxiliary variables.
        int eventCounter = 1;   // Keeps track of the events.
        int clientCounter = 1;  // Keeps track of the clients.
        int serverID;           // ID of available server on arrival events or transitioning server on departure events.
        Client client;          // Client related to the current event (Both arrivals and departures).
        Client newClient;       // New client on arrival events.
        Client nextClient;      // Next departing client on departure events.
        Event event;            // Current event.
        boolean openBusiness = true;    // Determines if the business is open and receiving clients.

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

        // Creating the first event, the arrival of the first client.
        events.add(new Event(eventCounter, "Llegada", client,
                0, newClient.getRealArrivalTime(), client.getDepartureTime(), servers, 1));

        // Enter the Matrix.
        while (true) {
            // Current event.
            event = events.get(eventCounter - 1);

            // If the business is closed and no clients remain inside, break the cycle.
            if ((!openBusiness) && (event.getNextDepartureTime() == 9999))
                break;

            // Comparing next arrival time to next departure time. The lesser will be the next event.
            if (event.getNextArrivalTime() < event.getNextDepartureTime()) {
                // Next event is an arrival. Getting the next arriving client.
                client = clients.get(clientCounter - 1);

                // Checking availability of servers. If available, set the arriving client, else the client waits.
                serverID = getAvailableServer(servers);
                if (serverID != -1) {
                    // A server is available.
                    servers.get(serverID).setAll(client, true);
                    client.setAll(client.getRealArrivalTime());
                }
                else {
                    // No server is available, add client to wait line.
                    waitLine.add(client);
                }

                // Check if business is open and maximum clients hasn't been reached.
                if ((openBusiness) &&
                        ((config.getMaxClients() == -1) ||
                        (countCurrentClients(servers, waitLine.size()) < config.getMaxClients()))) {
                    // A new client is possible. Creating a new client.
                    newClient = createClient(++clientCounter, event.getNextArrivalTime());
                    clients.add(newClient);

                    // Checking if client arrives before closing time.
                    if ((newClient.getRealArrivalTime() <= config.getOpenTime())) {
                        /*
                        The business is still open and the client arrives before closing time.
                        Check if the system was previously empty.
                        */
                        if (event.getNextDepartureTime() != 9999) {
                            // Creating an arrival event with the new client's arrival time.
                            events.add(new Event(++eventCounter, "Llegada", client,
                                    client.getRealArrivalTime(), newClient.getRealArrivalTime(),
                                    event.getNextDepartureTime(), servers, waitLine, countCurrentClients(servers, waitLine.size())));
                        }
                        else {
                            /*
                            System was previously empty.
                            Creating an arrival event with the new client's arrival time and current client's departure time.
                            */
                            events.add(new Event(++eventCounter, "Llegada", client,
                                    client.getRealArrivalTime(), newClient.getRealArrivalTime(),
                                    client.getDepartureTime(), servers, waitLine, countCurrentClients(servers, waitLine.size())));
                        }
                    }
                    else {
                        // The client arrives after closing time. Rollback changes and close business.
                        clients.remove(clientCounter - 1);
                        --clientCounter;
                        openBusiness = false;

                        // Creating an arrival event with next arrival time of 9999, so next event is a departure.
                        events.add(new Event(++eventCounter, "Llegada", client,
                                client.getRealArrivalTime(), 9999,
                                event.getNextDepartureTime(), servers, waitLine, countCurrentClients(servers, waitLine.size())));
                    }
                }
                else {
                    /*
                    A new client is not possible, maximum reached or business is closed.
                    Creating an arrival event with next arrival time of 9999, so next event is a departure.
                    */
                    events.add(new Event(++eventCounter, "Llegada", client,
                            client.getRealArrivalTime(), 9999,
                            event.getNextDepartureTime(), servers, waitLine, countCurrentClients(servers, waitLine.size())));

                    if (openBusiness)
                        ++lostClients;
                }
            }
            else {
                /*
                Next event is a departure.
                Getting the transitioning server ID (This server contains the departing client).
                */
                serverID = findDepartingClient(servers, event.getNextDepartureTime());

                // Getting the departing client and releasing the server.
                client = servers.get(serverID).getClient();
                servers.get(serverID).clearAll();

                // Checking if the waiting line and the servers are empty.
                if ((waitLine.isEmpty())
                        && (findNextDepartingClient(servers) == -1)) {
                    /*
                    The system is empty.
                    Creating a departure event with next departure time of 9999, so next event is an arrival.
                    */
                    events.add(new Event(++eventCounter, "Salida", client,
                            client.getDepartureTime(), event.getNextArrivalTime(),
                            9999, servers, waitLine, countCurrentClients(servers, waitLine.size())));
                }
                else {
                    if (!waitLine.isEmpty()) {
                        /*
                        The waiting line isn't empty.
                        Setting the next client on the waiting line to the server.
                        */
                        servers.get(serverID).setAll(waitLine.get(0), true);

                        // Updating the client's attributes and the waiting line.
                        clients.get(waitLine.get(0).getId() - 1).setAll(event.getNextDepartureTime());
                        waitLine.remove(0);
                    }
                    // Getting the next departing client.
                    nextClient = servers.get(findNextDepartingClient(servers)).getClient();

                    // Check if the system wasn't previously full.
                    if (event.getNextArrivalTime() != 9999) {
                        // Creating a departure event with the next client's departure time.
                        events.add(new Event(++eventCounter, "Salida", client,
                                client.getDepartureTime(), event.getNextArrivalTime(),
                                nextClient.getDepartureTime(), servers, waitLine, countCurrentClients(servers, waitLine.size())));
                    }
                    else {
                        /*
                        System was previously full.
                        Creating a new client to set the next arrival time.
                        */
                        newClient = createClient(++clientCounter, event.getNextDepartureTime());
                        clients.add(newClient);

                        // Checking if client arrives before closing time.
                        if ((openBusiness) &&
                                (newClient.getRealArrivalTime() <= config.getOpenTime())) {
                            /*
                            The business is still open and the client arrives before closing time.
                            Creating a departure event with the next client's departure time and the new client's arrival time.
                            */
                            events.add(new Event(++eventCounter, "Salida", client,
                                    client.getDepartureTime(), newClient.getRealArrivalTime(),
                                    nextClient.getDepartureTime(), servers, waitLine, countCurrentClients(servers, waitLine.size())));
                        }
                        else {
                            // The client arrives after closing time. Rollback changes and close business.
                            clients.remove(clientCounter - 1);
                            --clientCounter;
                            openBusiness = false;

                            // Creating a departure event with the next client's departure time and an arrival time of 9999.
                            events.add(new Event(++eventCounter, "Salida", client,
                                    client.getDepartureTime(), 9999,
                                    nextClient.getDepartureTime(), servers, waitLine, countCurrentClients(servers, waitLine.size())));
                        }
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();

        string.append("Day: " + dayID + "\n");

        // Printing all clients.
        string.append("Clients: \n");
        for (Client client : clients) {
            string.append(client.toString());
        }
        string.append("\n\n");

        // Printing all clients' arrival and service times in table format.
        string.append("Clients' arrival and service times: \n");
        string.append(String.format("%10s%10s%10s\n","ID","AT","ST"));
        string.append("======================================\n");
        for (Client client : clients) {
            string.append(String.format("%10d%10d%10d\n", client.getId(), client.getRealArrivalTime(), client.getServiceTime()));
        }
        string.append("\n\n");

        // Printing all events in table format.
        // Table header.
        string.append("Events: \n");
        string.append(String.format("%5s%15s%10s%5s", "ID", "Type", "Client", "TM"));

        for (int i = 0; i < config.getServerAmount(); i++) {
            string.append(String.format("%5s ", "S" + i));
        }

        string.append(String.format("%10s%10s%10s%10s\n", "Waiting", "Total", "AT", "DT"));

        string.append("===================================");

        for (int i = 0; i < config.getServerAmount(); i++) {
            string.append("=====");
        }

        string.append("=============================================\n");

        // Table body.
        for (Event event : events) {
            string.append(event.toString());
        }
        string.append("\n\n");

        return string.toString();
    }

}