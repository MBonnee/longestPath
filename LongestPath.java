package longestPath;

import java.io.PrintStream;
import java.util.Scanner;

import ui.LabyrinthUserInterface;
import ui.UIAuxiliaryMethods;
import ui.UserInterfaceFactory;

public class LongestPath {

	static final int WIDTH = 32, HEIGHT = 24;

	static final Coordinates LEFT = new Coordinates(-1, 0);
	static final Coordinates RIGHT = new Coordinates(1, 0);
	static final Coordinates UP = new Coordinates(0, -1);
	static final Coordinates DOWN = new Coordinates(0, 1);

	CoordinateRow path;
	CoordinateRow walls;
	CoordinateRow longestPath;
	Coordinates direction;
	Coordinates finish;
	
	LabyrinthUserInterface ui;
	PrintStream out;

	LongestPath() {
		UserInterfaceFactory.enableLowResolution(true);

		ui = UserInterfaceFactory.getLabyrinthUI(WIDTH, HEIGHT);
		out = new PrintStream(System.out);

		path = new CoordinateRow();
		walls = new CoordinateRow();
		longestPath = new CoordinateRow();
		direction = LEFT;
		finish = null;
	}

	// prints the number of coordinates it takes
	void printLongestPath() {
		ui.clear();
		
		for(int i = 0; i < walls.numberOfCoordinates; i++){
			ui.place(walls.coordinateRow[i].xCoordinate, walls.coordinateRow[i].yCoordinate, ui.WALL);
		}
		
		for(int i = 0; i < longestPath.numberOfCoordinates; i++){
			ui.place(longestPath.coordinateRow[i].xCoordinate, longestPath.coordinateRow[i].yCoordinate, ui.PATH);
		}
		
		ui.encircle(finish.xCoordinate, finish.yCoordinate);
		ui.showChanges();
		ui.printf("The longest path is %d", longestPath.length());
	}
	
	void endGame(){
		
		if (path.coordinateRow[path.numberOfCoordinates-1].isEqual(finish)){
			if(path.numberOfCoordinates > longestPath.numberOfCoordinates){
				longestPath.numberOfCoordinates = 0;
				for(int i = 0; i < path.numberOfCoordinates; i++){
					longestPath.addCoordinate(path.coordinateRow[i]);
				}
			}
		}
	}
	
	void board(){
		ui.clear();
		
		for(int i = 0; i < walls.numberOfCoordinates; i++){
			ui.place(walls.coordinateRow[i].xCoordinate, walls.coordinateRow[i].yCoordinate, ui.WALL);
		}
		
		for(int i = 0; i < path.numberOfCoordinates; i++){
			ui.place(path.coordinateRow[i].xCoordinate, path.coordinateRow[i].yCoordinate, ui.PATH);
		}
		
		ui.encircle(finish.xCoordinate, finish.yCoordinate);
		ui.wait(100);
		ui.showChanges();
	}

	void changeDirection() {
		
		if (direction == LEFT){
			direction = DOWN;
		} else if (direction == DOWN){
			direction = RIGHT;
		} else if (direction == RIGHT){
			direction = UP;
		} else {
			direction = LEFT;
		}
	}

	void move() {
		// adds 1 to x or y to move the snake
		endGame();
		board();
		

		for (int i = 0; i < 4; i++) {

			Coordinates shiftCoordinate = path.coordinateRow[path.numberOfCoordinates-1].copy();
			shiftCoordinate.shift(direction);

			if (!shiftCoordinate.collisionDetection(path, walls)) {
				path.addCoordinate(shiftCoordinate);
				move();
			}
			changeDirection();
		}
		
		path.removeCoordinate();
		
	}

	void buildLevel(Scanner wallCoordinatesScanner) {

		while (wallCoordinatesScanner.hasNext()) {

			int xCoordinate = wallCoordinatesScanner.nextInt();
			int yCoordinate = wallCoordinatesScanner.nextInt();

			Coordinates coordinate = new Coordinates(xCoordinate, yCoordinate);

			walls.addCoordinate(coordinate);
		}

	}

	void readStartingCoordinate(Scanner startingCoordinateScanner) {
		int xCoordinate = startingCoordinateScanner.nextInt();
		int yCoordinate = startingCoordinateScanner.nextInt();

		Coordinates start = new Coordinates(xCoordinate, yCoordinate);
		path.addCoordinate(start);
	}

	void readFinishCoordinate(Scanner finishCoordinateScanner) {
		int xCoordinate = finishCoordinateScanner.nextInt();
		int yCoordinate = finishCoordinateScanner.nextInt();

		finish = new Coordinates(xCoordinate, yCoordinate);

	}

	void start() {
		UIAuxiliaryMethods.askUserForInput();
		Scanner input = new Scanner(System.in);
		input.useDelimiter("=");

		String startingCoordinate = input.next();
		Scanner startingCoordinateScanner = new Scanner(startingCoordinate);
		readStartingCoordinate(startingCoordinateScanner);

		String finishCoordinate = input.next();
		Scanner finishCoordinateScanner = new Scanner(finishCoordinate);
		readFinishCoordinate(finishCoordinateScanner);

		String wallCoordinates = input.next();
		Scanner wallCoordinatesScanner = new Scanner(wallCoordinates);
		buildLevel(wallCoordinatesScanner);
		
		move();
		
		printLongestPath();
	}

	public static void main(String[] argv) {
		new LongestPath().start();
	}
}
