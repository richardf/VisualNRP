package sobol.experiments.utils;

import java.util.Arrays;
import sobol.base.random.generic.AbstractRandomGenerator;

public class WeightedSelector {

    private int[] lookup;
    private int totalWeight;
    private AbstractRandomGenerator random;

    WeightedSelector(int[] weights, AbstractRandomGenerator random) {
        this.random = random;
        this.lookup = computeLookup(weights);
    }

    private int[] computeLookup(int[] weights) {
        int[] lookup = new int[weights.length];
        int totalWeight = 0;

        for (int i = 0; i < weights.length; i++) {
            totalWeight += weights[i];
            lookup[i] = totalWeight;
        }

        this.totalWeight = totalWeight;
        return lookup;
    }

    public int getWeightedRandom() {
        int rand = random.singleInt(0, totalWeight);
        int pos = Arrays.binarySearch(lookup, rand);
        if (pos < 0) {
            pos = Math.abs(pos) - 1;
        }

        return pos;
    }
}
