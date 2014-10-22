import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


public class ShutDownScheduler extends JFrame
{
	private JPanel pane;
	private JLabel introLabel,hourLabel,minLabel,actionLabel,timeToLabel,countDownLabel,actionSelectedLabel;
	private JComboBox hourComboBox,minuteComboBox,actionComboBox;
	private String actions[]={"Shut Down","Log off","Restart"};
	private JButton startBtn,cancelBtn;
	private Timer timer=null;
	private int milliseconds;
	
	
	public ShutDownScheduler()
	{
		super("CountDown");
		
		//Instantiate the global variables
		pane=new JPanel(null);	
		introLabel=new JLabel("SELECT THE TIME AND ACTION\n");
		hourLabel=new JLabel("HOURS");
		minLabel=new JLabel("MINUTES");
		actionLabel=new JLabel("ACTION");
		hourComboBox= new JComboBox();
		minuteComboBox= new JComboBox();
		actionComboBox= new JComboBox();
		startBtn=new JButton("START");
		cancelBtn=new JButton("CANCEL");
		countDownLabel=new JLabel("00:00:00");
		actionSelectedLabel=new JLabel("Action Selected");
		
		//timeToLabel=new JLabel("The computer will "+actionComboBox.getSelectedItem().toString() + "in: ");
		
		//set components size
		this.setSize(400, 400);
		introLabel.setSize(300,20);
		hourLabel.setSize(50,50);
		minLabel.setSize(70,50);
		actionLabel.setSize(70,50);
		hourComboBox.setSize(50,20);
		minuteComboBox.setSize(50,20);
		actionComboBox.setSize(90,20);
		//timeToLabel.setSize(200,20);
		startBtn.setSize(90,20);
		cancelBtn.setSize(90,20);
		countDownLabel.setSize(250, 100);
		actionSelectedLabel.setSize(130, 35);
		
		//set font size
		Font fontClock = new Font("Comic",Font.BOLD + Font.ITALIC,34);
		countDownLabel.setFont(fontClock);
		Font fontAction = new Font("Comic",Font.BOLD + Font.ITALIC,14);
		actionSelectedLabel.setFont(fontAction);
		
		
		//set components locations
		introLabel.setLocation(100,25);
		hourLabel.setLocation(80,60);
		minLabel.setLocation(160,60);
		actionLabel.setLocation(240,60);
		hourComboBox.setLocation(78,100);
		minuteComboBox.setLocation(160,100);
		actionComboBox.setLocation(240,100);
		//timeToLabel.setLocation(24,150);
		startBtn.setLocation(60,300);
		cancelBtn.setLocation(220,300);
		countDownLabel.setLocation(120,140);
		actionSelectedLabel.setLocation(140,210 );
		
		
		
		
		//Populate ComboBox
			//Populate hour 0-12h
		for(int i=0;i<13;i++)
		{
			hourComboBox.addItem(String.valueOf(i));
		}
			//Populate min 0-59h
		for(int j=0;j<60;j++)
		{
			minuteComboBox.addItem(String.valueOf(j));
		}
			//Populate actions
		for(String action : actions)
		{
			actionComboBox.addItem(action);
		}
		
		//components colors
		pane.setBackground(Color.white);
		introLabel.setForeground(Color.red);
		startBtn.setForeground(Color.BLUE);
		cancelBtn.setForeground(Color.red);
		//add elements to the panel
		pane.add(introLabel);
		pane.add(hourLabel);
		pane.add(minLabel);
		pane.add(actionLabel);
		pane.add(hourComboBox);
		pane.add(minuteComboBox);
		pane.add(actionComboBox);
		pane.add(countDownLabel);
		pane.add(startBtn);
		pane.add(cancelBtn);
		pane.add(actionSelectedLabel);
		
		
		
		//add action to the btns
			//listener to start
		startBtn.addActionListener(new ActionListener() {
			 
            @Override
			public void actionPerformed(ActionEvent e) {
				startBtn.setEnabled(false);
				setActionLabel(actionComboBox.getSelectedItem().toString());
				cancelBtn.setEnabled(true);
            	startTimer();
				
			}

			
        });
			//listener to stop
		cancelBtn.addActionListener(new ActionListener() {
			 
            @Override
			public void actionPerformed(ActionEvent e) {
				
            	
            	cancel();
            	
			}

						
        });
		
		//add pane to the frame
		this.add(pane);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//by default the btn cancel is not enables
		cancelBtn.setEnabled(false);
		//make window visible
		this.setVisible(true);
		this.setResizable(false);
		
	}//end constructor
	
	
	/**
	 * This method cancel the operation of shutdown routine
	 */
	public void cancel()
	{
		
		timer.stop();
    	startBtn.setEnabled(true);
    	setTimeLabel("00:00:00");
    	
    	//write into the cmd to cancel any shutdown operation
    	try
    	{
			Runtime.getRuntime().exec("shutdown -a");
		} 
    	catch (IOException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}      	   	
    	setActionLabel("");
    	cancelBtn.setEnabled(false);
	}//end method cancel
	
	
	
	/**
	 * This method starts the timer( is the action of the btn start
	 */
	
	public  void startTimer()
	{
		final int second=getTimeSeconds();
		 milliseconds=((second*1000));
		 System.err.println(milliseconds);
		
		 String action=actionSelectedLabel.getText();
		 System.out.println("Selected: "+action);
     	
		 //switch to select what kind of operation to take depending of the action choosen
     	switch(action)
     	{
     		case "Shut Down":
     			try
             	{
     				Runtime.getRuntime().exec("shutdown -f -s -t " + second);
     				System.out.println("Selected shut down");
     				timer();
     				break;
 				} catch (IOException e1) 
 				{
 					// TODO Auto-generated catch block
 					e1.printStackTrace();
 				}
     		case "Log off":       	
     				
 					System.out.println("Selected log off");
 					timer();
 					break;
 				
     		case "Restart":
     			try
             	{
 					Runtime.getRuntime().exec("shutdown -t "+second+" -r -f");
 					System.out.println("Selected restart");
 					timer();
 					break;
 				} catch (IOException e1) 
 				{
 					// TODO Auto-generated catch block
 					e1.printStackTrace();
 				}		      		
     	}//end swicth
			
	}//end method startTimer
	
	/**
	 * This method update the jlabel each second
	 */
	public void timer()
	{
		 timer = new Timer(1000, new ActionListener() { 
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                if(milliseconds > 0) {
	                	milliseconds-= 1000;
	                	int hours = 0;
	                	if (milliseconds >= 3600000) {
	                		hours = (milliseconds / 3600000);
	                	}
	                		int minutes = ((milliseconds - hours * 3600000) / 60000);
	                		int seconds = (((milliseconds - hours * 3600000) - (minutes * 60000)) / 1000);
	                		String timeFormatted=String.format("%02d:%02d:%02d", hours,minutes,seconds);
	                				setTimeLabel(timeFormatted);
	                				//setTimeLabel(hours + ":" + minutes + ":" + seconds);
	                	
	                } else 
	                {
	                	// DONE
	                	setTimeLabel("Updating...");
	                	timer.stop();
	                	//if action choosen= log of do
	                	if(actionComboBox.getSelectedItem().equals("Log off"))
	                	{
	                		try 
	                		{
								Runtime.getRuntime().exec("shutdown /l");
							} 
	                		catch (IOException e1)
	                		{
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
	                	}
	                }//end main else
	            }//end actionPerformed
			});//end timer initialization
			timer.start();
			
	}//end method timer
	
	/**
	 * Method to return the the seconds chosen between hours and minutes
	 * 
	 */
	public int getTimeSeconds()
	{
		int seconds=0;
		int hourToSeconds=Integer.parseInt((String) hourComboBox.getSelectedItem())*3600;
		int minToSeconds=Integer.parseInt((String) minuteComboBox.getSelectedItem())*60;
		
		seconds=hourToSeconds+minToSeconds;
		return seconds;
	
	}//end method getTimeSeconds
	
	
	/**
	 * This method return the action
	 */
	public String getAction()
	{
		String act=actionComboBox.getSelectedItem().toString();
		return  act;

	}//end getAction
	
	/**
	 * Set timeLabel
	 */
	public void setTimeLabel(String text)
	{	
		countDownLabel.setText(text);
		
	}//end setTimeLabel
	
	/**
	 * Set actionLabel
	 */
	public void setActionLabel(String text)
	{	
		actionSelectedLabel.setText(text);
		
	}//end setActionLabel
	
	/**
	 * Main Method
	 */
	public static void main(String args[])
	{
		ShutDownScheduler down=new ShutDownScheduler();
	}
}//end class
