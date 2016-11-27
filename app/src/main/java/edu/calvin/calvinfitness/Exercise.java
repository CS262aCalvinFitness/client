package edu.calvin.calvinfitness;

/*
 * Created by Mitch Stark on 10/16/16.
 *
 * Exercise class - holds information on each individual exercise
 */

public class Exercise {
    private String name;
    private int reps, sets, weights;

    /*
     * Constructor for the exercise class
     *
     * @param exercise_name
     * @param exercise_reps
     * @param exercise_sets
     * @param exercise_weights
     */
    public Exercise(String exercise_name, int exercise_reps, int exercise_sets, int exercise_weights) {
        name = exercise_name;
        reps = exercise_reps;
        sets = exercise_sets;
        weights = exercise_weights;
    }

    /*
     * getName() returns the name of the exercise
     *
     * @param: none
     * @return - name
     */
    public String getName() {return name;}

    /*
     * getReps() returns the number of Reps of the exercise
     *
     * @param: none
     * @return - reps
     */
    public int getReps() {return reps;}

    /*
     * getSets() returns the number of Sets of the exercise
     *
     * @param: none
     * @return - sets
     */
    public int getSets() {return sets;}

    /*
     * getWeight() returns the weight of the exercise
     *
     * @param: none
     * @return - weights
     */
    public int getWeights() {return weights;}

    /*
     * setName() sets the name of the exercise
     *
     * @param newName - used to set the name of the exercise
     * @return: none
     */
    public void setName(String newName) {name = newName;}

    /*
     * setReps() sets the number of reps of the exercise
     *
     * @param newReps - used to set the number of exercise
     * @return: none
     */
    public void setReps(int newReps) {reps = newReps;}

    /*
     * setSets() sets the number of sets of the exercise
     *
     * @param newSets - used to set the number of sets
     */
    public void setSets(int newSets) {sets = newSets;}

    /*
     * setWeights() sets the weight of the exercise
     *
     * @param newWeights - used to set the number of weights
     * @return: none
     */
    public void setWeights(int newWeights) {weights = newWeights;}

    /*
     * equals() determines if two Exercises are equal
     *
     * @param obj - rhs Exercise object
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
