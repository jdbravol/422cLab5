package assignment5;

import javafx.scene.paint.Color;


public class CompadreJuan extends Critter {

    private static final int GENE_TOTAL = 24;
    private int[] genes = new int[8];
    private int dir;

    public CompadreJuan() {
        for (int k = 0; k < 8; k += 1) {
            genes[k] = GENE_TOTAL / 8;
        }
        dir = 0;
    }
    @Override
    public void doTimeStep() {
        if (getEnergy() > Params.run_energy_cost){
            run(dir);
        }
        else if (getEnergy() > Params.walk_energy_cost){
            walk(dir);
        }
        if (getEnergy() < 20){
            for (int i = 0; i < 8; i++) {
                if(look(i,true).equals("@")){
                    run(i);
                }
            }
        }
        if (getEnergy() > 200){
            CompadreJuan child = new CompadreJuan();
            for (int k = 0; k < 8; k += 1) {
                child.genes[k] = this.genes[k];
            }
            int g = Critter.getRandomInt(8);
            while (child.genes[g] == 0) {
                g = Critter.getRandomInt(8);
            }
            child.genes[g] -= 1;
            g = Critter.getRandomInt(8);
            child.genes[g] += 1;
            reproduce(child, Critter.getRandomInt(8));
        }

        dir +=2;
        dir %=8;

    }

    @Override
    public boolean fight(String opponent) {
        if (opponent.equals("JD")){
            walk(getRandomInt(8));
            return false;
        }
        else {
            return true;
        }
    }


    @Override
    public CritterShape viewShape() {
        return CritterShape.STAR;
    }

    public javafx.scene.paint.Color viewColor() {
        return Color.DARKSALMON;
    }


    @Override
    public String toString () {
        return "JD";
    }
}
