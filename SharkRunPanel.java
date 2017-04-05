import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
@SuppressWarnings("serial")
public class SharkRunPanel extends JPanel implements Runnable{
	Thread gameLoop;
	boolean isRunning, inAir;
	public Shark player = new Shark(500,500);
	public static void main(String[] args){
		new SharkRunPanel();
		
	}
	public SharkRunPanel(){
		JFrame frame = new JFrame("Shark Run");
		frame.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		System.setProperty("sun.java2d.opengl", "true");
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		frame.pack();
		frame.setVisible(true);
		frame.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				int id = e.getKeyCode();
				if(id== KeyEvent.VK_W){
					jump();
				}
				if(id== KeyEvent.VK_A){
					player.changeVelocityX(-player.getSpeed());
				}
				if(id== KeyEvent.VK_D){
					player.changeVelocityX(player.getSpeed());
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				int id = e.getKeyCode();
				if(id== KeyEvent.VK_A){
					player.changeVelocityX(0);
				}
				if(id== KeyEvent.VK_D){
					player.changeVelocityX(0);
				}
			}
			
		});
		start();
	}
	public synchronized void jump(){
		if(!inAir){
			Thread jumping = new Thread(new Runnable(){

				@Override
				public void run() {
					for(int jumpIndex = 0; jumpIndex < 100; jumpIndex++){
					player.changeVelocityY(-player.getSpeed()*10);
					repaint();
					}
				}
				
			});
			jumping.start();
		}
	}
	public synchronized void start(){
		isRunning = true;
		gameLoop = new Thread(this);
		gameLoop.start();
	}
	public synchronized void stop(){
		try {
			gameLoop.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		isRunning = false;
	}
	public void run() {
		while(isRunning){
			updateGame();
			try {
				Thread.sleep(11);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	public void updateGame(){
		updateSharkPos();
		updateSharkViaPhysics();
		repaint();
	}
	public void updateSharkPos(){
		player.changePosX(player.getVelocityX());
		player.changePosY(player.getVelocityY());
	}
	public void updateSharkViaPhysics(){
		
		if(player.getPosY()<500){
			inAir = true;
			player.changeVelocityY(3);
		}
		if(player.getPosY()>=500){
			inAir = false;
			player.changeVelocityY(0);
		}
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		drawShark(g);
		drawFloor(g);
	}
	public void drawFloor(Graphics g){
		g.setColor(Color.green);
		g.fillRect(10, 600, 1366, 100);
	}
	public void drawShark(Graphics g){
	g.setColor(Color.blue);
	g.fillRect((int)player.getPosX(), (int)player.getPosY(), 100,100 );
	}
	
}
class Shark{
	private double posX;
	private double posY;
	private double velocityX = 0;
	private double velocityY = 0;
	private double speed = 10;
	public Shark(double posX, double posY){
		this.posX= posX;
		this.posY= posY;
	}
	public double getPosX(){
		return posX;
	}
	public double getPosY(){
		return posY;
	}
	public double getSpeed(){
		return speed;
	}
	public void changeSpeed(double inc){
		speed+=inc;
	}
	public void changePosX(double inc){
		posX+=inc;
	}
	public void changePosY(double inc){
		posY+=inc;
	}
	public void changeVelocityX(double inc){
		velocityX = inc;
	}
	public void changeVelocityY(double inc){
		velocityY=inc;
	}
	public double getVelocityX(){
		return velocityX;
	}
	public double getVelocityY(){
		return velocityY;
	}
}