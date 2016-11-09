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
import javafx.stage.Stage;

public class Main extends Application {
	static GridPane controllerGrid = new GridPane();
	static GridPane worldGrid = new GridPane();
	static double bigSide = (double)Math.max(Params.world_height, Params.world_width);
	static double cellSize = (double) ((680.0 - (bigSide * 10)) / bigSide);
	static ArrayList<String> critterNames; 
	static Stage firstStage = new Stage();
	static Stage secondStage = new Stage();

	@Override
	public void start(Stage primaryStage) {
		try {
			critterNames = CritterController.getCrittterNames();			// get all critter names
		
			// deal with the Controller (primary stage)
			firstStage.setTitle("Critter World Controller");
			CritterController.initUI();
			Scene controlScene = new Scene(controllerGrid, 500, 600);
			firstStage.setScene(controlScene);
			firstStage.show();
			
			// deal with the Critter World (second Stage)
			Critter.displayWorld();
			
		} catch(Exception e) {
			e.printStackTrace();		
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
