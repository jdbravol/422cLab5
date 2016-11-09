/* CRITTERS GUI WorldView.java
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

/**
 * This class provides methods to set up a nice grid where we show the critters currently alive
 * it uses static elements from Main
 */

package assignment5;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.HashSet;
import java.util.Iterator;

import assignment5.Critter.CritterShape;
import javafx.geometry.*;
import assignment5.Critter.CritterShape;

public class WorldView {
	/**
	 * Draws an empty Critter World of the size specified 
	 * @param void
	 * @returns an empty (new) world GridPane
	 */
	public static GridPane drawInitialWorld(){
		GridPane newWorldGrid = new GridPane();
		
		newWorldGrid.setAlignment(Pos.CENTER);
		newWorldGrid.setHgap(3);
		newWorldGrid.setVgap(3);
		newWorldGrid.setPadding(new Insets(25,25,25,25));		// set qualities
		
		newWorldGrid.setGridLinesVisible(false);				// make lines visible
		
		for(int i = 0; i < Params.world_width; i++){
			for(int j = 0; j < Params.world_width; j++){	
				Rectangle blankSqr = new Rectangle(Main.cellSize, Main.cellSize, Color.WHITE);
				blankSqr.setStroke(Color.WHITE);
				blankSqr.setStrokeWidth(6);
				newWorldGrid.add(blankSqr, i, j);
			}
		}														// add dummy circle to show something
		return newWorldGrid;
	}
	
	/**
	 * creates the specified number of specified critter 
	 * @param1 critter to create
	 * @param2 number of critters to create
	 * @returns void
	 */
	public static void createNumCritters(String critterToCreate, int numCritters){
		for(int i = 0; i < numCritters; i++){
			try {
				Critter.makeCritter(critterToCreate);
			} catch (InvalidCritterException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * creates the specified number of specified critter 
	 * @param the critter whose shape to return
	 * @returns the critter's shape
	 */
	public static Shape getCritterShape(Critter newCritter){
		Shape critterShape = new Rectangle();
		
		switch(newCritter.viewShape()){
			case CIRCLE:
				critterShape = CritterShapes.getCircle(newCritter.viewFillColor(), newCritter.viewOutlineColor());
				break;
			case SQUARE:
				critterShape = CritterShapes.getSquare(newCritter.viewFillColor(), newCritter.viewOutlineColor());
				break;
			case TRIANGLE:
				critterShape = CritterShapes.getTriangle(newCritter.viewFillColor(), newCritter.viewOutlineColor());
				break;
			case DIAMOND:
				critterShape = CritterShapes.getDiamond(newCritter.viewFillColor(), newCritter.viewOutlineColor());
				break;
			case STAR:
				critterShape = CritterShapes.getStar(newCritter.viewFillColor(), newCritter.viewOutlineColor());
				break;
		}	
		return critterShape;
	}
	
	/**
	 * Updates the World View to its current status
	 */
	public static void updateWorldView(){
		// start new world from zero
		GridPane updatedWorld = WorldView.drawInitialWorld();
		
		// now update the world
        for(int row = 0; row < Params.world_height; row++){
            for (int col = 0; col < Params.world_width; col++){
            	// ger the critter in this location
            	HashSet<Critter> currentCell = Critter.locationMatrix[row][col].inHere;
            	if(currentCell.size() > 0){
	            	Iterator<Critter> currentCellIter = currentCell.iterator();
	                Critter critterHere = currentCellIter.next();
	                
	                // add this critter to the visual updated world
	                Shape critterHereShape = WorldView.getCritterShape(critterHere);
	                updatedWorld.add(critterHereShape, col, row);
            	}
            }
        }    
       
		// update the global world
		Main.worldGrid = updatedWorld;
		Scene worldScene = new Scene(Main.worldGrid, 690, 690);
		Main.secondStage.setScene(worldScene);
		Main.secondStage.show();
	}
}

