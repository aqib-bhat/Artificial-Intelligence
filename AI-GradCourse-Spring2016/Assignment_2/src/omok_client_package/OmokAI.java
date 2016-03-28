package omok_client_package;

import constants.NumericConstants;
import constants.StringConstants;
import util.OmokUtil;

public class OmokAI {
	
	/**
	 * @param board
	 * @param myDollType
	 * @return
	 */
	public String getNextMove(GameBoard.DollType[][] board, GameBoard.DollType myDollType) {
      String nextMove = StringConstants.EMPTY_STRING;

	  // Find best location for next piece based on the current board
      // Need to scan the board.
      
	  // Minimax algorithm with points given based on a heuristic
	  /*
	   * 1. First we find out about the existing situation of the board.
	   * 2. For each prospective line we have to assign points.
	   * 3. If there is any line with length 3, we have to stop its growth.
	   * 4. Otherwise, we can try to build on any of our own line with the most length.
	   */
      Result resultH = getDangerousHorizontalLines(board, myDollType);
      Result resultV = getDangerousVerticalLines(board, myDollType);
      if(resultH.isFound()){
    	  nextMove = resultH.getLocation();
      } else if (resultV.isFound()){
    	  nextMove = resultV.getLocation();  
      } else {
    	  nextMove = getFirstEmptyLocation(board);
      }
      
	  return nextMove;
	  //e.g. return "2,3" for the location x=2, y=3
	}
	
	/**
	 * @param board
	 * @return
	 */
	public String getFirstEmptyLocation(GameBoard.DollType[][] board){
		// Scan the board and return the first position found empty
		StringBuilder firstEmptyLoc = new StringBuilder();
		for (int column = 0; column < 15; column++) {
			for(int row = 0; row < 15; row++){
				if (board[column][row] == GameBoard.DollType.EMPTY){
					firstEmptyLoc.append(Integer.toString(column+1));
					firstEmptyLoc.append(",");
					firstEmptyLoc.append(Integer.toString(row+1));
					System.out.println("firstEmptyLoc: " + firstEmptyLoc.toString());
					return firstEmptyLoc.toString();
		    	}				
			}
	    }
		return firstEmptyLoc.toString();
	}
	
	/**
	 * @param board
	 * @param p_DollType
	 * @return
	 */
	public Result getDangerousHorizontalLines(GameBoard.DollType[][] board, GameBoard.DollType p_DollType){
		Result result = new Result();
		GameBoard.DollType opp_DollType = OmokUtil.getOppositeDollType(p_DollType);
		// Scan both from left to right as well as from right to left
		// DANGER: There is a sequence of 3 points and there:
		// 1. are either 2 empty cells in front of that, OR
		// 2. is an empty cell followed by a point of the sameType
	    for(int row = 0; row < 15; row++ ){
	        // scan from L to R
	    	for(int cL = 0; cL < 11; cL++){
	    		if(board[cL][row] == opp_DollType && 
	    				board[cL+1][row] == opp_DollType && 
	    				board[cL+2][row] == opp_DollType &&
	    				board[cL+3][row] == GameBoard.DollType.EMPTY &&
	    				( (board[cL+4][row] == GameBoard.DollType.EMPTY) || (board[cL+4][row] == opp_DollType) )){
	    			result.setFound(true);
	    			result.setLocation(Integer.toString(cL+3+1) + "," + Integer.toString(row+1));
	    			System.out.println("Returning LtoR: " + result.getLocation());
	    			return result;
	    		}
	    	}
	        // scan from R to L
	    	for(int c = 14; c > 3; c--){
	    		if(board[c][row] == opp_DollType && 
	    				board[c-1][row] == opp_DollType && 
	    				board[c-2][row] == opp_DollType &&
	    				board[c-3][row] == GameBoard.DollType.EMPTY &&
	    				( (board[c-4][row] == GameBoard.DollType.EMPTY || board[c-4][row] == opp_DollType) )){
	    			result.setFound(true);
	    			result.setLocation(Integer.toString(c-3+1) + "," + Integer.toString(row+1));
	    			System.out.println("Returning RtoL: " + result.getLocation());
	    			return result;
	    		}
	    	}

	    }
		return result;
	}
	
	/**
	 * @param board
	 * @param p_DollType
	 * @return
	 */
	public Result getDangerousVerticalLines(GameBoard.DollType[][] board, GameBoard.DollType p_DollType){
		Result result = new Result();
		GameBoard.DollType opp_DollType = OmokUtil.getOppositeDollType(p_DollType);
		// Scan both from top to bottom as well as from bottom to top
		// DANGER: There is a sequence of 3 points and there:
		// 1. are either 2 empty cells in front of that, OR
		// 2. is an empty cell followed by a point of the sameType
	    for(int column = 0; column < 15; column++ ){
	        // scan from top to bottom
	    	for(int rT = 0; rT < 11; rT++){
	    		if(board[column][rT] == opp_DollType && 
	    				board[column][rT+1] == opp_DollType && 
	    				board[column][rT+2] == opp_DollType &&
	    				board[column][rT+3] == GameBoard.DollType.EMPTY &&
	    				( (board[column][rT+4] == GameBoard.DollType.EMPTY) || (board[column][rT+4] == opp_DollType) )){
	    			result.setFound(true);
	    			result.setLocation(Integer.toString(column+1) + "," + Integer.toString(rT+3+1));
	    			System.out.println("Returning LtoR: " + result.getLocation());
	    			return result;
	    		}
	    	}
	        // scan from bottom to top
	    	for(int rB = 14; rB > 3; rB--){
	    		if(board[column][rB] == opp_DollType && 
	    				board[column][rB-1] == opp_DollType && 
	    				board[column][rB-2] == opp_DollType &&
	    				board[column][rB-3] == GameBoard.DollType.EMPTY &&
	    				( (board[column][rB-4] == GameBoard.DollType.EMPTY || board[column][rB-4] == opp_DollType) )){
	    			result.setFound(true);
	    			result.setLocation(Integer.toString(column+1) + "," + Integer.toString(rB-3+1));
	    			System.out.println("Returning RtoL: " + result.getLocation());
	    			return result;
	    		}
	    	}

	    }
		return result;		
	}
	
	public void getPromisingDaigonalLines(GameBoard.DollType[][] board, GameBoard.DollType p_DollType){
		
	}
	
	/**
	 * @author addy
	 *
	 */
	private class Result {
		private boolean found;
		private String location;
		public boolean isFound() {
			return found;
		}
		public void setFound(boolean found) {
			this.found = found;
		}
		public String getLocation() {
			return location;
		}
		public void setLocation(String location) {
			this.location = location;
		}
		
		Result(){
			setFound(false);
			setLocation(StringConstants.EMPTY_STRING);
		}
	}
	
	/**
	 * @author addy
	 *
	 */
	private class Line {
		private String startingLocation, endingLocation;
		public String getStartingLocation() {
			return startingLocation;
		}
		public void setStartingLocation(String startingLocation) {
			this.startingLocation = startingLocation;
		}
		public String getEndingLocation() {
			return endingLocation;
		}
		public void setEndingLocation(String endingLocation) {
			this.endingLocation = endingLocation;
		}
		public int getLength() {
			return length;
		}
		public void setLength(int length) {
			this.length = length;
		}
		int length;
		Line(){
			setStartingLocation(StringConstants.EMPTY_STRING);
			setEndingLocation(StringConstants.EMPTY_STRING);
			// this.startingLocation = this.endingLocation = StringConstants.EMPTY_STRING;
			setLength(NumericConstants.INT_ZERO);
			// this.length = NumericConstants.INT_ZERO;
		}
		
	}
	
}
