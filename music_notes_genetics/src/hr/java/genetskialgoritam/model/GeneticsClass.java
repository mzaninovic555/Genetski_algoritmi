package hr.java.genetskialgoritam.model;

import hr.java.genetskialgoritam.main.Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GeneticsClass {
    public static final int POP_SIZE = Main.POPULATION_SIZE;
    public static final int NUMBER_ELITES = 1;
    public static final int TOURNAMENT_SIZE = POP_SIZE/2;
    public static final double MUTATION_RATE = Main.MUTATION_RATE;

    public Population evolvePopulation(Population population){
        Population crossoverPopulation = crossoverPopulation(population);
        return mutatePopulation(crossoverPopulation);
    }
    private Population crossoverPopulation(Population population){
        Population newPopulation = new Population(population.getChromosomes().length);
        List<Chromosome> crossoverChromosomes = new ArrayList<>();
        for(int i = 0; i < NUMBER_ELITES; i++){
            newPopulation.getChromosomes()[i] = population.getChromosomes()[i];
        }
        for(int i = NUMBER_ELITES; i < population.getChromosomes().length; i++){
            if(i % 2 != 0) {
                Population tournamentPolulation1 = tournamentPopulation(population);
                Chromosome chromosome1 = tournamentPolulation1.getChromosomes()[0];
                Population tournamentPopulation2 = tournamentPopulation(population);
                Chromosome chromosome2 = tournamentPopulation2.getChromosomes()[0];
                crossoverChromosomes = crossoverChromosome(chromosome1, chromosome2);
                newPopulation.getChromosomes()[i] = crossoverChromosomes.get(0);
            }
            else{
                newPopulation.getChromosomes()[i] = crossoverChromosomes.get(1);
            }
        }
        return newPopulation;
    }
    private List<Chromosome> crossoverChromosome(Chromosome chromosome1, Chromosome chromosome2){
        List<Chromosome> crossoverChromosomes = new ArrayList<>(Arrays.asList(new Chromosome(), new Chromosome()));

        char[] tmpArray1 = new char[chromosome1.getGenes().length()], tmpArray2 = new char[chromosome1.getGenes().length()];
        int crossoverSmaller = ThreadLocalRandom.current().nextInt(0, chromosome1.getGenes().length()/2);
        int crossoverLarger = ThreadLocalRandom.current().nextInt(chromosome1.getGenes().length()/2+1, chromosome1.getGenes().length());
        if(crossoverSmaller % 2 != 0){
            crossoverSmaller--;
        }
        if(crossoverLarger % 2 != 0){
            crossoverLarger--;
        }
        for(int i = 0; i < chromosome1.getGenes().length(); i++){
            if(i < crossoverSmaller || i >= crossoverLarger){
                tmpArray1[i] = chromosome1.getGenes().toCharArray()[i];
                tmpArray2[i] = chromosome2.getGenes().toCharArray()[i];
            }
            else{
                tmpArray1[i] = chromosome2.getGenes().toCharArray()[i];
                tmpArray2[i] = chromosome1.getGenes().toCharArray()[i];
            }
        }
        crossoverChromosomes.get(0).setGenes(String.valueOf(tmpArray1));
        crossoverChromosomes.get(1).setGenes(String.valueOf(tmpArray2));
        return crossoverChromosomes;
    }
    private Population tournamentPopulation(Population population){
        Population tournamentPopulation = new Population(TOURNAMENT_SIZE);
        for(int i = 0; i < TOURNAMENT_SIZE; i++){
            tournamentPopulation.getChromosomes()[i] =
                    population.getChromosomes()[ThreadLocalRandom.current().nextInt(0, population.getChromosomes().length)];
        }
        tournamentPopulation.sortChromosomeByFitness();
        return tournamentPopulation;
    }
    private Population mutatePopulation(Population population){
        Population mutatePopulation = new Population(population.getChromosomes().length);
        for(int i = 0; i < NUMBER_ELITES; i++){
            mutatePopulation.getChromosomes()[i] = population.getChromosomes()[i];
        }
        for(int i = NUMBER_ELITES; i < population.getChromosomes().length; i++){
            mutatePopulation.getChromosomes()[i] = mutateChromosome(population.getChromosomes()[i]);
        }
        return mutatePopulation;
    }
    private Chromosome mutateChromosome(Chromosome chromosome){
        char[] tmpArray = new char[chromosome.getGenes().length()];
        Random r = new Random();
        for(int i = 0; i < chromosome.getGenes().length(); i++){
            if(Math.random() < MUTATION_RATE && chromosome.getGenes().toCharArray()[i] != ' '){
                tmpArray[i] = Chromosome.notes.charAt(r.nextInt(Chromosome.notes.length()));
            }
            else{
                tmpArray[i] =  chromosome.getGenes().toCharArray()[i];
            }
        }
        chromosome.setGenes(String.valueOf(tmpArray));
        return chromosome;
    }
}