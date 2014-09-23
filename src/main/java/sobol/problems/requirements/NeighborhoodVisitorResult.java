package sobol.problems.requirements;

/**
 * Class that represents the results of the local search phase
 */
public class NeighborhoodVisitorResult {

    /**
     * Status in the end of the local search
     */
    private NeighborhoodVisitorStatus status;
    /**
     * Fitness of the best neighbor, in case one has been found
     */
    private double neighborFitness;

    /**
     * Initializes a successful local search status
     *
     * @param status Status of the search, quite certainly a successful one
     * @param fitness Fitness of the best neighbor found
     */
    public NeighborhoodVisitorResult(NeighborhoodVisitorStatus status, double fitness) {
        this.status = status;
        this.neighborFitness = fitness;
    }

    /**
     * Initializes an unsuccessful local search
     *
     * @param status Status of the search: failure to find a better neighbor or
     * exhaustion
     */
    public NeighborhoodVisitorResult(NeighborhoodVisitorStatus status) {
        this.status = status;
        this.neighborFitness = 0.0;
    }

    /**
     * Returns the status of the local search
     */
    public NeighborhoodVisitorStatus getStatus() {
        return status;
    }

    /**
     * Return the fitness of the best neighbor found, if any
     */
    public double getNeighborFitness() {
        return neighborFitness;
    }
}