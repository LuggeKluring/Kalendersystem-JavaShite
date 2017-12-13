package javaWindow;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
//import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
//import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import org.json.JSONObject;
@SuppressWarnings("serial")


public class JavaWindow extends JFrame
{
	public JavaWindow() {
		super("Kalender");
		this.pack();
		this.setLocationRelativeTo(null);
	}
	
	
	public static String Username;
	JLabel label;
	JLabel pwdLabel;
	JLabel welcometxt;
	JLabel rightTxt;
	JLabel calendarNameLabel;
	JLabel eventTitleLabel;
	JLabel eventDateTimeLabel;
	JLabel eventInfoLabel;
	JLabel noticeTitleLabel;
	JLabel noticeDateTimeLabel;
	JLabel noticeInfoLabel;
	JTextField txt = new JTextField();
	JTextField calendarName = new JTextField();
	JTextField eventTitle = new JTextField();
	JTextField eventDateTime = new JTextField();
	JTextField noticeTitle = new JTextField();
	JTextField noticeDateTime = new JTextField();
	JPasswordField pass = new JPasswordField();
	JButton createCalendar = new JButton("+ Skapa ny kalender");
	JButton createEvent = new JButton("+ Skapa nytt event");
	JButton createNotice = new JButton("+ Skapa ny notis");
	JButton calendarListItem = new JButton("Test ");
	JTextArea eventInfo = new JTextArea(30, 50);
	JTextArea noticeInfo = new JTextArea(30, 50);
	public int textAreaLimit = 200;
	public int userId;
	JSONObject loggedUser = new JSONObject();
	JSONObject calendars = new JSONObject();

		//Ritar ut fönstret efter att lyckats logga in
	public void drawMainWindow(JSONObject loggedUser) 
	{
		String str = "http://localhost/kalendersystem/getCalendars.php?userCredSend="+loggedUser;
		str = str.replaceAll(" ", "%20");
		String returnValue = db(str);
		calendars.put("kalendrar", returnValue.split(" "));
		System.out.println(calendars);
		String[] calendarArr = returnValue.split(" ");
		int[] tempArray = Arrays.stream(calendarArr).mapToInt(Integer::parseInt).toArray();
		
		
		// *** Create colors ***
		Color darkGray = new Color(30,30,30);
		Color gray = new Color(45,45,45);
		// ***
		// *** Creates the JPanels and modifies them ***

		
		welcometxt = new JLabel("Dina kalendrar");
		rightTxt = new JLabel("Höger textelement");
		JPanel topSide = new JPanel(new FlowLayout(FlowLayout.LEADING));
		JPanel leftSide = new JPanel();
		JTabbedPane rightSide = new JTabbedPane(JTabbedPane.TOP);
			JPanel month = new JPanel();
			JPanel week = new JPanel();
			JPanel day = new JPanel();
		
    
		//topSide ---------
		topSide.setPreferredSize(new Dimension(1000, 100));
		topSide.setBackground(darkGray);
		topSide.setVisible(true);
		topSide.setForeground(new Color(255,255,255));
		

		//rightSide -------
		rightSide.setPreferredSize(new Dimension(750, 500));
		rightSide.setBackground(darkGray);
		rightSide.setVisible(true);
		rightSide.setForeground(new Color(255,255,255));
		
		//leftSide --------
		leftSide.setLayout(new BoxLayout(leftSide, BoxLayout.PAGE_AXIS));
		leftSide.add(welcometxt);
		welcometxt.setFont(new Font("Roboto", Font.BOLD, 30));
		welcometxt.setBorder(new EmptyBorder(10,10,10,10));
		leftSide.add(createCalendar);
		
		System.out.println(tempArray.length);
		
		for(int i : tempArray)
		{
			String calendarIdSend = "http://localhost/kalendersystem/getCalendarName.php?calendarIdSend="+i;
			calendarIdSend = calendarIdSend.replaceAll(" ", "%20");
			String calenderName = db(calendarIdSend);
			System.out.println(calenderName);
			leftSide.add(calendarListItem = new JButton(""+calenderName));
			calendarListItem.setText(""+calenderName);
			calendarListItem.setPreferredSize(new Dimension(140,25));
			calendarListItem.setBorderPainted(false);
			calendarListItem.setContentAreaFilled(false);
			calendarListItem.setForeground(new Color(255,255,255));
			calendarListItem.setFont(new Font("Roboto", Font.PLAIN, 13));
			calendarListItem.setBackground(null);
			System.out.println(i);
		}
		
		leftSide.add(createEvent);
		leftSide.add(createNotice);
		leftSide.setPreferredSize(new Dimension(250, 500));
		leftSide.setBackground(darkGray);
		leftSide.setVisible(true);
		leftSide.setForeground(new Color(255,255,255));
		createCalendar.setPreferredSize(new Dimension(140,25));
		createCalendar.setBorderPainted(false);
		createCalendar.setContentAreaFilled(false);
		createCalendar.setForeground(new Color(255,255,255));
		createCalendar.setFont(new Font("Roboto", Font.PLAIN, 13));
		createCalendar.setBackground(null);

		createEvent.setPreferredSize(new Dimension(140,25));
		createEvent.setBorderPainted(false);
		createEvent.setContentAreaFilled(false);
		createEvent.setForeground(new Color(255,255,255));
		createEvent.setFont(new Font("Roboto", Font.PLAIN, 13));
		createEvent.setBackground(null);
		createNotice.setPreferredSize(new Dimension(140,25));
		createNotice.setBorderPainted(false);
		createNotice.setContentAreaFilled(false);
		createNotice.setForeground(new Color(255,255,255));
		createNotice.setFont(new Font("Roboto", Font.PLAIN, 13));
		createNotice.setBackground(null);
		leftSide.setForeground(new Color(255,255,255));

		
		welcometxt.setForeground(new Color(255,255,255));
		rightTxt.setForeground(new Color(255,255,255));
		
		
		
		//JTabbedPane ------
		month.setBackground(gray);
		week.setBackground(gray);
		day.setBackground(gray);
		//dayGrid - Skapar rutnätet med dagar 
		JPanel dayGrid = new JPanel(new GridLayout(4,7,15,15));
			JButton dayButton;
			for(int i=0 ; i<35 ; i++) {
				int num = i+1;
				dayGrid.add(dayButton = new JButton(""+num));
				dayButton.setPreferredSize(new Dimension(60, 60));
				dayButton.setContentAreaFilled(false);
				dayButton.setBackground(null);
				dayButton.setBorderPainted(true);
			}
		month.add(dayGrid);
		
		rightSide.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT); //sätter tabbarna till högra sidan
		rightSide.setBorder(BorderFactory.createLineBorder(Color.darkGray, 0)); //Försöker sätta border color
		UIManager.put("TabbedPane.foreground", Color.lightGray); //ändrar färgen på texten till ljus grå
		UIManager.put("TabbedPane:TabbedPaneTab.contentMargins", new EmptyBorder(20,20,20,20));
		UIManager.put("TabbedPane.insets", new EmptyBorder(20,20,20,20));
		UIManager.put("TabbedPane.opaque", true);
		rightSide.setUI(new BasicTabbedPaneUI() {
			   @Override
			   protected void installDefaults() {
			       super.installDefaults();
			       highlight = new Color(255, 255, 255, 0);
			       lightHighlight = new Color(255, 255, 255, 0);
			       shadow = new Color(255, 255, 255, 0);
			       darkShadow = new Color(255, 255, 255, 0);
			       focus = Color.gray;
			       //Ändrar färger på olika effekter*/
			   }
			});
		// ***
		// *** Adds components to the Frame ***
		//leftSide.add(createCalendar);
		rightSide.addTab("Dag", day);
		rightSide.addTab("Vecka", week);
		rightSide.addTab("Månad", month);
    
		this.setForeground(new Color(255,255,255));
		add(topSide, BorderLayout.NORTH);
		add(leftSide, BorderLayout.WEST);
		add(rightSide, BorderLayout.CENTER);
		setSize(1000, 600); 
		setVisible(true);
		
		createCalendar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent create) 
			{
				try 
				{
					new JavaWindow().createCalendar();
				} 
				catch (Exception createEr) 
				{
					// TODO Auto-generated catch block
					createEr.printStackTrace();
				}
			}
		});
		
		createEvent.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent create) 
			{
				try 
				{
					new JavaWindow().createEvent();
				} 
				catch (Exception createEr) 
				{
					// TODO Auto-generated catch block
					createEr.printStackTrace();
				}
			}
		});
		
		createNotice.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent create) 
			{
				try 
				{
					new JavaWindow().createNotice();
				} 
				catch (Exception createEr) 
				{
					// TODO Auto-generated catch block
					createEr.printStackTrace();
				}
			}
		});
		
		//db();
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}	
	
	//skapar login-rutan
	public void start() {
		label = new JLabel("Användarnamn: ");
		pwdLabel = new JLabel("Lösenord: ");
		JTextField txt = new JTextField();
		JPasswordField pass = new JPasswordField();

		JButton button = new JButton("Submit");
		//Storleksdeklarering
		txt.setPreferredSize(new Dimension(200, 30));
		pass.setPreferredSize(new Dimension(200, 30));
		button.setPreferredSize(new Dimension(200,50));
		//Lägger till komponenter till fönstret
		add(label);
		add(txt);
		add(pwdLabel);
		add(pass);
		add(button);
		//Sätter flowLayout på frame
		setLayout(new FlowLayout());
		//Ger funktioner till knappen
		button.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					loggedUser.put("username",txt.getText());
					//omvandlar lösenord till en string
					char[] pswChar = pass.getPassword();
					String pswString = String.valueOf(pswChar);
					loggedUser.put("password", pswString);
					//Kopplar till php filen
					String str = "http://localhost/kalendersystem/kalendersystem.php?userCredSend="+loggedUser;
					str = str.replaceAll(" ", "%20");
					String returnValue = db(str);
					System.out.println(loggedUser);
					//kollar om fälterna är tomma eller ej
				    if (!txt.getText().isEmpty() && !pswString.isEmpty()) 
				    {
				    	//kollar om värdet php filen skickar tillbaka är 1 eller något annat	    	
						if(!returnValue.equals("0")) 
						{
							//Kallar på metoden som ritar ut kalender fönstret 
							new JavaWindow().drawMainWindow(loggedUser);
							userId= Integer.parseInt(returnValue);
							dispose();
						}	
						//Om värdet man får tillbaka från php filen är något annat än 1
						else
						{
							JOptionPane.showMessageDialog(null, "Fel användarnamn och/eller lösenord!");
							System.out.println("Fel användarnamn eller lösenord.");
							System.out.println(returnValue);
						}
					}   
				    //om en av fälten eller båda är tomma
					else
					{
						System.out.println("Något av fälten är tomma");
					}
				} 
				catch (Exception er) 
				{
					// TODO Auto-generated catch block
					er.printStackTrace();
				}
			}
		});
		
		setSize(800, 400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void getCalendars() {
		
		
		
	}
	//Detta är metoden får koppling till php filen, denna ser även till så att vi får returned data. 
	public String db(String link) 
	{
		try 
		{
			URL url = new URL(link);

			URLConnection phpConnection = url.openConnection();
			InputStream getData = phpConnection.getInputStream();
			String data = "";
			while(getData.available()>0)
			{
				data=data+((char) getData.read());
			}
			getData.close();	
			System.out.println(data);
			return data;
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public void createCalendar()
	{
		System.out.println(Username);
		calendarNameLabel = new JLabel("Kalender namn: ");
		JButton submitCalendarName = new JButton("Skapa kalender");
		calendarName.setPreferredSize(new Dimension(200,30));
		submitCalendarName.setPreferredSize(new Dimension(200,50));
		add(calendarNameLabel);
		add(calendarName);
		add(submitCalendarName);
		setLayout(new FlowLayout());
		setSize(800, 400);
		setVisible(true);
		submitCalendarName.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					if(!calendarName.getText().isEmpty())
					{
						System.out.println("Name: "+calendarName.getText());
						String str = "http://localhost/kalendersystem/calendarCreate.php?uNameSend="+Username+"&calendarNameSend="+calendarName.getText();
						str = str.replaceAll(" ", "%20");
						System.out.println(str);
						String returnValue = db(str);
						System.out.println(returnValue);
						dispose();
						JOptionPane.showMessageDialog(null, "Kalendern '"+calendarName.getText()+"' är skapad!");
					}
					else
					{
						System.out.println("Ange namn p� kalendern.");
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
	
	public void createEvent()
	{
		eventTitleLabel = new JLabel("Titel på event: ");
		eventDateTimeLabel = new JLabel("Datum och tid: ");
		eventInfoLabel = new JLabel("Om event: ");
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
		add(eventDateTime);
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
					if(!eventTitle.getText().isEmpty()&&!eventDateTime.getText().isEmpty())
					{
						System.out.println("Titel: "+eventTitle.getText());
						System.out.println("Event datum: "+eventDateTime.getText());
						System.out.println("Event beskrivning: "+eventInfo.getText());
						String str = "http://localhost/kalendersystem/createEvent.php?eventTitleSend="+eventTitle.getText()+"&eventDateSend="+eventDateTime.getText()+"&eventInfoSend="+eventInfo.getText();
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
	
	public void createNotice()
	{
		noticeTitleLabel = new JLabel("Titel på event: ");
		noticeDateTimeLabel = new JLabel("Datum och tid: ");
		noticeInfoLabel = new JLabel("Beskrivning: ");
		JButton submitNotice = new JButton("Skapa ny notis");
		noticeTitle.setPreferredSize(new Dimension(200,30));
		noticeDateTime.setPreferredSize(new Dimension(200,30));
		submitNotice.setPreferredSize(new Dimension(200,50));
		noticeInfo.setSize(200, 200);
		noticeInfo.setLineWrap(true);
		noticeInfo.setWrapStyleWord(true);
		noticeInfo.setRows(10);
		
		add(noticeTitleLabel);
		add(noticeTitle);
		add(noticeDateTimeLabel);
		add(noticeDateTime);
		add(noticeInfoLabel);
		add(noticeInfo);
		add(submitNotice);
		setLayout(new FlowLayout());
		setSize(800, 400);
		setVisible(true);
		submitNotice.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					if(!noticeTitle.getText().isEmpty()&&!noticeDateTime.getText().isEmpty())
					{
						System.out.println("Name: "+noticeTitle.getText());
						System.out.println("Event datum: "+noticeDateTime.getText());
						System.out.println("Event beskrivning: "+noticeInfo.getText());
						String str = "http://localhost/kalendersystem/createNotice.php?noticeTitleSend="+noticeTitle.getText()+"&noticeDateSend="+noticeDateTime.getText()+"&noticeInfoSend="+noticeInfo.getText();
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
						System.out.println("Ange titel och datum för notis.");
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


	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.getLookAndFeelDefaults().put("TabbedPane:TabbedPaneTab.contentMargins", new EmptyBorder(20, 20, 20, 20));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		new JavaWindow().start();
	}

	
}

