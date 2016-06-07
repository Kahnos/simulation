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

        private int TM = -1;
        private String type;
        private Client client;
        private ArrayList<Server> servers = new ArrayList<>();

        public Event() {
        }

        public Event(int TM, String type, Client client) {
            this.TM = TM;
            this.type = type;
            this.client = client;
        }

        public Event(int TM, String type, Client client, int serverAmount) {
            this.TM = TM;
            this.type = type;
            this.client = client;

            for (int i = 0; i < serverAmount; i++) {
                servers.add(new Server(i));
            }
        }

        public Event(int TM, String type, Client client, ArrayList<Server> servers) {
            this.TM = TM;
            this.type = type;
            this.client = client;
            this.servers = (ArrayList) servers.clone();
        }

        // -------------------- Getters -------------------- //

        public int getTM() {
            return TM;
        }

        public String getType() {
            return type;
        }

        public Client getClient() {
            return client;
        }

        public ArrayList<Server> getServers() {
            return servers;
        }

        // -------------------- Setters -------------------- //

        public void setTM(int TM) {
            this.TM = TM;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setClient(Client client) {
            this.client = client;
        }

        public void setServers(ArrayList<Server> servers) {
            this.servers = (ArrayList) servers.clone();
        }

        // -------------------- Other functions -------------------- //

        public void setServer(boolean busy, int index) {
            servers.get(index).setBusy(busy);
        }

        public void setServer(Client client, int index) {
            servers.get(index).setClient(client);
        }

        /**
         * Created by Kahnos - libcorrales.
         * Contains the current status and client of a server.
         */
        public class Server {

            int id = -1;
            private boolean busy = false;
            private Client client;

            public Server() {
            }

            public Server(int id) {
                this.id = id;
            }

            public Server(int id, Client client) {
                this.id = id;
                this.client = client;
            }

            public Server(int id, boolean busy, Client client) {
                this.id = id;
                this.busy = busy;
                this.client = client;
            }

            // -------------------- Getters -------------------- //

            public int getId() {
                return id;
            }

            public boolean isBusy() {
                return busy;
            }

            public Client getClient() {
                return client;
            }

            // -------------------- Setters -------------------- //

            public void setId(int id) {
                this.id = id;
            }

            public void setBusy(boolean busy) {
                this.busy = busy;
            }

            public void setClient(Client client) {
                this.client = client;
            }

        }

    }

}
