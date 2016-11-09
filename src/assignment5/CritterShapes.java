/* CRITTERS GUI CritterShapes.java
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
 * This class provides methods to generate the required critter shapes for the project
 */
package assignment5;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class CritterShapes {
	/**
	 * Returns a square to fit a cell of the world, of the color provided as parameter
	 * @param squareFill
	 * @param squareStroke
	 * @return a square of the cell size and of the specified color
	 */
	public static javafx.scene.shape.Rectangle getSquare(javafx.scene.paint.Color squareFill, javafx.scene.paint.Color squareStroke ){
		Rectangle square = new Rectangle(Main.cellSize, Main.cellSize, squareFill);
		square.setStroke(squareStroke);
		square.setStrokeWidth(3);
		return square;
	}
	
	/**
	 * Returns a circle to fit a cell of the world, of the specified color
	 * @param circleFill
	 * @param circleStroke
	 * @return a circle of the cell size and of the specified color
	 */
	public static javafx.scene.shape.Circle getCircle(javafx.scene.paint.Color circleFill, javafx.scene.paint.Color circleStroke){
		Circle circle = new Circle(Main.cellSize / 2, circleFill);
		circle.setStroke(circleStroke);
		circle.setStrokeWidth(3);
		return circle;
	}
	
	/**
	 * Returns a triangle to fit a cell of the world, of the specified color
	 * @param triangleFill
	 * @param triangleStroke
	 * @return a triangle of the cell size and of the specified color
	 */
	public static javafx.scene.shape.Polygon getTriangle(javafx.scene.paint.Color triangleFill, javafx.scene.paint.Color triangleStroke){
		Polygon triangle = new Polygon();
		
		triangle.getPoints().addAll(new Double[]{
			((double) Main.cellSize / 2.0), 0.0,
		    0.0, (double)Main.cellSize,
		    (double) Main.cellSize, (double) Main.cellSize });
		
		triangle.setFill(triangleFill);
		triangle.setStroke(triangleStroke);
		triangle.setStrokeWidth(3);
		
		return triangle;
	}
	
	/**
	 * Returns a star to fit a cell of the world, of the specified color
	 * @param starFill
	 * @param starStroke
	 * @return a star of the cell size and of the specified color
	 */
	public static javafx.scene.shape.Polygon getStar(javafx.scene.paint.Color starFill, javafx.scene.paint.Color starStroke){
		Polygon star = new Polygon();
		
		star.getPoints().addAll(new Double[]{
			0.0, ((double) Main.cellSize * 0.3),									// 1
		    ((double) Main.cellSize * 0.35), ((double) Main.cellSize * 0.3),		// 2
		    ((double) Main.cellSize * 0.5), 0.0,									// 3		
			((double) Main.cellSize * 0.65), ((double) Main.cellSize * 0.3),		// 4		
			(double) Main.cellSize, ((double) Main.cellSize * 0.3), 				// 5		
			((double) Main.cellSize * 0.75), ((double) Main.cellSize * 0.5),		// 6		
			(double) Main.cellSize, (double) Main.cellSize,							// 7		
			((double) Main.cellSize * 0.5), ((double) Main.cellSize * 0.65),		// 8					
			0.0, (double) Main.cellSize,											// 9		
			((double) Main.cellSize * 0.25), ((double) Main.cellSize * 0.5)});		// 10
																		
		star.setFill(starFill);
		star.setStroke(starStroke);
		star.setStrokeWidth(3);
		
		return star;
	}
	
	/**
	 * Returns a diamond to fit a cell of the world, of the specified color
	 * @param diamondFill
	 * @param diamondStroke
	 * @return a diamond of the cell size and of the specified color
	 */
	public static javafx.scene.shape.Polygon getDiamond(javafx.scene.paint.Color diamondFill, javafx.scene.paint.Color diamondStroke){
		Polygon diamond = new Polygon();
		
		diamond.getPoints().addAll(new Double[]{
			((double) Main.cellSize * 0.5), 0.0,						// 1
		    (double) Main.cellSize, ((double) Main.cellSize * 0.5),		// 2
		    ((double) Main.cellSize * 0.5), (double) Main.cellSize,		// 3		
			0.0, ((double) Main.cellSize * 0.5)});						// 4
																		
		diamond.setFill(diamondFill);
		diamond.setStroke(diamondStroke);
		diamond.setStrokeWidth(3);
		
		return diamond;
	}
		
}
