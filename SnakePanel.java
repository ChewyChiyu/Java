import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
@SuppressWarnings("serial")
public class SnakePanel extends JPanel implements Runnable {
	Thread t;
	boolean isRunning;
	Directions d  = Directions.RIGHT;
	int snakeLength = 0;
	int snakeHeadX = 100;
	int snakeHeadY = 100;
	final int PIECESIZE = 100;
	HashMap<Integer, SnakeSegment> snake = new HashMap<Integer, SnakeSegment>();
	public static void main(String[] args){
		new SnakePanel();
	}
	public SnakePanel(){
		setUpPanel();
		setUpKeys();
		setUpSnake();
		start();
		
	}
	public void setUpSnake(){
		snake.put(snakeLength++ , new SnakeSegment(Directions.RIGHT));
		addToSnake();
		addToSnake();
		addToSnake();
		addToSnake();
		addToSnake();
		addToSnake();
		addToSnake();
		addToSnake();
		addToSnake();
		addToSnake();
		addToSnake();
		addToSnake();
		addToSnake();
		addToSnake();



	}
	public void setUpKeys(){
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
				if(!d.equals(Directions.DOWN)){
					d = Directions.UP;
				}
			}

		});
		this.getActionMap().put("D",
				new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!d.equals(Directions.LEFT)){
					d = Directions.RIGHT;
				}
			}

		});
		this.getActionMap().put("S",
				new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!d.equals(Directions.UP)){
					d = Directions.DOWN;
				}
			}

		});
		this.getActionMap().put("A",
				new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!d.equals(Directions.RIGHT)){
					d = Directions.LEFT;
				}
			}

		});
	}
	public void setUpPanel(){
		JFrame frame = new JFrame("Snake!");
		frame.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		System.setProperty("sun.java2d.opengl", "true");
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}
	public synchronized void start(){
		isRunning = true;
		t = new Thread(this);
		t.start();
	}
	public synchronized void stop(){
		try{
			t.join();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			isRunning = false;
		}
	}
	@Override
	public void run(){
		while(isRunning){
			update();
			try{
				Thread.sleep(100);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public void update(){
		moveSnake();
		repaint();
	}
	public void addToSnake(){
		snake.put(snakeLength, new SnakeSegment(snake.get(snakeLength-1).getDirections()));
		snakeLength++;
	}
	public void moveSnake(){
		snake.get(0).setDirections(d);
		int diff = 0;
		for(int i = 0; i < snakeLength-1; i++){
			if(!snake.get(i).getDirections().equals(snake.get(i+1).getDirections())){
				diff++;
			}
		}
		System.out.println("Differences : " + diff);
		Directions temp = null;
		for(int i = snakeLength-2; i>=0; i--){
			if(!snake.get(i).getDirections().equals(temp)&&!snake.get(i+1).getDirections().equals(snake.get(i).getDirections())){
			snake.get(i+1).setDirections(snake.get(i).getDirections());
			temp = snake.get(i).getDirections();
			}
			System.out.println(i);
		
		}
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		printSnake();
		drawSnake(g);
	}
	public void printSnake(){
		System.out.println("\n\n");
		int index = 0;
		for(SnakeSegment s : snake.values()){
			System.out.println("Snake segment " + index++ + " direction : " + s.getDirections() );
		}
	}
	public void drawSnake(Graphics g){
		g.setFont(new Font ("Garamond", Font.BOLD , 20));
		switch(snake.get(0).getDirections()){
		case UP:   
			snakeHeadY-=PIECESIZE;
			break;
		case DOWN: 
			snakeHeadY+=PIECESIZE;
			break;
		case LEFT: 
			snakeHeadX-=PIECESIZE;
			break;
		case RIGHT:
			snakeHeadX+=PIECESIZE;
			break;
		}
		g.fillRect(snakeHeadX, snakeHeadY, PIECESIZE, PIECESIZE);
		g.setColor(Color.WHITE);
		g.drawString("0", snakeHeadX + PIECESIZE/2, snakeHeadY + PIECESIZE/2);
		g.setColor(Color.BLACK);
		int snakeSegX = snakeHeadX;
		int snakeSegY = snakeHeadY;
		
		int index = 0;
		for(SnakeSegment s : snake.values()){
			if(index++!=0){
			switch(s.getDirections()){
			case UP:   
				snakeSegY+=PIECESIZE;
				break;
			case DOWN: 
				snakeSegY-=PIECESIZE;
				break;
			case LEFT: 
				snakeSegX+=PIECESIZE;
				break;
			case RIGHT:
				snakeSegX-=PIECESIZE;
				break;
			}
			g.fillRect(snakeSegX, snakeSegY, PIECESIZE, PIECESIZE);
			g.setColor(Color.WHITE);
			g.drawString(""+(index-1), snakeSegX+ PIECESIZE/2, snakeSegY+PIECESIZE/2);
			g.setColor(Color.BLACK);
			}
		}
	}
}
class SnakeSegment{
	private Directions Directions;
	public SnakeSegment(Directions Directions){
		this.Directions = Directions;
	}
	public Directions getDirections(){
		return Directions;
	}
	public void setDirections(Directions d){
		Directions = d;
	}
}
enum Directions{
	UP , DOWN, LEFT, RIGHT;
}
