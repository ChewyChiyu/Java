import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
@SuppressWarnings("serial")
public class EatGamePanel extends JPanel implements Runnable{
	public boolean gameLoop;
	public Thread t;
	public int eaterLength = 0;
	public int foodOnScreen = 0;
	public int foodEaten = 0;
	public int rowIndex = -1;
	public int colIndex = -1;
	Direction d = Direction.RIGHT;
	int[][] board = new int[50][50];
	public static void main(String[] args){
		new EatGamePanel();
	}
	public EatGamePanel(){
		setUpBoard();
		addButton();
		JFrame frame = new JFrame("Go Square Go!");
		frame.setPreferredSize(new Dimension(510,600));
		System.setProperty("sun.java2d.opengl", "true");
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"),
				"W");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"),
				"D");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"),
				"S");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"),
				"A");
		this.getActionMap().put("W",
				new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!d.equals(Direction.DOWN)){
					d = Direction.UP;
				}
			}

		});
		this.getActionMap().put("D",
				new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!d.equals(Direction.LEFT)){
					d = Direction.RIGHT;
				}
			}

		});
		this.getActionMap().put("S",
				new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!d.equals(Direction.UP)){
					d = Direction.DOWN;
				}
			}

		});
		this.getActionMap().put("A",
				new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!d.equals(Direction.RIGHT)){
					d = Direction.LEFT;
				}
			}

		});
		spawnFood();
		start();
	}
	public void addButton(){
		JButton pause = new JButton("Pause");
		pause.setBounds(200, 520, 90, 30);
		pause.addActionListener(e ->{
			if(gameLoop)
				stop();
			else
				start();
		});
		this.add(pause);
	}
	public synchronized void start(){
		t = new Thread(this);
		gameLoop = true;
		t.start();
	}
	public synchronized void stop(){
		gameLoop = false;
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	public void run(){
		while(gameLoop){
			updateGame();
			try{
				Thread.sleep(50);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public void updateGame(){
		moveHead();
		checkForHit();
		foodEaten();
		repaint();
	}
	public void foodEaten(){
		int tempFood = 0;
		for(int row = 0; row < board.length; row++){
			for(int col = 0; col < board[0].length; col++){
				if(board[row][col]==2){
					tempFood++;
				}
			}
		}
		if(tempFood<foodOnScreen){
			foodOnScreen--;
			foodEaten++;
			spawnFood();
		}
	}
	public void spawnFood(){
		foodOnScreen++;
		int foodRow = (int)(Math.random()*board.length-2)+1;
		int foodCol = (int)(Math.random()*board[0].length-2)+1;
		if(board[foodRow][foodCol]==0)
			board[foodRow][foodCol] = 2; //FOOD
	}
	public void checkForHit(){
		int tempLength = 0;
		int tempRowIndex = -1;
		int tempColIndex = -1;
		for(int row = 0; row < board.length; row++){
			for(int col = 0; col < board[0].length; col++){
				if(board[row][col]==-1){
					tempLength++;
				}
				if(board[row][col]==1){
					tempRowIndex = row;
					tempColIndex = col;
				}
			}
		}
		boolean death = (rowIndex==tempRowIndex&&colIndex==tempColIndex)?true:false;
		if(tempLength<eaterLength || death){
			int reply = JOptionPane.showConfirmDialog(null, "You Died After Eating " + foodEaten + "\n Play Again?" , "Death", JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION) {
				eaterLength = 0;
				foodOnScreen = 0;
				foodEaten = 0;
				setUpBoard();
				spawnFood();

			}
			else {
				System.exit(0);
			}
		}
		rowIndex = tempRowIndex;
		colIndex = tempColIndex;
	}


	public void moveHead(){
		try{
		for(int row = 0; row < board.length-1; row++){
			for(int col = 0; col < board[0].length-1; col++){
				if(board[row][col]==1){
					if(d.equals(Direction.DOWN)){ //could of used switch statement but oh well
						board[row][col] = -1;
						board[row+1][col] = 1;
						eaterLength++;
						return;
					}else if (d.equals(Direction.UP)){
						board[row][col] = -1;
						board[row-1][col] = 1;
						eaterLength++;
						return;
					}else if (d.equals(Direction.LEFT)){
						board[row][col] = -1;
						board[row][col-1] = 1;
						eaterLength++;
						return;
					}else if (d.equals(Direction.RIGHT)){
						board[row][col] = -1;
						board[row][col+1] = 1;
						eaterLength++;
						return;
					}

				}
			}
		}
		}catch(Exception e){

		}
	}
	public void setUpBoard(){
		for(int row = 0; row < board.length; row++){
			for(int col = 0; col < board[0].length; col++){
				board[row][col] = 0; //FOOOOOOD
			}
		}
		board[10][15] = 1; //EATER
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		drawStrings(g);
		game(g);	

	}
	public void drawStrings(Graphics g){
		g.setFont(new Font ("Garamond", Font.BOLD , 15));
		g.drawString("Foodies Eaten " + foodEaten , 20,550);
	}
	public void game(Graphics g){
		int xBorder = 0;
		int yBorder = 0;
		final int SPACER = 10;
		for(int row = 0; row < board.length; row++){
			for(int col = 0; col < board[0].length; col++){
				g.setColor(Color.BLACK);
				if(board[row][col]==1)
					g.setColor(Color.RED);
				if(board[row][col]==-1)
					g.setColor(Color.WHITE);
				if(board[row][col]==2)
					g.setColor(Color.BLUE);
				g.fillRect(xBorder, yBorder, SPACER, SPACER);

				xBorder+=SPACER;

			}
			xBorder = 0;
			yBorder+=SPACER;	
		}

	}
}

enum Direction {
	RIGHT, LEFT, UP, DOWN;
}
