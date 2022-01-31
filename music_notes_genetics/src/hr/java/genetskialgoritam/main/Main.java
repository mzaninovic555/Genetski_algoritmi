package hr.java.genetskialgoritam.main;

import hr.java.genetskialgoritam.model.*;
import java.time.Duration;
import java.time.Instant;
import org.jfugue.*;

public class Main {
    public final static double MUTATION_RATE = 0.05;
    public final static int POPULATION_SIZE = 8;
    public static final String NOTES = "C D E F G A B";
    public static final String LITTLE_LAMB = "E D C D E E E D D D E G G";
    public static final String MARIO_BROS = "E E E C E G G C G E A B B A G E G A F G E C D B C G E A B B A C E G A F G E C D B G F F D E G A C A C D G F F D E C C C G F F D E G A C A C D D D C";
    public static final String LET_IT_SNOW = "C C C C B A G F C G F G F G F E C D D D C B A G E D C C B A A G F C C C B A G F C G F G F G F E C D D D C B A G E D C C B A A G F E F G A G E C G E G F F E D C D E E F G A G E C G C B A B A B C C C C C B A G F C G F G F G F E C D D D D C B A G E D C C B A A G F";
    public static final String TARGET_MELODY = NOTES;
    public static int MELODY_SIZE = TARGET_MELODY.length();

    public static void main(String[] args) {
        Instant startTime = Instant.now();
        System.out.println("\nGenetski algoritam: sto visa fitness brojka -> to je bolji kromosom.");
        System.out.println("TRAZENA MELODIJA: " + TARGET_MELODY);

        Player player = new Player();
        int generationCounter = 0;
        Population population = new Population(GeneticsClass.POP_SIZE).initializePopulation();
        GeneticsClass geneticsClass = new GeneticsClass();
        printPopulation(population, generationCounter);

        while(!TARGET_MELODY.equals(population.getChromosomes()[0].getGenes())){
            generationCounter++;
            population = geneticsClass.evolvePopulation(population);
            population.sortChromosomeByFitness();
            population.getChromosomes()[0].printChromosome();
            System.out.println(" | Generacija: " + generationCounter);
            printPopulation(population, generationCounter);
            if(generationCounter % 10 == 0){
                player.play(population.getChromosomes()[0].getGenes());
            }
        }

        System.out.println("\n-----------------Rjesenje-----------------");
        population.getChromosomes()[0].printChromosome();
        System.out.println();
        System.out.println("\nBroj potrebnih generacija: " + generationCounter);
        Instant stopTime = Instant.now();
        System.out.println("Vrijeme izvodjenja: " + Duration.between(stopTime, startTime));
        player.play(population.getChromosomes()[0].getGenes());
    }
    public static void printPopulation(Population population, int generationCounter){
        System.out.println("\n-----------------Generation " + generationCounter + "-----------------");
        for(int i = 0; i < population.getChromosomes().length; i++){
            System.out.print("Chromosome " + (i+1) + ": ");
            population.getChromosomes()[i].printChromosome();
            System.out.println();
        }
    }
}