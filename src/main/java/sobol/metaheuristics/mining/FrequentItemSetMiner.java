package sobol.metaheuristics.mining;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.mahout.common.Pair;
import org.apache.mahout.common.iterator.StringRecordIterator;
import org.apache.mahout.fpm.pfpgrowth.convertors.StatusUpdater;
import org.apache.mahout.fpm.pfpgrowth.fpgrowth.FPGrowth;

/**
 * This class uses the FPGrowth algorithm implemented in Apache Mahout to
 * perform frequent itemset mining.
 */
public class FrequentItemSetMiner {
    private static final String SUPPORT_SEPARATOR = ":";
    private String clusterSeparator;
    private int minSupport;
    private int numPatterns;

    public FrequentItemSetMiner(int minSupport, int numPatterns, String separator) {
        this.minSupport = minSupport;
        this.numPatterns = numPatterns;
        this.clusterSeparator = separator;
    }

    public FrequentItemSetMiner() {
        this(2, 10, " ");
    }
    
    public List<Pattern> doMining(List<String> dataset) throws IOException {
        
        FPGrowth<String> fp = new FPGrowth<String>();
        Set<String> features = new HashSet<String>();
        final Set<String> patterns = new ConcurrentSkipListSet<String>();
        
        Collection<Pair<String, Long>> frequencies = fp.generateFList(new StringRecordIterator(dataset, clusterSeparator), minSupport);
                
        fp.generateTopKFrequentPatterns(
            new StringRecordIterator(dataset, clusterSeparator),
            frequencies, 
            (long) minSupport, 
            numPatterns, 
            features, 
            getOutputCollector(patterns),
            getDullUpdater()
        );
        
        List<String> patternsSortedByImportance = sortPatterns(patterns);
        return getTopPatternsBySupportAndSize(removeSupportFromPattern(patternsSortedByImportance));
    }
    
    private StatusUpdater getDullUpdater() {
        StatusUpdater updater = new StatusUpdater() {
            public void update(String status) { }
        };
        return updater;
    }
    
    private OutputCollector<String, List<Pair<List<String>, Long>>> getOutputCollector(final Set<String> patterns) {
        
        OutputCollector<String, List<Pair<List<String>, Long>>> output =
                new OutputCollector<String, List<Pair<List<String>, Long>>>() {
                    @Override
                    public void collect(String x1, List<Pair<List<String>, Long>> listPair)
                            throws IOException {

                        StringBuilder sb = new StringBuilder();
                        String sep = " ";
                        
                        for (Pair<List<String>, Long> pair : listPair) {
                            
                            if(pair.getFirst().size() > 1) {
                                sb.append(pair.getSecond());
                                sb.append(SUPPORT_SEPARATOR);

                                for (String item : pair.getFirst()) {
                                    sb.append(item);
                                    sb.append(sep);
                                }

                                patterns.add(sb.toString().trim());
                                sb.delete(0, sb.length());
                            }
                        }
                    }
                };        
        
        return output;
    }

    private List<Pattern> getTopPatternsBySupportAndSize(List<String> patterns) {
        int count = 0;
        List<Pattern> listOfPatterns = new ArrayList<Pattern>();
        
        while(count < patterns.size() && count < numPatterns) {
            listOfPatterns.add(new Pattern(patterns.get(count)));
            count++;
        }
        
        return listOfPatterns;
    }

    private List<String> sortPatterns(Set<String> patterns) {
        List<String> sorted = new ArrayList<String>(patterns);

        Collections.sort(sorted, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String[] tokenO1 = o1.split(SUPPORT_SEPARATOR);
                String[] tokenO2 = o2.split(SUPPORT_SEPARATOR);

                if(tokenO1[0].equals(tokenO2[0])) {
                    //same support. Check size of pattern
                    String[] clustersO1 = tokenO1[1].split(clusterSeparator);
                    String[] clustersO2 = tokenO2[1].split(clusterSeparator);

                    Integer sizeO1 = clustersO1.length;
                    Integer sizeO2 = clustersO2.length;
                    return -1 * sizeO1.compareTo(sizeO2);
                }
                else {
                    //compare with support
                    Integer supO1 = Integer.valueOf(tokenO1[0]);
                    Integer supO2 = Integer.valueOf(tokenO2[0]);
                    return -1 * supO1.compareTo(supO2);
                }
            }
        });
        
        return sorted;
    }

    private List<String> removeSupportFromPattern(List<String> patternsSortedByImportance) {
        List<String> patterns = new ArrayList<String>();
        
        for (String patt : patternsSortedByImportance) {
            String[] tokens = patt.split(SUPPORT_SEPARATOR);
            patterns.add(tokens[1]);
        }
        
        return patterns;
    }
}
