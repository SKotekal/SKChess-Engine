import java.util.*;
import java.io.*;

public class MoveGen 
{
	/*
	 * TODO - Need to deal with castling and castling restrictions.
	 */
	public static ArrayList<Move> gen(Board pos, int who2move)//+1 for white, -1 for black
	{
		ArrayList<Move> ret = new ArrayList<Move>();
		int piece = 0;
		int[] move = pos.getArr();
		for(int i = 0; i < 64; i++)
		{
			piece = pos.getPiece(i);
			if(piece*who2move > 0) //Piece matches with whose turn it is to move
			{
				if(Math.abs(piece) == 1) //Pawn
				{
					if(piece > 0) //White
					{
						if(ChessControl.whiteView)
						{
							if(Board.isValid(i+7) && i%8 != 0 && pos.getPiece(i+7) < 0) //Capture Up and to the left
							{
								ret.add(new Move(i, i+7, piece));
							}
							if(Board.isValid(i+9) && i%8 != 7 && pos.getPiece(i+9) < 0) //Capture Up and to the right
							{
								ret.add(new Move(i, i+9, piece));
							}
							if(Board.isValid(i+8) && pos.getPiece(i+8) == 0) //Move up 1
							{
								ret.add(new Move(i, i+8, piece));
							}
							if(Board.getRank(i) == 1) //Move up 2
							{
								if(pos.getPiece(i+16) == 0 && pos.getPiece(i+8) == 0)
								{
									ret.add(new Move(i, i+16, piece));
								}
							}
						}
						else
						{
							if(Board.isValid(i-7) && i%8 != 7 && pos.getPiece(i-7) < 0) //Up and to the left
							{
								ret.add(new Move(i, i-7, piece));
							}
							if(Board.isValid(i-9) && i%8 != 0 && pos.getPiece(i-9) < 0) //Up and to the right
							{
								ret.add(new Move(i, i-9, piece));
							}
							if(Board.isValid(i-8) && pos.getPiece(i-8) == 0) //Move up 1
							{
								ret.add(new Move(i, i-8, piece));
							}
							if(Board.getRank(i) == 6) //Move up 2
							{
								if(pos.getPiece(i-16) == 0 && pos.getPiece(i-8) == 0)
								{
									ret.add(new Move(i, i-16, piece));
								}
							}
						}
						
					}
					if(piece < 0) //Black
					{
						if(ChessControl.whiteView)
						{
							if(Board.isValid(i-7) && i%8 != 7 && pos.getPiece(i-7) > 0) //Up and to the left
							{
								ret.add(new Move(i, i-7, piece));
							}
							if(Board.isValid(i-9) && i%8 != 0 && pos.getPiece(i-9) > 0) //Up and to the right
							{
								ret.add(new Move(i, i-9, piece));
							}
							if(Board.isValid(i-8) && pos.getPiece(i-8) == 0) //Move up 1
							{
								ret.add(new Move(i, i-8, piece));
							}
							if(Board.getRank(i) == 6) //Move up 2
							{
								if(pos.getPiece(i-16) == 0 && pos.getPiece(i-8) == 0)
								{
									ret.add(new Move(i, i-16, piece));
								}
							}
						}
						else
						{
							if(Board.isValid(i+7) && i%8 != 0 && pos.getPiece(i+7) > 0) //Capture Up and to the left
							{
								ret.add(new Move(i, i+7, piece));
							}
							if(Board.isValid(i+9) && i%8 != 7 && pos.getPiece(i+9) > 0) //Capture Up and to the right
							{
								ret.add(new Move(i, i+9, piece));
							}
							if(Board.isValid(i+8) && pos.getPiece(i+8) == 0) //Move up 1
							{
								ret.add(new Move(i, i+8, piece));
							}
							if(Board.getRank(i) == 1) //Move up 2
							{
								if(pos.getPiece(i+16) == 0 && pos.getPiece(i+8) == 0)
								{
									ret.add(new Move(i, i+16, piece));
								}
							}
						}
					}
				}
				else if(Math.abs(piece) == 2) //Knight
				{
					if(Board.isValid(i+16+1) && pos.getPiece(i+16+1)*piece <= 0)
					{
						ret.add(new Move(i, i+16+1, piece));
					}
					if(Board.isValid(i+16-1) && pos.getPiece(i+16-1)*piece <= 0)
					{
						ret.add(new Move(i, i+16-1, piece));
					}
					if(Board.isValid(i-16+1) && pos.getPiece(i-16+1)*piece <= 0)
					{
						ret.add(new Move(i, i-16+1, piece));
					}
					if(Board.isValid(i-16-1) && pos.getPiece(i-16-1)*piece <= 0)
					{
						ret.add(new Move(i, i-16-1, piece));
					}
					if(Board.isValid(i+2+8) && pos.getPiece(i+2+8)*piece <= 0)
					{
						ret.add(new Move(i, i+2+8, piece));
					}
					if(Board.isValid(i-2+8) && pos.getPiece(i-2+8)*piece <= 0)
					{
						ret.add(new Move(i, i-2+8, piece));
					}
					if(Board.isValid(i+2-8) && pos.getPiece(i+2-8)*piece <= 0)
					{
						ret.add(new Move(i, i+2-8, piece));
					}
					if(Board.isValid(i-2-8) && pos.getPiece(i-2-8)*piece <= 0)
					{
						ret.add(new Move(i, i-2-8, piece));
					}
				} 
				else if(Math.abs(piece) == 3) //Bishop
				{
					for(int a = 1; a < 8; a++) //Up and Right
					{
						if(!Board.isValid(i+a*9))
						{
							break;
						}
						if(pos.getPiece(i+a*9)*piece > 0)
						{
							break;
						}
						if(pos.getPiece(i+a*9)*piece < 0)
						{
							ret.add(new Move(i, i+a*9, piece));
							break;
						}
						ret.add(new Move(i, i+a*9, piece));
						
						if((i+a*9)%8 == 7)
						{
							break;
						}
					}
					for(int a = 1; a < 8; a++) //Down and Left
					{
						if(!Board.isValid(i-a*9))
						{
							break;
						}
						if(pos.getPiece(i-a*9)*piece > 0)
						{
							break;
						}
						if(pos.getPiece(i-a*9)*piece < 0)
						{
							ret.add(new Move(i, i-a*9, piece));
							break;
						}
						ret.add(new Move(i, i-a*9, piece));

						if((i+a*9)%8 == 0)
						{
							break;
						}
						
					}
					for(int a = 1; a < 8; a++) //Up and Left
					{
						if(!Board.isValid(i+a*7))
						{
							break;
						}
						if(pos.getPiece(i+a*7)*piece > 0)
						{
							break;
						}
						if(pos.getPiece(i+a*7)*piece < 0)
						{
							ret.add(new Move(i, i+a*7, piece));
							break;
						}
						ret.add(new Move(i, i+a*7, piece));

						if((i+a*7)%8 == 0)
						{
							break;
						}
						
					}
					for(int a = 1; a < 8; a++) //Down and Right
					{
						if(!Board.isValid(i-a*7))
						{
							break;
						}
						if(pos.getPiece(i-a*7)*piece > 0)
						{
							break;
						}
						if(pos.getPiece(i-a*7)*piece < 0)
						{
							ret.add(new Move(i, i-a*7, piece));
							break;
						}
						ret.add(new Move(i, i-a*7, piece));

						
						if((i+a*7)%8 == 7)
						{
							break;
						}
						
					}
				}
				else if(Math.abs(piece) == 4) //Rook
				{
					for(int a = 1; a < 8; a++) //Up
					{
						if(!Board.isValid(i+a*8))
						{
							break;
						}
						if(pos.getPiece(i+a*8)*piece > 0)
						{
							break;
						}
						if(pos.getPiece(i+a*8)*piece < 0)
						{
							ret.add(new Move(i, i+a*8, piece));
							break;
						}
						ret.add(new Move(i, i+a*8, piece));
					}
					for(int a = 1; a < 8; a++) //Down
					{
						if(!Board.isValid(i-a*8))
						{
							break;
						}
						if(pos.getPiece(i-a*8)*piece > 0)
						{
							break;
						}
						if(pos.getPiece(i-a*8)*piece < 0)
						{
							ret.add(new Move(i, i-a*8, piece));
							break;
						}
						ret.add(new Move(i, i-a*8, piece));
					}
					for(int a = 1; a < 8; a++) //Right
					{
						if(!Board.isValid(i+a))
						{
							break;
						}
						if(pos.getPiece(i+a)*piece > 0)
						{
							break;
						}
						if(pos.getPiece(i+a)*piece < 0)
						{
							ret.add(new Move(i, i+a, piece));
							break;
						}
						ret.add(new Move(i, i+a, piece));
						if((i+a)%8 == 7)
						{
							break;
						}
						
					}
					for(int a = 1; a < 8; a++) //Left
					{
						if(!Board.isValid(i-a))
						{
							break;
						}
						if(pos.getPiece(i-a)*piece > 0)
						{
							break;
						}
						if(pos.getPiece(i-a)*piece < 0)
						{
							ret.add(new Move(i, i-a, piece));
							break;
						}
						ret.add(new Move(i, i-a, piece));
						if((i-a)%8 == 0)
						{
							break;
						}
					}
				}
				else if(Math.abs(piece) == 5) //Queen
				{
					for(int a = 1; a < 8; a++) //Up
					{
						if(!Board.isValid(i+a*8))
						{
							break;
						}
						if(pos.getPiece(i+a*8)*piece > 0)
						{
							break;
						}
						if(pos.getPiece(i+a*8)*piece < 0)
						{
							ret.add(new Move(i, i+a*8, piece));
							break;
						}
						ret.add(new Move(i, i+a*8, piece));
					}
					for(int a = 1; a < 8; a++) //Down
					{
						if(!Board.isValid(i-a*8))
						{
							break;
						}
						if(pos.getPiece(i-a*8)*piece > 0)
						{
							break;
						}
						if(pos.getPiece(i-a*8)*piece < 0)
						{
							ret.add(new Move(i, i-a*8, piece));
							break;
						}
						ret.add(new Move(i, i-a*8, piece));
					}
					for(int a = 1; a < 8; a++) //Right
					{
						if(!Board.isValid(i+a))
						{
							break;
						}
						if(pos.getPiece(i+a)*piece > 0)
						{
							break;
						}
						if(pos.getPiece(i+a)*piece < 0)
						{
							ret.add(new Move(i, i+a, piece));
							break;
						}
						ret.add(new Move(i, i+a, piece));
						if((i+a)%8 == 7)
						{
							break;
						}
						
					}
					for(int a = 1; a < 8; a++) //Left
					{
						if(!Board.isValid(i-a))
						{
							break;
						}
						if(pos.getPiece(i-a)*piece > 0)
						{
							break;
						}
						if(pos.getPiece(i-a)*piece < 0)
						{
							ret.add(new Move(i, i-a, piece));
							break;
						}
						ret.add(new Move(i, i-a, piece));
						if((i-a)%8 == 0)
						{
							break;
						}
					}
					for(int a = 1; a < 8; a++) //Up and Right
					{
						if(!Board.isValid(i+a*9))
						{
							break;
						}
						if(pos.getPiece(i+a*9)*piece > 0)
						{
							break;
						}
						if(pos.getPiece(i+a*9)*piece < 0)
						{
							ret.add(new Move(i, i+a*9, piece));
							break;
						}
						ret.add(new Move(i, i+a*9, piece));
						
						if((i+a*9)%8 == 7)
						{
							break;
						}
					}
					for(int a = 1; a < 8; a++) //Down and Left
					{
						if(!Board.isValid(i-a*9))
						{
							break;
						}
						if(pos.getPiece(i-a*9)*piece > 0)
						{
							break;
						}
						if(pos.getPiece(i-a*9)*piece < 0)
						{
							ret.add(new Move(i, i-a*9, piece));
							break;
						}
						ret.add(new Move(i, i-a*9, piece));

						if((i+a*9)%8 == 0)
						{
							break;
						}
						
					}
					for(int a = 1; a < 8; a++) //Up and Left
					{
						if(!Board.isValid(i+a*7))
						{
							break;
						}
						if(pos.getPiece(i+a*7)*piece > 0)
						{
							break;
						}
						if(pos.getPiece(i+a*7)*piece < 0)
						{
							ret.add(new Move(i, i+a*7, piece));
							break;
						}
						ret.add(new Move(i, i+a*7, piece));

						if((i+a*7)%8 == 0)
						{
							break;
						}
						
					}
					for(int a = 1; a < 8; a++) //Down and Right
					{
						if(!Board.isValid(i-a*7))
						{
							break;
						}
						if(pos.getPiece(i-a*7)*piece > 0)
						{
							break;
						}
						if(pos.getPiece(i-a*7)*piece < 0)
						{
							ret.add(new Move(i, i-a*7, piece));
							break;
						}
						ret.add(new Move(i, i-a*7, piece));

						
						if((i+a*7)%8 == 7)
						{
							break;
						}
						
					}
				}
				if(Math.abs(piece) == 6) //King
				{
					if(Board.isValid(i+1) && i%8 != 7 && !(pos.getPiece(i+1)*piece > 0))
					{
						ret.add(new Move(i, i+1, piece));
					}
					if(Board.isValid(i+9) && i%8 != 7 && !(pos.getPiece(i+9)*piece > 0))
					{
						ret.add(new Move(i, i+9, piece));
					}
					if(Board.isValid(i+8) && !(pos.getPiece(i+8)*piece > 0))
					{
						ret.add(new Move(i, i+8, piece));
					}
					if(Board.isValid(i+7) && i%8 != 0 && !(pos.getPiece(i+7)*piece > 0))
					{
						ret.add(new Move(i, i+7, piece));
					}
					if(Board.isValid(i-1) && i%8 != 0 &&  !(pos.getPiece(i-1)*piece > 0))
					{
						ret.add(new Move(i, i-1, piece));
					}
					if(Board.isValid(i-7) && i%8 != 7 && !(pos.getPiece(i-7)*piece > 0))
					{
						ret.add(new Move(i, i-7, piece));
					}
					if(Board.isValid(i-8) && !(pos.getPiece(i-8)*piece > 0))
					{
						ret.add(new Move(i, i-8, piece));
					}
					if(Board.isValid(i-9) && i%8 != 0 &&  !(pos.getPiece(i-9)*piece > 0))
					{
						ret.add(new Move(i, i-9, piece));
					}
				}
			}
		}
		
		return ret;
	}
	
	public static ArrayList<Move> pieceGen(Board pos, int i, int piece)
	{
		ArrayList<Move> ret = new ArrayList<Move>();
		if(Math.abs(piece) == 1) //Pawn
		{
			if(piece > 0) //White
			{
				if(ChessControl.whiteView)
				{
					if(Board.isValid(i+7) && i%8 != 0 && pos.getPiece(i+7) < 0) //Capture Up and to the left
					{
						ret.add(new Move(i, i+7, piece));
					}
					if(Board.isValid(i+9) && i%8 != 7 && pos.getPiece(i+9) < 0) //Capture Up and to the right
					{
						ret.add(new Move(i, i+9, piece));
					}
					if(Board.isValid(i+8) && pos.getPiece(i+8) == 0) //Move up 1
					{
						ret.add(new Move(i, i+8, piece));
					}
					if(Board.getRank(i) == 1) //Move up 2
					{
						if(pos.getPiece(i+16) == 0 && pos.getPiece(i+8) == 0)
						{
							ret.add(new Move(i, i+16, piece));
						}
					}
				}
				else
				{
					if(Board.isValid(i-7) && i%8 != 7 && pos.getPiece(i-7) < 0) //Up and to the left
					{
						ret.add(new Move(i, i-7, piece));
					}
					if(Board.isValid(i-9) && i%8 != 0 && pos.getPiece(i-9) < 0) //Up and to the right
					{
						ret.add(new Move(i, i-9, piece));
					}
					if(Board.isValid(i-8) && pos.getPiece(i-8) == 0) //Move up 1
					{
						ret.add(new Move(i, i-8, piece));
					}
					if(Board.getRank(i) == 6) //Move up 2
					{
						if(pos.getPiece(i-16) == 0 && pos.getPiece(i-8) == 0)
						{
							ret.add(new Move(i, i-16, piece));
						}
					}
				}
				
			}
			if(piece < 0) //Black
			{
				if(ChessControl.whiteView)
				{
					if(Board.isValid(i-7) && i%8 != 7 && pos.getPiece(i-7) > 0) //Up and to the left
					{
						ret.add(new Move(i, i-7, piece));
					}
					if(Board.isValid(i-9) && i%8 != 0 && pos.getPiece(i-9) > 0) //Up and to the right
					{
						ret.add(new Move(i, i-9, piece));
					}
					if(Board.isValid(i-8) && pos.getPiece(i-8) == 0) //Move up 1
					{
						ret.add(new Move(i, i-8, piece));
					}
					if(Board.getRank(i) == 6) //Move up 2
					{
						if(pos.getPiece(i-16) == 0 && pos.getPiece(i-8) == 0)
						{
							ret.add(new Move(i, i-16, piece));
						}
					}
				}
				else
				{
					if(Board.isValid(i+7) && i%8 != 0 && pos.getPiece(i+7) > 0) //Capture Up and to the left
					{
						ret.add(new Move(i, i+7, piece));
					}
					if(Board.isValid(i+9) && i%8 != 7 && pos.getPiece(i+9) > 0) //Capture Up and to the right
					{
						ret.add(new Move(i, i+9, piece));
					}
					if(Board.isValid(i+8) && pos.getPiece(i+8) == 0) //Move up 1
					{
						ret.add(new Move(i, i+8, piece));
					}
					if(Board.getRank(i) == 1) //Move up 2
					{
						if(pos.getPiece(i+16) == 0 && pos.getPiece(i+8) == 0)
						{
							ret.add(new Move(i, i+16, piece));
						}
					}
				}
			}
		}
		else if(Math.abs(piece) == 2) //Knight
		{
			if(Board.isValid(i+16+1) && pos.getPiece(i+16+1)*piece <= 0)
			{
				ret.add(new Move(i, i+16+1, piece));
			}
			if(Board.isValid(i+16-1) && pos.getPiece(i+16-1)*piece <= 0)
			{
				ret.add(new Move(i, i+16-1, piece));
			}
			if(Board.isValid(i-16+1) && pos.getPiece(i-16+1)*piece <= 0)
			{
				ret.add(new Move(i, i-16+1, piece));
			}
			if(Board.isValid(i-16-1) && pos.getPiece(i-16-1)*piece <= 0)
			{
				ret.add(new Move(i, i-16-1, piece));
			}
			if(Board.isValid(i+2+8) && pos.getPiece(i+2+8)*piece <= 0)
			{
				ret.add(new Move(i, i+2+8, piece));
			}
			if(Board.isValid(i-2+8) && pos.getPiece(i-2+8)*piece <= 0)
			{
				ret.add(new Move(i, i-2+8, piece));
			}
			if(Board.isValid(i+2-8) && pos.getPiece(i+2-8)*piece <= 0)
			{
				ret.add(new Move(i, i+2-8, piece));
			}
			if(Board.isValid(i-2-8) && pos.getPiece(i-2-8)*piece <= 0)
			{
				ret.add(new Move(i, i-2-8, piece));
			}
		} 
		else if(Math.abs(piece) == 3) //Bishop
		{
			for(int a = 1; a < 8; a++) //Up and Right
			{
				if(!Board.isValid(i+a*9))
				{
					break;
				}
				if(pos.getPiece(i+a*9)*piece > 0)
				{
					break;
				}
				if(pos.getPiece(i+a*9)*piece < 0)
				{
					ret.add(new Move(i, i+a*9, piece));
					break;
				}
				ret.add(new Move(i, i+a*9, piece));
				
				if((i+a*9)%8 == 7)
				{
					break;
				}
			}
			for(int a = 1; a < 8; a++) //Down and Left
			{
				if(!Board.isValid(i-a*9))
				{
					break;
				}
				if(pos.getPiece(i-a*9)*piece > 0)
				{
					break;
				}
				if(pos.getPiece(i-a*9)*piece < 0)
				{
					ret.add(new Move(i, i-a*9, piece));
					break;
				}
				ret.add(new Move(i, i-a*9, piece));

				if((i+a*9)%8 == 0)
				{
					break;
				}
				
			}
			for(int a = 1; a < 8; a++) //Up and Left
			{
				if(!Board.isValid(i+a*7))
				{
					break;
				}
				if(pos.getPiece(i+a*7)*piece > 0)
				{
					break;
				}
				if(pos.getPiece(i+a*7)*piece < 0)
				{
					ret.add(new Move(i, i+a*7, piece));
					break;
				}
				ret.add(new Move(i, i+a*7, piece));

				if((i+a*7)%8 == 0)
				{
					break;
				}
				
			}
			for(int a = 1; a < 8; a++) //Down and Right
			{
				if(!Board.isValid(i-a*7))
				{
					break;
				}
				if(pos.getPiece(i-a*7)*piece > 0)
				{
					break;
				}
				if(pos.getPiece(i-a*7)*piece < 0)
				{
					ret.add(new Move(i, i-a*7, piece));
					break;
				}
				ret.add(new Move(i, i-a*7, piece));

				
				if((i+a*7)%8 == 7)
				{
					break;
				}
				
			}
		}
		else if(Math.abs(piece) == 4) //Rook
		{
			for(int a = 1; a < 8; a++) //Up
			{
				if(!Board.isValid(i+a*8))
				{
					break;
				}
				if(pos.getPiece(i+a*8)*piece > 0)
				{
					break;
				}
				if(pos.getPiece(i+a*8)*piece < 0)
				{
					ret.add(new Move(i, i+a*8, piece));
					break;
				}
				ret.add(new Move(i, i+a*8, piece));
			}
			for(int a = 1; a < 8; a++) //Down
			{
				if(!Board.isValid(i-a*8))
				{
					break;
				}
				if(pos.getPiece(i-a*8)*piece > 0)
				{
					break;
				}
				if(pos.getPiece(i-a*8)*piece < 0)
				{
					ret.add(new Move(i, i-a*8, piece));
					break;
				}
				ret.add(new Move(i, i-a*8, piece));
			}
			for(int a = 1; a < 8; a++) //Right
			{
				if(!Board.isValid(i+a))
				{
					break;
				}
				if(pos.getPiece(i+a)*piece > 0)
				{
					break;
				}
				if(pos.getPiece(i+a)*piece < 0)
				{
					ret.add(new Move(i, i+a, piece));
					break;
				}
				ret.add(new Move(i, i+a, piece));
				if((i+a)%8 == 7)
				{
					break;
				}
				
			}
			for(int a = 1; a < 8; a++) //Left
			{
				if(!Board.isValid(i-a))
				{
					break;
				}
				if(pos.getPiece(i-a)*piece > 0)
				{
					break;
				}
				if(pos.getPiece(i-a)*piece < 0)
				{
					ret.add(new Move(i, i-a, piece));
					break;
				}
				ret.add(new Move(i, i-a, piece));
				if((i-a)%8 == 0)
				{
					break;
				}
			}
		}
		else if(Math.abs(piece) == 5) //Queen
		{
			for(int a = 1; a < 8; a++) //Up
			{
				if(!Board.isValid(i+a*8))
				{
					break;
				}
				if(pos.getPiece(i+a*8)*piece > 0)
				{
					break;
				}
				if(pos.getPiece(i+a*8)*piece < 0)
				{
					ret.add(new Move(i, i+a*8, piece));
					break;
				}
				ret.add(new Move(i, i+a*8, piece));
			}
			for(int a = 1; a < 8; a++) //Down
			{
				if(!Board.isValid(i-a*8))
				{
					break;
				}
				if(pos.getPiece(i-a*8)*piece > 0)
				{
					break;
				}
				if(pos.getPiece(i-a*8)*piece < 0)
				{
					ret.add(new Move(i, i-a*8, piece));
					break;
				}
				ret.add(new Move(i, i-a*8, piece));
			}
			for(int a = 1; a < 8; a++) //Right
			{
				if(!Board.isValid(i+a))
				{
					break;
				}
				if(pos.getPiece(i+a)*piece > 0)
				{
					break;
				}
				if(pos.getPiece(i+a)*piece < 0)
				{
					ret.add(new Move(i, i+a, piece));
					break;
				}
				ret.add(new Move(i, i+a, piece));
				if((i+a)%8 == 7)
				{
					break;
				}
				
			}
			for(int a = 1; a < 8; a++) //Left
			{
				if(!Board.isValid(i-a))
				{
					break;
				}
				if(pos.getPiece(i-a)*piece > 0)
				{
					break;
				}
				if(pos.getPiece(i-a)*piece < 0)
				{
					ret.add(new Move(i, i-a, piece));
					break;
				}
				ret.add(new Move(i, i-a, piece));
				if((i-a)%8 == 0)
				{
					break;
				}
			}
			for(int a = 1; a < 8; a++) //Up and Right
			{
				if(!Board.isValid(i+a*9))
				{
					break;
				}
				if(pos.getPiece(i+a*9)*piece > 0)
				{
					break;
				}
				if(pos.getPiece(i+a*9)*piece < 0)
				{
					ret.add(new Move(i, i+a*9, piece));
					break;
				}
				ret.add(new Move(i, i+a*9, piece));
				
				if((i+a*9)%8 == 7)
				{
					break;
				}
			}
			for(int a = 1; a < 8; a++) //Down and Left
			{
				if(!Board.isValid(i-a*9))
				{
					break;
				}
				if(pos.getPiece(i-a*9)*piece > 0)
				{
					break;
				}
				if(pos.getPiece(i-a*9)*piece < 0)
				{
					ret.add(new Move(i, i-a*9, piece));
					break;
				}
				ret.add(new Move(i, i-a*9, piece));

				if((i+a*9)%8 == 0)
				{
					break;
				}
				
			}
			for(int a = 1; a < 8; a++) //Up and Left
			{
				if(!Board.isValid(i+a*7))
				{
					break;
				}
				if(pos.getPiece(i+a*7)*piece > 0)
				{
					break;
				}
				if(pos.getPiece(i+a*7)*piece < 0)
				{
					ret.add(new Move(i, i+a*7, piece));
					break;
				}
				ret.add(new Move(i, i+a*7, piece));

				if((i+a*7)%8 == 0)
				{
					break;
				}
				
			}
			for(int a = 1; a < 8; a++) //Down and Right
			{
				if(!Board.isValid(i-a*7))
				{
					break;
				}
				if(pos.getPiece(i-a*7)*piece > 0)
				{
					break;
				}
				if(pos.getPiece(i-a*7)*piece < 0)
				{
					ret.add(new Move(i, i-a*7, piece));
					break;
				}
				ret.add(new Move(i, i-a*7, piece));

				
				if((i+a*7)%8 == 7)
				{
					break;
				}
				
			}
		}
		if(Math.abs(piece) == 6) //King
		{
			if(Board.isValid(i+1) && i%8 != 7 && !(pos.getPiece(i+1)*piece > 0))
			{
				ret.add(new Move(i, i+1, piece));
			}
			if(Board.isValid(i+9) && i%8 != 7 && !(pos.getPiece(i+9)*piece > 0))
			{
				ret.add(new Move(i, i+9, piece));
			}
			if(Board.isValid(i+8) && !(pos.getPiece(i+8)*piece > 0))
			{
				ret.add(new Move(i, i+8, piece));
			}
			if(Board.isValid(i+7) && i%8 != 0 && !(pos.getPiece(i+7)*piece > 0))
			{
				ret.add(new Move(i, i+7, piece));
			}
			if(Board.isValid(i-1) && i%8 != 0 &&  !(pos.getPiece(i-1)*piece > 0))
			{
				ret.add(new Move(i, i-1, piece));
			}
			if(Board.isValid(i-7) && i%8 != 7 && !(pos.getPiece(i-7)*piece > 0))
			{
				ret.add(new Move(i, i-7, piece));
			}
			if(Board.isValid(i-8) && !(pos.getPiece(i-8)*piece > 0))
			{
				ret.add(new Move(i, i-8, piece));
			}
			if(Board.isValid(i-9) && i%8 != 0 &&  !(pos.getPiece(i-9)*piece > 0))
			{
				ret.add(new Move(i, i-9, piece));
			}
		}
		return ret;
	}
}
