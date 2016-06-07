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



    /**
     * Created by Kahnos - libcorrales.
     * Contains information about a specific event in a day. Includes type, status of servers, current time and involved client.
     */
    public class Event {

    }
}
