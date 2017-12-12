package javaWindow;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SkapaEvent extends JFrame {
	public SkapaEvent() {
		super("Skapa event");
		JavaWindow mainWindow = new JavaWindow();
		JTextField eventTitle = new JTextField();
		JTextField eventDateTime = new JTextField();
		JTextArea eventInfo = new JTextArea(30, 50);
		JLabel eventTitleLabel = new JLabel("Titel på event: ");
		JLabel eventDateTimeLabel = new JLabel("Datum och tid: ");
		JLabel eventInfoLabel = new JLabel("Om event: ");
		JButton submitEvent = new JButton("Skapa nytt event");
		eventTitle.setPreferredSize(new Dimension(200,30));
		eventDateTime.setPreferredSize(new Dimension(200,30));
		submitEvent.setPreferredSize(new Dimension(200,50));
		eventInfo.setSize(200, 200);
		eventInfo.setLineWrap(true);
		eventInfo.setWrapStyleWord(true);
		eventInfo.setRows(10);
		
		
		
		add(eventTitleLabel);
		add(eventTitle);
		add(eventDateTimeLabel);
		
		//add(eventDateTime);
		add(eventInfoLabel);
		add(eventInfo);
		add(submitEvent);
		setLayout(new FlowLayout());
		setSize(800, 400);
		setVisible(true);
		submitEvent.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					if(!eventTitle.getText().isEmpty())
					{
						
						System.out.println("Titel: "+eventTitle.getText());
						System.out.println("Event datum: "+date);
						System.out.println("Event beskrivning: "+eventInfo.getText());
						String str = "http://localhost/kalendersystem/createEvent.php?eventTitleSend="+eventTitle.getText()+"&eventDateSend="+date+"&eventInfoSend="+eventInfo.getText();
						str = str.replaceAll("\n", "%0A");
						str = str.replaceAll("\t", "%09");
						str = str.replaceAll(" ", "%20");
						System.out.println(str);
						String returnValue = db(str);
						System.out.println(returnValue);
						dispose();
					}
					else
					{
						System.out.println("Ange titel och datum för eventet.");
					}
				} 
				catch (Exception er) 
				{
					// TODO Auto-generated catch block
					er.printStackTrace();
				}
			}
		});
	}
}
