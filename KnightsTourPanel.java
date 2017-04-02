import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPanel;


/*
 * This class will be the display and will get the starting position
 * of the knight using a mousePress.  It should also have the data
 * like the 2D array and the current location of the knight.  The 
 * paintComponent method should redraw the view from the beginning, as it
 * always should.
 */

public class KnightsTourPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public byte gridType = 0;
	public int rowU = 0, colU = 0, nextRow, nextCol;
	boolean knightPlaced = false;
	int knightJumps = 0, SQUARE_LENGTH = 70;
	Map<Integer, Integer> m = new HashMap<Integer, Integer>();
	public final int  X_SHIFT = 10, Y_SHIFT = 10;
	public boolean[][][] board = new boolean[8][8][2];
	public KnightsTourPanel(int w, int h) {
		this.setPreferredSize(new Dimension(w,h));
		this.setBackground(Color.green);
		setUpBoard();
		addMouseListener();
		repaint();
	}
	public void setUpBoard(){
		for(int row = 0 ; row < board.length; row++){
			for( int col = 0; col < board[0].length; col++){
				board[row][col][1] = false;
				board[row][col][0] = false;
			}
		}
		knightPlaced = false;
		knightJumps = 0;
		m.clear();
		setUpBoardPhase2();
	}
	public void setUpBoardPhase2(){
		switch(gridType){
		case 0 : 
			for(int row = 0; row < board.length; row++){
				for(int col = 0; col < board[0].length; col++){
					board[row][col][1] = true;	
				}
			}
			break;
		case 1:
			int index = board[0].length;
			for(int row = 0; row < board.length; row++){
				for(int col = 0; col < index; col++){
					board[row][col][1] = true;
				}
				index--;
			}
			break;
		case 2:
			int spacer = (int) ( board[0].length /2 );
			int indexA = 1, indexB = -1;
			for(int row = 0; row <board.length/2; row++){
				for(int i = 0; i < spacer + indexB; i++){
					board[row][i][1] = false;
				}

				for(int i = spacer + indexB ; i < spacer + indexA; i++){
					board[row][i][1] = true;

				}
				for(int i = spacer + indexA; i < board[0].length; i++){
					board[row][i][1] = false;

				}
				indexA++;
				indexB--;

			}
			int indexC = 1;
			for(int row = board.length/2; row <board.length-1; row++){
				for(int i = 0 ; i < indexC; i++){
					board[row][i][1] = false;
				}
				for(int i = indexC ; i < board[0].length-indexC; i++){
					board[row][i][1] = true;
				}
				for(int i = board[0].length-indexC ; i < board.length; i++){
					board[row][i][1] = false;
				}
				indexC++;
			}

			break;
		case 3:
			final int radius = (board.length-1)/2;
			for(int  row=0; row<board.length; row++){
				for(int col=0; col<board[0].length; col++){
					if(distance(row, col, radius)<=radius){
						board[row][col][1] = true;
					}

				}


			}

			break;


		}
	}
	public void clearAll(){
		for(int row =0; row < board.length; row++){
			for( int col = 0; col < board[0].length; col++){
				board[row][col][0] = false;
			}
		}
		knightPlaced = false;
		m.clear();
		knightJumps = 0;
	}

	public boolean validMove(int row1, int col1,int row2, int col2){
		if(board[row2][col2][0]||!board[row2][col2][1])
			return false;
		if((Math.abs(row2-row1)==1&&Math.abs(col2-col1)==2)||( Math.abs(row2-row1)==2&&Math.abs(col2-col1)==1) )
			return true;
		return false;
	}
	public int validMovesPossible(int rows, int cols){
		int count = 0;
		for(int row = 0; row < board.length; row++){
			for(int col = 0; col < board[0].length; col++){
				if((!board[row][col][0]&&board[row][col][1])&&((Math.abs(rows-row)==1&&Math.abs(cols-col)==2)||( Math.abs(rows-row)==2&&Math.abs(cols-col)==1))){
					count++;
				}
			}
		}

		return count;
	}

	// add the mouse listener.  This will only work for the 
	// first click, and then after the first click, there should
	// be no more mouse listening!
	private void addMouseListener() {
		// Whoever has focus is who can interact with mouse and keyboard, etc
		this.requestFocusInWindow();

		// similar to having an entity ready to interact with the Mouse
		this.addMouseListener(new MouseListener() {
			/*
			 * If you want to detect mouse dragging, then use a mouseMotionListener
			 */
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// I like to avoid using this one because if you are moving
				// the mouse while you are trying to click, it sometimes doesn't
				// register the click.  A click is a press and release at the 
				// same location

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// when the mouse enters the panel, this is called

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// duh...

			}

			@Override
			public void mousePressed(MouseEvent click) {

				if(!knightPlaced&&(click.getX()>X_SHIFT&&click.getX()<X_SHIFT+(SQUARE_LENGTH*(board[0].length+1))
						&& click.getY() > Y_SHIFT && click.getY() < Y_SHIFT+(SQUARE_LENGTH*board.length))){
					if(gridType==3){
						colU = ((click.getX()-X_SHIFT) / SQUARE_LENGTH);
					}else
					colU = ((click.getX()-X_SHIFT) / SQUARE_LENGTH)-1;
					rowU = (click.getY()-Y_SHIFT) / SQUARE_LENGTH;
					System.out.println("row : " + rowU + " col : " + colU);
					if(board[rowU][colU][1])
						board[rowU][colU][0] = true;
					m.put((rowU*board[0].length)+colU, knightJumps++);
					knightPlaced = true;
					repaint();
				}

			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// use this to find out when the mouse was released

			}

		});
	}


	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawGrid(g);
		printGrid();
		g.setFont(new Font ("Garamond", Font.BOLD , 15));
		g.drawString("Jumps : " + knightJumps, 700, 200);
		// stuff to draw the board and knight

	}
	public void printGrid(){
		for (Map.Entry<Integer, Integer> map : m.entrySet()) {
		}
		System.out.println("\n\n\n");
		int placement = 0;
		for(int row = 0; row < board.length; row++){
			for(int col = 0; col < board[0].length; col++){
				if(board[row][col][1]){
					if(board[row][col][0])
						System.out.print(" " + m.get(placement) + " ");
					else
						System.out.print(" _ ");
					placement++;
				}else{
					System.out.print(" ");
					placement++;
				}
			}
			System.out.println();
		}
	}
	public void drawGrid(Graphics g){
		switch(gridType){
		case 0:
			int x = X_SHIFT;
			int y = Y_SHIFT;
			int placement = 0;
			for(int row = 0; row < board.length; row++){
				for(int col = 0; col < board[0].length; col++){
					if(board[row][col][0]){
						g.setFont(new Font ("Garamond", Font.BOLD , 18));
						g.drawRect(x+=SQUARE_LENGTH, y, SQUARE_LENGTH, SQUARE_LENGTH);
						g.drawString(""+m.get(placement), x +(SQUARE_LENGTH/2) -5, 5 + y + SQUARE_LENGTH/2 );
					}
					else{
						g.setColor(Color.BLACK);
						g.drawRect(x+=SQUARE_LENGTH, y, SQUARE_LENGTH, SQUARE_LENGTH);
					}
					placement++;
				}
				x = X_SHIFT;
				y +=SQUARE_LENGTH;
			}
			break;
		case 1:
			int x2 = X_SHIFT;
			int y2 = Y_SHIFT;
			int placement2 = 0;
			int shift = board[0].length;
			for(int row = 0; row < board.length; row++){
				for(int col = 0; col < shift; col++){
					if(board[row][col][0]){
						g.setFont(new Font ("Garamond", Font.BOLD , 18));
						g.drawRect(x2+=SQUARE_LENGTH, y2, SQUARE_LENGTH, SQUARE_LENGTH);
						g.drawString(""+m.get(placement2), x2 +(SQUARE_LENGTH/2) -5, 5 + y2 + SQUARE_LENGTH/2 );
					}
					else{
						g.setColor(Color.BLACK);
						g.drawRect(x2+=SQUARE_LENGTH, y2, SQUARE_LENGTH, SQUARE_LENGTH);
					}	
					placement2++;
					System.out.println(placement2); 
				}
				placement2 += (board[0].length - shift);
				shift--;
				x2 = X_SHIFT;
				y2 +=SQUARE_LENGTH;

			}

			break;
		case 2:
			int x3 = X_SHIFT;
			int y3 = Y_SHIFT;
			int placement3 = 0;
			int spacer = (int) ( board[0].length /2 );
			int indexA = 1, indexB = -1;
			for(int row = 0; row <board.length/2; row++){
				for(int i = 0; i < spacer + indexB; i++){
					x3+=SQUARE_LENGTH;
					placement3++;
				}

				for(int i = spacer + indexB ; i < spacer + indexA; i++){
					if(board[row][i][0]){
						g.setFont(new Font ("Garamond", Font.BOLD , 18));
						g.drawRect(x3+=SQUARE_LENGTH, y3, SQUARE_LENGTH, SQUARE_LENGTH);
						g.drawString(""+m.get(placement3), x3 +(SQUARE_LENGTH/2) -5, 5 + y3 + SQUARE_LENGTH/2 );
					}
					else{
						g.setColor(Color.BLACK);
						g.drawRect(x3+=SQUARE_LENGTH, y3, SQUARE_LENGTH, SQUARE_LENGTH);
					}	
					placement3++;

				}
				for(int i = spacer + indexA; i < board[0].length; i++){
					x3+=SQUARE_LENGTH;
					placement3++;

				}
				indexA++;
				indexB--;
				x3 = X_SHIFT;
				y3 +=SQUARE_LENGTH;

			}
			int indexC = 1;

			for(int row = board.length/2; row <board.length-1; row++){
				for(int i = 0 ; i < indexC; i++){
					x3+=SQUARE_LENGTH;
					placement3++;

				}
				for(int i = indexC ; i < board[0].length-indexC; i++){
					if(board[row][i][0]){
						g.setFont(new Font ("Garamond", Font.BOLD , 18));
						g.drawRect(x3+=SQUARE_LENGTH, y3, SQUARE_LENGTH, SQUARE_LENGTH);
						g.drawString(""+m.get(placement3), x3 +(SQUARE_LENGTH/2) -5, 5 + y3 + SQUARE_LENGTH/2 );
					}
					else{
						g.setColor(Color.BLACK);
						g.drawRect(x3+=SQUARE_LENGTH, y3, SQUARE_LENGTH, SQUARE_LENGTH);
					}		
					placement3++;
				}
				for(int i = board[0].length-indexC ; i < board.length; i++){
					x3+=SQUARE_LENGTH;
					placement3++;

				}
				indexC++;
				x3 = X_SHIFT;
				y3 +=SQUARE_LENGTH;
			}

			x3 = X_SHIFT;
			y3 +=SQUARE_LENGTH;
			placement3++;
			break;
		case 3:
			int radius = (board.length-1)/2;
			int x4 = X_SHIFT;
			int y4 = Y_SHIFT;
			int placement4 = 0;
			for(int row = 0; row < board.length; row++){
				for(int col = 0; col < board[0].length; col++){
					if(distance(row, col, radius)<=radius){
					
					if(board[row][col][0]){
						g.setFont(new Font ("Garamond", Font.BOLD , 18));
						g.drawRect(x4, y4, SQUARE_LENGTH, SQUARE_LENGTH);
						g.drawString(""+m.get(placement4), x4 +(SQUARE_LENGTH/2) -5, 5 + y4 + SQUARE_LENGTH/2 );
					}
					else{
						g.setColor(Color.BLACK);
						g.drawRect(x4, y4, SQUARE_LENGTH, SQUARE_LENGTH);
					}
					
					}
					x4+=SQUARE_LENGTH;
					placement4++;
				}
				x4 = X_SHIFT;
				y4 +=SQUARE_LENGTH;
			}
			break;


		}

	}

	public int distance(int row, int col, int radius){
		double deltaX= Math.abs(row-radius);
		double deltaY= Math.abs(col-radius);
		double distance=Math.sqrt((deltaX*deltaX)+(deltaY*deltaY));
		return (int)(Math.ceil(distance));

	}

	/* make random move just selects a new location at random
	 * if the knight is trapped (no new locations to move to)
	 * then false is returned.  Otherwise, true is returned.
	 * The knight's location should be updated and the 
	 */
	public boolean makeRandomMove() {
		while(true){
			if(validMovesPossible(rowU, colU) > 0){
				nextRow = (int)(Math.random()*board.length);
				nextCol= (int)(Math.random()*board[0].length);
				if(validMove(rowU,colU,nextRow,nextCol)){
					board[nextRow][nextCol][0] = true;
					rowU = nextRow;
					colU = nextCol;
					m.put((rowU*board[0].length)+colU, knightJumps++);
					repaint();
					return true;
				}	
			}else{
				JOptionPane.showMessageDialog(null, "No Valid Moves left \n " +  knightJumps  + "  jumps");
				return false;
			}
		}
	}
	/* make a move to a new location that ensures the best chance
	 * for a complete traversal of the board.
	 * if the knight is trapped (no new locations to move to)
	 * then false is returned.  Otherwise, true is returned.
	 */
	public boolean makeThoughtfulMove() {
		Map<Integer, Integer> logic = new HashMap<Integer, Integer>();
		int index = validMovesPossible(rowU,colU);

		while(true){
			if(validMovesPossible(rowU, colU) > 0){
				nextRow = (int)(Math.random()*board.length);
				nextCol= (int)(Math.random()*board[0].length);

				if(validMove(rowU,colU,nextRow,nextCol)&&!logic.containsKey((nextRow*board[0].length)+nextCol)){
					logic.put(((nextRow*board[0].length) + nextCol), validMovesPossible(nextRow,nextCol));
				}	
				if(logic.size()==index){
					int optimalLocation = 0;
					Integer min = Collections.min(logic.values());
					for (Map.Entry<Integer, Integer> map : logic.entrySet()) {
						if(map.getValue().equals(min))
							optimalLocation = map.getKey();
					}
					int row = (int)(optimalLocation/ board[0].length);
					int col = (optimalLocation % board[0].length);
					board[row][col][0] = true;
					rowU = row;
					colU = col;
					m.put((rowU*board[0].length)+colU, knightJumps++);
					repaint();

					return true;
				}
			}else{
				JOptionPane.showMessageDialog(null, "No Valid Moves left \n " +  knightJumps  + "  jumps");
				return false;
			}
		}
	}



}



