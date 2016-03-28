package simplepackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import simplepackage.Display;
import simplepackage.GameBoard.DollType;


public class MainControl {
	
	public static void main(String[] args) {
		boolean isGameContinue = true;		
		Socket BSocket = null;
		Socket WSocket = null;
		
		BufferedReader reader = null;
		PrintWriter writer = null;
		DollType turn = DollType.BLACK;
		
		ServerSocket server = null;
		BufferedReader BReader = null;
		PrintWriter BWriter = null;
		BufferedReader WReader = null;
		PrintWriter WWriter = null;
		
		BufferedReader CurrentReader = null;
		PrintWriter CurrentWriter = null;
		
		System.out.println("Server started");

		try {
			server = new ServerSocket(8000);
			System.out.println("Waiting for the client Black");
			BSocket = server.accept();
			System.out.println("Client Black connected");
			System.out.println("Waiting for the client White");
			WSocket = server.accept();
			System.out.println("Client White connected");
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		try {
			BReader = new BufferedReader(
					new InputStreamReader(BSocket.getInputStream()));
			BWriter = new PrintWriter(BSocket.getOutputStream(), true);
			WReader = new BufferedReader(
					new InputStreamReader(WSocket.getInputStream()));
			WWriter = new PrintWriter(WSocket.getOutputStream(), true);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		BWriter.println("WELCOME " + DollType.BLACK.toString());
		WWriter.println("WELCOME " + DollType.WHITE.toString());

		String msg = null;
		String sr = null;
		
		int boardSize = 15;//10; //number of grid
		
		int gridSize = 20;
		int XboardLength = boardSize * gridSize;
		int YboardLength = boardSize * gridSize + gridSize;

		turn = DollType.BLACK;
		
		GameBoard gameBoard = new GameBoard(boardSize);		
		Display canvas = new Display(XboardLength, YboardLength, gridSize, boardSize, gameBoard.getBoard(), "Server Board");

		try {
			canvas.nextFrame(gameBoard.getBoard());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(isGameContinue) {
			
			if(turn == DollType.BLACK) {
				System.out.println("Enter a doll position for BLACK (e.g. 2,3):");
				BWriter.println("ENTER Enter a doll position for BLACK (e.g. 2,3):");
				WWriter.println("MESSAGE Black turn. Wait.");
				CurrentReader = BReader;
				CurrentWriter = BWriter;
				try {
					sr = BReader.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("IO error trying to read your doll position!");
					BWriter.println("MESSAGE IO error trying to read your doll position! ");
					System.exit(1);
				}
			} else {
				System.out.println("Enter a doll position for WHITE (e.g. 2,3):");
				WWriter.println("ENTER Enter a doll osition for WHITE (e.g. 2,3)");
				BWriter.println("MESSAGE White turn. Wait.");
				CurrentReader = WReader;
				CurrentWriter = WWriter;
				try {
					sr = WReader.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("IO error trying to read your doll position!");
					WWriter.println("MESSAGE IO error trying to read your doll position! ");
					System.exit(1);
				}
			}	
			
			String[] tockens = sr.split(",");
			
			//wrong input or error handling
			if(tockens.length != 2) {
				System.out.println("Wrong input (format)!");
				CurrentWriter.println("Wrong input (format)!");
				continue;
			}
						
			try { 
				Integer.parseInt(tockens[0]); 
		    } catch(NumberFormatException e) { 
		    	System.out.println("Wrong input (not a number)!");
		    	CurrentWriter.println("MESSAGE Wrong input (not a number)!");
		        continue;
		    }
			
			try { 
				Integer.parseInt(tockens[1]); 
		    } catch(NumberFormatException e) { 
		    	System.out.println("Wrong input (not a number)!");
		    	CurrentWriter.println("MESSAGE Wrong input (not a number)!");
		        continue;
		    }

			//set the doll
			String rtnStr = gameBoard.setDoll(turn, Integer.parseInt(tockens[0]), Integer.parseInt(tockens[1])); 
			if(!rtnStr.equals("Okay")) {
		    	System.out.println(rtnStr);
		    	CurrentWriter.println(rtnStr);
				continue;
			}
			
			try {
				canvas.nextFrame(gameBoard.getBoard());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//send board update information through the sockets
			String turnStr = turn.toString();
			System.out.println("Turn string: " + turnStr);
			BWriter.println("VALID_MOVE "+turnStr+","+tockens[0]+","+tockens[1]);
			WWriter.println("VALID_MOVE "+turnStr+","+tockens[0]+","+tockens[1]);
			
			//make alternative turn
			if(turn == DollType.BLACK) {
				turn = DollType.WHITE;
			} else if(turn == DollType.WHITE) {
				turn = DollType.BLACK;
			}
			
			//check winner
			DollType winner = gameBoard.checkWinner();
			if(winner != DollType.EMPTY) {
				if(winner == DollType.BLACK) {
					System.out.println("BLACK Wins");
					BWriter.print("WIN " + winner.toString());
					WWriter.print("WIN " + winner.toString());
					
				} else if (winner == DollType.WHITE) {
					System.out.println("White Wins");
					BWriter.print("WIN " + winner.toString());
					WWriter.print("WIN " + winner.toString());
				}

				BWriter.println(" ");
				WWriter.println(" ");
							    
				isGameContinue = false;
			}
		}	
		if (server != null) {
			try {
				server.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.exit(1);
	}
}
