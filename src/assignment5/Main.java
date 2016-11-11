/* CRITTERS GUI Main.java
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
	
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class Main extends Application {
	static GridPane controllerGrid = new GridPane();
	static GridPane worldGrid = new GridPane();
	static double bigSide = (double)Math.max(Params.world_height, Params.world_width);

	static double cellSize;

	static ArrayList<String> critterNames; 
	static Stage firstStage = new Stage();
	static Stage secondStage = new Stage();
	
	static{
		if((Params.world_height <= 65) || (Params.world_height <= 65)){
			cellSize = (double) ((680.0 - (bigSide * 10)) / bigSide);
		}
		else{
			cellSize = (double) ((0.8 - (bigSide * 0)) / bigSide);
		}
	}
	@Override
	public void start(Stage primaryStage) {
		try {
			critterNames = CritterController.getCrittterNames();			// get all critter names
		
			// deal with the Controller (primary stage)
			CritterController.displayController();
			
			
			// deal with the Critter World (second Stage)
			Critter.displayWorld();
			
			//Shape testShape = CritterShapes.getStar(Color.ORANGE, Color.BLUE);
			//Main.worldGrid.add(testShape, 5, 6);
			
		} catch(Exception e) {
			e.printStackTrace();		
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
