package hr.java.genetskialgoritam.model;

import java.util.concurrent.ThreadLocalRandom;

public class GeneticsClass {
    public static final int POP_SIZE = 16;
    public static final int NUMBER_ELITES = 1;
    public static final int TOURNAMENT_SIZE = POP_SIZE/2;
    public static final double MUTATION_RATE = 0.03;

    public Population evolvePopulation(Population population){
        Population crossoverPopulation = crossoverPopulation(population);
        return mutatePopulation(crossoverPopulation);
    }
    private Population crossoverPopulation(Population population){
        Population newPopulation = new Population(population.getChromosomes().length);
        for(int i = 0; i < NUMBER_ELITES; i++){
            newPopulation.getChromosomes()[i] = population.getChromosomes()[i];
        }
        for(int i = NUMBER_ELITES; i < population.getChromosomes().length; i++){
            Population tournamentPolulation1 = tournamentPopulation(population);
            Chromosome chromosome1 = tournamentPolulation1.getChromosomes()[0];
            Population tournamentPopulation2 = tournamentPopulation(population);
            Chromosome chromosome2 = tournamentPopulation2.getChromosomes()[0];
            newPopulation.getChromosomes()[i] = crossoverChromosome(chromosome1, chromosome2);
        }
        return newPopulation;
    }
    private Chromosome crossoverChromosome(Chromosome chromosome1, Chromosome chromosome2){
        Chromosome crossoverChromosome = new Chromosome();
        int crossoverI = ThreadLocalRandom.current().nextInt(0, crossoverChromosome.getGenes().length);

        for(int i = 0; i < crossoverChromosome.getGenes().length; i++){
            for(int j = 0; j < crossoverChromosome.getGenes().length; j++){
                if(i < crossoverI){
                    crossoverChromosome.getGenes()[i][j] = chromosome1.getGenes()[i][j];
                }
                else{
                    crossoverChromosome.getGenes()[i][j] = chromosome2.getGenes()[i][j];
                }
            }
        }
        return crossoverChromosome;
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
        for(int i = 0; i < chromosome.getGenes().length; i++){
            if(Math.random() < MUTATION_RATE){
                int tmpJ = 0;
                for(int j = 0; j < chromosome.getGenes().length; j++){
                    if(chromosome.getGenes()[i][j] == 1){
                        tmpJ = j;
                        chromosome.getGenes()[i][j] = 0;
                    }
                }
                while(true){
                    int newJ = ThreadLocalRandom.current().nextInt(0, chromosome.getGenes().length);
                    if(newJ != tmpJ){
                        chromosome.getGenes()[i][newJ] = 1;
                        break;
                    }
                }
            }
        }
        return chromosome;
    }
}