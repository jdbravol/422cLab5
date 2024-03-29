/* CRITTERS GUI CritterController.java
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
 * This class provides methods for generating useful data for the Critter controller
 */
package assignment5;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import com.sun.glass.events.MouseEvent;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import sun.misc.Cleaner;

public class CritterController {
	static String selectedMakeCritter = "";
	static String selectedRunStatsCritter = "";
	static int animationFPS = 0;
	static TextArea runStatsTextPaneConsole = new TextArea();
	static GridPane animControlGrid;
	static Text animationTitle = new Text("Animating at " + animationFPS + " Step/s");
	static Scene controlScene;
	static Scene animControlScene;
	static Timer animationTimer;
	
	/**
	 * This method gets all .class file names in the current package
	 * @param none
	 * @return an array list of strings
	 */
	public static ArrayList<String> getClassNames(){
		ArrayList<String> classNames = new ArrayList<String>();

		File[] files = new File("src/assignment5/").listFiles(); 				// get an array with files in the directory
		 																		// If this pathname does not denote a directory, 
																				// then listFiles() returns null.
		
		for (File file : files) {
		    if (file.isFile()) {
		    	if(file.getName().endsWith(".java")){
		    		classNames.add(file.getName().replaceAll(".java", ""));		// iterate through all files and add to array those that are classes
		    	}
		    }
		}
		return classNames;
	}
	
	/**
	 * This method gets the names of all  critters
	 * @param none
	 * @return an array list of strings
	 * @throws ClassNotFoundException 
	 */
	public static ArrayList<String> getCrittterNames() throws ClassNotFoundException{
		ArrayList<String> critterNames = new ArrayList<String>();
		ArrayList<String> classNames = CritterController.getClassNames();
		
		Class<?> critterHolder = Class.forName("assignment5.Critter");
		
		// iterate through all classes and save the name of those that
		// are instances of critter:
		for(int i = 0; i < classNames.size(); i++){
			String currentClass = classNames.get(i);
			try{
				Class<?> checkClass = Class.forName("assignment5." + currentClass);
				
				if(critterHolder.isAssignableFrom(checkClass) && !Modifier.isAbstract(checkClass.getModifiers())){
					critterNames.add(classNames.get(i));
				}
			}
			catch(Exception e){
				continue;
			}
		}
		return critterNames;
	}
	
	/**
	 * This method starts and determines the UI components of the game controller
	 * @param none
	 * @return none
	 */
	public static void initUI(){
		// -2 : Set UI attributes
		
		Main.controllerGrid.setAlignment(Pos.CENTER);
		Main.controllerGrid.setHgap(10);
		Main.controllerGrid.setVgap(10);
		Main.controllerGrid.setPadding(new Insets(25,25,25,25));	
		Main.controllerGrid.setGridLinesVisible(false);
		
		// -1.0: Set seed UI
		
		// -1.1: Set title
		Text setSeedTitle = new Text("Please Set Seed");
		setSeedTitle.setFill(Color.DARKBLUE);
		setSeedTitle.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 14));	
		Main.controllerGrid.add(setSeedTitle, 1, 0);
		
		// -1.2: Add num field and label field
		TextField seedNumber = new TextField("");
		Main.controllerGrid.add(seedNumber, 1, 1);
		
		Label seedResponse = new Label("Seed = Default Random");
		seedResponse.setTextFill(Color.BLACK);
		seedResponse.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 14));
		seedResponse.setPrefWidth(230);
		Main.controllerGrid.add(seedResponse, 2, 0);
		
		// -1.3: Add button
		
		// -1.3.1: Create and add button
		Button setSeedButton = new Button();
		setSeedButton.setText("Set Seed!");
		setSeedButton.setTextFill(Color.RED);
		setSeedButton.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 14));
		Main.controllerGrid.add(setSeedButton, 2, 1);
		
		// -1.3.2: Add button handler
		
		setSeedButton.setOnAction((ActionEvent e)->{
			// first see if valid number of critter entered
			int seedNum = 0;
			try{
				seedNum = Integer.parseInt(seedNumber.getText());
				Critter.setSeed(seedNum);
				seedResponse.setText("New Seed = " + seedNum);
			}
			catch(Exception ex){
				return;
			}
		});
		
		
		// 1.0: ADD THE MAKE CRITTER INTERFACE
		
		// 1.1: Add the title
		
		Text makeCritterTitle = new Text("Make Critter");
		makeCritterTitle.setFill(Color.DARKBLUE);
		makeCritterTitle.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 14));	
		Main.controllerGrid.add(makeCritterTitle, 1, 2);
		
		
		// 1.2: Add the label
		
		Label critterResponse = new Label("Please select a critter");
		critterResponse.setTextFill(Color.ORANGERED);
		critterResponse.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 14));
		critterResponse.setPrefWidth(230);
		Main.controllerGrid.add(critterResponse, 2, 2);
		
		
		// 1.3: Add the number field
		
		// 1.3.1: Add the message
		Label critterNumberMessage = new Label("Enter # of critters below:");
		critterNumberMessage.setTextFill(Color.BLACK);
		critterNumberMessage.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 14));
		Main.controllerGrid.add(critterNumberMessage, 2, 3);
		
		// 1.3.2: Add the number field
		TextField critterNumber = new TextField("");
		Main.controllerGrid.add(critterNumber, 2, 4);
		
		
		// 1.4: Add the creation button
		
		// 1.4.1: Create and add button
		Button critterMakeButton = new Button();
		critterMakeButton.setText("Make Critters!");
		critterMakeButton.setTextFill(Color.RED);
		critterMakeButton.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 14));
		Main.controllerGrid.add(critterMakeButton, 2, 5);
		
		// 1.4.2: Add button handler
		
		critterMakeButton.setOnAction((ActionEvent e)->{
			// first see if valid number of critter entered
			int critterNumCreate = 0;
			try{
				critterNumCreate = Integer.parseInt(critterNumber.getText());
			}
			catch(Exception ex){
				return;
			}
			
			// if critter selected and one or more critter to create selected
			if((selectedMakeCritter.length() != 0) && (critterNumCreate != 0)){
				WorldView.createNumCritters(selectedMakeCritter, critterNumCreate);
				CritterController.doRunStats();
				critterResponse.setText(critterNumCreate + " " + selectedMakeCritter + " Created!");
			}
		});

		// 1.5: Add the list of critters
		
		// 1.5.1: Create the list
		ObservableList<String> presentCritters = FXCollections.observableArrayList(Main.critterNames);
		ListView<String> critterNames = new ListView<String>(presentCritters);
		critterNames.setPrefSize(140, 100);
		Main.controllerGrid.add(critterNames, 1, 3, 1, 3);								
		
		// 1.5.2: Add controllers for the list, mark which critter is being selected for creation
		MultipleSelectionModel<String> critterSelModel = critterNames.getSelectionModel();
		critterSelModel.selectedItemProperty().addListener(new ChangeListener<String>(){
			public void changed(ObservableValue<? extends String> changed, String oldVal, String newVal){
				selectedMakeCritter = newVal;										
				critterResponse.setText(newVal + " selected");
				critterNumberMessage.setText("Enter # of " + newVal + " below:");
				critterMakeButton.setText("Make " + newVal + "!");
			}	
		});
		
		// 2.0: ADD THE TIME STEPS INTERFACE
		
		// 2.1.0: Add the time step title
		
		Text timeStepTitle = new Text("Do Time Steps:");
		timeStepTitle.setFill(Color.DARKBLUE);
		timeStepTitle.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 14));	
		Main.controllerGrid.add(timeStepTitle, 1, 7);
		
		
		// 2.2.0: Add the time step buttons
		
		// 2.2.1: Add the 1 time step button
		
		// 2.2.1.A: Create and add button
		Button oneTimeBtn = new Button();
		oneTimeBtn.setText("Do 1 Time Step");
		oneTimeBtn.setTextFill(Color.RED);
		oneTimeBtn.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 14));
		Main.controllerGrid.add(oneTimeBtn, 1, 8);
		
		// 2.2.1.B: Add button handler
		oneTimeBtn.setOnAction((ActionEvent e)->{
			// do the single time step
			Critter.worldTimeStep();
			
			// update the world view
			WorldView.updateWorldView();
			
			CritterController.doRunStats();
		});
		
		
		// 2.2.2: Add the 100 time step button
		
		// 2.2.2.A: Create and add button
		Button hunTimeBtn = new Button();
		hunTimeBtn.setText("Do 100 Time Steps");
		hunTimeBtn.setTextFill(Color.RED);
		hunTimeBtn.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 14));
		Main.controllerGrid.add(hunTimeBtn, 1, 9);
		
		// 2.2.2.B: Add button handler
		hunTimeBtn.setOnAction((ActionEvent e)->{
			// do the 100 time steps
			for(int i = 0; i < 100; i++){
				Critter.worldTimeStep();
			}
			
			// update the world view
			WorldView.updateWorldView();
			
			CritterController.doRunStats();
		});
		
		
		// 2.2.3: Add the 1000 time step button
		
		// 2.2.3.A: Create and add button
		Button thouTimeBtn = new Button();
		thouTimeBtn.setText("Do 1000 Time Steps");
		thouTimeBtn.setTextFill(Color.RED);
		thouTimeBtn.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 14));
		Main.controllerGrid.add(thouTimeBtn, 1, 10);
		
		// 2.2.3.B: Add button handler
		
		thouTimeBtn.setOnAction((ActionEvent e)->{
			// do the 1000 time steps
			for(int i = 0; i < 1000; i++){
				Critter.worldTimeStep();
			}
			
			// update the world view
			WorldView.updateWorldView();
			
			CritterController.doRunStats();
		});
		
		
		// 2.3.0: Draw "or" message
		
		// 2.3.1: Add the message
		Label orNumberMessage = new Label("Or enter # of steps below:");
		orNumberMessage.setTextFill(Color.BLACK);
		orNumberMessage.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 14));
		Main.controllerGrid.add(orNumberMessage, 2, 8);
		
		// 2.3.2: Add the number field
		TextField orNumberField = new TextField("");
		Main.controllerGrid.add(orNumberField, 2, 9);
				
		// 2.3.3: Create and add button
		Button orNumberbtn = new Button();
		orNumberbtn.setText("Do Custon # of Steps!");
		orNumberbtn.setTextFill(Color.DARKGOLDENROD);
		orNumberbtn.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 14));
		Main.controllerGrid.add(orNumberbtn, 2, 10);
		
		// 2.3.4: Add button handler
		
		orNumberbtn.setOnAction((ActionEvent e)->{
			// first see if valid number of steps entered
			int numSteps = 0;
			try{
				numSteps = Integer.parseInt(orNumberField.getText());
			}
			catch(Exception ex){
				return;
			}
			
			// if more than one time step to do
			if(numSteps != 0){
				// do the time steps
				for(int i = 0; i < numSteps; i++){
					Critter.worldTimeStep();
				}
				
				// update the world view
				WorldView.updateWorldView();
				
				CritterController.doRunStats();
			}
		});
		
		// 3.0: The Run Stats interface
		
		// 3.1.0: Add the runStats title
		
		Text runStatsTitle = new Text("Select RunStats Critter");
		runStatsTitle.setFill(Color.DARKBLUE);
		runStatsTitle.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 14));	
		Main.controllerGrid.add(runStatsTitle, 1, 11);

		// 3.2.0: Add the TextPane For Displaying Stats
		
		// 3.2.1: Create a TextArea and set it as the default system console
		PrintStream streamRunStats = new PrintStream(new TextPaneConsole(runStatsTextPaneConsole));
		System.setOut(streamRunStats);
		
		// 3.2.2: Add the text area to the UI
		runStatsTextPaneConsole.setMaxHeight(75);
		Main.controllerGrid.add(runStatsTextPaneConsole, 2, 12);
		
		// 3.3.0: Add the list to the system
		
		// 3.3.1: Add the list of critters
		
		// 3.3.1.1: Create the list
		ObservableList<String> presentRunStatsCritters = FXCollections.observableArrayList(Main.critterNames);
		ListView<String> critterRunStatsNames = new ListView<String>(presentRunStatsCritters);
		critterRunStatsNames.setPrefSize(140, 100);
		Main.controllerGrid.add(critterRunStatsNames, 1, 12, 1, 3);								
		
		// 3.3.1.2: Add controllers for the list, mark which critter is being selected for creation
		MultipleSelectionModel<String> critterStatsSelModel = critterRunStatsNames.getSelectionModel();
		critterStatsSelModel.selectedItemProperty().addListener(new ChangeListener<String>(){
			public void changed(ObservableValue<? extends String> changed, String oldVal, String newVal){
				selectedRunStatsCritter = newVal;
				CritterController.doRunStats();
			}	
		});
		
		
		// 4.0: The animations interface

		// 4.1.0: Add animations title
		
		Text animationTitle = new Text("Select Steps/s for Animation");
		animationTitle.setFill(Color.DARKBLUE);
		animationTitle.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 14));	
		Main.controllerGrid.add(animationTitle, 1, 15);

		// 4.2.0: Add the Animation button:
		
		// Create and add the button
		Button animationbtn = new Button();
		animationbtn.setText("Animate!");
		animationbtn.setTextFill(Color.DARKGOLDENROD);
		animationbtn.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 14));
		Main.controllerGrid.add(animationbtn, 2, 16);
		
		CritterController.animationControl();				// establish all animation attributes
		animControlScene = new Scene(animControlGrid, 500, 800);
		
		// Add button handler
		animationbtn.setOnAction((ActionEvent e)->{
			// first see if valid number of steps entered
			if(animationFPS > 0){
				CritterController.animationTitle.setText("Animating at " + animationFPS + " Step/s");
				Main.firstStage.setScene(animControlScene);
				Main.firstStage.show();
				
				// BEGIN ANIMATION
				animationTimer = new Timer();
				animationTimer.schedule(new AnimThread(), 0, 1000);
			}
		});
		
		
		// 4.3.0: Add the list to the system
		
		// 4.3.1: Add the list of critters
		
		// 4.3.1.1: Create the list
		String[] fpsOptionsArr = {"2", "5", "10", "20","50", "100"};
		ObservableList<String> fpdOptions = FXCollections.observableArrayList(fpsOptionsArr);
		ListView<String> fpsOptionsDisp = new ListView<String>(fpdOptions);
		fpsOptionsDisp.setPrefSize(140, 100);
		Main.controllerGrid.add(fpsOptionsDisp, 1, 16, 1, 3);								
		
		// 4.3.1.2: Add controllers for the list, mark which critter is being selected for creation
		MultipleSelectionModel<String> fpsSelModel = fpsOptionsDisp.getSelectionModel();
		fpsSelModel.selectedItemProperty().addListener(new ChangeListener<String>(){
			public void changed(ObservableValue<? extends String> changed, String oldVal, String newVal){
				animationFPS = Integer.parseInt(newVal);
			}	
		});
		
		// 5.0: Add quit button
		
		// Create and add the button
		Button quitbtn = new Button();
		quitbtn.setText("QUIT");
		quitbtn.setTextFill(Color.DARKGOLDENROD);
		quitbtn.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 14));
		Main.controllerGrid.add(quitbtn, 1, 21);
		

		// Add button handler
		quitbtn.setOnAction((ActionEvent e)->{
			Platform.exit();
		});
	}	
	
	public static void doRunStats(){
		runStatsTextPaneConsole.clear();
		System.out.println(selectedRunStatsCritter + " Stats:");
		try{
			java.util.List<Critter> critterInstancesStats;
			Class<?> critterClass = Class.forName("assignment5." + selectedRunStatsCritter);
			critterInstancesStats = Critter.getInstances(selectedRunStatsCritter);
	
			Method method = critterClass.getMethod("runStats", List.class);
			method.invoke(critterClass, critterInstancesStats);
		}
		catch(Exception e){
			;
		}
	}
	
	public static void displayController(){
		Main.firstStage.setTitle("Critter World Controller");
		CritterController.initUI();
		controlScene = new Scene(Main.controllerGrid, 500, 800);
		Main.firstStage.setScene(controlScene);
		Main.firstStage.show();
	}
	
	public static void animationControl(){
		// change the stage to animation
		Main.firstStage.setTitle("Critter Animation Controller");
		animControlGrid = new GridPane();
		animControlGrid.setAlignment(Pos.CENTER);
		animControlGrid.setHgap(10);
		animControlGrid.setVgap(10);
		animControlGrid.setPadding(new Insets(25,25,25,25));	
		animControlGrid.setGridLinesVisible(false);
		
		// add title and button
		animationTitle.setFill(Color.DARKBLUE);
		animationTitle.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 14));	
		animControlGrid.add(animationTitle, 1, 1);
		
		// Create and add the button
		Button animationbtn = new Button();
		animationbtn.setText("STOP ANIMATION");
		animationbtn.setTextFill(Color.DARKGOLDENROD);
		animationbtn.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 14));
		animControlGrid.add(animationbtn, 1, 2);
		
		// Add button handler
		animationbtn.setOnAction((ActionEvent e)->{
			// STOP ANIMATION!
			animationTimer.cancel();
			
			Main.firstStage.setScene(controlScene);
			Main.firstStage.show();	
		});
		
	}
	

	
	
	public static void generateAnim(){
		;
	}
}
