package simulation;

import java.util.ArrayList;

/**
 * Created by Kahnos - libcorrales.
 * Contains information about all events, clients, statistics and keeps track of times in a simulation day.
 */
public class Day {

    private ArrayList<Event> events = new ArrayList<>();
    private ArrayList<Client> clients = new ArrayList<>();
    private int nextArrivalTime = 0;
    private int nextDepartureTime = 0;

    public Day() {
    }

    public Day(Config config) {

    }

    /**
     * Created by Kahnos - libcorrales.
     * Contains information about a specific event in a day. Includes type, status of servers, current waiting line, current time and involved client.
     */
    public class Event {

        

        /**
         * Created by Kahnos - libcorrales.
         * Contains the current status and client of a server.
         */
        public class Server {

            private boolean busy = false;
            private Client client;

            public Server() {
            }

            public Server(Client client) {
                this.client = client;
            }

            public Server(boolean busy, Client client) {
                this.busy = busy;
                this.client = client;
            }

            // -------------------- Getters -------------------- //

            public boolean isBusy() {
                return busy;
            }

            public Client getClient() {
                return client;
            }

            // -------------------- Setters -------------------- //

            public void setBusy(boolean busy) {
                this.busy = busy;
            }

            public void setClient(Client client) {
                this.client = client;
            }

        }

    }

}
