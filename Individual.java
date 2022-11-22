import java.util.Random;

public class Individual  {

    private int fitnessScore;
    private int[] ind;

    public Individual(int n) {
        setIndividual(n);
        calculateFitnessScore();
    }

    public Individual(int[] ind) {
        this.ind = ind;
        calculateFitnessScore();
    }

    public void changeElement(int i, int e) {
        this.ind[i] = e;
        calculateFitnessScore();
    }

    private void setIndividual(int n) {
        ind = new int[n];

        Random random = new Random();

        for(int i = 0; i < n; i++) {
            ind[i] = random.nextInt(n) + 1;
        }
    }

    private void calculateFitnessScore() {
        int com = (ind.length * (ind.length - 1) ) / 2;

        int buff = 0;
        for(int i = 0; i < ind.length; i++) {
            for(int k = i+1; k < ind.length; k++) {
                if(ind[i] == ind[k]) {
                    buff++;
                }else if(ind[i] + k - i == ind[k]) {
                    buff++;
                }else if(ind[i] - (k - i) == ind[k]) {
                    buff++;
                }
            } 
        }
        fitnessScore = com - buff;
    }

    public int getfitnessScore() {
        return fitnessScore;
    }

    public int[] getInd() {
        return ind;
    }

    @Override
    public String toString() {
        String s = "[";

        for(int i = 0; i < ind.length; i++) {
            if(i < ind.length-1) {
                s = s + ind[i] + ", ";
            }else {
                s = s + ind[i];
            }
        }
        s = s + "]";
        return s;
    }    

    @Override 
    public boolean equals(Object o) {

        Individual i = (Individual) o;
        
        if(this.toString().equals(i.toString())) {
            
            return true;
        }
        return false;
    }
   
}
