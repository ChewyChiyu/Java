import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SketchPanel extends JPanel{
	StringBuilder selectedColor = new StringBuilder("Black");
	private static final long serialVersionUID = 1L;
	public Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	final int XGRID = d.width; final int YGRID = d.height;
	Pixel[][] grid = new Pixel[YGRID][XGRID];
	public static void main(String[] args){
		new SketchPanel();
	}
	public SketchPanel(){
		setUpPanel();
		setUpButtons();
		setUpColors();
	}
	public void setUpButtons(){
		JButton clear = new JButton("Clear");
		clear.setBounds(0,70,100,50);
		clear.addActionListener(e-> {cleanPanel();repaint();});
		this.add(clear);
	}
	public void setUpPanel(){
	for(int i = 0; i < grid.length; i++){
		for(int j = 0 ; j<grid[0].length; j++){
			grid[i][j] = new Pixel();
		}
	}
	cleanPanel();
	JFrame frame = new JFrame("Paint Panel!");
	frame.setPreferredSize(d);
	frame.add(this);
	this.setBackground(Color.white);
	this.setLayout(null);
	frame.pack();
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setVisible(true);
	frame.setResizable(false);
	frame.addMouseMotionListener(new MouseAdapter() {
        	public void mouseDragged(MouseEvent e) {
        	    if(e.getX()<XGRID&&e.getX()>0&&e.getY()<YGRID&&e.getY()>0){
        	    	switch(selectedColor.toString()){
        	    	case "Red" : grid[e.getY()][e.getX()].setColor(Color.red);break;
        	    	case "Orange" : grid[e.getY()][e.getX()].setColor(Color.orange);break;
        	    	case "Yellow" : grid[e.getY()][e.getX()].setColor(Color.yellow);break;
        	    	case "Green" : grid[e.getY()][e.getX()].setColor(Color.green);break;
        	    	case "Blue" : grid[e.getY()][e.getX()].setColor(Color.blue);break;
        	    	case "Purple" : grid[e.getY()][e.getX()].setColor(new Color(230, 0, 172));break;
        	    	case "White" : grid[e.getY()][e.getX()].setColor(new Color(255,254,255));break;
        	    	case "Gray" : grid[e.getY()][e.getX()].setColor(Color.gray);break;
        	    	case "Black" : grid[e.getY()][e.getX()].setColor(Color.black);break;

        	    	}
        	    }
        		repaint();
        	    }
		});
	}
	public void cleanPanel(){
		for(int row = 0; row < grid.length; row++){
			for(int col = 0; col < grid[0].length; col++){
				grid[row][col].setColor(Color.white);
			}
		}
	}
	public void setUpColors(){
		int index = 0;
		JButton[] colors = {new JButton(),new JButton(),new JButton(),new JButton(),new JButton(),new JButton(),new JButton()
							,new JButton(),new JButton()}; 	
		int xBounds = 0;
		int yBounds = 0;
		final int SPACER = 151;
		for(JButton b : colors){
			b.setName(""+index++);
			b.setBounds(xBounds, yBounds, SPACER, SPACER/3);
			xBounds+=SPACER;
			switch(index){
			case 1: b.setBackground(Color.red);break; 
			case 2:b.setBackground(Color.orange);break; 
			case 3:b.setBackground(Color.yellow);break; 
			case 4:b.setBackground(Color.green);break; 
			case 5:b.setBackground(Color.blue);break; 
			case 6:b.setBackground(new Color(230, 0, 172));break; 
			case 7:b.setBackground(Color.white);break; 
			case 8:b.setBackground(Color.gray);break; 
			case 9:b.setBackground(Color.black);break; 
			}
			b.addActionListener(e->{
				
				switch(b.getName()){
				case "0":selectedColor = new StringBuilder("Red");break;
				case "1":selectedColor = new StringBuilder("Orange");break;
				case "2":selectedColor = new StringBuilder("Yellow");break;
				case "3":selectedColor = new StringBuilder("Green");break;
				case "4":selectedColor = new StringBuilder("Blue");break;
				case "5":selectedColor = new StringBuilder("Purple");break;
				case "6":selectedColor = new StringBuilder("White");break;
				case "7":selectedColor = new StringBuilder("Gray");break;
				case "8":selectedColor = new StringBuilder("Black");break;
				}
			});
			this.add(b);
		}
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		drawGrid(g);
	}
	
	public void drawGrid(Graphics g){
		final int SPACER = 25;
		for(int row = 0; row < grid.length; row++){
			for(int col = 0; col < grid[0].length; col++){
				if(!grid[row][col].getColor().equals(Color.white)){
					g.setColor(grid[row][col].getColor());
					g.fillOval(col, row-SPACER, 10, 10);			
				}

			}
		}
	}
	
}
class Pixel {
	private Color c;
	public Color getColor(){
		return c;
	}
	public void setColor(Color c){
		this.c = c;
	}
}
