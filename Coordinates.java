package longestPath;

public class Coordinates {


	
	int xCoordinate;
	int yCoordinate;
	
	
	Coordinates(int x, int y){
		xCoordinate = x;
		yCoordinate = y;
	}
	
	void shift(Coordinates c){
		xCoordinate += c.xCoordinate;
		yCoordinate += c.yCoordinate; 
	}
	
	Coordinates copy(){
		Coordinates c = new Coordinates(xCoordinate, yCoordinate);
		
		return c;
		
	}
	
	boolean isEqual(Coordinates c){
		
		if(c.xCoordinate == xCoordinate && c.yCoordinate == yCoordinate){
			return true;
		}
		
		return false;
	}
	
	// detects if new coordinate collides with a wall
	boolean collisionDetection(CoordinateRow path, CoordinateRow wall){
		
		for (int i = 0; i < wall.numberOfCoordinates; i++){
			if (isEqual(wall.coordinateRow[i])){
				return true;
			}
		}
		
		for (int i = 0; i < path.numberOfCoordinates; i++){
			if (isEqual(path.coordinateRow[i])){
				return true;
			}
		}
		
		return false;
	}
}
