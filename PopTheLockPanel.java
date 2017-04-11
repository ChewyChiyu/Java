import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
@SuppressWarnings("serial")
public class PopTheLockPanel extends JPanel implements Runnable{
	Thread game;
	int totalHits = 0;
	boolean isRunning;
	final int WIDTH = 600;
	final int HEIGHT = 600;
	public double lockAngleSpeed = Math.PI/60; //Starting speeds
	public double keyAngleSpeed = Math.PI/50;
	public double lockAngle;
	Timer lockTick;
	public double keyAngle;
	Timer keyTick;
	Rectangle key = new Rectangle(0, -100, 25, 100);
	Rectangle lock = new Rectangle(0, -250, 40, 100);
	public static void main(String[] args){
		new PopTheLockPanel();
	}
	public PopTheLockPanel(){
		setUpPanel();
		start();
		repaint();
	}
	public void setUpPanel(){
		JFrame frame = new JFrame("Pop The Lock!");
		frame.add(this);
		frame.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);

		lockTick = new Timer(90, e->{
			if(Math.toDegrees(lockAngle)<=360 && Math.toDegrees(lockAngle)>=-360 )
				lockAngle += (lockAngleSpeed);
			else
				lockAngle = 0;
		});
		lockTick.start();
		keyTick = new Timer(90, e->{
			if(Math.toDegrees(keyAngle)>=-360 && Math.toDegrees(keyAngle)<=360)
				keyAngle -= (keyAngleSpeed);
			else
				keyAngle = 0;
		});
		keyTick.start();
		lockTick.start();

		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "Hit");
		this.getActionMap().put("Hit", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("LockAngle : " + Math.toDegrees(lockAngle) + " KeyAngle " + Math.toDegrees(keyAngle));
				if(Math.toDegrees(keyAngle)<0&&Math.toDegrees(lockAngle)>0)
					checkForHit( Math.toDegrees(keyAngle) + 360,  Math.toDegrees(lockAngle));
				else if (Math.toDegrees(keyAngle)>0&&Math.toDegrees(lockAngle)<0)
					checkForHit( Math.toDegrees(keyAngle),  Math.toDegrees(lockAngle) + 360);
				else
					checkForHit( Math.toDegrees(keyAngle) + 360,  Math.toDegrees(lockAngle) + 360);
			}

		});

	}
	public void checkForHit(double keyAngle, double lockAngle){
		System.out.println("LockAngle : " + (lockAngle) + " KeyAngle " + (keyAngle));
		if(Math.abs((keyAngle) - (lockAngle))<=20){
			totalHits++;
			switch((int)(Math.random()*2)){
			case 0 : 
				keyAngleSpeed*=-1.1;
				lockAngleSpeed*=-1.1;
				break;
			case 1 : 
				keyAngleSpeed*=1.1;
				lockAngleSpeed*=1.1;
				break;
			}
		}else if(Math.abs(keyAngle)<20 && Math.abs(lockAngle)>340 || Math.abs(keyAngle)>340 && Math.abs(lockAngle)< 20){
			totalHits++;
			switch((int)(Math.random()*2)){
			case 0 : 
				keyAngleSpeed*=-1.1;
				lockAngleSpeed*=-1.1;
				break;
			case 1 : 
				keyAngleSpeed*=1.1;
				lockAngleSpeed*=1.1;
				break;
			}
		}else
			hitMiss();
	}
	public void hitMiss(){
		lockTick.stop();
		keyTick.stop();
		int reply = JOptionPane.showConfirmDialog(null, "You Missed!" + "\n Play Again?" , "Missed A Hit", JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION) {
			keyAngle = 0;
			lockAngle = 0;
			totalHits = 0;
			lockAngleSpeed = Math.PI/60;
			keyAngleSpeed = Math.PI/50;
			lockTick.start();
			keyTick.start();
		}
		else {
			System.exit(0);
		}
	}
	public synchronized void start(){
		isRunning = true;
		game = new Thread(this);
		game.start();
	}
	public synchronized void stop(){
		try{
			isRunning = false;
			game.join();
		}catch(Exception e){

		}
	}
	public void run(){
		while(isRunning){
			updatePanel();
			try{
				Thread.sleep(10);
			}catch(Exception e){

			}
		}
	}
	public void updatePanel(){
		repaint();
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		drawLock(g);
		drawKey(g);
		drawScore(g);
	}
	public void drawScore(Graphics g){
		g.setFont(new Font ("Garamond", Font.BOLD , 35));
		g.setColor(Color.BLACK);
		g.drawString("Hits : " + totalHits,-60,-100);
	}
	public void drawKey(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		AffineTransform oldXForm = g2d.getTransform();
		g2d.rotate(keyAngle);
		g2d.fill(key);
		g2d.setTransform(oldXForm);
	}
	public void drawLock(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.translate(WIDTH/2, HEIGHT/2);
		AffineTransform oldXForm = g2d.getTransform();
		g2d.rotate(lockAngle);
		g2d.fillOval(-250, -250, 500, 500);
		g2d.setColor(Color.WHITE);
		g2d.fillOval(-200, -200, 400, 400);
		g2d.fill(lock);
		g2d.setTransform(oldXForm);
	}
}
