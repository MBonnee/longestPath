package longestPath;

public class CoordinateRow {
	
	static final int MAX_COORDINATES = 1000;

	int xCoordinate;
	int yCoordinate;
	
	Coordinates[] coordinateRow;
	int numberOfCoordinates;
	
	CoordinateRow(){

		coordinateRow = new Coordinates[MAX_COORDINATES];
		numberOfCoordinates = 0;

	}
	
	void addCoordinate(Coordinates coordinates){

		coordinateRow[numberOfCoordinates] = coordinates;
		numberOfCoordinates++;
	}
	
	void removeCoordinate(){
		numberOfCoordinates -= 1;
	}
	
	int length(){
		
		return numberOfCoordinates;
	}
	
	boolean isInRow(Coordinates coordinates){
	
		for(int i = 0; i < numberOfCoordinates; i++){
			if(coordinateRow[i] == coordinates){
				return true;
			}
		}
		
		return false;
	}
	

}
