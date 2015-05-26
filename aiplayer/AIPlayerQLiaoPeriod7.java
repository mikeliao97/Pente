/* Name: Qin Liao
 * Time Spent: 1 hour and 30 minutes
 * Reflection: My AI is pretty simple but it works. I think it can win 
 * against  many AI's that my fellow classmates created. It simply 
 * uses recursion to check for which move has the longest line of pieces.
 * It is always on the offensive. It only attacks from the middle, where
 * there are most spots to expand. Overall, I am quite pleased with my AI.
 * I think it has the potential to win some games.
 * 
 * 
 */


package aiplayer;
import java.util.ArrayList;

import pente.AIPlayer;
import pente.Location;

public class AIPlayerQLiaoPeriod7 extends AIPlayer {

	// Change these to match your AIPlayer info
	private static String name = "Senioritis";
	private static String iconFile = "black.png";
	
	public AIPlayerQLiaoPeriod7(int id) {
		super(iconFile, name, id);
	}

	// Adding helper methods like this is a good idea
	public boolean isInBounds(int[][] array, int row, int col) {
		return row >= 0 && row < array.length && col >= 0 && col < array[0].length;
	}

	@Override
	public Location makeMove(int[][] idArray, int moveCount) {

		// If we are Player 1 and this is our first move, play in the center of the board
		if (moveCount == 1) {
			return new Location(idArray.length/2, idArray[0].length/2);
		}
		// If we are Player 1 and this is our second move, play randomly outside the center box
		else if (moveCount == 3) {
			
			return new Location(idArray.length/2 - 3, idArray.length/2 - 3);
		}
		
		
		
			
			
			// If empty, see if there are any neighbors here that are ours
			//if idArray[row][col] == 0 that means it found a piece that is ours?
			int row = idArray.length/2;
			int col = idArray.length/2;
			ArrayList list = new ArrayList();
			
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					if (isInBounds(idArray, row + i, col + j)) {
						if (idArray[row + i][col + j] == getID()){
							System.out.println(getID());	
							list.add(new Location(row + i, col + j));
						}
					}
				}
			}
			

			if (list.size() > 0) {
				Location loc = (Location) list.remove(0);
				System.out.println(getName() + " found one of our piece at" + loc.getRow() + ", " + loc.getCol() + ")");
				
				
				//Now that we found one of our piece
				//we use recrusion to find which direction the piece is already spread out
				int max = 0;
				String bestMove = "top"; //just initialize the bestMove to top
				
				//top
				int top = numberConsecutive(idArray, loc.getRow(), loc.getCol(), 0, -1);
				if(top > max){
					max = top;
					bestMove = "top";
				}
				
				//top right
				int top_right = numberConsecutive(idArray, loc.getRow(), loc.getCol(), 1, -1);
				if(top_right > max){
					max = top_right;
					bestMove = "top_right";
				}
				
				//right
				int right = numberConsecutive(idArray, loc.getRow(), loc.getCol(), 1, 0);
				if(right > max){
					max = right;
					bestMove = "right";
				}
				
				
				//bottom right
				int bottom_right = numberConsecutive(idArray, loc.getRow(), loc.getCol(), 1, 1);
				if(bottom_right > max){
					max = bottom_right;
					bestMove = "bottom_right";
				}
				
				
				//bottom
				int bottom = numberConsecutive(idArray, loc.getRow(), loc.getCol(), 0, 1);
				if(bottom > max){
					max = bottom;
					bestMove = "bottom";
				}
				
				
				//bottom left
				int bottom_left = numberConsecutive(idArray, loc.getRow(), loc.getCol(), -1, 1);
				if(bottom_left > max){
					max = bottom_left;
					bestMove = "bottom_left";
				}
				
				
				//left
				int left = numberConsecutive(idArray, loc.getRow(), loc.getCol(), -1, 0);
				if(left > max){
					max = left;
					bestMove = "left";
				}
			
				//top left			
				int top_left = numberConsecutive(idArray, loc.getRow(), loc.getCol(), -1, -1);
				if(top_left > max){
					max = top_left;
					bestMove = "top_left";
				}
				
				switch (bestMove.toLowerCase()) {
	            case "top":
	                return new Location(loc.getRow() + max * 0, loc.getCol() + max * -1);
	        
	            case "top_right":
	                return new Location(loc.getRow() + max * 1, loc.getCol() + max * -1);
	         
	            case "right":
	                return new Location(loc.getRow() + max * 1, loc.getCol() + max * 0);
	            
	            case "bottom_right":
	                return new Location(loc.getRow() + max * 1, loc.getCol() + max * 1);
	              
	            case "bottom":
	                return new Location(loc.getRow() + max * 0, loc.getCol() + max * 1);
	               
	            case "bottom_left":
	                return new Location(loc.getRow() + max * -1, loc.getCol() + max * 1);
	               
	            case "left":
	                return new Location(loc.getRow() + max * -1, loc.getCol() + max * 0);
	            case "top_left":
	                return new Location(loc.getRow() + max * -1, loc.getCol() + max * -1);
	            default: 	              
	                return null;
	        }
				
				
			}
		//place is it within the overall close 9 boxes
			row = idArray.length/2;
			col = idArray.length/2;
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					if (isInBounds(idArray, row + i, col + j)) {
						if (idArray[row + i][col + j] == 0){						
							return new Location(row + i, col + j);
						}
					}
				}
			}

			return null;
	}
	
	public int numberConsecutive(int[][] idArray, int row,int col, int x, int y){
		//if it found one of our piece then keep going according to x and y
		System.out.println("This is idArray:");
		System.out.println(idArray[row][col]);
		System.out.println(row);
		System.out.println(col);
		if(idArray[row][col] == getID()){ 
			return 1 + numberConsecutive(idArray, row + x, col + y, x, y);
		}
		else if(idArray[row][col] != getID() && idArray[row][col] != 0){
			//meaning your opponent blocked i
			return -100;
		}
		else{ //if it can't find anything then whatever
			return 0;
		}
		
		
	}
	
	public boolean isOutsideStartBox(int[][] idArray, int row, int col) {
		return Math.abs(row - idArray.length/2) > 2 && Math.abs(col - idArray[0].length/2) > 2;
	}

}
