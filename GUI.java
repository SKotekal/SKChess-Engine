import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

/*
 * TODO - Implement user input on moving pieces.
 */
public class GUI extends JFrame
{
	private JPanel board;
	private JButton[] squares = new JButton[64];
	private String[] pathNames = {"./pieces/bk.png", "./pieces/bq.png", "./pieces/br.png", "./pieces/bb.png", "./pieces/bn.png", "./pieces/bp.png",
									"./pieces/blank.png", "./pieces/wp.png", "./pieces/wn.png", "./pieces/wb.png", "./pieces/wr.png", "./pieces/wq.png",
									"./pieces/wk.png"}; //Holds filenames for piece images
	private JPanel side; //Component holding buttons for placing new pieces
	private JPanel param; //Component holding user input parameters
	private JButton[] placePieces = new JButton[14]; //Array for placing new pieces
	private ButtonHandler handle = new ButtonHandler(); //Handles moving pieces
	private PlacePieceHandler ppHandle = new PlacePieceHandler(); //Handles placing pieces
	private int who2move = 0;
	
	public GUI() throws IOException
	{
		
		//Makes the GUI chessboard squares
		board = new JPanel(new GridLayout(8, 8));
		board.setBorder(new LineBorder(Color.BLACK));
		for(int i = 7; i >= 0; i--)
		{
			for(int j = 0; j < 8; j++)
			{
				JButton sq = new JButton(new ImageIcon(ImageIO.read(new File(pathNames[6]))));
				sq.setMargin(new Insets(0, 0, 0, 0));
				sq.setBorder(new LineBorder(Color.BLACK));
				if((j%2 == 1 && i%2 == 1) || (j%2 == 0 && i%2 == 0))
				{
					sq.setBackground(Color.GRAY);
				}
				else
				{
					sq.setBackground(Color.WHITE);
				}
				//sq.addActionListener(handle);
				sq.addActionListener(ppHandle);
				board.add(sq);
				squares[i*8+j] = sq;
			}
		}
		board.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		
		//Makes the GUI buttons for placing new pieces
		side = new JPanel(new GridLayout(2, 7));
		for(int i = 5; i >= 0; i--)
		{
			JButton sq = new JButton(new ImageIcon(ImageIO.read(new File(pathNames[i]))));
			sq.setMargin(new Insets(0, 0, 0, 0));
			sq.setBorder(new LineBorder(Color.BLACK));
			sq.addActionListener(ppHandle);
			side.add(sq);
			placePieces[i] = sq;
		}
		
		JButton temp = new JButton(new ImageIcon(ImageIO.read(new File(pathNames[6]))));
		temp.setMargin(new Insets(0, 0, 0, 0));
		temp.setBorder(new LineBorder(Color.BLACK));
		temp.addActionListener(ppHandle);
		side.add(temp);
		placePieces[6] = temp;
		
		for(int i = 7; i < pathNames.length; i++)
		{
			JButton sq = new JButton(new ImageIcon(ImageIO.read(new File(pathNames[i]))));
			sq.setMargin(new Insets(0, 0, 0, 0));
			sq.setBorder(new LineBorder(Color.BLACK));
			sq.addActionListener(ppHandle);
			side.add(sq);
			placePieces[i] = sq;
		}
		JButton start = new JButton("START!");
		start.setMargin(new Insets(0, 0, 0, 0));
		start.setBorder(new LineBorder(Color.BLACK));
		start.addActionListener(new StartHandler());
		side.add(start);
		placePieces[13] = start;
		
		//Makes the GUI elements for user input parameters
		param = new JPanel(new GridLayout(2, 7));
		JTextField fen = new JTextField(80);
		JLabel fenLabel = new JLabel("FEN (Pieces only): ");
		
		JTextField delim = new JTextField(1);
		JLabel delimLabel = new JLabel("FEN 1 Char Delim: ");
		
		JTextField ply = new JTextField(3);
		JLabel plyLabel = new JLabel("Ply: ");
		
		//JRadioButton 
		
		//Finishes GUI init
		this.setLayout(new BorderLayout());
		this.add(board, BorderLayout.LINE_START);
		this.add(side, BorderLayout.PAGE_END);
		this.add(param, BorderLayout.LINE_END);
		this.setSize(700, 675);
		this.setResizable(false);
		this.setTitle("SKChess");
		this.setVisible(true);
	}
	
	/*
	 * Takes array input and sets GUI according to the array
	 */
	public void set(int[] arr, boolean whiteView, int toMove) throws IOException
	{
		who2move = toMove;
		
		/*if(whiteView)
		{*/
			for(int i = 7; i >= 0; i--)
			{
				for(int j = 0; j < 8; j++)
				{
					JButton sq = squares[i*8+j];
					sq.setIcon(new ImageIcon(ImageIO.read(new File(pathNames[arr[Board.convert(i*8+j)]+6]))));
					
					sq.setMargin(new Insets(0, 0, 0, 0));
					sq.setBorder(new LineBorder(Color.BLACK));
					if((j%2 == 1 && i%2 == 1) || (j%2 == 0 && i%2 == 0))
					{
						sq.setBackground(Color.GRAY);
					}
					else
					{
						sq.setBackground(Color.WHITE);
					}	
					squares[i*8+j] = sq;

				}
			}
	}
	
	//Makes move on the GUI
	public void GUIMove(Move m, int piece) throws IOException, InterruptedException
	{
		JButton fr = squares[m.getFrom()];
		fr.setIcon(new ImageIcon(ImageIO.read(new File(pathNames[6]))));
		squares[m.getFrom()] = fr;
		
		JButton t = squares[m.getTo()];
		t.setIcon(new ImageIcon(ImageIO.read(new File(pathNames[6+piece]))));
		squares[m.getTo()] = t;
		
	}
	
	//Places piece onto GUI
	public void GUIPlacePiece(int piece, int sq) throws IOException
	{
		JButton s = squares[sq];
		s.setIcon(new ImageIcon(ImageIO.read(new File(pathNames[piece+6]))));
		squares[sq] = s;
	}
	
	//Returns array of buttons for squares on the GUI
	public JButton[] getButtons()
	{
		JButton[] ret = new JButton[64];
		for(int i = 0; i < 64; i++)
		{
			ret[i] = squares[i];
		}
		return ret;
	}
	
	//Returns array of buttons for placing pieces on the GUI
	public JButton[] getPlacePieces()
	{
		JButton[] ret = new JButton[14];
		for(int i = 0; i < 14; i++)
		{
			ret[i] = placePieces[i];
		}
		return ret;
	}
	
	//Removes the placing action handler once the DONE button is pressed
	public void start()
	{
		for(int i = 0; i < 64; i++)
		{
			squares[i].removeActionListener(ppHandle);
			squares[i].addActionListener(handle);
		}
		for(int i = 0; i < 14; i++)
		{
			placePieces[i].removeActionListener(ppHandle);
		}
	}
	
	public int getWho2Move()
	{
		int ret = who2move;
		return ret;
	}
	
	public void switchMove(int negOrpos)
	{
		who2move = negOrpos;
	}
}
