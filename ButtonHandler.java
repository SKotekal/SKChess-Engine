import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.JButton;

public class ButtonHandler implements ActionListener
{
	private Move tryMove = new Move(-1, -1, 0);
	private ArrayList<Move> possible = new ArrayList<Move>();
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		JButton s = ((JButton) e.getSource());
		s.addActionListener(this); 
		JButton[] grid = ChessControl.gui.getButtons();
		
		//Find square that was pressed
		int index = -1;
		for(int i = 0; i < 64; i++)
		{
			if(grid[i].equals(s))
			{
				index = i;
				break;
			}
		}
				
		int piece = ChessControl.board.getArr()[Board.convert(index)];
		int who2move = ChessControl.gui.getWho2Move();

		if(possible.size() == 0 && 	piece*who2move >= 0) //If no previous from square, and it is this piece color's turn, set from to this square
		{
			tryMove.setFrom(index);
			tryMove.setPiece(piece);
			possible = MoveGen.pieceGen(ChessControl.board, index, piece);
		}
		else //Otherwise, check if current square is a valid move for the piece that is on the from square
		{
			boolean flag = false;
			for(int i = 0; i < possible.size(); i++)
			{
				if(possible.get(i).getTo() == index) //If so, make the move and clear the possibilities for the next move.
				{
					try {
						ChessControl.controlMove(possible.get(i), possible.get(i).getIntPiece());
					} catch (IOException | InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					tryMove = possible.get(i);
					flag = true;
					ChessControl.gui.switchMove(-1*tryMove.getIntPiece()/Math.abs(tryMove.getIntPiece()));
					possible.clear();
					
					//Prompt engine to make a move. Need to somehow disable the buttons during this time.
					try {
						ChessControl.engine();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					break;
				}
			}
			possible.clear();
			if(!flag && piece*who2move >= 0) //If it is not a valid move, set the current square as from
			{
				possible.clear();
				tryMove.setFrom(index);
				tryMove.setPiece(piece);
				possible = MoveGen.pieceGen(ChessControl.board, index, piece);
			}
		}
	
	}
}
