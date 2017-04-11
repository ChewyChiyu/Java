import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")	
public class PongPanel extends JPanel{
	Thread player1;
	Thread player2;
	boolean isRunning;
	Paddle playerOne = new Paddle(100,30);
	Paddle playerTwo = new Paddle(100,530);
	int playerOneScore = 0;
	int playerTwoScore = 0;
	final int WIDTH = 600;
	final int HEIGHT = 600;
	Ball ball = new Ball(WIDTH/2, HEIGHT/2);
	public static void main(String[] args){
		new PongPanel();
	}
	public PongPanel(){
		startPanel();
		start();
		repaint();
	}
	public void startPanel(){
		JFrame frame = new JFrame("Pong!");
		frame.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		System.setProperty("sun.java2d.opengl", "true");
		frame.add(this);
		this.setBackground(Color.BLACK);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		frame.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				int id = e.getKeyCode();
				if(id==KeyEvent.VK_A){
					playerOne.changeXVelocity(-5);
				}
				if(id==KeyEvent.VK_D){
					playerOne.changeXVelocity(5);
				}if(id==KeyEvent.VK_J){
					playerTwo.changeXVelocity(-5);
				}if(id==KeyEvent.VK_L){
					playerTwo.changeXVelocity(5);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				int id = e.getKeyCode();
				if(id==KeyEvent.VK_A){
					playerOne.changeXVelocity(0);
				}
				if(id==KeyEvent.VK_D){
					playerOne.changeXVelocity(0);
				}if(id==KeyEvent.VK_J){
					playerTwo.changeXVelocity(0);
				}if(id==KeyEvent.VK_L){
					playerTwo.changeXVelocity(0);
				}
			}

		});

	}
	public synchronized void start(){
		player1 = new Thread(new Runnable(){
			public void run(){
				while(isRunning){
					updatePlayer1();
					try{
						Thread.sleep(10);
					}catch(Exception e){

					}
				}
			}
		});
		player2 = new Thread(new Runnable(){
			public void run(){
				while(isRunning){
					updatePlayer2();
					try{
						Thread.sleep(10);
					}catch(Exception e){

					}
				}
			}
		});
		isRunning = true;
		player1.start();
		player2.start();
	}
	public synchronized void stop(){
		try{
			isRunning = false;
			player1.join();
			player2.join();
		}catch(Exception e){

		}
	}
	
	public void updatePlayer1(){
		checkForPlayer1Hit();
		checkForBoardHit();
		updateBallLocation();
		updatePlayer1Location();
		checkForWin();
		repaint();
	}
	public void updatePlayer2(){
		checkForPlayer2Hit();
		checkForBoardHit();
		updateBallLocation();
		updatePlayer2Location();
		checkForWin();
		repaint();
	}
	public synchronized void checkForWin(){
		if(playerOneScore>=11){
			int reply = JOptionPane.showConfirmDialog(null, "Player One Wins!" + "\n Play Again?" , "Win", JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION) {
				playerOneScore = 0;
				playerTwoScore = 0;
				ball.reset();
				return;
			}
			else {
				System.exit(0);
			}
		}
		if(playerTwoScore>=11){
			int reply = JOptionPane.showConfirmDialog(null, "Player Two Wins!" + "\n Play Again?" , "Win", JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION) {
				playerOneScore = 0;
				playerTwoScore = 0;
				ball.reset();
				return;
			}
			else {
				System.exit(0);
			}
		}
	}
	public void checkForPlayer1Hit(){
		int playerOneX1 = playerOne.getXPos();
		int playerOneX2 = playerOne.getXPos() + playerOne.getWidth();
		int playerOneY1 = playerOne.getYPos();
		int playerOneY2 = playerOne.getYPos() + playerOne.getHeight();
		if(ball.getXPos() >= playerOneX1 && ball.getXPos() <= playerOneX2 && ball.getYPos() > playerOneY1 && ball.getYPos() < playerOneY2){
			ball.changeXVelocity(ball.getXVelocity()+playerOne.getXVelocity());
			ball.changeYVelocity(-ball.getYVelocity());
		}
	}
	public void checkForPlayer2Hit(){
		int playerTwoX1 = playerTwo.getXPos();
		int playerTwoX2 = playerTwo.getXPos() + playerTwo.getWidth();
		int playerTwoY1 = playerTwo.getYPos() - 10; //Buffer
		int playerTwoY2 = playerTwo.getYPos() + playerTwo.getHeight();
		if(ball.getXPos() >= playerTwoX1 && ball.getXPos() <= playerTwoX2 && ball.getYPos() > playerTwoY1 && ball.getYPos() < playerTwoY2){
			ball.changeXVelocity(ball.getXVelocity()+playerTwo.getXVelocity());
			ball.changeYVelocity(-ball.getYVelocity());
		}

	}
	public synchronized void checkForBoardHit(){

		if(ball.getXPos() <= 0 || ball.getXPos() >= WIDTH ){
			ball.changeXVelocity(-ball.getXVelocity());
		}
		if( ball.getYPos() <= 0 || ball.getYPos() >= HEIGHT){
			if(ball.getYPos()<=0){
				playerTwoScore++;
				ball.reset();
			}
			if(ball.getYPos() >=HEIGHT){
				playerOneScore++; 
				ball.reset();
			}
		}
	}
	public synchronized void updateBallLocation(){
		ball.changeXPos(ball.getXVelocity());
		ball.changeYPos(ball.getYVelocity());
	}
	public void updatePlayer1Location(){
		if(playerOne.getXPos()+playerOne.getWidth()>WIDTH){
			playerOne.changeXPos(-5);
		}
		if(playerOne.getXPos()<0){
			playerOne.changeXPos(5);
		}
		playerOne.changeXPos(playerOne.getXVelocity());
	}
	public void updatePlayer2Location(){
		if(playerTwo.getXPos()+playerTwo.getWidth()>WIDTH){
			playerTwo.changeXPos(-5);
		}
		if(playerTwo.getXPos()<0){
			playerTwo.changeXPos(5);
		}
		playerTwo.changeXPos(playerTwo.getXVelocity());
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		drawPaddles(g);
		drawBall(g);
		drawScore(g);
	}
	public void drawScore(Graphics g){
		g.setFont(new Font ("Garamond", Font.BOLD , 40));
		g.setColor(Color.WHITE);
		g.drawString(playerOneScore + " : " + playerTwoScore, 230, 200);
	}
	public void drawBall(Graphics g){
		g.setColor(Color.WHITE);
		g.fillOval(ball.getXPos(), ball.getYPos(), ball.getWidth(), ball.getHeight());
	}
	public void drawPaddles(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(playerOne.getXPos(), playerOne.getYPos(), playerOne.getWidth(), playerOne.getHeight());
		g.fillRect(playerTwo.getXPos(), playerTwo.getYPos(), playerTwo.getWidth(), playerTwo.getHeight());

	}
}
class Paddle{
	int xPos;
	int yPos;
	int xVelocity = 0;
	final int HEIGHT = 10;
	final int WIDTH = 130;
	public Paddle(int x, int y){
		xPos = x;
		yPos = y;
	}
	public void changeXPos(int inc){
		xPos += inc;
	}
	public void changeYPos(int inc){
		yPos += inc;
	}
	public void changeXVelocity(int inc){
		xVelocity = inc;
	}
	public int getXPos(){
		return xPos;
	}
	public int getYPos(){
		return yPos;
	}
	public int getXVelocity(){
		return xVelocity;
	}
	public int getWidth(){
		return WIDTH;
	}
	public int getHeight(){
		return HEIGHT;
	}
}
class Ball{
	int xPos;
	int yPos;
	int xVelocity;
	int yVelocity = -1;
	final int HEIGHT = 20;
	final int WIDTH = 20;
	int ballResetX;
	int ballResetY;
	public Ball(int x, int y){
		xPos = x;
		yPos = y;
		ballResetX = xPos;
		ballResetY = yPos;
	}
	public void changeXPos(int inc){
		xPos += inc;
	}
	public void changeYPos(int inc){
		yPos += inc;
	}
	public void changeXVelocity(int inc){
		xVelocity = inc;
	}
	public void changeYVelocity(int inc){
		yVelocity = inc;
	}
	public int getXPos(){
		return xPos;
	}
	public int getYPos(){
		return yPos;
	}
	public int getXVelocity(){
		return xVelocity;
	}
	public int getYVelocity(){
		return yVelocity;
	}
	public int getWidth(){
		return WIDTH;
	}
	public int getHeight(){
		return HEIGHT;
	}
	public void reset(){
		xPos = ballResetX;
		yPos = ballResetY;
		xVelocity = 0;
		yVelocity = -yVelocity;
	}
}
