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
import javax.swing.Timer;
@SuppressWarnings("serial")
public class SirBlockyTheMiner extends JPanel implements Runnable{
	public boolean gameLoop;
	public Thread t;
	public int eaterLength = 0;
	public int foodOnScreen = 0;
	public int foodEaten = 0;
	public boolean missedFood = false;
	public Timer time;
	Direction d = Direction.DOWN;
	int[][] board = new int[50][50];
	Color[][] background = new Color[50][50];
	Color[][] skyBack = new Color[10][50];
	public static void main(String[] args){
		new SirBlockyTheMiner();
	}
	public SirBlockyTheMiner(){
		setUpBoard();
		addButton();
		JFrame frame = new JFrame("Go Square Go!");
		frame.setPreferredSize(new Dimension(510,630));
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
		time = new Timer(100, e->{
			moveScreenDown();
			checkForHit();
		});
		spawnFood();
		start();
		
	}
	public void addButton(){
		JButton pause = new JButton("Pause");
		pause.setBounds(200, 30, 90, 30);
		pause.setBackground(new Color(0, 153, 255));
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
		time.start();
		gameLoop = true;
		t.start();
	}
	public synchronized void stop(){
		gameLoop = false;
		time.stop();
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
		foodEaten();
		repaint();
	}
	public void moveScreenDown(){
		for(int row = 0; row < board.length-1; row++){
			for(int col = 0; col < board[0].length; col++){
				int index = board[row+1][col];
				board[row+1][col] = board[row][col];
				board[row][col] = index;
				if(row==0&&board[row][col]==2){
					missedFood = true;
				}
				if(row==0 && board[row][col]!=0){
					eaterLength--;
					board[row][col] = 0;
				}
				
			}
		}
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
		int foodRow = ( board.length/2 ) +(int)(Math.random()*board.length/2);
		int foodCol = (int)(Math.random()*board[0].length-2);
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
		boolean death = (tempColIndex==board[0].length-1||tempRowIndex==board.length-2||tempRowIndex==-1||tempColIndex==-1)?true:false;
		if(tempLength<eaterLength || death || missedFood){
			stop();
			int reply = JOptionPane.showConfirmDialog(null, "You Died After Eating " + foodEaten + "\n Play Again?" , "Death", JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION) {
				eaterLength = 0;
				foodOnScreen = 0;
				foodEaten = 0;
				missedFood = false;
				d = Direction.DOWN;
				setUpBoard();
				spawnFood();
				start();
			}
			else {
				System.exit(0);
			}
		}
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
		for(int row = 0; row < skyBack.length; row++){
			for(int col = 0; col < skyBack[0].length; col++){
				if(row > skyBack.length-3){
					if((int)(Math.random()*2)==1){
						skyBack[row][col] = new Color(0, 153, 51);
					}else
						skyBack[row][col] = new Color(0, 230, 77);
				}else{
					if((int)(Math.random()*2)==1){
						skyBack[row][col] = new Color(0, 153, 255);
					}else
						skyBack[row][col] = new Color(128, 204, 255);
				}
			}
		}
		for(int row = 0; row < board.length; row++){
			for(int col = 0; col < board[0].length; col++){
				board[row][col] = 0; //FOOOOOOD
				if((int)(Math.random()*2)==1){
					background[row][col] = new Color(102, 53, 0);
				}else
					background[row][col] = new Color(153, 79, 0);
			}
		}
		board[10][15] = 1; //EATER
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		game(g);	
		drawStrings(g);

	}
	public void drawStrings(Graphics g){
		g.setFont(new Font ("Garamond", Font.BOLD , 15));
		g.setColor(Color.WHITE);
		g.drawString("Foodies Eaten " + foodEaten , 20,30);
	}

	public void game(Graphics g){
		int xBorderZ = 0;
		int yBorderZ = 0;
		final int SPACER = 10;
		for(int row = 0; row < skyBack.length; row++){
			for(int col = 0; col < skyBack[0].length; col++){
				g.setColor(skyBack[row][col]);
				g.fillRect(xBorderZ, yBorderZ, SPACER, SPACER);
				xBorderZ+=SPACER;
			}
			xBorderZ = 0;
			yBorderZ+=SPACER;	
		}
		int xBorder = 0;
		int yBorder = 100;
		for(int row = 0; row < board.length; row++){
			for(int col = 0; col < board[0].length; col++){
				g.setColor(background[row][col]);
				if(board[row][col]==1)
					g.setColor(new Color(153, 0, 0));
				if(board[row][col]==-1)
					g.setColor(new Color(51, 26, 0));
				if(board[row][col]==2)
					g.setColor(new Color(122, 122, 82));
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
