import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.time.LocalDateTime;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
@SuppressWarnings("serial")
public class Clock extends JPanel{
	Timer prompt;
	int hour;
	int minute;
	int second;
	double secondAngle;
	double minuteAngle;
	double hourAngle;
	public static void main(String[] args){
		new Clock();
	}
	public Clock(){
		setUpPanel();
		setUpTimer();
		repaint();
	}
	public void setUpTimer(){
		prompt = new Timer(1000, e->{
			hour = LocalDateTime.now().getHour();
			minute = LocalDateTime.now().getMinute();
			second = LocalDateTime.now().getSecond();
			secondAngle = (Math.PI/60)*(second);
			minuteAngle = (Math.PI/60)*(minute);
			hourAngle = (Math.PI/60)*(hour);
			System.out.println("Hour: " + hour + " Minute: " + minute + " Second : " + second);
			repaint();
		});
		prompt.start();
	}
	public void setUpPanel(){
		JFrame frame = new JFrame("Clock");
		frame.add(this);
		frame.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("X : " + e.getX() + " Y : " + e.getY());

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

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
	public void paintComponent(Graphics  g){
		super.paintComponent(g);
		drawTime(g);
	}
	public void drawTime(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.translate(630, 330);
		AffineTransform oldXForm = g2d.getTransform();
		Rectangle rectSecond = new Rectangle(0, -300, 5, 300);
		Rectangle rectMinute = new Rectangle(0,-300,10,300);
		Rectangle rectHour = new Rectangle(0,-200,10,200);
		
		g2d.rotate(secondAngle*2);
		g2d.fill(rectSecond);
		g2d.setTransform(oldXForm); 
		g2d.rotate(minuteAngle*2);
		g2d.fill(rectMinute);
		g2d.setTransform(oldXForm); 
		g2d.rotate(hourAngle*10);
		g2d.fill(rectHour);
		g2d.setTransform(oldXForm); 
		
	}
}
