package assignment5;

import javafx.scene.paint.Color;

public class Santiago extends Critter{

    @Override
    public void doTimeStep() {
        if (getEnergy() > Params.walk_energy_cost) {
            walk(getRandomInt(8));
        }
        if (getEnergy() == Params.walk_energy_cost){
            for (int i = 0; i < 8; i++){
                if (look(i,false).equals("@")){
                    walk(i);
                    break;
                }
            }
        }
    }

    @Override
    public boolean fight(String opponent) {
        if (getEnergy() > 10) {
            return true;
        }
        else{
            int dir = getRandomInt(8);
            if (look(dir, true) == null) {
                run(dir);
                return false;
            }
            else {
                return false;
            }
        }
    }

    @Override
    public CritterShape viewShape() {
        return CritterShape.TRIANGLE;
    }

    public javafx.scene.paint.Color viewColor() { return Color.CHOCOLATE; }


    public String toString() {
        return "T";
    }

}

