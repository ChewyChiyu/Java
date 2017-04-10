import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
@SuppressWarnings("serial")
public class FlappySquare extends JPanel implements Runnable{
	Thread game;
	volatile int score = 0;
	final int YBUFFER = 900;
	final int XBUFFER = 1300;
	Timer scoring;
	volatile boolean canScore = true;
	boolean hit = false;
	boolean isRunning;
	Square flappy = new Square();
	ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
	public static void main(String[] args){
		new FlappySquare();
	}
	public FlappySquare(){
		setUpPanel();
		setUpRectangles();
		start();
	}
	public void setUpRectangles(){
		for(int i = 0; i < 4; i++){
			int top = 0; int bottom = 0; int xBorder = 0;
			switch((int)(Math.random()*3)){
			case 0: top = 400; bottom = 300; break;
			case 1:	top = 200; bottom = 500;break;
			case 2:	top = 500; bottom = 200; break;
			case 3:	top = 300; bottom = 400; break;
			}
			switch(i){
			case 0: xBorder = 930; break;
			case 1: xBorder = 1270;break;
			case 2: xBorder = 1570; break;
			case 3: xBorder = 1870; break;

			}
			rectangles.add(new Rectangle(top, xBorder));
			rectangles.add(new Rectangle(bottom, xBorder));
		}
	}
	public void setUpPanel(){
		JFrame frame = new JFrame("Flappy Square!");
		frame.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		System.setProperty("sun.java2d.opengl", "true");
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"),
				"SPACE");
		this.getActionMap().put("SPACE",
				new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				jump();
			}

		});
		scoring = new Timer(2500, e->{
			if(!canScore){				//2.5 second delay for scoring
				canScore = true;
			}
		});
		scoring.start();
	}
	public synchronized void jump(){
		Thread jump = new Thread(new Runnable(){
			public void run(){
				for(int index = 0; index < 15; index++){
					flappy.changeY(-7);
					try{
						Thread.sleep(1);	
					}catch(Exception e){

					}
				}
			}
		});
		jump.start();
	}
	public synchronized void start(){
		game = new Thread(this);
		isRunning = true;
		game.start();
	}
	public synchronized void stop(){
		isRunning = false;
		try{
			game.join();
		}catch(Exception e){

		}
	}
	public void run(){
		while(isRunning){
			updateGame();
			try{
				Thread.sleep(9);
			}catch(Exception e){

			}
		}
	}
	public void updateGame(){
		physicsFlappy();
		shiftRectangles();
		checkForHit();
		repaint();
	}
	public void checkForHit(){
		if(hit){			//I will do this tomorrow LOL
			int reply = JOptionPane.showConfirmDialog(null, "You Died with a Score of " + score + "\n Play Again?" , "Death", JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION) {
				rectangles.clear();
				flappy = new Square();
				setUpRectangles();
				score = 0;
				hit = false;
			}
			else {
				System.exit(0);
			}
		}
	}
	public void shiftRectangles(){
		int index = 0;
		for(int i = 0; i < 4; i++){
			Rectangle top = rectangles.get(index++);
			Rectangle bottom = rectangles.get(index++);
			top.changeXPos(-1);
			bottom.changeXPos(-1);
			if(top.getXPos()<=0&&bottom.getXPos()<=0){
				int topH = 0; int bottomH = 0;
				switch((int)(Math.random()*3)){
				case 0: topH = 400; bottomH = 300; break;
				case 1:	topH = 200; bottomH = 500;break;
				case 2:	topH = 500; bottomH = 200; break;
				case 3:	topH = 300; bottomH = 400; break;
				}
				top.changeHeight(topH);
				bottom.changeHeight(bottomH);
				top.changeXPos(XBUFFER);
				bottom.changeXPos(XBUFFER);
			}

		}
	}
	public void physicsFlappy(){
		if(flappy.getX()>600){
			flappy.changeXVelocity(0);
		}
		flappy.changeX(flappy.getVelocityX());
		flappy.changeY(flappy.getVelocityY());
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		drawFlappy(g);
		drawRectangles(g);
		drawScore(g);
	}
	public void drawScore(Graphics g){
		g.setColor(Color.BLUE);
		g.setFont(new Font ("Garamond", Font.BOLD , 35));
		g.drawString(""+score, 650, 150);
	}
	public void drawRectangles(Graphics g){
		int index = 0;
		g.setColor(Color.BLACK);
		for(int i = 0; i < 4; i++){
			try{
				Rectangle top = rectangles.get(index++);
				Rectangle bottom = rectangles.get(index++);
				upDateScore(top.getXPos()+top.getWidth()/2, top.getHeight(), top.getHeight()+top.getWidth()*3);
				hitAnyWalls(top.getXPos(),top.getHeight(), bottom.getHeight(), top.getWidth());
				g.fillRect(top.getXPos(), 0, top.getWidth(), top.getHeight());
				g.fillRect(bottom.getXPos(), YBUFFER-bottom.getHeight(), bottom.getWidth(), bottom.getHeight());
			}
			catch(Exception e){
				//I know what is causing this i will fix it later
			}
		}

	}
	public void drawFlappy(Graphics g){
		g.setColor(Color.RED);
		g.fillRect(flappy.getX(), flappy.getY(), flappy.SPACER, flappy.SPACER);
	}
	public synchronized void hitAnyWalls(int x, int h1, int h2, int width){
		int flappyTop = flappy.getY();
		int flappyBottom = flappy.getY()+flappy.SPACER;
		if(flappy.getX()>x&&flappy.getX()<x+width){
			if(flappyTop<h1||flappyBottom>YBUFFER-h2){
				hit = true;
			}
		}

	}
	public synchronized void upDateScore(int x, int y,int y2){
		int flapX1 = flappy.getX();
		int flapX2 = flappy.getX() + (int) (flappy.SPACER);
		int flapY1 = flappy.getY() + flappy.SPACER/2;

		if(flapX1 < x && flapX2 > x && flapY1 > y && flapY1 < y2 && canScore){
			score++;
			canScore = !canScore;
		}
	}
}
class Square{
	private int xPos;
	private int yPos;
	private int yVelocity = 2;
	private int xVelocity = 1;
	final int SPACER;
	public Square(){
		xPos = 100;
		yPos = 100;
		SPACER = 50;
	}
	public void changeX(int inc){
		xPos += inc;
	}
	public void changeY(int inc){
		yPos += inc;
	}
	public void changeXVelocity(int inc){
		xVelocity = inc;
	}
	public void changeYVelocity(int inc){
		yVelocity = inc;
	}
	public int getVelocityX(){
		return xVelocity;
	}
	public int getVelocityY(){
		return yVelocity;
	}
	public int getX(){
		return xPos;
	}
	public int getY(){
		return yPos;
	}
}
class Rectangle{
	private int height;
	private int xPos;
	private int yPos = 0;
	private int width = 100;
	public Rectangle(int height, int xPos){
		this.height = height;
		this.xPos = xPos;
	}
	public int getHeight(){
		return height;
	}
	public int getXPos(){
		return xPos;
	}
	public int getYPos(){
		return yPos;
	}
	public void changeXPos(int inc){
		xPos += inc;
	}
	public void changeHeight(int inc){
		height = inc;
	}
	public int getWidth(){
		return width;
	}
}
