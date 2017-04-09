import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
@SuppressWarnings("serial")
public class SnakePanel extends JPanel implements Runnable {
	Thread t;
	boolean isRunning;
	boolean dead = false;
	Directions d  = Directions.RIGHT;
	int snakeLength = 0;
	volatile int snakeHeadX = 100;
	volatile int snakeHeadY = 100;
	volatile int foodX;
	volatile int foodY;
	final int FOOD_SIZE = 20;
	final int PIECESIZE = 20;
	Timer time;
	HashMap<Integer, SnakeSegment> snake = new HashMap<Integer, SnakeSegment>();
	public static void main(String[] args){
		new SnakePanel();
	}
	public SnakePanel(){
		setUpPanel();
		addButton();
		setUpKeys();
		setUpSnake();
		spawnFood();
		start();
	}
	public void addButton(){
		JButton pause = new JButton("Pause");
		pause.setBounds(0, 0, 90, 30);
		pause.setBackground(Color.WHITE);
		pause.addActionListener(e ->{
			if(isRunning)
				stop();
			else
				start();
		});
		JButton restart = new JButton("Restart");
		restart.setBounds(100, 0, 90, 30);
		restart.setBackground(Color.WHITE);
		restart.addActionListener(e->{
			stop();
			snake.clear();
			d = Directions.DOWN;
			snakeLength = 0;
			snakeHeadX = 100;
			snakeHeadY = 100;
			setUpSnake();
			spawnFood();
			start();
		});
		this.add(restart);
		this.add(pause);
	}
	public void spawnFood(){
		foodX = (int)(Math.random()*500)+50;
		foodY = (int)(Math.random()*500)+50;
		
		if(foodX%2!=1||foodY%2!=1){
			spawnFood();
			return;
		}
		if(hitHead(foodX+FOOD_SIZE/2,foodY+FOOD_SIZE/2)){
			spawnFood();
			return;
		}
			
		
		
	}
	public void setUpSnake(){
		snake.put(snakeLength++ , new SnakeSegment(Directions.RIGHT));
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
		time = new Timer(1, e->{
			checkIfHitHead();
		});
	}
	public void setUpPanel(){
		JFrame frame = new JFrame("Snake!");
		frame.setPreferredSize(new Dimension(600,600));
		System.setProperty("sun.java2d.opengl", "true");
		frame.add(this);
		this.setBackground(Color.BLACK);
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
		time.start();
	}
	public synchronized void stop(){
		isRunning = false;
		time.stop();
		try{
			t.join();
		}catch(Exception e){
			e.printStackTrace();
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
		checkIfDead();
		repaint();
	}
	public void checkIfDead(){
		if(dead){
				int reply = JOptionPane.showConfirmDialog(null, "You Died" + "\n Play Again?" , "Death", JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					dead = false;
					d = Directions.DOWN;
					snake.clear();
					snakeLength = 0;
					snakeHeadX = 100;
					snakeHeadY = 100;
					setUpSnake();
					spawnFood();
					start();
					return;
				}
				else {
					System.exit(0);
				}
				stop();
			}
	}
	public void checkIfHitHead(){
		if(hitHead(foodX+FOOD_SIZE/2,foodY+FOOD_SIZE/2)){
			spawnFood();
			addToSnake();
		}
	}
	public void addToSnake(){
		snake.put(snakeLength, new SnakeSegment(snake.get(snakeLength-1).getDirections()));
		snakeLength++;
	}
	public void moveSnake(){
		snake.get(0).setDirections(d);
		Directions temp = null;
		for(int i = snakeLength-2; i>=0; i--){
			if(!snake.get(i).getDirections().equals(temp)&&!snake.get(i+1).getDirections().equals(snake.get(i).getDirections())){
			snake.get(i+1).setDirections(snake.get(i).getDirections());
			temp = snake.get(i).getDirections();
			}		
		}
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		drawSnake(g);
		drawFood(g);
	}
	public void drawFood(Graphics g){
		g.setColor(Color.YELLOW);
		g.fillOval(foodX, foodY, FOOD_SIZE, FOOD_SIZE);
	}
	public boolean hitHead(int objX, int objY){
		int sHX1 = snakeHeadX;
		int sHY1 = snakeHeadY;
		int sHX2 = snakeHeadX+PIECESIZE;
		int sHY2 = snakeHeadY+PIECESIZE;
		if(objX>sHX1&&objX<sHX2&&objY>sHY1&&objY<sHY2){
			return true;
		}
		return false;
	}
	public boolean hitTail(int sHS1, int sHS2){
		int sTX1 = sHS1;
		int sTY1 = sHS2;
		int sTX2 = sHS1+PIECESIZE;
		int sTY2 = sHS2+PIECESIZE;
		if(foodX+FOOD_SIZE/2>sTX1&&foodX+FOOD_SIZE/2<sTX2&&foodY+FOOD_SIZE/2>sTY1&&foodY+FOOD_SIZE/2<sTY2){
			return true;
		}
		return false;
	}
	public synchronized void drawSnake(Graphics g){
		g.setFont(new Font ("Garamond", Font.BOLD , 20));
		try{
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
		}catch(Exception e){
			
		}
		g.setColor(Color.WHITE);
		g.fillOval(snakeHeadX, snakeHeadY, PIECESIZE, PIECESIZE);
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
			if(hitHead(snakeSegX+PIECESIZE/2, snakeSegY+PIECESIZE/2)||snakeHeadX<0||snakeHeadX>600||snakeHeadY<0||snakeHeadY>600){
				dead = true;
			}
			if(hitTail(snakeSegX,snakeSegY)){
				spawnFood();
			}
			g.fillOval(snakeSegX, snakeSegY, PIECESIZE, PIECESIZE);
			g.setColor(Color.WHITE);
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
