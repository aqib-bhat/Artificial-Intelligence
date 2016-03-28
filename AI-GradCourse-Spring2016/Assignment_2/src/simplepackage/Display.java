package simplepackage;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import simplepackage.GameBoard.DollType;

class Jframe extends JFrame{
	
	private int xLength, yLength, gridSize, dollSize, boardSize, gap;
	public GameBoard.DollType[][] board; 
	
	public Jframe(){
	}
	
	public Jframe(int boxlen, int boxwid, int gridSize, int boardSize, GameBoard.DollType[][] board, String title){
		gap = 30;
		xLength = boxlen + gap*2;
		yLength = boxwid + gap*2;
		this.gridSize = gridSize;				
		this.boardSize = boardSize;
		this.board = board;
		dollSize = 10;
		
		Color colorBackground = new Color(224,180,54);

		this.setSize(xLength,yLength);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBackground(colorBackground);
		
		this.setTitle(title);
		
		setVisible(true);
	}
	
	@Override 
	public void paint(Graphics g) { 
		super.paint(g); 
				
		// draw the lines
		g.setColor(Color.black);
		for(int j=0; j <= boardSize; j++) {
			g.drawLine(gap + j*gridSize, gap + 0, gap + j*gridSize, gap + boardSize*gridSize);
		}		
		
		for(int k=0; k <= boardSize; k++) {
			g.drawLine(gap + 0, gap + k*gridSize, gap + boardSize*gridSize, gap + k*gridSize);
		}
		
		//draw the dolls
		Graphics2D g2 = (Graphics2D) g;
	    g2.setStroke(new BasicStroke(2));
	    
	    for(int i=0; i<this.boardSize; i++) {
	    	for(int j=0; j<this.boardSize; j++) {
	    		if(this.board[i][j] != DollType.EMPTY) {
	    			if(this.board[i][j] == DollType.WHITE) {
	    				g2.drawOval(gap+(i+1)*gridSize-(dollSize+5), gap+(j+1)*gridSize-(dollSize+5), dollSize, dollSize);
	    			} else if(this.board[i][j] == DollType.BLACK) {
	    				g2.drawOval(gap+(i+1)*gridSize-(dollSize+5), gap+(j+1)*gridSize-(dollSize+5), dollSize, dollSize);
	    				g2.fillOval(gap+(i+1)*gridSize-(dollSize+5), gap+(j+1)*gridSize-(dollSize+5), dollSize, dollSize);	    				
	    			}
	    		}
	    	}
	    }
	}
}

public class Display { 
	public Display(){}
	
	public void init(int boxlen, int boxwid, int gridSize, int boardSize, GameBoard.DollType[][] board, String title) {
		jf = new Jframe(boxlen, boxwid, gridSize, boardSize, board, title);
	}
	
	public Display(int boxlen, int boxwid, int gridSize, int boardSize, GameBoard.DollType[][] board, String title) {
		jf = new Jframe(boxlen, boxwid, gridSize, boardSize, board, title);
	}
	
	public void nextFrame(GameBoard.DollType[][] pBoard) throws InterruptedException {
		jf.board = pBoard;
		Color colorBackground = new Color(224,180,54);
		jf.setBackground(colorBackground);		
		jf.repaint();
	}	
	private Jframe jf;
}
