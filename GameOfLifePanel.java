
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;

public class GameOfLifePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public int col =0, row = 0;
	public int scale = 5;
	public boolean[][][] board = new boolean[10*scale][20*scale][3];
	public boolean[][] evoBoard = new boolean[10*scale][20*scale];
	public boolean[][] decBoard = new boolean[10*scale][20*scale];
	File desktop = new File( System.getProperty("user.home") + "/Desktop/GameOfLifeStorage");
	public boolean alreadyDec = false;
	public boolean drawPhase = true;
	public int generation = 0;
	public int liveCells = 0;
	public int speed = 250;
	public Timer timer;
	public JSlider frames, resize;
	public String savedWork = "";
	public static void main(String[] args){
		 JFrame gameFrame = new JFrame("Conway's Game of Life");
	        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
	        GameOfLifePanel platformerGamePanel = new GameOfLifePanel(screenDim);
	        gameFrame.add(platformerGamePanel);
	        gameFrame.pack();
	        gameFrame.setVisible(true);
	        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	GameOfLifePanel(Dimension dimension) {
		this.setPreferredSize(dimension);
		setUpClickListener();
		setUpGrid();
		setUpTimer();
		setUpSavedWork();
		String[] maps = {"Glider" , "SpaceShip" ,"10CellRow", "GliderGun","Saved Work"};
		JComboBox<String> dropDown = new JComboBox<String>(maps);
		dropDown.setSelectedIndex(4);
		JButton increment = new JButton ("Increment");
		JButton clearBoard = new JButton ("Clear Board");
		JButton run = new JButton ("Run");
		JButton save = new JButton ("Save");
		JButton delete = new JButton ("Delete");
		JButton decrement = new JButton ("Decrement");
		frames = new JSlider(1, 1000);
		frames.addChangeListener(e -> sliderChanged() );
		resize = new JSlider(4, 10);
		resize.addChangeListener(e -> sliderChanged() );
		dropDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JComboBox<String> j = (JComboBox)event.getSource();
				String mapName = (String)j.getSelectedItem();
				if(mapName.equals("Saved Work")){
					mapName = JOptionPane.showInputDialog("Which one?..... \n " + savedWork);
				}
				load(mapName);
			}


		});
		clearBoard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				liveCells = 0;
				generation = 0;
				setUpGrid();
				repaint();
			}

		});
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String name = JOptionPane.showInputDialog("What do you want to Delete? \n " + savedWork);
				int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to Delete" , "Delete", JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					delete(name);
					savedWork = savedWork.replace(name + " ", "");
					setUpSavedWork();
					JOptionPane.showMessageDialog(null, "Deleted.");
				}

			}

		});
		decrement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(generation>0&&!alreadyDec&&!timer.isRunning()){
					alreadyDec = !alreadyDec;
					generation--;
					int tempCells = 0;
					for(int row = 0; row < board.length; row++){
						for(int col = 0; col < board[0].length; col++){
							if(decBoard[row][col])
								tempCells++;
							board[row][col][0] = decBoard[row][col];
							evoBoard[row][col] = decBoard[row][col];
						}
					}
					liveCells = tempCells;
					repaint();
				}
			}

		});
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to Save?" , "Save", JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					String name = JOptionPane.showInputDialog("What do you want to call it?");
					if(savedWork.indexOf(name)==-1)
						save(name);
					else
						JOptionPane.showMessageDialog(null, "Sorry, name is unavaiable");	
					setUpSavedWork();
					JOptionPane.showMessageDialog(null, "Saved.");
				}


			}

		});
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				alreadyDec = false;
				drawPhase = !drawPhase;
				if(!drawPhase)
					timer.start();
				else
					timer.stop();
				repaint();
			}

		});
		increment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				printBoard();
				alreadyDec = false;
				life();
			}

		});

		this.add(increment);
		this.add(decrement);
		this.add(run);
		this.add(clearBoard);
		this.add(frames);
		this.add(dropDown);
		this.add(save);
		this.add(delete);
		this.add(resize);
	}
	public void setUpGrid(){
		for(int row = 0; row < board.length; row++){
			for(col = 0; col < board[0].length; col++){
				board[row][col][0] = false;
				board[row][col][1] = false;
				board[row][col][2] = false;
				evoBoard[row][col] = false;
			}
		}

	}
	public void sliderChanged(){
		speed = frames.getValue();
		timer.setDelay(speed);
		scale = resize.getValue();
		scale*=2;;
		repaint();
	}

	private void setUpTimer() {
		timer = new Timer(501-speed, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				life();
			}

		});

	}
	public void setUpSavedWork(){
		try
		{
			
			
			BufferedReader reader = new BufferedReader(new FileReader(desktop));
			String line;
			while ((line = reader.readLine()) != null)
			{
				if(line.indexOf("!")!=-1){
					if(savedWork.indexOf(line.substring(1))==-1)
						savedWork+= line.substring(1)+" ";
				}
			}
			reader.close();
		}
		catch (Exception e)
		{
			System.err.format("Exception occurred trying to read '%s'.", "str/storage");
			e.printStackTrace();

		}
	}
	public void load(String mapName){
		try
		{
			
			BufferedReader reader = new BufferedReader(new FileReader(desktop));
			String line;
			boolean start = false;
			while ((line = reader.readLine()) != null)
			{

				if(line.indexOf("@")!=-1 && start){
					repaint();
					break;
				}
				if(start){
					int row = Integer.parseInt(line.substring(0, line.indexOf(" ")).trim());
					int col = Integer.parseInt(line.substring(line.indexOf(" ")).trim());
					board[row][col][0] = true;
					evoBoard[row][col] = true;
					liveCells++;
				}
				if(line.equals("!"+mapName)||line.equals(mapName))
					start = true;


			}
			reader.close();

		}
		catch (Exception e)
		{
			System.err.format("Exception occurred trying to read '%s'.", "str/storage");
			e.printStackTrace();

		}

	}
	public void save(String name) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(desktop,true));) {
			bw.newLine();
			bw.write("!"+name);
			for(int row = 0; row < board.length; row++){
				for(int col = 0 ;col < board[0].length; col++){
					if(board[row][col][0]){
						bw.newLine();
						bw.write(row+" "+col);
					}
				}
			}
			bw.newLine();
			bw.write("@Stop");
		} catch (IOException e) {

			e.printStackTrace();

		}
	}
	public void delete(String name){
		
		try {
			File tempFile = new File(desktop.getAbsolutePath() + ".tmp");
			BufferedWriter pw = new BufferedWriter(new FileWriter(tempFile,true));
			
			
			BufferedReader br = new BufferedReader(new FileReader(desktop));
			
			String line ;
			boolean start = false;
			while ((line = br.readLine()) != null) {
				if(line.equals("!"+name))
					start = true;
				if(!start){
					pw.write(line);
					pw.newLine();

				}
				if(line.indexOf("@")!=-1&&start){
					start = false;
				}
			}
			tempFile.renameTo(desktop);
			pw.close();
			br.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	private void setUpClickListener() {
		this.requestFocusInWindow();
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent arg0) {

			}

			@Override
			public void mousePressed(MouseEvent click) {
				if(click.getX() < 1330 || click.getX() > scale &&
						click.getY() < 670 && click.getY()> 70	&& drawPhase){
					col = (click.getX() - scale ) / scale;
					row = (click.getY() - 70 )/ scale;
					col--;
					board[row][col][0] = !board[row][col][0];
					evoBoard[row][col] = !evoBoard[row][col];
					if(board[row][col][0])
						liveCells++;
					else
						liveCells--;
				}
				repaint();
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

		});

	}

	public void life(){
		for(int row = 0; row < board.length; row++){
			for(col = 0; col < board[0].length; col++){
				if(!board[row][col][0]){
					checkForBirth(row, col);
				}
			}
		}
		for(int row = 0; row < board.length; row++){
			for(col = 0; col < board[0].length; col++){
				if(board[row][col][0]){
					checkForSurvival(row, col);
				}
			}
		}
		for(int row = 0; row < board.length; row++){
			for(col = 0; col < board[0].length; col++){
				decBoard[row][col] = board[row][col][0];
				board[row][col][0] = evoBoard[row][col];
			}
		}
		generation++;
		repaint();
	}

	public void checkForSurvival(int row, int col){
		int count = 0;	
		if(row!=0&&row!=board.length-1&&col!=0&&col!=board[0].length){
			if(board[row+1][col][0])
				count++;
			if(board[row-1][col][0])
				count++;
			if(board[row][col+1][0])
				count++;
			if(board[row][col-1][0])
				count++;
			if(board[row+1][col+1][0])
				count++;
			if(board[row-1][col-1][0])
				count++;
			if(board[row-1][col+1][0])
				count++;
			if(board[row+1][col-1][0])
				count++;
		}
		if(count!=3 && count!=2){
			board[row][col][1] = true;
			evoBoard[row][col] = false;
			liveCells--;
		}
	}
	public void checkForBirth(int row, int col){
		int count = 0;

		if(row!=0&&row!=board.length-1&&col!=0&&col!=board[0].length-1){
			if(board[row+1][col][0])
				count++;
			if(board[row-1][col][0])
				count++;
			if(board[row][col+1][0])
				count++;
			if(board[row][col-1][0])
				count++;
			if(board[row+1][col+1][0])
				count++;
			if(board[row-1][col-1][0])
				count++;
			if(board[row-1][col+1][0])
				count++;
			if(board[row+1][col-1][0])
				count++;
		}
		if(count==3){
			evoBoard[row][col] = true;
			liveCells++;
		}
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setFont(new Font ("Garamond", Font.BOLD , 15));
		g.drawString("Draw Enabled : " + drawPhase , 10, 20);
		g.drawString("Generation " + generation, 10, 40);
		g.drawString("Live Cells : " + liveCells, 10, 60);
		g.drawString("Speed " + (1001-speed) , 600, 40);
		g.drawString("resize " + (scale) , 1100, 40);
		drawGrid(g);
	}
	public void drawGrid(Graphics g ){
		int incrementX = scale;
		int incrementY = 70;
		for(int row = 0; row <board.length; row++){
			for(col = 0; col < board[0].length; col++){
				g.setColor(Color.black);
				g.drawRect(incrementX+=scale, incrementY, scale, scale);
				if(board[row][col][0]){
					switch((int)(Math.random()*7)){
					case 0: g.setColor(Color.yellow);break;
					case 1: g.setColor(Color.red);break;
					case 2: g.setColor(Color.blue);break;
					case 3: g.setColor(Color.green);break;
					case 4: g.setColor(Color.pink);break;
					case 5: g.setColor(Color.orange);break;
					case 6: g.setColor(Color.magenta);break;
					}
					g.fillRect(incrementX+1, incrementY+1, scale-2, scale-2);

				}else if(board[row][col][1]){
					g.setColor(Color.gray);
					g.fillRect(incrementX+1, incrementY+1, scale-2, scale-2);
				}

			}

			incrementX =scale;
			incrementY+=scale;
		}
	}
	public void printBoard(){
		for(int row = 0; row < board.length; row++){
			for(col = 0; col < board[0].length; col++){
				String text = (board[row][col][0])?"O":"_";
				System.out.print(text);
			}
			System.out.println();
		}
	}
}
