/* CRITTERS GUI Critter.java
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

import java.util.List;
import assignment5.Params;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public abstract class Critter {
	/* NEW FOR PROJECT 5 */
	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}
	
	/* the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the default
	 * color to be the same as you background 
	 * 
	 * critters must override at least one of the following three methods, it is not 
	 * proper for critters to remain invisible in the view
	 * 
	 * If a critter only overrides the outline color, then it will look like a non-filled 
	 * shape, at least, that's the intent. You can edit these default methods however you 
	 * need to, but please preserve that intent as you implement them. 
	 */
	public javafx.scene.paint.Color viewColor() { 
		return javafx.scene.paint.Color.WHITE; 
	}
	
	public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
	public javafx.scene.paint.Color viewFillColor() { return viewColor(); }
	
	public abstract CritterShape viewShape(); 
	
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	
	// this matrix keeps track of critters at each specific location
	public static Locations[][] locationMatrix = locationMatrixInit();

	// this matrix keeps track of critters prior to timestep

	public static Locations[][] oldMatrix;

    //inFight is a flag that will detect if a critter is in a fight or not
    private static boolean inFight;
    private boolean fightMoved;

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	
	// ******************* IMPLEMENT THISS!!!!!! ********************************
	protected String look(int direction, boolean steps) {
		
		/*
		Locations[][] lookingMatrix;
		int step;
		int newX = this.x_coord;
		int newY = this.y_coord;
		this.energy -= Params.look_energy_cost;

		if (inFight){
			lookingMatrix = locationMatrix;
		}
		else{
			lookingMatrix = oldMatrix;
		}

		if (steps){
			step = 1;
		}
		else{
			step = 2;
		}
		switch (direction) {
			case 0:
				newX +=  step;
				newX %= Params.world_width;
			case 1:
				newX +=  step;
				newX %= Params.world_width;
				newY += step;
				newY %= Params.world_height;
			case 2:
				newY += this.y_coord;
				newY %= Params.world_height;

			case 3:
				newX -= step;
				newX %= Params.world_width;
				newY += step;
				newY %= Params.world_height;
			case 4:
				newX -= step;
				newX %= Params.world_width;
			case 5:
				newX -= step;
				newX %= Params.world_width;
				newY -= step;
				newY %= Params.world_height;
			case 6:
				newY -= step;
				newY %= Params.world_height;
			case 7:
				newX += step;
				newX %= Params.world_width;
				newY -= step;
				newY %= Params.world_height;
		}
		if (lookingMatrix[newY][newX].inHere.isEmpty()){
			return null;
		}
		else{
			Iterator<Critter> cellIterator = lookingMatrix[this.y_coord][this.x_coord+step].inHere.iterator();
			return 	cellIterator.next().toString();
		}
		*/
		return "";
	}
	
	
	
	/**
	 * @name Locations
	 * @description this private class contains a HashSet holding
	 * all critters found in a certain location of the map
	 */
	static public class Locations{
		protected HashSet<Critter> inHere;
		
		Locations(){
			inHere = new HashSet<Critter>(0);
		}
	}
	
	
	/**
	 * @name locationMatrixInit
	 * @description This private method initiates a new locationMatrix
	 * @returns an initialized location matrix of empty locations
	 */
	private static Locations[][] locationMatrixInit(){
		Locations[][] newLocations = new Locations[Params.world_height][Params.world_width];
		
		for(int row = 0; row < Params.world_height; row++){
			for(int col = 0; col < Params.world_width; col++){
				newLocations[row][col] = new Locations();
			}
		}
		return newLocations;
	}

	/**
	 * returns a random number from 0 to (max - 1)
	 * @param max
	 * @return random int from 0 to (max - 1)
	 */
    public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
    /**
     * sets the seed for random number generating
     * @param new_seed
     */
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/**
	 *  a one-character long string that visually depicts your critter in the ASCII interface
	 *   
	 */
	public String toString() { return ""; }
	
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
    private boolean haveMoved;

	/**
	 * @name changeCoordinates
	 * @description This private method moves the critter in the specified number of steps
	 * and direction
	 * @param1 number of steps to move
	 * @param2 direction to move towards
	 * @returns nothing
	 */
    private void changeCoordinates(int steps, int direction){
        switch (direction) {
            case 0:
                this.x_coord += steps;
                this.x_coord %= Params.world_width;
                break;
            case 1:
                this.x_coord += steps;
                this.x_coord %= Params.world_width;
                this.y_coord -= steps;
                if(this.y_coord < 0){
                	this.y_coord = Params.world_height - 1;
                }
                break;
            case 2:
                this.y_coord -= steps;
                if(this.y_coord < 0){
                	this.y_coord = Params.world_height - 1;
                }
                break;
            case 3:
                this.x_coord -= steps;
                if(this.x_coord < 0){
                	this.x_coord = Params.world_width - 1;
                }
                this.y_coord -= steps;
                if(this.y_coord < 0){
                	this.y_coord = Params.world_height - 1;
                }
                break;
            case 4:
                this.x_coord -= steps;
                if(this.x_coord < 0){
                	this.x_coord = Params.world_width - 1;
                }
                break;
            case 5:
                this.x_coord -= steps;
                if(this.x_coord < 0){
                	this.x_coord = Params.world_width - 1;
                }
                this.y_coord += steps;
                this.y_coord %= Params.world_height;
                break;
            case 6:
                this.y_coord += steps;
                this.y_coord %= Params.world_height;
                break;
            case 7:
                this.x_coord += steps;
                this.x_coord %= Params.world_width;
                this.y_coord += steps;
                this.y_coord %= Params.world_height;
                break;
        }
    }
    
    /**
     * @name checkDeath
     * @description checks if cell is available for moving into during fight
     * @param cell in HashSet form
     * @return boolean telling if available or not
     */
    private boolean checkDeath(HashSet<Critter> cell){
    	if(cell.size() == 0){
    		return true;
    	}
    	
    	// check if all dead
    	for(Critter checkIfDead: cell){
    		if(checkIfDead.energy > 0){
    			return false;
    		}
    	}
    	return true;
    }

    /**
	 * @name changeCoordinates
	 * @description This private method moves the critter in the specified number of steps
	 * and direction (when the critter is in a fight)
	 * @param1 number of steps to move
	 * @param2 direction to move towards
	 * @returns nothing
	 */
    private void moveInFight(int steps, int direction){
    	int newX;
    	int newY;
        switch (direction) {
            case 0:
            		newX = this.x_coord + steps;
            		newX %= Params.world_width;
            	
                if (checkDeath(locationMatrix[this.y_coord][newX].inHere)) {
                    this.x_coord += steps;
                    this.x_coord %= Params.world_width;
                    this.fightMoved = true;
                }
                break;
            case 1:
            		newX = this.x_coord + steps;
            		newX %= Params.world_width;
            	
            		newY = this.y_coord - steps;
            		if(newY < 0){
            			newY = Params.world_height - 1;
                }
            		
                if (checkDeath(locationMatrix[newY][newX].inHere)) {
                    this.x_coord += steps;
                    this.x_coord %= Params.world_width;
                    this.y_coord -= steps;
                    if(this.y_coord < 0){
                    	this.y_coord = Params.world_height - 1;
                    }
                    this.fightMoved = true;
                }
                break;
            case 2:
            		newY = this.y_coord - steps;
            		if(newY < 0){
            			newY = Params.world_height - 1;
                }
            		
                if (checkDeath(locationMatrix[newY][this.x_coord].inHere)) {
                    this.y_coord -= steps;
                    if(this.y_coord < 0){
                    	this.y_coord = Params.world_height - 1;
                    }
                    this.fightMoved = true;
                }
                break;
            case 3:
            		newX = this.x_coord - steps;
            		if(newX < 0){
            			newX = Params.world_width - 1;
                }
            	
            		newY = this.y_coord - steps;
            		if(newY < 0){
            			newY = Params.world_height - 1;
                }
            		
                if (checkDeath(locationMatrix[newY][newX].inHere)) {
                    this.x_coord -= steps;
                    if(this.x_coord < 0){
                    	this.x_coord = Params.world_width - 1;
                    }
                    
                    this.y_coord -= steps;
                    if(this.y_coord < 0){
                    	this.y_coord = Params.world_height - 1;
                    }
                    this.fightMoved = true;
                }
                break;
            case 4:
            		newX = this.x_coord - steps;
            		if(newX < 0){
            			newX = Params.world_width - 1;
                }
            	
                if (checkDeath(locationMatrix[this.y_coord][newX].inHere)) {
                    this.x_coord -= steps;
                    if(this.x_coord < 0){
                    	this.x_coord = Params.world_width - 1;
                    }
                    this.fightMoved = true;
                }
                break;
            case 5:
            		newX = this.x_coord - steps;
                if(newX < 0){
                		newX = Params.world_width - 1;
                }
                
                newY = this.y_coord + steps;
                newY %= Params.world_height;
            	
                if (checkDeath(locationMatrix[newY][newX].inHere)) {
                    this.x_coord -= steps;
                    if(this.x_coord < 0){
                    	this.x_coord = Params.world_width - 1;
                    }
                    this.y_coord += steps;
                    this.y_coord %= Params.world_height;
                    this.fightMoved = true;
                }
                break;
            case 6:
                newY = this.y_coord + steps;
                newY %= Params.world_height;
            	
                if (checkDeath(locationMatrix[newY][this.x_coord].inHere)) {
                    this.y_coord += steps;
                    this.y_coord %= Params.world_height;
                    this.fightMoved = true;
                }
                break;
            case 7:
                newX = this.x_coord + steps;
                newX %= Params.world_width;
                
                newY = this.y_coord + steps;
                newY %= Params.world_height;
            	
                if (checkDeath(locationMatrix[newY][newX].inHere)) {
                    this.x_coord += steps;
                    this.x_coord %= Params.world_width;
                    this.y_coord += steps;
                    this.y_coord %= Params.world_height;
                    this.fightMoved = true;
                }
                break;
        }
    }
    
    /**
	 * @name changeCoordinates
	 * @description This private method walks the critter in the specified direction
	 * @param direction to move towards
	 * @returns nothing
	 */
	protected final void walk(int direction) {
        this.energy -= Params.walk_energy_cost;
        if(!haveMoved){
            locationMatrix[this.y_coord][this.x_coord].inHere.remove(this);
        }
        if(inFight && !haveMoved){
            moveInFight(1, direction);
            locationMatrix[this.y_coord][this.x_coord].inHere.add(this);
            this.haveMoved = true;

        }
        else if(!haveMoved) {
            changeCoordinates(1, direction);
            locationMatrix[this.y_coord][this.x_coord].inHere.add(this);
            this.haveMoved = true;
        }
	}
	
	
	/**
	 * @name changeCoordinates
	 * @description This private method runs the critter in the specified direction
	 * @param direction to move towards
	 * @returns nothing
	 */
	protected final void run(int direction) {
		this.energy -= Params.run_energy_cost;
        if(!haveMoved){
            locationMatrix[this.y_coord][this.x_coord].inHere.remove(this);
        }
        if(inFight && !haveMoved){
            moveInFight(2, direction);
            locationMatrix[this.y_coord][this.x_coord].inHere.add(this);
            this.haveMoved = true;
        }
        else if(!haveMoved) {
            changeCoordinates(2, direction);
            locationMatrix[this.y_coord][this.x_coord].inHere.add(this);
            this.haveMoved = true;
        }
	}

	/**
	 * @name changeCoordinates
	 * @description This private method reproduces the critter
	 * @param direction to move towards
	 * @returns nothing
	 */
	protected final void reproduce(Critter offspring, int direction) {
		// confirm parent has enough energy
		if(this.energy < Params.min_reproduce_energy){
			return;
		}
		
		offspring.energy = this.energy / 2;						// assign offspring half of parent's energy (round down);
		this.energy = (this.energy / 2) + (this.energy % 2);	// assign parent half of parent's energy (round up);
		
		// assign coordinates to child 
		offspring.x_coord = this.x_coord;
		offspring.y_coord = this.y_coord;
		changeCoordinates(1, direction);
		
		// add offspring to array list
		babies.add(offspring);
	}

	/**
	 * makes the critter do whatever its is designed to do during a time step
	 */
	public abstract void doTimeStep();
	
	/**
	 * makes the critter do whatever its is designed to do during a fight
	 * @param opponent
	 * @return true if wants to fight or false if not want to fight
	 */
	public abstract boolean fight(String opponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * Also, this method adds the critter to the visual world
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
        try{

        	// dynamically create class:
            Critter newCritter = (Critter) Class.forName(myPackage + "." + critter_class_name).newInstance(); 
            newCritter.x_coord = getRandomInt(Params.world_width);      				// sets random x axis
            newCritter.y_coord = getRandomInt(Params.world_height);     				// sets random y axis
            newCritter.energy = Params.start_energy;                        			// sets starting energy
            population.add(0, newCritter);				                    			// adds to living hashset
            locationMatrix[newCritter.y_coord][newCritter.x_coord].inHere.add(newCritter);		// add new critter to contents of such location
            
            // add critter to the visual world
            Rectangle bckSqr = new Rectangle(Main.cellSize, Main.cellSize, Color.WHITE);
            bckSqr.setStroke(Color.WHITE);
			if((Params.world_height <= 65) || (Params.world_height <= 65)){
				bckSqr.setStrokeWidth(6);
			}
			else{
				bckSqr.setStrokeWidth(6);
			}
            Main.worldGrid.add(bckSqr, newCritter.x_coord, newCritter.y_coord);
            
            Shape newCritterShape = WorldView.getCritterShape(newCritter);
            Main.worldGrid.add(newCritterShape, newCritter.x_coord, newCritter.y_coord);
        }
        // catch invalid critter errors
        catch(ClassNotFoundException ex){
            throw new InvalidCritterException(critter_class_name);
        } catch (InstantiationException e) {
            throw new InvalidCritterException(critter_class_name);
        } catch (IllegalAccessException e) {
            throw new InvalidCritterException(critter_class_name);
        }
    }
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
        try{
        	// dynamically create class:
        	for(int row = 0; row < Params.world_height; row++){
	        	for(int col = 0; col < Params.world_width; col++){
	        		Locations thisCritterCell = locationMatrix[row][col];
		            for (Critter maybeInstance: thisCritterCell.inHere){
		            Class givenCritter = Class.forName("assignment5." + critter_class_name);
		                if (givenCritter.isInstance(maybeInstance)) {
		                    result.add(0, maybeInstance);
		                 }
		
		            }
	        	}
        	}
        }
        // catch invalid critter errors
        catch(ClassNotFoundException ex) {
            throw new InvalidCritterException(critter_class_name);
        }
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
            locationMatrix[this.getY_coord()][this.getX_coord()].inHere.remove(this);
			super.x_coord = new_x_coord;
            locationMatrix[this.getY_coord()][this.getX_coord()].inHere.add(this);
		}
		
		protected void setY_coord(int new_y_coord) {
            locationMatrix[this.getY_coord()][this.getX_coord()].inHere.remove(this);
			super.y_coord = new_y_coord;
            locationMatrix[this.getY_coord()][this.getX_coord()].inHere.add(this);

		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() { 	//gets the y coordinate of the critter
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
        population.clear();
        locationMatrix = locationMatrixInit();
	}
	
	/**
	 * goes through the seven necessary steps of the World Time Step
	 */
	public static void worldTimeStep() {
		// 0: initializes matrix with old locations
		oldLocationInit();

		// 1: do everyone's time steps
        for(Critter critter: population){
            critter.doTimeStep();
        }

        // 2: resolve all encounters
        resolveEncounters();
        
        // 3: update rest energy
        for(Critter critter: population){
            critter.energy -= Params.rest_energy_cost;
        }
        
        // 4: generate algae
        generateAlgae();
        
        // 5: move babies into population and clear babies list
        Iterator<Critter> babyIterator = babies.iterator();
        Critter newBaby;
        while(babyIterator.hasNext()){
        	newBaby = babyIterator.next();
        	population.add(0, newBaby);
        	locationMatrix[newBaby.y_coord][newBaby.x_coord].inHere.add(newBaby);
        }
        babies.clear();
        
        // 6: remove all dead
        Iterator<Critter> livingIterator = population.iterator();
        Critter maybeDeadCrit;
        while(livingIterator.hasNext()){
        	maybeDeadCrit = livingIterator.next();
        	if(maybeDeadCrit.energy <= 0){
        		population.remove(maybeDeadCrit);
        		locationMatrix[maybeDeadCrit.y_coord][maybeDeadCrit.x_coord].inHere.remove(maybeDeadCrit);
        		livingIterator = population.iterator();
        	}
        }
        
        // 7: reset haveMoved and fightMoved
        for(Critter critter: population){
            critter.haveMoved = false;
            critter.fightMoved = false;
        }
	}
	
	
	/**
	 * @name generateAlgae
	 * @description creates algae for the world
	 */
	private static void generateAlgae(){
		int numAlgae = assignment5.Params.refresh_algae_count;
		
		for(int i = 0; i < Params.refresh_algae_count; i++){
			Critter newAlgae = new Algae();
			newAlgae.energy = Params.start_energy;
			newAlgae.x_coord = Critter.getRandomInt(Params.world_width);
			newAlgae.y_coord = Critter.getRandomInt(Params.world_height);
			population.add(0, newAlgae);
			locationMatrix[newAlgae.y_coord][newAlgae.x_coord].inHere.add(newAlgae);
		}

	}

	/**
	 * @name oldLocationMatrix
	 * @description	initializes matrix with previous positions prior to time step
	 */
	private static void oldLocationInit(){
		Locations[][] oldLocations = new Locations[Params.world_height][Params.world_width];

		for(int row = 0; row < Params.world_height; row++){
			for(int col = 0; col < Params.world_width; col++){
				oldLocations[row][col] = new Locations();
				oldLocations[row][col].inHere = (HashSet<Critter>) locationMatrix[row][col].inHere.clone();
			}
		}
	}

	/**
	 * @name resolveEncounters
	 * @description resolves all encounters in the world
	 */
	private static void resolveEncounters(){
        inFight = true;         // set fighting
        for(int row = 0; row < Params.world_height; row++){
            for (int col = 0; col < Params.world_width; col++){
            	// extract all critters living in current cell
            	HashSet<Critter> livingInCell = new HashSet<Critter>(0);
            	HashSet<Critter> currentCell = locationMatrix[row][col].inHere;
            	for(Critter maybeAlive: currentCell){
            		if(maybeAlive.energy > 0){
            			livingInCell.add(maybeAlive);
            		}
            	}
                while (livingInCell.size() > 1){
                    Iterator<Critter> encounterIt = livingInCell.iterator();
                    Critter critterA = encounterIt.next();
                    Critter critterB = encounterIt.next();

                    boolean responseA = critterA.fight(critterB.toString());
                    boolean responseB = critterB.fight(critterA.toString());

                    if (critterA.fightMoved || critterB.fightMoved){
                        if (critterA.fightMoved && critterB.fightMoved){
                            livingInCell.remove(critterA);
                            livingInCell.remove(critterB);
                            continue;
                        }
                        else if (critterA.fightMoved){
                        	livingInCell.remove(critterA);
                        	continue;
                        }
                        else{
                        	livingInCell.remove(critterB);
                        	continue;
                        }
                    }

                    if (critterA.energy <= 0 || critterB.energy <= 0){
                        if (critterA.energy <= 0 && critterB.energy <=0){
                            livingInCell.remove(critterA);
                            livingInCell.remove(critterB);
                            continue;
                        }
                        else if (critterA.energy <= 0){
                        	livingInCell.remove(critterA);
                        	continue;
                        }
                        else{
                        	livingInCell.remove(critterB);
                        	continue;
                        }
                    }

                    if ((critterA.energy>0) && (critterB.energy>0) && (!critterA.fightMoved) && (!critterB.fightMoved)){
                        //calculate A's roll
                        int rollA;
                        int rollB;
                        if (responseA){
                            rollA = Critter.getRandomInt(critterA.energy+1);
                        }
                        else {
                            rollA = 0;
                        }

                        //calculate B's roll
                        if (responseB){
                            rollB = Critter.getRandomInt(critterB.energy+1);
                        }
                        else {
                            rollB = 0;
                        }

                        //compare rolls
                        if (rollA >= rollB){
                            critterA.energy += (critterB.energy / 2);
                            critterB.energy = 0;
                            livingInCell.remove(critterB);
                            continue;
                        }

                        else{
                            critterB.energy += (critterA.energy / 2);
                            critterA.energy = 0;
                            livingInCell.remove(critterA);
                            continue;
                        }
                    }

                }
            }
        }

        inFight = false;        // clear fighting
	}
	
	

    /**
     * topBottom  will create the top and bottom lines of the world in the display
     */
	private static void topBottom(){
		System.out.print("+");
		for (int i = 0; i < Params.world_width; i++){
			System.out.print("-");
		}
		System.out.println("+");
	}

    /**
     * displayWorld will display in the console the critter world, it utilizes the matrix CritterWorld
     */
	public static void displayWorld() {
		/*
		topBottom();        //header of the world
        for (int row = 0; row < Params.world_height; row++) {         //will go row by row outputting critter in location
            System.out.print("|");
            for (int col = 0; col < Params.world_width; col++) {
                if (!locationMatrix[row][col].inHere.isEmpty()){
                    Iterator<Critter> singleCritterIterator = locationMatrix[row][col].inHere.iterator();
                    System.out.print(singleCritterIterator.next().toString());
                }
                else{
                    System.out.print(" ");
                }
            }
            System.out.print("|");
            System.out.println("");
        }
		topBottom();
		*/
		
		Main.secondStage.setTitle("Critter World");
		Main.worldGrid = WorldView.drawInitialWorld();
		Scene worldScene = new Scene(Main.worldGrid, 800, 800);
		Main.secondStage.setScene(worldScene);
		Main.secondStage.show();
	}
	
}
