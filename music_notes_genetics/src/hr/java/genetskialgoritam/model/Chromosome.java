package hr.java.genetskialgoritam.model;

import hr.java.genetskialgoritam.main.Main;

import java.util.Arrays;
import java.util.Random;

public class Chromosome {
    public static final String notes = "CDEFGAB";
    private String genes;
    private final int size;
    private int fitness = 0;
    private boolean changedFitness = true;

    public Chromosome() {
        this.size = Main.MELODY_SIZE;
        char[] array = new char[this.size];
        Arrays.fill(array, '0');
        this.genes = new String(array);
    }

    public Chromosome randomChromosome(){
        Random r = new Random();
        char[] tmpChar = genes.toCharArray();
        for(int i = 0; i < size; i++){
            if(i % 2 == 0 || i == 0) {
                tmpChar[i] = notes.charAt(r.nextInt(notes.length()));
            }
            else if(i % 2 != 0 && i != 0) {
                tmpChar[i] = ' ';
            }
        }
        genes = String.valueOf(tmpChar);
        return this;
    }

    public void calculateFitness(){
        int fitness = 0;
        for(int i = 0; i < genes.length(); i++){
            if(genes.toCharArray()[i] == Main.TARGET_MELODY.toCharArray()[i] && genes.toCharArray()[i] != ' '){
                fitness++;
            }
        }
        this.fitness = fitness;
    }
    public void setGenes(String genes){
        this.genes = genes;
    }

    public String getGenes() {
        changedFitness = true;
        return genes;
    }

    public int getFitness() {
        if(changedFitness){
            calculateFitness();
            changedFitness = false;
        }
        return fitness;
    }

    public void printChromosome(){
        System.out.print(genes + " | Fitness: " + fitness);
    }
}