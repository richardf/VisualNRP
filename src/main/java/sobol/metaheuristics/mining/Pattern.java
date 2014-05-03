package sobol.metaheuristics.mining;

import java.util.Arrays;

public class Pattern {
    public int[] pattern;

    public Pattern(String strPattern) {
        this(strPattern, " ");
    }

    public Pattern(String strPattern, String separator) {
        this.pattern = parseStringPattern(strPattern, separator);
    }

    private int[] parseStringPattern(String strPattern, String separator) {
        String[] tokens = strPattern.split(separator);
        
        int[] patt = new int[tokens.length];

        for (int idx = 0; idx < tokens.length; idx++) {
            patt[idx] = Integer.valueOf(tokens[idx]);
        }
        
        return patt;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Arrays.hashCode(this.pattern);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pattern other = (Pattern) obj;
        if (!Arrays.equals(this.pattern, other.pattern)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i : pattern) {
            sb.append(i);
            sb.append(" ");
        }
        return sb.toString().trim();
    }
}
