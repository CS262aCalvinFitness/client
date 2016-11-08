package edu.calvin.calvinfitness;

/**
 * Created by mitchstark on 10/16/16.
 * Excercise class - holds information on each individual exercise
 */

public class Exercise {
    private String name;
    private int reps, sets, weights;

    /**
     * @param exercise_name
     * @param exercise_reps
     * @param exercise_sets
     * @param exercise_weights
     * Constructor for the exercise class
     */
    public Exercise(String exercise_name, int exercise_reps, int exercise_sets, int exercise_weights) {
        name = exercise_name;
        reps = exercise_reps;
        sets = exercise_sets;
        weights = exercise_weights;
    }

    /**
     * @return - name
     */
    public String getName() {return name;}

    /**
     *
     * @return - reps
     */
    public int getReps() {return reps;}

    /**
     *
     * @return - sets
     */
    public int getSets() {return sets;}

    /**
     *
     * @return - weights
     */
    public int getWeights() {return weights;}

    /**
     *
     * @param newName - used to set the name of the workout
     */
    public void setName(String newName) {name = newName;}

    /**
     *
     * @param newReps - used to set the number of reps
     */
    public void setReps(int newReps) {reps = newReps;}

    /**
     *
     * @param newSets - used to set the number of sets
     */
    public void setSets(int newSets) {sets = newSets;}

    /**
     *
     * @param newWeights - used to set the number of weights
     */
    public void setWeights(int newWeights) {weights = newWeights;}

    /**
     *
     * @param obj - rhs Excercise object
     * @return - true or false based on equality
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Exercise) {
            Exercise rhs = (Exercise)obj;

            if ((rhs.getName() == name) && (rhs.getReps() == reps) && (rhs.getSets() == sets)
                    && (rhs.getWeights() == weights)) {
                return true;
            }
        }
        return false;
    }
}
