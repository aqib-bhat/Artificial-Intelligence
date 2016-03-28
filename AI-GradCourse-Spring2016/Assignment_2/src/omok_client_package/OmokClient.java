package omok_client_package;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import omok_client_package.GameBoard.DollType;

public class OmokClient {
    public OmokClient() {}
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedReader in;
    PrintWriter out;
    GameBoard.DollType myDollType = GameBoard.DollType.EMPTY;

    public static void main(String[] args) throws Exception {
        OmokClient client = new OmokClient();
        client.run();
    }
    
    private void run() throws IOException {
    	String sr=null;
    	String input=null;
        // Make connection and initialize streams
        String serverAddress = "127.0.0.1";
        Socket socket = new Socket(serverAddress, 8000);
        in = new BufferedReader(new InputStreamReader(
            socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        //Display
        int boardSize = 15; //number of grid		
		int gridSize = 20;
		int XboardLength = boardSize * gridSize;
		int YboardLength = boardSize * gridSize + gridSize;
		
		GameBoard gameBoard = new GameBoard(boardSize);		
		Display canvas = new Display(XboardLength, YboardLength, gridSize, boardSize, gameBoard.getBoard(), "Client Board");
		OmokAI omokAI = new OmokAI();
			
		try {
			canvas.nextFrame(gameBoard.getBoard());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // Process all messages from server, according to the protocol.
        while (true) {	
        	input = in.readLine();
        	System.out.println("Received Server Message: " + input);
        	
        	if (input.startsWith("WELCOME ")) {
        		String contstr = input.substring(8);
        		canvas.setTitle(contstr);
        		if(contstr.equals(GameBoard.DollType.BLACK.toString())) {
        			myDollType = GameBoard.DollType.BLACK;
        		} else if (contstr.equals(GameBoard.DollType.WHITE.toString())) {
        			myDollType = GameBoard.DollType.WHITE;
        		}
        	} else if (input.startsWith("ENTER ")) {
        		System.out.println("Waiting for a piece position (e.g. 2,3):");       
        			
        		
        		////////////////////////////////////////////////
        		//select next piece location //////////////////
        		//and then send the location to the server /// 
        		//e.g. sr = "2,3" for the location x=2, y=3 //
        		////////////////////////////////////////////
        		if(myDollType == GameBoard.DollType.WHITE){
        		  sr = omokAI.getNextMove(gameBoard.getBoard(), myDollType);
        		} else {
        		  sr = br.readLine();
        		}
         		
        		// sr = br.readLine();
        		       		
        		out.println(sr);
        	} else if (input.startsWith("WIN ")) {
        		System.out.println("Game Ends. Systems ends.");
        		System.exit(1);
        	} else if (input.startsWith("VALID_MOVE ")) {
        		//set the doll
        		String contstr = input.substring(11);
        		String[] tockens = contstr.split(",");    		
        		DollType turn;
        		if(tockens[0].equals(DollType.BLACK.toString())) {
        			turn = DollType.BLACK;
        		} else turn = DollType.WHITE;
        		
    			String rtnStr = gameBoard.setDoll(turn, Integer.parseInt(tockens[1]), Integer.parseInt(tockens[2])); 
    			if(!rtnStr.equals("Okay")) {
    		    	System.out.println(rtnStr);
    				continue;
    			}		
    			try {
    				canvas.nextFrame(gameBoard.getBoard());
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}			
        	}        	
        }
    }
}