import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
@SuppressWarnings("serial")
public class AtariOutBreak extends JPanel implements Runnable{
	Thread t;
	boolean isRunning;
	final int WIDTH = 1150;
	final int HEIGHT = 700;
	int triesLeft = 10;
	Paddle mainPlayer = new Paddle(500,580);
	ArrayList<Ball> balls = new ArrayList<Ball>();
	ArrayList<Block> blocks = new ArrayList<Block>();
	public static void main(String[] args){
		new AtariOutBreak();
	}
	public AtariOutBreak(){
		balls.add(new Ball(WIDTH/2, HEIGHT/2+10)); //add initial ball
		setUpPanel();
		setUpBlocks();
		start();
	}
	public void setUpBlocks(){
		int wid = WIDTH-50; //5ddd0 pixel buffer
		int xBuffer = 0;
		int yBuffer = 0;
		for(int col = 0; col < 5; col++){
			int height = 0;
			while(wid>=0){
				byte type = 0; //will random later
				Block b = new Block(xBuffer, yBuffer, type);
				height = b.getHeight();
				blocks.add(b);
				xBuffer+=(b.getWidth()+5); // Spacing
				wid-=(b.getWidth()+5);
			}
			yBuffer+= (height + 5); //Spacing
			wid = WIDTH - 50;
			xBuffer = 0;
		}
	}
	public void setUpPanel(){
		JFrame frame = new JFrame("Atari OutBreak!");
		frame.add(this);
		this.setLayout(null);
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {				
			}

			@Override
			public void keyPressed(KeyEvent e) {	
				int id = e.getKeyCode();
				if(id==KeyEvent.VK_A){
					mainPlayer.changeXVelocity(-5);
				}
				if(id==KeyEvent.VK_D){
					mainPlayer.changeXVelocity(5);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {			
				int id = e.getKeyCode();
				if(id==KeyEvent.VK_A){
					mainPlayer.changeXVelocity(0);

				}
				if(id==KeyEvent.VK_D){
					mainPlayer.changeXVelocity(0);

				}
			}

		});
	}
	public synchronized void start(){
		t = new Thread(this);
		isRunning = true;
		t.start();
	}
	public synchronized void stop(){
		try{
			isRunning = false;
			t.join();
		}catch(Exception e){

		}
	}
	public void run(){
		while(isRunning){
			try{
				updatePanel();
				Thread.sleep(10);
			}catch(Exception e){

			}
		}
	}
	public void updatePanel(){
		updateMainPlayerLocation();
		updateBallLocation();
		checkForHitPlayer();
		checkForHitBlock();
		checkForLose();
		repaint();
	}
	public void checkForLose(){
		if(balls.isEmpty()){
			triesLeft--;
			balls.add(new Ball(WIDTH/2, HEIGHT/2+10));
		}
		if(triesLeft<=0){
			int reply = JOptionPane.showConfirmDialog(null, "No Tries Left!" + "\n Play Again?" , "Lose", JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION) {
				triesLeft = 10;
				balls.clear();
				balls.add(new Ball(WIDTH/2, HEIGHT/2+10));
				return;
			}
			else {
				System.exit(0);
			}
		}
	}
	public void updateBallLocation(){
		for(int index = 0; index < balls.size(); index++){
			Ball b = balls.get(index);
			b.changeXPos(b.getXVelocity());
			b.changeYPos(b.getYVelocity());
		}
	}
	public void checkForHitBlock(){
		for(int index = 0; index < blocks.size(); index++){
			for(int ball = 0; ball < balls.size(); ball++){
				Block b = blocks.get(index);
				Ball c = balls.get(ball);
				int ballCenterX = c.getXPos()+c.getWidth()/2;
				int ballCenterY = c.getYPos()+c.getHeight()/2;
				if(b.isHit(ballCenterX, ballCenterY)){
					blocks.remove(b);
					
					if(b.getYPos()+b.getHeight()-5 < ballCenterY &&b.getYPos() +b.getHeight() + 5 > ballCenterY){
					c.changeXVelocity(c.getXVelocity());
					c.changeYVelocity(-c.getYVelocity());
					}else{
						c.changeXVelocity(-c.getXVelocity());
						c.changeYVelocity(c.getYVelocity());
						
					}
				}


			}
		}
	}
	public void checkForHitPlayer(){
		for(int index = 0; index < balls.size(); index++){
			Ball b = balls.get(index);
			if(b.getXPos()<=0||b.getXPos()>=WIDTH){
				b.changeXVelocity(-b.getXVelocity());
			}
			if(b.getYPos()<=0){
				b.changeYVelocity(-b.getYVelocity());
			}
			if(b.getYPos()>=HEIGHT){
				balls.remove(b);
				return;
			}
			int x1 = mainPlayer.getXPos();
			int x2 = mainPlayer.getXPos() + mainPlayer.getWidth();
			int y1 = mainPlayer.getYPos()-10; //10 pixel buffer
			int y2 = mainPlayer.getYPos() + mainPlayer.getHeight();
			if(b.getXPos() >= x1 && b.getXPos() <= x2 && b.getYPos() > y1 && b.getYPos() < y2){
				b.changeXVelocity(b.getXVelocity()+mainPlayer.getXVelocity());
				b.changeYVelocity(-b.getYVelocity());
			}
		}

	}
	public void updateMainPlayerLocation(){
		if(mainPlayer.getXPos()<=0){
			mainPlayer.changeXPos(WIDTH);
		}
		if(mainPlayer.getXPos()>=WIDTH){
			mainPlayer.changeXPos(-WIDTH);
		}
		mainPlayer.changeXPos(mainPlayer.getXVelocity());
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		drawMainPlayer(g);
		drawBalls(g);
		drawBlocks(g);
		drawStrings(g);
	}
	public void drawBlocks(Graphics g){
		for(int index = 0; index < blocks.size(); index++){
			Block b = blocks.get(index);
			g.setColor(b.getColor());
			g.fillRect(b.getXPos(), b.getYPos(), b.getWidth(), b.getHeight());
		}
	}
	public void drawStrings(Graphics g){
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.setColor(Color.BLACK);
		g.drawString("Tries Left "+triesLeft, 50, 600);
	}
	public void drawBalls(Graphics g){
		g.setColor(Color.BLACK);
		for(int index = 0; index < balls.size(); index++){
			Ball b = balls.get(index);
			g.fillOval(b.getXPos(), b.getYPos(), b.getWidth(), b.getHeight());
		}
	}
	public void drawMainPlayer(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(mainPlayer.getXPos(), mainPlayer.getYPos(), mainPlayer.getWidth(), mainPlayer.getHeight());
	}
}
class Paddle{
	int xPos;
	int yPos;
	final int WIDTH = 200;
	final int HEIGHT = 10;
	int xVelocity;
	public Paddle(int x, int y){
		xPos = x;
		yPos = y;
		xVelocity = 0;
	}
	public void changeXPos(int inc){
		xPos += inc;
	}
	public void changeYPos(int inc){
		yPos +=inc;	
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
	public int getHeight(){
		return HEIGHT;
	}
	public int getWidth(){
		return WIDTH;
	}
}
class Ball{
	int xPos;
	int yPos;
	int xVelocity;
	int yVelocity = 1;
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
class Block{
	int xPos;
	int yPos;
	int height;
	int width;
	Color c;
	public Block(int x, int y, byte type){
		xPos = x;
		yPos = y;

		switch(type){
		case 0 : 
			width = 100;
			height = 50;
			c = Color.BLUE;
			break;
		}
	}
	public boolean isHit(int ballX1, int ballY1){
		int blockX1 = xPos;
		int blockX2 = xPos + width;
		int blockY1 = yPos;
		int blockY2 = yPos + height;
		return(ballX1>blockX1&&ballX1<blockX2&&ballY1>blockY1&&ballY1<blockY2);
	}
	public int getXPos(){
		return xPos;
	}
	public int getYPos(){
		return yPos;
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	public Color getColor(){
		return c;
	}
}
