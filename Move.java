import java.util.*;
import java.io.*;

/*
 * Object that holds three int values: Two 8x8 values for the from and to squares
 * and another value for the piece. -6..-1 is BK to BP and 1..6 is WP to WK
 */
public class Move implements Comparable<Move>
{
	private int from;
	private int to;
	private int piece;
	private String[] encode = {"BK", "BQ", "BR", "BB", "BN", "BP", "", "WP", "WN", "WB", "WR", "WQ", "WK"};
	public Move(int x8x8From, int x8x8To, int p)
	{
		/*if(ChessControl.whiteView)
		{*/
			from = x8x8From;
			to = x8x8To;
		/*}
		else
		{
			from = (7-Board.getFile(x8x8From)) + ((7-Board.getRank(x8x8From))*8);
			to = (7-Board.getFile(x8x8To)) + ((7-Board.getRank(x8x8To))*8);
		}*/
		piece = p;
	}
	/*public static Move bView(Move m)
	{
		int f = (7-Board.getFile(m.getFrom())) + ((7-Board.getRank(m.getFrom()))*8); 
		int t = (7-Board.getFile(m.getTo())) + ((7-Board.getRank(m.getTo()))*8); 
		int p = m.getIntPiece();
		
		Move ret = new Move(f, t, p);
		return ret;
	}*/
	public int getFrom()
	{
		return from;
	}
	public int getTo()
	{
		return to;
	}
	public void setFrom(int f)
	{
		from = f;
	}
	public void setTo(int t)
	{
		to = t;
	}
	public void setPiece(int pie)
	{
		piece = pie;
	}
	public int getIntPiece()
	{
		return piece;
	}
	public String getPiece()
	{
		return encode[6+piece];
	}
	public String toString()
	{
		return (encode[6+piece] + ": " + ((char) (from%8 + 'a')) + "" + (from/8+1) + "-" + ((char) (to%8+'a')) + "" + (to/8+1));
	}
	
	@Override
	public int compareTo(Move arg0) 
	{
		if(from < arg0.getFrom())
		{
			return -1;
		}
		else if(from > arg0.getFrom())
		{
			return 1;
		}
		else
		{
			if(to < arg0.getTo())
			{
				return -1;
			}
			else if(to > arg0.getTo())
			{
				return 1;
			}
			else
			{
				return ((Integer) piece).compareTo(((Integer) arg0.getIntPiece()));
			}
		}
	}
}
