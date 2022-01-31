package hr.java.genetskialgoritam.model;

import hr.java.genetskialgoritam.main.Main;
import java.util.concurrent.ThreadLocalRandom;

public class Chromosome {
    private int[][] genes;
    private final int size;
    private int fitness = 0;
    private boolean changedFitness = true;

    public Chromosome() {
        this.size = Main.QUEENS_NUMBER;
        this.genes = new int[size][size];
    }

    public Chromosome randomChromosome(){
        for(int i = 0; i < size; i++){
            int j = ThreadLocalRandom.current().nextInt(0, genes.length);
            genes[i][j] = 1;
        }
        return this;
    }

    public void calculateFitness(){
        int fitness = 0;
        for(int i = 0; i < genes.length; i++){
            for(int j = 0; j < genes.length; j++){
                if(genes[i][j] == 1) {
                    fitness += checkHorizontal(i, j);
                    fitness += checkVertical(i, j);
                    fitness += checkDiagonal(i, j);
                }
            }
        }
        this.fitness = fitness;
    }
    public int checkHorizontal(int x, int y){
        int collisionCount = 0;
        for(int i = 0; i < genes.length; i++){
            if(i != y){
                if(genes[x][i] == 1){
                    collisionCount++;
                }
            }
        }
        return collisionCount;
    }
    public int checkVertical(int x, int y){
        int collisionCount = 0;
        for(int i = 0; i < genes.length; i++){
            if(i != x){
                if(genes[i][y] == 1){
                    collisionCount++;
                }
            }
        }
        return collisionCount;
    }
    public int checkDiagonal(int x, int y){
        int collisionCount = 0;
        int i = x-1, j = y+1;
        while(true){
            if(i < 0 || j >= genes.length){
                break;
            }
            if(genes[i][j] == 1){
                collisionCount++;
            }
            i--; j++;
        }

        i = x+1; j = y-1;
        while(true){
            if(i >= genes.length || j < 0){
                break;
            }
            if(genes[i][j] == 1){
                collisionCount++;
            }
            i++; j--;
        }
        i = x-1; j = y-1;
        while(true){
            if(i < 0 || j < 0){
                break;
            }
            if(genes[i][j] == 1){
                collisionCount++;
            }
            i--; j--;
        }
        i = x+1; j = y+1;
        while(true){
            if(i >= genes.length || j >= genes.length){
                break;
            }
            if(genes[i][j] == 1){
                collisionCount++;
            }
            i++; j++;
        }

        return collisionCount;
    }

    public int[][] getGenes() {
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
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(j == 0 && i != 0){
                    System.out.print("|");
                }
                System.out.print(genes[i][j]);
            }
        }
    }
}