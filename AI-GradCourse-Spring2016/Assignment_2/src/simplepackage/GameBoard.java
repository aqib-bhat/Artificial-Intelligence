package simplepackage;

public class GameBoard {
	private int size;
	public enum DollType{EMPTY,WHITE,BLACK}
	private DollType[][] board; 
	private int gameRule = 5; // game Rule, number of consecutive dolls to win. 
	
	public GameBoard(int size) {
		this.size = size;
		board = new DollType[size][size];	
		initBoard();
	}
	
	public void initBoard() {
		for(int i=0; i<size; i++) {
			for(int j=0; j<size; j++){
				board[i][j] = DollType.EMPTY;
			}
		}
	}
	
	public DollType[][] getBoard() {
		return board;
	}
	
	public String setDoll(DollType dt, int x, int y) {
		if((x-1) < 0 || x > size || (y-1) < 0 || y > size) {			
			System.out.println("Wrong position! (out of range)");
			return "Wrong position! (out of range)";
		}
		
		if(board[x-1][y-1] == DollType.EMPTY) {
			board[x-1][y-1] = dt;
			return "Okay";
		} else {
			System.out.println("There is already another doll in that position!");
			return "There is already another doll in that position!";
		}
	}

	public DollType checkWinner() {
		//return DollType.EMPTY;
		//return DollType.BLACK;
		//return DollType.WHITE;
		int bCount = 0;
		int wCount = 0;
		
		//check horizontal
		for(int y=0; y<size; y++) {
			bCount = 0;
			wCount = 0;
			for(int x=0; x<size; x++) {
				if(board[x][y] == DollType.BLACK) {
					if(wCount != 0) wCount = 0;
					bCount++;
					if(bCount >= gameRule) break;
				} else if(board[x][y] == DollType.WHITE) {
					if(bCount != 0) bCount = 0;
					wCount++;
					if(wCount >= gameRule) break;
				} else if(board[x][y] == DollType.EMPTY) {
					bCount = 0;
					wCount = 0;
				}
			}
			if(wCount >= gameRule || bCount >= gameRule) break;
		}
		
		if(bCount >= gameRule) return DollType.BLACK;
		else if (wCount >= gameRule) return DollType.WHITE;
		
		
		//check vertical
		for(int x=0; x<size; x++) {
			bCount = 0;
			wCount = 0;
			for(int y=0; y<size; y++) {
				if(board[x][y] == DollType.BLACK) {
					if(wCount != 0) wCount = 0;
					bCount++;
					if(bCount >= gameRule) break;
				} else if(board[x][y] == DollType.WHITE) {
					if(bCount != 0) bCount = 0;
					wCount++;
					if(wCount >= gameRule) break;
				} else if(board[x][y] == DollType.EMPTY) {
					bCount = 0;
					wCount = 0;
				}
			}
			if(wCount >= gameRule || bCount >= gameRule) break;
		}
		
		if(bCount >= gameRule) return DollType.BLACK;
		else if (wCount >= gameRule) return DollType.WHITE;
		
		//check top-left to bottom-right diagonal
		//upper diagonal
		for(int x=0; x<size; x++) {
			bCount = 0;
			wCount = 0;
			for(int i=x, y=0; i<size; i++,y++) {
				if(board[i][y] == DollType.BLACK) {
					if(wCount != 0) wCount = 0;
					bCount++;
					if(bCount >= gameRule) break;
				} else if(board[i][y] == DollType.WHITE) {
					if(bCount != 0) bCount = 0;
					wCount++;
					if(wCount >= gameRule) break;
				} else if(board[i][y] == DollType.EMPTY) {
					bCount = 0;
					wCount = 0;
				}
			}
			if(wCount >= gameRule || bCount >= gameRule) break;
		}
		
		if(bCount >= gameRule) return DollType.BLACK;
		else if (wCount >= gameRule) return DollType.WHITE;
		
		//check top-left to bottom-right diagonal
		//lower diagonal
		for(int y=0; y<size; y++) {
			bCount = 0;
			wCount = 0;
			for(int x=0, i=y; i<size; i++,x++) {
				if(board[x][i] == DollType.BLACK) {
					if(wCount != 0) wCount = 0;
					bCount++;
					if(bCount >= gameRule) break;
				} else if(board[x][i] == DollType.WHITE) {
					if(bCount != 0) bCount = 0;
					wCount++;
					if(wCount >= gameRule) break;
				} else if(board[x][i] == DollType.EMPTY) {
					bCount = 0;
					wCount = 0;
				}
			}
			if(wCount >= gameRule || bCount >= gameRule) break;
		}
		
		if(bCount >= gameRule) return DollType.BLACK;
		else if (wCount >= gameRule) return DollType.WHITE;
		
		
		//check top-right to bottom-left diagonal
		//upper diagonal
		for(int x=(size-1); x>=0; x--) {
			bCount = 0;
			wCount = 0;
			for(int i=x, y=0; i>=0; i--,y++) {
				if(board[i][y] == DollType.BLACK) {
					if(wCount != 0) wCount = 0;
					bCount++;
					if(bCount >= gameRule) break;
				} else if(board[i][y] == DollType.WHITE) {
					if(bCount != 0) bCount = 0;
					wCount++;
					if(wCount >= gameRule) break;
				} else if(board[i][y] == DollType.EMPTY) {
					bCount = 0;
					wCount = 0;
				}
			}
			if(wCount >= gameRule || bCount >= gameRule) break;
		}
		
		if(bCount >= gameRule) return DollType.BLACK;
		else if (wCount >= gameRule) return DollType.WHITE;
		
		//check top-right to bottom-left diagonal
		//lower diagonal
		for(int x=0; x<size; x++) {
			bCount = 0;
			wCount = 0;
			for(int i=x, y=(size-1); i<size; i++,y--) {
				if(board[i][y] == DollType.BLACK) {
					if(wCount != 0) wCount = 0;
					bCount++;
					if(bCount >= gameRule) break;
				} else if(board[i][y] == DollType.WHITE) {
					if(bCount != 0) bCount = 0;
					wCount++;
					if(wCount >= gameRule) break;
				} else if(board[i][y] == DollType.EMPTY) {
					bCount = 0;
					wCount = 0;
				}
			}
			if(wCount >= gameRule || bCount >= gameRule) break;
		}
		
		if(bCount >= gameRule) return DollType.BLACK;
		else if (wCount >= gameRule) return DollType.WHITE;
		
		
		//no winner yet
		return DollType.EMPTY;
	}
}
