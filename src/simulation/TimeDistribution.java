package simulation;

import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by Kahnos - libcorrales.
 * Defines combinations of times and probability distributions. This will be used for the clients arrival and service times.
 */
public class TimeDistribution {

    private int time = -1;
    private double probabilityMin = -1;
    private double probabilityMax = -1;
    private double probabilityTotal = -1;

    public TimeDistribution() {
    }

    public TimeDistribution(int time, double probabilityMin, double probabilityMax) {
        this.time = time;
        this.probabilityMin = probabilityMin;
        this.probabilityMax = probabilityMax;
        probabilityTotal = probabilityMax - probabilityMin;
    }

    /**
     * This constructor sets the probability range by checking the previous TDs in the ArrayList and calculating the proper range.
     * @param time contains the time value inputted by the user.
     * @param probabilityTotal contains the total probability inputted by the user.
     * @param timeDistributions contains all the previous TDs added by the user.
     */
    public TimeDistribution(int time, double probabilityTotal, ArrayList<TimeDistribution> timeDistributions){
        double probabilityCounter = 0;

        if (timeDistributions.isEmpty()){
            probabilityMin = 0;
            probabilityMax = probabilityTotal - 0.01;
        }
        else{
            for (TimeDistribution TD : timeDistributions) {
                probabilityCounter += TD.getProbabilityTotal();
            }
            probabilityMin = probabilityCounter;
            probabilityMax = probabilityCounter + probabilityTotal - 0.01;
        }
        this.time = time;
        this.probabilityTotal = probabilityTotal;
    }

    // -------------------- Getters -------------------- //

    public int getTime() {
        return time;
    }

    public double getProbabilityMin() {
        return probabilityMin;
    }

    public double getProbabilityMax() {
        return probabilityMax;
    }

    public double getProbabilityTotal() {
        return probabilityTotal;
    }

    // -------------------- Setters -------------------- //

    public void setTime(int time) {
        this.time = time;
    }

    public void setProbabilityMin(double probabilityMin) {
        this.probabilityMin = probabilityMin;
    }

    public void setProbabilityMax(double probabilityMax) {
        this.probabilityMax = probabilityMax;
    }

    public void setProbabilityTotal(double probabilityTotal) {
        this.probabilityTotal = probabilityTotal;
    }

    // -------------------- Other functions -------------------- //

    /**
     * Adds up the probabilities of all TDs with index i or lower, then returns it.
     * @param timeDistributions contains all the TDs.
     * @param index contains the index to check.
     * @return the summary of distribution totals.
     */
    public static double probabilityPreviousSummary(ArrayList<TimeDistribution> timeDistributions, int index) {
        double summary = 0;

        for (int i = 0; i < index; i++) {
            summary += timeDistributions.get(i).getProbabilityTotal();
        }

        return summary;
    }

    /**
     * Verifies that the summary of time distribution totals in the ArrayList is less than 1 or adds up to 1 exactly.
     * @param timeDistributions contains the TDs to verify.
     * @return -1 if the summary is < 0 || > 1, 0 if the summary is >= 0 && < 1, 1 if the summary is 1.
     */
    public static int verifyProbabilities(ArrayList<TimeDistribution> timeDistributions){
        double probabilitySummary = 0;

        for (TimeDistribution TD : timeDistributions) {
            probabilitySummary += TD.getProbabilityTotal();
        }

        if ((probabilitySummary < 0)||(probabilitySummary > 1))
            return -1;
        else if ((probabilitySummary >= 0)&&(probabilitySummary < 1))
            return 0;

        return 1;
    }

    @Override
    public String toString(){
        return "Time: " + Integer.toString(time) + " - P. Total: " + Double.toString(probabilityTotal) + " - P. Min: "
                + Double.toString(probabilityMin) + " - P. Max: " + Double.toString(probabilityMax);
    }

}