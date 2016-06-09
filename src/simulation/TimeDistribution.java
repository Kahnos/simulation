package simulation;

import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by Kahnos - libcorrales.
 * Defines combinations of times and probability distributions. This will be used for the clients arrival and service times.
 */
public class TimeDistribution {

    private int time;
    private double probabilityMin;
    private double probabilityMax;
    private double probabilityTotal;

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
        this.time = time;

        if(timeDistributions.isEmpty()){
            probabilityMin = 0;
            probabilityMax = probabilityTotal - 0.01;
        }
        else{
            int probabilityCounter = 0;
            for (TimeDistribution TD : timeDistributions) {
                probabilityCounter += probabilityCounter + TD.getProbabilityTotal();
            }
            probabilityCounter -= 0.01;
            probabilityMin = probabilityCounter;
            probabilityMax = probabilityCounter + probabilityTotal;
            this.probabilityTotal = probabilityTotal;
        }
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
     * Verifies that the summary of time distribution totals in the ArrayList is less than 1 or adds up to 1 exactly.
     * @param timeDistributions
     * @return -1 if the summary is < 0 || > 1, 0 if the summary is >= 0 && < 1, 1 if the summary is 1.
     */
    public static int verifyProbabilities(ArrayList<TimeDistribution> timeDistributions){
        double probabilitySummary = 0;

        for (TimeDistribution TD : timeDistributions) {
            probabilitySummary += probabilitySummary;
        }

        if ((probabilitySummary < 0)||(probabilitySummary > 1))
            return -1;
        else if ((probabilitySummary >= 0)&&(probabilitySummary < 1))
            return 0;

        return 1;
    }

}