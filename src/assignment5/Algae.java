/* CRITTERS GUI Algae.java
 * EE422C Project 5 submission by
 * Juan Bravo
 * jdb5338
 * 16475
 * Santiago Echeverri
 * se7365
 * 16470
 * Slip days used: Extra (non-slip) day granted by Dr.Nandakumar due to special circumstances
 * Fall 2016
 */
package assignment5;

import assignment5.Critter.TestCritter;

public class Algae extends TestCritter {

	public String toString() { return "@"; }
	
	public boolean fight(String opponent) {
		if (toString().equals(opponent)) { // same species as me!
			/* try to move away */
			walk(Critter.getRandomInt(8));
		}
		return false; 
	}
	
	public void doTimeStep() {
		setEnergy(getEnergy() + Params.photosynthesis_energy_amount);
	}
	
	public CritterShape viewShape() { return CritterShape.CIRCLE; }
	public javafx.scene.paint.Color viewColor() { return javafx.scene.paint.Color.GREEN; }
}
