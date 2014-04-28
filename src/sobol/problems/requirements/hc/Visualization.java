package sobol.problems.requirements.hc;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import sobol.base.random.RandomGeneratorFactory;
import sobol.base.random.generic.AbstractRandomGenerator;
import sobol.base.random.pseudo.PseudoRandomGeneratorFactory;
import sobol.problems.requirements.model.Project;

/**
 * Hill Climbing landscape explorer for the next release problem
 *
 */
public class Visualization
{

    /**
     * Order under which requirements will be accessed
     */
    private int[] selectionOrder;

    /**
     * Best solution found by the Hill Climbing search
     */
    private boolean[] bestSolution;

    /**
     * Fitness of the best solution found
     */
    private double fitness;

    /**
     * Number of random restart executed
     */
    private int randomRestartCount;

    /**
     * Number of the random restart where the best solution was found
     */
    private int restartBestFound;

    /**
     * Set of requirements to be optimized
     */
    private Project project;

    /**
     * Available budget to select requirements
     */
    private int availableBudget;

    /**
     * Number of solutions to be generated based on an initial solution
     */
    private int numberOfDerivedSolutions;

    /**
     * Initializes the Hill Climbing search process
     */
    public Visualization(PrintWriter detailsFile, Project project, int budget, int numberOfDerivedSolutions) throws Exception
    {
        this.project = project;
        this.availableBudget = budget;
        this.randomRestartCount = 0;
        this.restartBestFound = 0;
        this.numberOfDerivedSolutions = numberOfDerivedSolutions;

        createRandomSelectionOrder(project);
    }


    /**
     * Gera uma ordem aleatoria de selecao dos requisitos
     */
    protected void createRandomSelectionOrder(Project project)
    {
        int customerCount = project.getCustomerCount();
        int[] temporaryOrder = new int[customerCount];

        for (int i = 0; i < customerCount; i++)
            temporaryOrder[i] = i;

        this.selectionOrder = new int[customerCount];
        PseudoRandomGeneratorFactory factory = new PseudoRandomGeneratorFactory();
        AbstractRandomGenerator generator = factory.create(customerCount);
        double[] random = generator.randDouble();

        for (int i = 0; i < customerCount; i++)
        {
            int index = (int)(random[i] * (customerCount - i));
            this.selectionOrder[i] = temporaryOrder[index];

            for (int j = index; j < customerCount-1; j++)
                temporaryOrder[j] = temporaryOrder[j+1];
        }

        for (int i = 0; i < customerCount; i++)
        {
            boolean achou = false;

            for (int j = 0; j < customerCount && !achou; j++)
                if (this.selectionOrder[j] == i)
                    achou = true;

            if (!achou)
                System.out.println("ERRO DE GERACAO DE INICIO ALEATORIO");
        }
    }

    /**
     * Returns the number of random restarts executed during the search process
     */
    public int getRandomRestarts()
    {
        return randomRestartCount;
    }

    /**
     * Returns the number of the restart in which the best solution was found
     */
    public int getRandomRestartBestFound()
    {
        return restartBestFound;
    }

    /**
     * Returns the best solution found by the search process
     */
    public boolean[] getBestSolution()
    {
        return bestSolution;
    }

    /**
     * Returns the fitness of the best solution
     */
    public double getFitness()
    {
        return fitness;
    }

    /**
     * Prints a solution into a string
     */
    public String printSolution(boolean[] solution)
    {
        String s = "[" + (solution[0] ? "S" : "-");

        for (int i = 1; i < solution.length; i++)
            s += " " + (solution[i] ? "S" : "-");

        return s + "]";
    }

    /**
     * Evaluates the fitness of a solution, saving detail information
     */
    private double evaluate(Solution solution)
    {
        int cost = solution.getCost();
        return (cost <= availableBudget) ? solution.getProfit() : -cost;
    }


    /**
     * Creates a random solution
     */
    private boolean[] createRandomSolution(AbstractRandomGenerator random)
    {
        int customerCount = project.getCustomerCount();
        boolean[] solution = new boolean[customerCount];
        double[] sample = random.randDouble();

        for (int i = 0; i < customerCount; i++)
            solution[i] = (sample[i] >= 0.5);

        return solution;
    }

    private boolean[] createRandomSolutionWithNElements(int numberOfElements, AbstractRandomGenerator random) {
        int customerCount = project.getCustomerCount();
        boolean[] solution = new boolean[customerCount];
        Arrays.fill(solution, false);

        List<Integer> listOfPossibilities = generateListOfSize(customerCount);

        for (int i = 1; i <= numberOfElements; i++) {
            int rand = random.singleInt(0, listOfPossibilities.size());
            int position = listOfPossibilities.remove(rand);

            solution[position] = true;
        }

        return solution;
    }

    private List<Integer> generateListOfSize(int size) {
        List<Integer> list = new ArrayList<Integer>();

        for (int i=0; i < size; i++) {
            list.add(i);
        }

        return list;
    }

    /**
     * Executes the Hill Climbing search with random restarts
     */
    public boolean[] execute() throws Exception
    {
        int customerCount = project.getCustomerCount();
        AbstractRandomGenerator random = RandomGeneratorFactory.createForPopulation(customerCount);
        DecimalFormat df = new DecimalFormat("#####.##");

        FileWriter outFile = new FileWriter(new File(project.getName()).getName().replace(".txt", "") + "_70.txt");
        PrintWriter out = new PrintWriter(outFile);

//        FileWriter outFile2 = new FileWriter(project.getName() + "_best.txt", true);
//        PrintWriter out2 = new PrintWriter(outFile2);

        this.bestSolution = new boolean[customerCount];

        
        //numero de elementos da solucao
        for(int numElemens = 1; numElemens <= project.getCustomerCount(); numElemens++) {

//            out.print(numElemens + "\t");
//            out2.print(numElemens + "\t");
            
            List<Double> listSol = new LinkedList<Double>();

            for(int deriv = 0; deriv < this.numberOfDerivedSolutions; deriv++) {

                boolean[] solution = createRandomSolutionWithNElements(numElemens, random);
                //this.bestSolution = createRandomSolutionWithNElements(numElemens, random);
                Solution hcrs = new Solution(project);
                hcrs.setAllCustomers(solution);
//                this.fitness = evaluate(hcrs);
                double currFitness = evaluate(hcrs);
                System.out.print(currFitness+";");
                out.print(currFitness+";");
//                listSol.add(currFitness);
//                if(deriv > 0 && deriv % 99 == 0) {
//                    double best = Collections.max(listSol);
//                    out.print(df.format(getAverage(listSol)) + "\t"); //+ df.format(getAverage(listSol)) + "\t" + df.format(getStandardDeviation(listSol)) + "\t");
//                    out2.print(df.format(best) + "\t");
//                }
                
//                if(currFitness > this.fitness) {
//                    this.fitness = currFitness;
//                    copySolution(solution, this.bestSolution);
//                }
//                System.out.println(printSolution(this.bestSolution));

//                System.out.print(this.fitness + ";");
            }
            System.out.println("");
            //detailsFile.write("\n");
            //detailsFile.flush();
            out.println();
//            out2.println();
//            out2.flush();
            //System.out.println();
        }

        out.close();
//        out2.println("R\t" + MainProgram.countCustomersInSolution(bestSolution) + "\t" + this.fitness);
//        out2.close();
        
        return bestSolution;
    }

    
    protected void copySolution(boolean[] source, boolean[] target) {
        int len = source.length;

        for (int i = 0; i < len; i++) {
            target[i] = source[i];
        }
    }

    private double getAverage(List<Double> list) {
        double avg = 0;
        for (Double d : list) {
            avg += d;
        }
        
        return avg / list.size();
    }
    
   private double getStandardDeviation(List<Double> values) {
        double deviation = 0.0;
        if ((values != null) && (values.size() > 1)) {
            double mean = getAverage(values);
            for (double value : values) {
                double delta = value-mean;
                deviation += delta*delta;
            }
            deviation = Math.sqrt(deviation/values.size());
        }
        return deviation;
    }
    
    private List<Integer> getElementsOfSolutionWithValue(boolean inSolution, boolean[] sol) {
        List<Integer> list = new ArrayList<Integer>();

        for (int idx=0; idx < sol.length; idx++) {
            if(sol[idx] == inSolution) {
                list.add(idx);
            }
        }

        return list;
    }

    private double calculateDelta(double fit1, double fit2) {
        return Math.abs(fit1 - fit2);
    }

    private double calculateAvg(List<Double> deltas) {
        double avg = 0;
        for (Double d : deltas) {
            avg += d;
        }

        return avg / deltas.size();
    }

    private int calculateDistanceBetween(boolean[] sol1, boolean[] sol2) {
        if(sol1.length != sol2.length)  {
            throw new IllegalArgumentException("solutions must have the same size.");
        }

        int dist = 0;
        for (int i = 0; i < sol1.length; i++) {
            if(sol1[i] != sol2[i]) {
                dist++;
            }
        }

        return dist;
    }
}

class Sol {
    public double fitness;
    public int size;

    public Sol(double fitness, int size) {
        this.fitness = fitness;
        this.size = size;
    }
}