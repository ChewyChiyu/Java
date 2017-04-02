import java.awt.*;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
/* One of the tricky things you have to figure out is how to have
 * the controls in the control panel talk to the KnightsTourPanel.
 * I know you'll figure out a way.  DON'T USE STATIC METHODS!!!!!
 */
import javax.swing.JSlider;
import javax.swing.Timer;
public class KnightsTourControlPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public JRadioButton random, algro;
	public JButton step, run, pause, clear, changeGrid;
	public Timer t;
	public JSlider frames;
	JFrame frame = new JFrame("Resize menu");
	public ButtonGroup b;
	public int speed = 250, w, h;
	public KnightsTourPanel k;
	public KnightsTourControlPanel(int w, int h,KnightsTourPanel k) {
		this.setPreferredSize(new Dimension(w, h));
		this.setBackground(Color.orange);
		this.k = k;
		this.w = w;
		this.h = h;
		new Resize();
		this.setLayout(null);
		frame.setVisible(false);
		setUpButtonsAndSliders();
		repaint();

	}

	/* Add all the buttons and sliders used to control this Knight's tour.
	 * It is best if you allow the placement of the components to be handled
	 * by a layout manager.  You can find out lots about layouts if you google
	 * it!  You can also bind key events to the buttons and sliders, as well
	 */
	public void setUpButtonsAndSliders() {
		b = new ButtonGroup();
		step = new JButton("Step");
		pause = new JButton("Pause");
		run = new JButton("Run");
		clear = new JButton("Clear");
		frames = new JSlider(1, 500);
		random = new JRadioButton();
		changeGrid = new JButton("Resize");
		algro = new JRadioButton();
		frames.setBounds(300, 10, 300, 50);
		random.setBounds(750,10,20,20);
		changeGrid.setBounds(300,70,100,50);
		run.setBounds(150,10,100,50);
		algro.setBounds(750,80,20,20);
		step.setBounds(10,10,100,50);
		pause.setBounds(150,70,100,50);
		clear.setBounds(10,70,100,50);
		b.add(random);
		b.add(algro);
		random.setSelected(true);
		step.setSelected(true);
		frames.addChangeListener(e -> {
			speed = frames.getValue();
			t.setDelay(frames.getValue());
			repaint();
		});
		changeGrid.addActionListener(e -> {
			if(!frame.isVisible())
				frame.setVisible(true);
			else
				frame.setVisible(false);
		});
		step.addActionListener(e -> {
			if(k.knightPlaced){
				if(random.isSelected())
					k.makeRandomMove();
				else
					k.makeThoughtfulMove();

			}
		});
		t = new Timer(speed, (e -> {
			if(random.isSelected()){
				if(!k.makeRandomMove())
					t.stop();
			}else{
				if(!k.makeThoughtfulMove())
					t.stop();
			}

		}));

		pause.addActionListener(e -> {
			t.stop();

		});
		run.addActionListener(e -> {
			if(k.knightPlaced)
				t.start();
		});
		clear.addActionListener(e -> {
			if(t.isRunning())
				t.stop();
			k.clearAll();
			k.repaint();

		});
		this.add(changeGrid);
		this.add(random);
		this.add(algro);
		this.add(frames);
		this.add(run);
		this.add(step);
		this.add(pause);
		this.add(clear);
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setFont(new Font ("Garamond", Font.BOLD , 15));
		g.drawString("Speed : " + (500-speed), 410, 75);
		g.drawString("Random", 660, 30);
		g.drawString("Algorithm",660, 100);

	}
	class Resize extends JPanel{
		public Resize(){
			frame.setPreferredSize(new Dimension(400,200));
			frame.setResizable(false);
			frame.add(this);
			this.setBackground(Color.orange);
			this.setLayout(null);
			setUpGUI();
			frame.pack();
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
		public void setUpGUI(){

			JButton generate = new JButton("New Grid");
			JButton rightTriangle = new JButton("New Triangle");
			JButton diamond = new JButton("New Diamond");
			JButton circle = new JButton("New Circle");
			circle.setBounds(190, 100, 130, 50);
			diamond.setBounds(150,20,150,50);
			generate.setBounds(20,20,100,50);
			rightTriangle.setBounds(20,100,130,50);
			
			diamond.addActionListener(e -> {k.gridType = 2; generate(); k.setUpBoard();}); 
			rightTriangle.addActionListener(e -> {k.gridType = 1; generate();k.setUpBoard();});
			circle.addActionListener(e -> {k.gridType = 3; generate();k.setUpBoard();});
			generate.addActionListener( e -> {k.gridType = 0; generate();k.setUpBoard();});
			this.add(generate);
			this.add(rightTriangle);
			this.add(diamond);
			this.add(circle);
		}
		public void generate(){
			if(k.gridType==3){
				String radius = JOptionPane.showInputDialog("Please input a Radius");
				k.board = new boolean[Integer.parseInt(radius)*2+1][Integer.parseInt(radius)*2+1][2];
				if(Integer.parseInt(radius)>5){
					int i = (int) ((w*h)/((Integer.parseInt(radius)*Integer.parseInt(radius)*(Integer.parseInt(radius) * .4 ))));
					k.SQUARE_LENGTH = (int) (Math.sqrt(i)*1.4);
				}else{
					k.SQUARE_LENGTH = 70; //Standard Value
				}
				k.knightPlaced = false;
				k.knightJumps = 0;
				k.repaint();
				return;
			}
			String row  = JOptionPane.showInputDialog("Please input a row number");
			String col  = JOptionPane.showInputDialog("Please input a col number");
			k.board = new boolean[Integer.parseInt(row)][Integer.parseInt(col)][2];
			if(k.gridType == 2 && ((Integer.parseInt(row)!=Integer.parseInt(col)) || Integer.parseInt(row)%2!=0)){
				JOptionPane.showMessageDialog(null,"Invalid Diamond, Please use equal row and col with even numbers");
				generate();
				return;
			}
			if(Integer.parseInt(row)>9||Integer.parseInt(col)>9){
				int i = (w*h)/(Integer.parseInt(row)*Integer.parseInt(col));
				k.SQUARE_LENGTH = (int) (Math.sqrt(i)*1.48);
			}else{
				k.SQUARE_LENGTH = 70; //Standard Value
			}
			k.knightPlaced = false;
			k.knightJumps = 0;
			k.repaint();
		}
	}


}

