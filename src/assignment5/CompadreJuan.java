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
        dir = getRandomInt(8);
    }
    @Override
    public void doTimeStep() {
        if (getEnergy() > 50){
            CompadreJuan child = new CompadreJuan();
            reproduce(child, Critter.getRandomInt(8));
        }

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
