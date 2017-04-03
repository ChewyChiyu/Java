import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Calculator extends JPanel{
	StringBuilder line = new StringBuilder();
	public static void main(String[] args){
		new Calculator();
	}
	public Calculator(){
		setUpGrid();
		setUpPanel();
		repaint();
	}
	public void setUpGrid(){
		JFrame frame = new JFrame("Calculator");
		frame.add(this);
		this.setLayout(null);
		frame.setPreferredSize(new Dimension(500,500));
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	public void setUpPanel(){
		int index = 0;
		int xBuffer = 0;
		int yBuffer = 100;
		final int SPACER = 100;
		JButton[] buttons = { new JButton("0"), new JButton("1"), new JButton("2"), 
				new JButton("3"), new JButton("4"), new JButton("5"), 
				new JButton("6"), new JButton("7"), new JButton("8"), 
				new JButton("9"), new JButton("+"), new JButton("-"), 
				new JButton("/"), new JButton("*"), new JButton("Clear"), 
				new JButton("Enter") };
		for(JButton b : buttons){
			b.setName(""+index++);
			if(!b.getName().equals("15"))
				b.setBounds(xBuffer, yBuffer, 100, 100);
			else
				b.setBounds(xBuffer, yBuffer, 500, 100);
			xBuffer+=SPACER;
			if(xBuffer >= 500){
				xBuffer = 0;
				yBuffer+=SPACER;
			}
			b.addActionListener(e ->{
				switch(b.getName()){
				case "10": line.append("+"); break;
				case "11":  line.append("-"); break;
				case "12": line.append("/"); break;
				case "13":  line.append("*"); break;
				case "14": line = new StringBuilder(); break;
				case "15":  calculate(); break;
				default: line.append(b.getName()); break;
				}
				repaint();
			});
			this.add(b);

		}
	}
	public void calculate(){
		int j = 0;
		HashMap<Integer, StringBuilder> sequence = new HashMap<Integer, StringBuilder>();
		StringBuilder b = new StringBuilder();
		for(int i = 0; i < line.length(); i++){
			if(line.charAt(i)!='+'&&line.charAt(i)!='-'&&line.charAt(i)!='/'&&line.charAt(i)!='*'){
				b.append(line.charAt(i));
			}
			else{
				sequence.put(j++, b);
				sequence.put(j++, new StringBuilder(line.substring(i,i+1)));
				System.out.println(line.charAt(i));
				b = new StringBuilder();
			}
			if(i==line.length()-1){
				sequence.put(j++, b);
			}
		}
		String calculation = null;
		String[] b2 = new String[sequence.size()];
		int index = 0;
		for(StringBuilder b3 : sequence.values()){
			b2[index++] = b3.toString();
		}
		for(int i = 0 ; i < b2.length-1; i++){
			if(b2[i].equals("+")){
				if(calculation==null){
					calculation = ""+(Double.parseDouble(b2[i-1]) + Double.parseDouble(b2[i+1]));
				}else
					calculation = ""+(Double.parseDouble(calculation) + Double.parseDouble(b2[i+1]));
			}
			if(b2[i].equals("-")){
				if(calculation==null){
					calculation = ""+(Double.parseDouble(b2[i-1]) - Double.parseDouble(b2[i+1]));
				}else
					calculation = ""+(Double.parseDouble(calculation) - Double.parseDouble(b2[i+1]));
			}
			if(b2[i].equals("*")){
				if(calculation==null){
					calculation = ""+(Double.parseDouble(b2[i-1]) * Double.parseDouble(b2[i+1]));
				}else
					calculation = ""+(Double.parseDouble(calculation) * Double.parseDouble(b2[i+1]));
			}
			if(b2[i].equals("/")){
				if(calculation==null){
					calculation = ""+(Double.parseDouble(b2[i-1]) / Double.parseDouble(b2[i+1]));
				}else
					calculation = ""+(Double.parseDouble(calculation) / Double.parseDouble(b2[i+1]));
			}
		}
		line = new StringBuilder(calculation);
		repaint();
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		drawScreen(g);
	}
	public void drawScreen(Graphics g){
		g.setColor(Color.white);
		g.fillRect(0, 0, 500, 100);
		g.setColor(Color.black);
		g.setFont(new Font ("Garamond", Font.BOLD , 25));
		g.drawString(line.toString(), 10, 60);
	}

}

