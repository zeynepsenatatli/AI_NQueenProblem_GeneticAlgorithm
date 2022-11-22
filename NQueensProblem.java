import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class NQueensProblem {
    
    private int population_size;
    private int numberOfQueens;
    private Individual[] population;
    ArrayList<Individual> offsprings = new ArrayList<>();
    private double mutationRate = 0.05;
    public int gen = 0;

    Random random = new Random();    

    public NQueensProblem(int population_size, int numberOfQueens) {
        this.population_size = population_size;
        this.numberOfQueens = numberOfQueens;
    }

    public void createPopulation() {

        population = new Individual[population_size];

        for(int k = 0; k < population_size; k++) {
            population[k] = new Individual(numberOfQueens);
        }   
    }

    public ArrayList<Individual> doGeneration() {
      
        ArrayList<Individual> offlist = new ArrayList<Individual>();
        List<Individual> pList = Arrays.asList(population);
        
        while(offlist.size() != population_size) {
           
            Individual parent1 = selectParent();
            Individual parent2 = selectParent();

            while(parent1.equals(parent2)) {
                parent2 = selectParent();
            }

            Individual[] offs = crossover(parent1, parent2);

            for(int i = 0; i < offs.length; i++) {
                mutation(offs[i]);
            } 
         
            if(!offlist.contains(offs[0]) && !offlist.contains(offs[1]) && !pList.contains(offs[0]) && !pList.contains(offs[1])) {
                offlist.add(offs[0]);
                offlist.add(offs[1]);
            }

        }
        return offlist;
    }

    public Individual selectParent() {
        //Fitnessproportionale Selektion (Roulette Wheel Selection)
        int sum = 0;
        for(Individual i : population) {
            sum = sum + i.getfitnessScore();
        }

        double[] p = new double[population.length];
        for(int i = 0; i < population.length; i++) {
            p[i] = (double)(population[i].getfitnessScore()) / sum;
        }

        //cum-sum of probabilities
        for(int i = 1; i < p.length; i++) {
            p[i] = p[i-1] + p[i];
        }

        double rnd = random.nextDouble(1);

        int index = 0;
        for(int k = 0; k < p.length; k++) {
            if(rnd > p[k]) {
                continue;
            }else {
                index = k;
                break;
            }
        }
        return population[index];
    }

    public Individual[] crossover(Individual p1, Individual p2) {

        int point = random.nextInt(numberOfQueens-3) + 2;
      
        //subarray
        int[] offspring1 = new int[p1.getInd().length];
        int[] offspring2 = new int[p1.getInd().length];

        System.arraycopy(p1.getInd(), 0, offspring1, 0, point);
        System.arraycopy(p2.getInd(), point, offspring1, point, offspring1.length-point);

        System.arraycopy(p2.getInd(), 0, offspring2, 0, point);
        System.arraycopy(p1.getInd(), point, offspring2, point, offspring2.length-point);

        Individual[] result = {new Individual(offspring1), new Individual(offspring2)};
        return result;        
      
    }

    public void mutation(Individual off) {

        for(int k = 0; k < numberOfQueens; k++) {
            if(random.nextDouble() < mutationRate) {
                int rnd = random.nextInt(numberOfQueens-1)+1;

                off.changeElement(k, rnd);
            }
        }
    }
    
    public void updatePopulation(ArrayList<Individual> offsprings) {

        Individual[] buff = new Individual[2*population_size];

        System.arraycopy(population, 0, buff, 0, population_size);
      
        for(int k = 0; k < population_size; k++) {
            buff[k+population_size] = offsprings.get(k);
        }

        Arrays.sort(buff, new Comparator<Individual>() {
            @Override
            public int compare(Individual a, Individual b) {
                return b.getfitnessScore() - a.getfitnessScore(); 
            }
        });

        System.arraycopy(buff, 0, population, 0, population_size);
    }

    public Individual[] getPopulation() {
        return population;
    }
    
}




