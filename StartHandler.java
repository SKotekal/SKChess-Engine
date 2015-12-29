import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class StartHandler implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent e)
	{
		ChessControl.gui.start(); //If done button is pressed, call GUI to remove placePieceHandler from all buttons.
	}


}
