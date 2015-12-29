import java.util.*;
import java.io.*;

public class Board //Square centric board with 0x88 implementation
{
	private static final int x88 = Integer.valueOf("88", 16);
	private int[] board;
	private String[] pieceEncode = {"", "p", "n", "b", "r", "q", "k"};
	private int[] numPiece = new int[13]; //+1,+2,+3,+4,+5,+6 is White; -1,-2,-3,-4,-5,-6 is Black; 0 is neutral
												 // 7, 8, 9,10,11,12 is White;  5, 4, 3, 2, 1, 0 is Black; 6 is neutral
	
	public Board(String fen, char delim, boolean whiteView)
	{
		board = new int[128]; //Follows x88 board convention
		
		if(whiteView)
		{
			int rank = 7;
			int file = 0;
			for(int i = 0; i < fen.length(); i++) //Fills board based on FEN input
			{
				if(fen.charAt(i) == delim)
				{
					rank--;
					file = 0;
					continue;
				}
				
				if(fen.charAt(i) > '0' && fen.charAt(i) < '9')
				{
					file += Integer.valueOf(fen.substring(i, i+1));
					continue;
				}
				
				if(fen.charAt(i) >= 'a' && fen.charAt(i) <= 'z')
				{
					board[16*rank+file] = -1*Arrays.asList(pieceEncode).indexOf(fen.substring(i, i+1));
					numPiece[6-Arrays.asList(pieceEncode).indexOf(fen.substring(i, i+1))]++;
				}
				else
				{
					board[16*rank+file] = Arrays.asList(pieceEncode).indexOf(fen.substring(i, i+1).toLowerCase());
					numPiece[6+Arrays.asList(pieceEncode).indexOf(fen.substring(i, i+1))]++;
				}
				file++;
			}
		}
		else
		{
			int rank = 0;
			int file = 7;
			for(int i = 0; i < fen.length(); i++) //Fills board based on FEN input
			{
				if(fen.charAt(i) == delim)
				{
					rank++;
					file = 7;
					continue;
				}
				
				if(fen.charAt(i) > '0' && fen.charAt(i) < '9')
				{
					file += Integer.valueOf(fen.substring(i, i+1));
					continue;
				}
				
				if(fen.charAt(i) >= 'a' && fen.charAt(i) <= 'z')
				{
					board[16*rank+file] = -1*Arrays.asList(pieceEncode).indexOf(fen.substring(i, i+1));
					numPiece[6-Arrays.asList(pieceEncode).indexOf(fen.substring(i, i+1))]++;
				}
				else
				{
					board[16*rank+file] = Arrays.asList(pieceEncode).indexOf(fen.substring(i, i+1).toLowerCase());
					numPiece[6+Arrays.asList(pieceEncode).indexOf(fen.substring(i, i+1))]++;
				}
				file--;
			}
		}
	}
	public Board(int[] arr) //Makes board based on array input
	{
		board = new int[arr.length];
		for(int i = 0; i < arr.length; i++)
		{
			board[i] = arr[i];
			numPiece[6+arr[i]]++;
		}
	}
	public int[] getArr() //Returns board array
	{
		int[] ret = new int[board.length];
		for(int i = 0; i < board.length; i++)
		{
			ret[i] = board[i];
		}
		return ret;
	}
	public void putPiece(int piece, int sq) //Places piece onto board
	{
		board[convert(sq)] = piece;
	}
	public static boolean isValid(int x8x8) //Checks if square is on the board
	{
		return (convert(x8x8) & x88) == 0;
	}
	public int getPiece(int x8x8) //Returns piece or 0 on requested square
	{
		return board[convert(x8x8)];
	}
	public static int convert(int x8x8) //Converts 8x8 number into x88 number
	{
		return x8x8 + (x8x8 & ~7);
	}
	public static int getFile(int x8x8) //Returns file for 8x8 number
	{
		return convert(x8x8) & 7;
	}
	public static int getRank(int x8x8) //Returns rank for 8x8 number
	{
		return convert(x8x8) >> 4;
	}
	public double eval(int who2move) //-1 for black, +1 for white
	{
		return who2move*(200*(numPiece[12]-numPiece[0]) + 9*(numPiece[11]-numPiece[1]) + 5*(numPiece[10]-numPiece[2]) + 3*(numPiece[9]-numPiece[3] + numPiece[8]-numPiece[4])
				+ 1*(numPiece[7]-numPiece[5]));
	}
	public Board move(Move m) //Makes requested move and returns a new board. Original board is unchanged.
	{
		int[] a = this.getArr();
		int piece = a[Board.convert(m.getFrom())];
		a[Board.convert(m.getFrom())] = 0;
		a[Board.convert(m.getTo())] = piece;
		return new Board(a);
		
	}
	public void selfMove(Move m) //Makes board on itself. 
	{
		int piece = board[Board.convert(m.getFrom())];
		board[Board.convert(m.getFrom())] = 0;
		board[Board.convert(m.getTo())] = piece;
	}
	public String toString() //Prints board
	{
		
		String ret = "";
		String temp = "";
		for(int i = 0; i < 128; i += 16)
		{
			for(int j = 0; j < 8; j++)
			{
				temp += "\t" + board[i+j];
			}
			ret = temp + "\n" + ret;
			temp = "";
		}
		return "\n" + ret;
	}
}
