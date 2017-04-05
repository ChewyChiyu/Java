import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class CheckersBoard extends JPanel{
	Checker[][] board = new Checker[8][8];
	final int SPACER = 70;
	public int redLeft = 12;
	public int blueLeft = 12;
	public boolean redTurn = true;
	private static final long serialVersionUID = 1L;
	public static void main(String[] args){
		new CheckersBoard();
	}
	public CheckersBoard(){
		startFrame();
		setUpButtons();
		startGame();
		repaint();

	}
	public void setUpButtons(){
		JButton newGame = new JButton("New");
		newGame.setBounds(570, 10, 70, 30);
		newGame.addActionListener(e -> { startGame(); repaint(); });
		this.add(newGame);
	}
	public void startFrame(){
		JFrame frame = new JFrame("Checkers!");
		this.setLayout(null);
		frame.add(this);
		frame.setPreferredSize(new Dimension(650,590));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.addMouseListener(new MouseListener(){
			int primaryX; int secondaryX;
			int primaryY; int secondaryY;
			@Override
			public void mouseClicked(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {		
				if(e.getX()>0&&e.getX()<560&&e.getY()>0&&e.getY()<560){
					primaryY = e.getY() / SPACER;
					primaryX = e.getX() / SPACER;
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				if(e.getX()>0&&e.getX()<560&&e.getY()>0&&e.getY()<560){
					secondaryY = e.getY() / SPACER;
					secondaryX = e.getX() / SPACER;
				}
				System.out.println("Primary row : " + primaryY + " Primary col "  + primaryX);
				System.out.println("Secondary row : " + secondaryY + " Secondary col "  + secondaryX);
				if(!redTurn&&board[primaryY][primaryX].getColor().equals(Color.RED))
					return;
				if(redTurn&&board[primaryY][primaryX].getColor().equals(Color.BLUE))
					return;
				if(!neutralMove(primaryX , primaryY, secondaryX, secondaryY))
					attackMove(primaryX , primaryY, secondaryX, secondaryY);
				redTurn = !redTurn;
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
			}

		});
	}
	public boolean neutralMove(int x1, int y1, int x2, int y2){
	
		if(Math.abs(x1-x2)!=1||Math.abs(y1-y2)!=1){
			System.out.println("Invalid move");
			return false;
		}
		if(board[y1][x1].getColor().equals(Color.RED)&&(y2-y1)<0){
			System.out.println("Invalid move");
			return false;
		}
		if(board[y1][x1].getColor().equals(Color.BLUE)&&(y2-y1)>0){
			System.out.println("Invalid move");
			return false;
		}
		if(board[y1][x1]!=null&&board[y2][x2]==null){
			System.out.println("move is neutral, no lives taken");
			board[y2][x2] = new Checker(board[y1][x1].getColor());
			board[y1][x1] = null;
			repaint();
			return true;
		}
		return false;
	}
	private void attackMove(int x1, int y1, int x2, int y2){
		if(!redTurn&&board[y1][x1].getColor().equals(Color.RED)){
			System.out.println("Not your turn!");
			return;
		}
		if(Math.abs(x1-x2)!=2||Math.abs(y1-y2)!=2){
			System.out.println("Invalid move 1");
			return;
		}
		if(board[Math.abs(y1+y2)/2][Math.abs(x1+x2)/2] ==null||board[Math.abs(y1+y2)/2][Math.abs(x1+x2)/2].getColor().equals(board[y1][x1].getColor())){
			System.out.println("Invalid move 1");
			return;
		}
		if(board[Math.abs(y1+y2)/2][Math.abs(x1+x2)/2].getColor().equals(Color.BLUE))
			blueLeft--;
		if(board[Math.abs(y1+y2)/2][Math.abs(x1+x2)/2].getColor().equals(Color.RED))
			redLeft--;
		
		board[y2][x2] = new Checker(board[y1][x1].getColor());
		board[y1][x1] = null;
		board[Math.abs(y1+y2)/2][Math.abs(x1+x2)/2] = null;
		repaint();
	}
	public void startGame(){
		redLeft = 12;
		blueLeft =12;
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j <board[0].length; j++){
				board[i][j] = null;
				if(i<3){
					if(i%2==0&&j%2==1){
						board[i][j] = new Checker(Color.RED);
					}
					if(i%2==1&&j%2==0){
						board[i][j] = new Checker(Color.RED);
					}
				}
				if(i>4){
					if(i%2==0&&j%2==1){
						board[i][j] = new Checker(Color.BLUE);
					}
					if(i%2==1&&j%2==0){
						board[i][j] = new Checker(Color.BLUE);
					}
				}
			}
		}
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		drawBackGround(g);
		drawPieces(g);
		checkForWinner();
	}
	public void checkForWinner(){
		if(redLeft==0){
			JOptionPane.showMessageDialog (null, "Blue has won");
			startGame();
		}
		if(blueLeft==0){
			JOptionPane.showMessageDialog (null, "Red has won");
			startGame();
		}
	}
	public void drawPieces(Graphics g){
		int xBuffer = 0;
		int yBuffer = 0;
		for(int row = 0; row < board.length; row++){
			for(int col = 0; col < board[0].length; col++){
				if(board[row][col]!=null){
					if(board[row][col].getColor().equals(Color.BLUE))
						g.setColor(Color.BLUE);
					if(board[row][col].getColor().equals(Color.RED))
						g.setColor(Color.RED);
					g.fillOval(xBuffer, yBuffer, SPACER, SPACER);
				}
				xBuffer+=SPACER;
			}
			xBuffer = 0;
			yBuffer+=SPACER;
		}
	}

	public void drawBackGround(Graphics g){
		//checkers board is 8x8
		g.setFont(new Font ("Garamond", Font.BOLD , 15));
		g.drawString("Red Left ", 560, 80);
		g.drawString(""+ redLeft, 560, 100);
		g.drawString("Blue Left ", 560, 120);
		g.drawString(""+ blueLeft, 560, 140);
		g.drawString("Red's Turn?", 560, 170);
		g.drawString(""+redTurn, 560, 190);
		boolean isWhite = false;
		int xBuffer = 0;
		int yBuffer = 0;
		for(int row = 0; row < board.length; row++){
			for(int col = 0; col < board[0].length; col++){

				isWhite = !isWhite;
				if(isWhite){
					g.setColor(Color.white);
				}else
					g.setColor(Color.black);
				g.fillRect(xBuffer, yBuffer, SPACER, SPACER);

				xBuffer+=SPACER;
			}
			isWhite = !isWhite;
			xBuffer = 0;
			yBuffer+=SPACER;
		}
	}
}
class Checker{
	private Color c;
	public Checker(Color c){
		this.c = c;
	}
	public Color getColor(){
		return c;
	}
}
