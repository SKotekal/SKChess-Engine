import java.util.*;
import java.io.*;

public class ChessControl
{	
	//Black to move.
	//  4r1k1/8/8/8/8/8/5PPP/6K1 /
	
	public static GUI gui;
	public static Board board;
	public static boolean whiteView;
	public static int who2move;
	public static int ply;
	
	public static void main(String[] args) throws IOException, InterruptedException
	{
		gui = new GUI();
		//Input FEN + 1 Char Delim
		Scanner scan = new Scanner(System.in);
		System.out.print("FEN + 1 Char Delim?: ");
		String input = scan.nextLine();
		StringTokenizer stk;
		
		//Keep going until requested to exit
		while(!(input.toUpperCase().equals("DONE")))
		{
			stk = new StringTokenizer(input);
			String FEN = stk.nextToken();
			String delim = stk.nextToken();
			
			//Who to move?
			System.out.print("Who to move? (B or W): ");
			String player = scan.nextLine();
			who2move = 0;
			if(player.toUpperCase().equals("B"))
			{
				who2move = -1;
			}
			else
			{
				who2move = 1;
			}
			
			//Ply?
			System.out.print("Ply? (Even Number): ");
			ply = Integer.parseInt(scan.nextLine());
			
			//View?
			System.out.print("View? (B or W): ");
			whiteView = true;
			if(scan.nextLine().toUpperCase().equals("B"))
			{
				whiteView = false;
			}
 
			//Init board and gui
			board = new Board(FEN, delim.charAt(0), whiteView);	
			
			gui.set(board.getArr(), whiteView, who2move);

			//Start engine
			ChessControl.engine();
			
			//Prompt for next data
			System.out.println();
			System.out.print("FEN + 1 Char Delim?: ");
			input = scan.nextLine();
		}
		System.out.println("EXITED");
		System.exit(0);
	}

	public static void engine() throws IOException, InterruptedException
	{
		Thread.sleep(500);
		System.out.println("\t------------------------ORIG-----------------------------");
		System.out.println(board);
		
		who2move = gui.getWho2Move();
		
		//Init variables for engine 
		ArrayList<Move> possible = MoveGen.gen(board, who2move);
		double max = Integer.MIN_VALUE;
		Board bMove = new Board(board.getArr());
		Move eng = new Move(-1, -1, 0);
		int movePiece = 0;
		
		//Start negamax algorithm
		for(int i = 0; i < possible.size(); i++)
		{
			double score = -1*negaMax(board.move(possible.get(i)), ply-1, -1*who2move);
			
			//Find best move
			if(score > max)
			{
				max = score;
				bMove = new Board(board.move(possible.get(i)).getArr());
				eng = possible.get(i);
				movePiece = board.getArr()[Board.convert(possible.get(i).getFrom())];
			}
		}
		
		//Print best move and board position
		System.out.println("\t------------------------MOVE-----------------------------");
		System.out.println(bMove);
		System.out.println(eng);
		
		ChessControl.controlMove(eng, movePiece);
		gui.switchMove(-1*who2move);	
	}
	
	//Negamax algorithm
	public static double negaMax(Board pos, int depth, int who2move)
	{
		//Search is complete: return evaluation of end position
		if(depth == 0)
		{
			double s = pos.eval(who2move);
			return s;
		}
		
		//Find best move from this position
		double max = Integer.MIN_VALUE;
		ArrayList<Move> possible = MoveGen.gen(pos, who2move); //Generate all possible moves for current player
		
		for(int i = 0; i < possible.size(); i++)
		{
			//Recurse on each move. Need to multiply by -1 as player changes
			double score = -1*negaMax(pos.move(possible.get(i)), depth-1, -1*who2move);
			
			//Find score of best move
			if(score > max)
			{
				max = score;
			}
		}
		return max;
	}
	
	//Makes move on both GUI and board
	public static void controlMove(Move m, int piece) throws IOException, InterruptedException
	{
		gui.GUIMove(m, piece);
		board.selfMove(m);
	}
	
	//Places piece as requested from GUI buttons
	public static void placePiece(int piece, int x8x8) throws IOException
	{
		board.putPiece(piece, x8x8);
		gui.GUIPlacePiece(piece, x8x8);
	}

}
