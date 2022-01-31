package hr.java.genetskialgoritam.main;

import hr.java.genetskialgoritam.model.*;

import java.time.Duration;
import java.time.Instant;

public class Main {
    public static final int QUEENS_NUMBER = 10;

    public static void main(String[] args) {
        Instant startTime = Instant.now();
        System.out.println("\nGenetski algoritam: sto manja fitness brojka -> to je bolji kromosom.");

        int generationCounter = 0;
        Population population = new Population(GeneticsClass.POP_SIZE).initializePopulation();
        GeneticsClass geneticsClass = new GeneticsClass();
        printPopulation(population, generationCounter);

        while(population.getChromosomes()[0].getFitness() > 0){
            generationCounter++;
            population = geneticsClass.evolvePopulation(population);
            population.sortChromosomeByFitness();
            //printPopulation(population, generationCounter);
        }

        System.out.println("\n-----------------Rjesenje-----------------");
        for(int i = 0; i < QUEENS_NUMBER; i++){
            for(int j = 0; j < QUEENS_NUMBER; j++){
                if(i != 0 && j == 0){
                    System.out.println();
                }
                System.out.print(population.getChromosomes()[0].getGenes()[i][j] + " ");
            }
        }
        System.out.println("\nBroj potrebnih generacija: " + generationCounter);
        Instant stopTime = Instant.now();
        System.out.println("Vrijeme izvodjenja: " + Duration.between(stopTime, startTime));
    }
    public static void printPopulation(Population population, int generationCounter){
        System.out.println("\n-----------------Generation " + generationCounter + "-----------------");
        for(int i = 0; i < population.getChromosomes().length; i++){
            System.out.print("Chromosome " + (i+1) + ": " );
            population.getChromosomes()[i].printChromosome();
            System.out.println(" | Fitness: " + population.getChromosomes()[i].getFitness());
        }
    }
}