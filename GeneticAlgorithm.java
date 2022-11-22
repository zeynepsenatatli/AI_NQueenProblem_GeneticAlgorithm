import java.util.ArrayList;

public class GeneticAlgorithm {

    public static void main(String[] args) {
        
        int numberOfQueens = 8;
        int population_size = 10;

        NQueensProblem eightqueens = new NQueensProblem(population_size, numberOfQueens);
        
        eightqueens.createPopulation();

        int checkFitness = 0;
        for(int gen = 0; gen <= 500; gen++) {
            System.out.println("Generation: " + gen);
            for(Individual i: eightqueens.getPopulation()) {
                System.out.print(i);
                System.out.println("Fitness Score: "+ i.getfitnessScore());
                if(i.getfitnessScore() > checkFitness) {
                    checkFitness = i.getfitnessScore();
                }
            }
            if(checkFitness == 28) {
                System.out.println("Solution found");
                break;
            }else{
                ArrayList<Individual> newgen = eightqueens.doGeneration();
                eightqueens.updatePopulation(newgen);
            }

            if(gen == 500) {
                System.out.println("Solution not found");
            }
        }
    }
}