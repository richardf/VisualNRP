package sobol.problems.requirements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class VisUpperLimitCalculator {

    private int lower;
    private int size;
    private float[] intervals;
    private Map<Float, List<Double>> fitnessForInterval;
    private int currInterval;
    
    public VisUpperLimitCalculator(int lower, int size, float... intervals) {
        this.lower = lower;
        this.size = size;
        currInterval = -1;
        this.intervals = Arrays.copyOf(intervals, intervals.length);
        fitnessForInterval = new HashMap<Float, List<Double>>();
        initiateFitnessLists();
    }

    private void initiateFitnessLists() {
        for(float interval : intervals) {
            fitnessForInterval.put(interval, new LinkedList<Double>());
        }
    }
    
    public int getUpper() {
        currInterval++;
        if(currInterval >= intervals.length) {
            throw new RuntimeException("All intervals have been already used.");
        }
        
        int upper = calculateUpper(intervals[currInterval]);
        
        return upper;
    }
    
    public void addFitness(double fitness) {
        float interval = intervals[currInterval];
        fitnessForInterval.get(interval).add(fitness);
    }
    
    public int getBestUpper() {
        List<Float> averages = new ArrayList<Float>(intervals.length);
        for(float interval : intervals) {
            averages.add(calculateAverage(fitnessForInterval.get(interval)));
        }
        
        float bestInterval = selectBestInterval(averages);
        return calculateUpper(bestInterval);
    }
    
    private float calculateAverage(List<Double> list) {
        if(list.isEmpty()) return 0;
        
        float sum = 0;
        for(Double el : list) {
            sum += el;
        }
        
        return sum / list.size();
    }

    private float selectBestInterval(List<Float> averages) {
        float bestAvg = Float.MIN_VALUE;
        int bestInterval = 0;
        
        for(int i = 0; i < averages.size(); i++) {
            if(averages.get(i) > bestAvg) {
                bestAvg = averages.get(i);
                bestInterval = i;
            }
        }
        
        return intervals[bestInterval];
    }

    private int calculateUpper(float interval) {
        int upper = Math.round(size * interval);     
        
        upper = upper + lower;
        
        if(upper > size) {
            upper = size;
        }
        
        return upper;        
    }
}
