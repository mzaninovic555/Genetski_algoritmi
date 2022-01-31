package hr.java.genetskialgoritam.model;

import java.util.Arrays;

public class Population {
    private Chromosome[] chromosomes;

    public Population(int sizePopulation) {
        this.chromosomes = new Chromosome[sizePopulation];
    }
    public Population initializePopulation(){
        for(int i = 0; i < chromosomes.length; i++){
            chromosomes[i] = new Chromosome().randomChromosome();
        }
        sortChromosomeByFitness();
        return this;
    }
    public void sortChromosomeByFitness(){
        Arrays.sort(chromosomes, (chromosome1, chromosome2) -> {
            int tmp = 0;
            if(chromosome1.getFitness() > chromosome2.getFitness()){
                tmp = -1;
            }
            else if(chromosome1.getFitness() < chromosome2.getFitness()){
                tmp = 1;
            }
            return tmp;
        });
    }
    public Chromosome[] getChromosomes() {
        return chromosomes;
    }
}
