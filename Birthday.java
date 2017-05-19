import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.time.Month;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingConstants;

public class Birthday {
	JFrame frame = new JFrame("Birtyday");
	CardLayout cardLay = new CardLayout();
	JPanel masterPane = new JPanel(cardLay);
	private int year;
	private int month;
	private int day;
	public static void main(String[] args){
		new Birthday();
	}
	Birthday(){

		panel();

	}
	void panel(){
		year();
		month();

		frame.add(masterPane);
		frame.setPreferredSize(new Dimension(300,400));
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	void process(){

		LocalDateTime dateTime = LocalDateTime.of(year, month, day, 0, 0);	
		System.out.println(dateTime.getDayOfWeek());
		JOptionPane.showMessageDialog(null,
				"Was born on a " + dateTime.getDayOfWeek() ,
				"Day of the Week",
				JOptionPane.DEFAULT_OPTION);            

	}
	void day(){
		int[] days = {31,(year%4==0)?29:28,30,30,31,30,31,31,30,31,30,31};
		System.out.println(days[month-1]);
		JPanel day = new JPanel(new BorderLayout());
		JPanel dayNumbers = new JPanel(new GridLayout(days[month-1],1));
		JLabel dayLabel = new JLabel("INPUT DAY", SwingConstants.CENTER);
		for(int index = days[month-1]; index >=1 ; index--){
			JButton d = new JButton(""+index);
			d.setName(""+index);
			d.addActionListener(e->{
				this.day = Integer.parseInt(d.getName());
				process();
			});
			dayNumbers.add(d);
		}

		JScrollPane scrollDay = new JScrollPane(dayNumbers, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollDay.getViewport ().setScrollMode (JViewport.BACKINGSTORE_SCROLL_MODE );
		scrollDay.getViewport ().setScrollMode (JViewport.SIMPLE_SCROLL_MODE );

		day.add(scrollDay,BorderLayout.CENTER);
		day.add(dayLabel,BorderLayout.NORTH);
		masterPane.add(day, "DAY");
	}
	void month(){
		JPanel month = new JPanel(new BorderLayout());
		JPanel monthNumbers = new JPanel(new GridLayout(12,1));
		JLabel monthLabel = new JLabel("INPUT MONTH", SwingConstants.CENTER);
		String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		for(int index = 0; index < months.length; index++){
			JButton m = new JButton(months[index]);
			m.setName(""+(index+1));
			m.addActionListener(e->{
				this.month = Integer.parseInt(m.getName());
				System.out.println(this.month);
				day();
				cardLay.show(masterPane, "DAY");
			});
			monthNumbers.add(m);
		}
		JScrollPane scrollMonth = new JScrollPane(monthNumbers, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollMonth.getViewport ().setScrollMode (JViewport.BACKINGSTORE_SCROLL_MODE );
		scrollMonth.getViewport ().setScrollMode (JViewport.SIMPLE_SCROLL_MODE );

		month.add(scrollMonth,BorderLayout.CENTER);
		month.add(monthLabel,BorderLayout.NORTH);
		masterPane.add(month, "MONTH");

	}
	void year(){
		JPanel year = new JPanel(new BorderLayout());
		JPanel yearNumbers = new JPanel(new GridLayout(1001,1));
		JLabel yearLabel = new JLabel("INPUT YEAR", SwingConstants.CENTER);
		for(int index = 2500; index >= 1500; index--){
			JButton y = new JButton(""+index);
			y.setName(""+index);
			y.addActionListener(e->{
				System.out.println(y.getName());
				this.year = Integer.parseInt(y.getName());
				cardLay.show(masterPane, "MONTH");
			});
			yearNumbers.add(y);
		}	
		JScrollPane scrollYear = new JScrollPane(yearNumbers, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollYear.getViewport ().setScrollMode (JViewport.BACKINGSTORE_SCROLL_MODE );
		scrollYear.getViewport ().setScrollMode (JViewport.SIMPLE_SCROLL_MODE );

		year.add(scrollYear,BorderLayout.CENTER);
		year.add(yearLabel,BorderLayout.NORTH);
		masterPane.add(year, "YEAR");

	}
}
