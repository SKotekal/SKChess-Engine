import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;


public class PlacePieceHandler implements ActionListener
{
	int piece;
	boolean atLeastOne = false;
	int sq = -1;
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		JButton s = ((JButton) e.getSource());
		s.addActionListener(this); 
		JButton[] pp = ChessControl.gui.getPlacePieces();
		
		boolean piecePress = false;
		for(int i = 0; i < 14; i++) //Find which piece was selected to be placed, if a place button was pressed.
		{
			if(pp[i].equals(s) && i == 13)
			{
				piecePress = true;
				atLeastOne = true;
				piece = 0;
				break;
			}
			if(pp[i].equals(s))
			{
				piecePress = true;
				atLeastOne = true;
				piece = i-6;
				break;
			}
		}
		if(!piecePress && atLeastOne) //If pressed button was actually a chess square, then place the piece on that square.
		{
			JButton[] grid = ChessControl.gui.getButtons();
			for(int i = 0; i < 64; i++)
			{
				if(grid[i].equals(s))
				{
					sq = i;
					break;
				}
			}
			try {
				ChessControl.placePiece(piece, sq);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
	}

}
