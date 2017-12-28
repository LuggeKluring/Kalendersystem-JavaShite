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
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

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
import javax.swing.SwingConstants;
//import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import org.json.JSONObject;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeIncrement;

//Main javaklassen för kalendersystemet där just nu i stortsett allt händer. 
@SuppressWarnings("serial")
public class JavaWindow extends JFrame
{
	//funktion för fönster som används till att starta andra funktioner
	public JavaWindow() {
		
		super("Kalender");
		this.pack();
		this.setLocation(250, 150);;
		
	}	
	//skapandet av publika variablar
	
	//int
	public static String Username;
	public int textAreaLimit = 200;
	public static int userId;
	public static int tempUserId=0;
	public static int numbrsOfCalendars = 0;
	public static int currentCalendar = 0;
	public static int monthNum;
	
	//JLabel
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
	
	//JTextField
	JTextField txt = new JTextField();
	JTextField calendarName = new JTextField();
	JTextField eventTitle = new JTextField();
	JTextField eventDateTime = new JTextField();
	JTextField noticeTitle = new JTextField();
	JTextField noticeDateTime = new JTextField();
	
	//JPasswordField
	JPasswordField pass = new JPasswordField();
	
	//JButton
	JButton createCalendar = new JButton("+ Skapa ny kalender");
	JButton createEvent = new JButton("+ Skapa nytt event");
	JButton createNotice = new JButton("+ Skapa ny notis");
	JButton calendarListItem = new JButton("Test ");
	public static JButton dayButton;
	
	//JTextArea
	JTextArea eventInfo = new JTextArea(30, 50);
	JTextArea noticeInfo = new JTextArea(30, 50);
	
	//font
	Font roboto = new Font("Roboto", Font.PLAIN, 13);
	
	//JSONObject
	JSONObject loggedUser = new JSONObject();
	JSONObject calendars = new JSONObject();
	/***DatePicker***/
	DatePickerSettings dateSettings = new DatePickerSettings();
	TimePickerSettings timeSettings = new TimePickerSettings();
	DatePicker datePicker = new DatePicker();
	TimePicker timePicker = new TimePicker();
	
	//String
	public static String selectedDate;
	public static String selectedTime;
	
	//JPanel
	public static JPanel dayGrid = new JPanel(new GridLayout(0,7));
	
	//Calendar
	public static Calendar c = Calendar.getInstance();
	
	//Ritar ut fönstret efter att lyckats logga in
	public void drawMainWindow(JSONObject loggedUser) 
	{
		//Skapar kontakten till getCalendars.php som skall användas för att hämta kalendrarna för användaren.
		String str = "http://localhost/kalendersystem/getCalendars.php?userCredSend="+loggedUser;
		str = str.replaceAll(" ", "%20");
		String returnValue = db(str);
		
		
		// *** Create colors ***
		Color darkGray = new Color(30,30,30);
		Color gray = new Color(45,45,45);
		// ***
		// *** Creates the JPanels and modifies them ***

		//skapandet utav JLabel, JPanel och JTabbedPane.
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
		topSide.add(createEvent);
		topSide.add(createNotice);

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
		
		//kollar om java får tillbaka ett svar eller ej.
		if(!returnValue.isEmpty())
		{
			//Fixar formattet för värdena man får tillbaka
			calendars.put("kalendrar", returnValue.split(" "));
			String[] calendarArr = returnValue.split(" ");
			int[] tempArray = Arrays.stream(calendarArr).mapToInt(Integer::parseInt).toArray();
			numbrsOfCalendars=tempArray.length;
			
			//går igenom alla kalendrar i arrayen.
			for(int i : tempArray)
			{
				//Skickar kalenderid till getCalendarName för att hämta namnen på kalendrarna.
				String calendarIdSend = "http://localhost/kalendersystem/getCalendarName.php?calendarIdSend="+i;
				calendarIdSend = calendarIdSend.replaceAll(" ", "%20");
				String calenderName = db(calendarIdSend);
				
				//lägger till nya knappar för antalet kalendrar
				leftSide.add(calendarListItem = new JButton(""+calenderName));
				calendarListItem.setText(""+calenderName);
				calendarListItem.setPreferredSize(new Dimension(140,25));
				calendarListItem.setBorderPainted(false);
				calendarListItem.setContentAreaFilled(false);
				calendarListItem.setForeground(new Color(255,255,255));
				calendarListItem.setFont(new Font("Roboto", Font.PLAIN, 13));
				calendarListItem.setBackground(null);
				
				//Ger currentCalendar nuvarande kalender id
				currentCalendar=i;
				
				//ger knapparna uppgifter de skall göra när man trycker på dem.
				calendarListItem.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent create) 
					{
						try 
						{
							//skickar kalender id och användar id för att sedan få tillbaka personens behörighet. 
							String calendarPermission = "http://localhost/kalendersystem/calendarPermission.php?userSend="+userId+"&calendarIdSend="+i;
							currentCalendar=i;
							calendarPermission = calendarPermission.replaceAll(" ", "%20");
							String permission = db(calendarPermission);
							System.out.println(permission);
							new JavaWindow().getEvent();
							new JavaWindow().getNotices();
						} 
						catch (Exception createEr) 
						{
							
							createEr.printStackTrace();
						}
					}
				});
			}
		}
		leftSide.setPreferredSize(new Dimension(250, 500));
		leftSide.setBackground(darkGray);
		leftSide.setVisible(true);
		leftSide.setForeground(new Color(255,255,255));
		createCalendar.setPreferredSize(new Dimension(140,25));
		createCalendar.setBorderPainted(false);
		createCalendar.setContentAreaFilled(false);
		createCalendar.setForeground(new Color(255,255,255));
		createCalendar.setFont(roboto);
		createCalendar.setBackground(null);

		createEvent.setPreferredSize(new Dimension(140,25));
		createEvent.setBorderPainted(false);
		createEvent.setContentAreaFilled(false);
		createEvent.setForeground(new Color(255,255,255));
		createEvent.setFont(roboto);
		createEvent.setBackground(null);
		createNotice.setPreferredSize(new Dimension(140,25));
		createNotice.setBorderPainted(false);
		createNotice.setContentAreaFilled(false);
		createNotice.setForeground(new Color(255,255,255));
		createNotice.setFont(roboto);
		createNotice.setBackground(null);
		leftSide.setForeground(new Color(255,255,255));	
		welcometxt.setForeground(new Color(255,255,255));
		rightTxt.setForeground(new Color(255,255,255));
		//JTabbedPane ------
		month.setBackground(gray);
		week.setBackground(gray);
		day.setBackground(gray);
		//dayGrid - Skapar rutnätet med dagar 
		
		
//		for(int w=0; w<5; w++) {
//			for(int d=0; d<7; d++) {
//				dayGrid.add(dayButton = new JButton());
//				dayButton.setPreferredSize(new Dimension(60,60));
//			}
//		}
		//
		//************* DayGrid ****************
		//
		dayGrid.setBackground(new Color(0,0,0,0));
		Dimension dayGridSize = new Dimension(650, 400);
		dayGrid.setPreferredSize(dayGridSize);
		Date dayGridDate = new Date();
		c.setTime(dayGridDate);
		drawDayGrid();
			//******* monthPicker *******
			JPanel monthPicker = new JPanel(new BorderLayout());
			JPanel days = new JPanel(new GridLayout());
			monthPicker.setPreferredSize(new Dimension(650,60));
			String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
			JLabel selectedMonthLabel = new JLabel(monthNames[monthNum]+"\t"+c.get(Calendar.YEAR), SwingConstants.CENTER);
			
			JButton nextBtn = new JButton(">");
				nextBtn.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent create) 
					{
						try 
						{
								c.add(Calendar.MONTH, 1);
								System.out.println("---Framåt---");
								System.out.println("Månad: "+c.get(Calendar.MONTH)+"	"+"År: "+c.get(Calendar.YEAR));
								selectedMonthLabel.setText(monthNames[c.get(Calendar.MONTH)]+c.get(Calendar.YEAR));
								drawDayGrid();
						} 
						catch (Exception createEr) 
						{
							
							createEr.printStackTrace();
						}
					}
				});
			JButton prevBtn = new JButton("<");
				prevBtn.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent create) 
					{
						try 
						{
							
							c.add(Calendar.MONTH, -1);
							System.out.println("---Bakåt---");
							System.out.println("Månad: "+c.get(Calendar.MONTH)+"	"+"År: "+c.get(Calendar.YEAR));
							selectedMonthLabel.setText(monthNames[c.get(Calendar.MONTH)]+c.get(Calendar.YEAR));
							drawDayGrid();
						} 
						catch (Exception createEr) 
						{
							
							createEr.printStackTrace();
						}
					}
				});
			
			JLabel mon = new JLabel("Mån", SwingConstants.CENTER);
			JLabel tue = new JLabel("Tis", SwingConstants.CENTER);
			JLabel wed = new JLabel("Ons", SwingConstants.CENTER);
			JLabel thu = new JLabel("Tor", SwingConstants.CENTER);
			JLabel fri = new JLabel("Fre", SwingConstants.CENTER);
			JLabel sat = new JLabel("Lör", SwingConstants.CENTER);
			JLabel sun = new JLabel("Sön", SwingConstants.CENTER);
			days.add(mon);
			days.add(tue);
			days.add(wed);
			days.add(thu);
			days.add(fri);
			days.add(sat);
			days.add(sun);
			monthPicker.add(prevBtn, BorderLayout.WEST);
			monthPicker.add(selectedMonthLabel);
			monthPicker.add(nextBtn, BorderLayout.EAST);
			monthPicker.add(days, BorderLayout.SOUTH);
		month.add(monthPicker);
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
					
					createEr.printStackTrace();
				}
			}
		});		
		//db();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}	
	public void drawDayGrid() {
		dayGrid.removeAll();
		c.set(Calendar.DAY_OF_MONTH, 1);
		  int skipDays = 0;
		  switch (c.get(Calendar.DAY_OF_WEEK)) {
		  case Calendar.MONDAY:
		    skipDays = 1;
		    break;
		  case Calendar.TUESDAY:
		    skipDays = 2;
		    break;
		  case Calendar.WEDNESDAY:
		    skipDays = 3;
		    break;
		  case Calendar.THURSDAY:
		    skipDays = 4;
		    break;
		  case Calendar.FRIDAY:
		    skipDays = 5;
		    break;
		  case Calendar.SATURDAY:
		    skipDays = 6;
		    break;
		  default:
		  }
		  for (int i = 1; i < skipDays; i++) {
		    dayGrid.add(dayButton = new JButton("")); 
		    dayButton.setPreferredSize(new Dimension(60,60));
		    dayButton.setBorderPainted(false);
		  }
		  int maxDay = c.getMaximum(Calendar.DAY_OF_MONTH);
		  for (int i = 1; i <= maxDay; i++) {
		    dayGrid.add(dayButton = new JButton(String.valueOf(i)));
		    dayButton.setPreferredSize(new Dimension(60,60));
		    dayButton.setBorderPainted(false);
		  }
		  super.pack();
		  super.repaint();
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
					Username=txt.getText();
					loggedUser.put("username",txt.getText());
					//omvandlar lösenord till en string
					char[] pswChar = pass.getPassword();
					String pswString = String.valueOf(pswChar);
					loggedUser.put("password", pswString);
					//Kopplar till php filen
					String str = "http://localhost/kalendersystem/kalendersystem.php?userCredSend="+loggedUser;
					str = str.replaceAll(" ", "%20");
					String returnValue = db(str);
					//kollar om fälterna är tomma eller ej
				    if (!txt.getText().isEmpty() && !pswString.isEmpty()) 
				    {
				    	//kollar om värdet php filen skickar tillbaka är 1 eller något annat	    	
						if(!returnValue.equals("0")) 
						{
							//Kallar på metoden som ritar ut kalender fönstret 
							tempUserId= Integer.parseInt(returnValue);
							userId= Integer.parseInt(returnValue);
							new JavaWindow().drawMainWindow(loggedUser);
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
					
					er.printStackTrace();
				}
			}
		});	
		setSize(800, 400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
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
			return data;
		} 
		catch (Exception e) 
		{
			
			e.printStackTrace();
		}
		return "";
	}

	public void createCalendar()
	{
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
						int calendarIdCreate = tempUserId*1000+numbrsOfCalendars;
						String str = "http://localhost/kalendersystem/calendarCreate.php?uNameSend="+Username+
								"&calendarNameSend="+calendarName.getText()+"&userSend="+userId+"&calendarIdSend="+calendarIdCreate;
						str = str.replaceAll(" ", "%20");
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
					
					er.printStackTrace();
				}
			}
		});
	}
	
	public void createEvent() //TODO createEvent
	{	
		dateSettings = new DatePickerSettings();
	    dateSettings.setFormatForDatesCommonEra("yyyy-MM-dd");
	    timeSettings.setFormatForDisplayTime("hh:mm");
	    timeSettings.use24HourClockFormat();
	    timeSettings.setDisplaySpinnerButtons(true);
	    timeSettings.initialTime = LocalTime.now();
	    timeSettings.generatePotentialMenuTimes(TimeIncrement.FifteenMinutes, null, null);
	    datePicker = new DatePicker(dateSettings);
	    timePicker = new TimePicker(timeSettings);
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
		add(datePicker);
		add(timePicker);
		
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
						//DatePicker output
						selectedDate = datePicker.getDateStringOrEmptyString();
						selectedTime = timePicker.getTimeStringOrEmptyString();
						
						System.out.println("Titel: "+eventTitle.getText());
						System.out.println("Event datum: "+selectedDate/*ConvertDate.formattedDate*/);
						System.out.println("Event beskrivning: "+eventInfo.getText());
						String str = "http://localhost/kalendersystem/createEvent.php?eventTitleSend="+eventTitle.getText()+
								"&eventDateSend="+selectedDate+"%20"+selectedTime+"&eventInfoSend="+eventInfo.getText()+"&calendarIdSend="+currentCalendar;
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
					
					er.printStackTrace();
				}
			}
		});
	}
	
	public void createNotice()	//TODO createNotice
	{
		dateSettings = new DatePickerSettings();
	    dateSettings.setFormatForDatesCommonEra("yyyy-MM-dd");
	    timeSettings.setFormatForDisplayTime("hh:mm");
	    timeSettings.use24HourClockFormat();
	    timeSettings.setDisplaySpinnerButtons(true);
	    timeSettings.initialTime = LocalTime.now();
	    timeSettings.generatePotentialMenuTimes(TimeIncrement.FifteenMinutes, null, null);
	    datePicker = new DatePicker(dateSettings);
	    timePicker = new TimePicker(timeSettings);
		noticeTitleLabel = new JLabel("Notistitel: ");
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
		add(datePicker);
		add(timePicker);
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
					if(!noticeTitle.getText().isEmpty())
					{
						// Get datepicker output
						selectedDate = datePicker.getDateStringOrEmptyString();
						selectedTime = timePicker.getTimeStringOrEmptyString();
						//ConvertDate.toCalendar(selectedDate);
						
						System.out.println("Name: "+noticeTitle.getText());
						System.out.println("Event datum: "+selectedDate/*ConvertDate.formattedDate*/);
						System.out.println("Event beskrivning: "+noticeInfo.getText());
						String str = "http://localhost/kalendersystem/createNotice.php?noticeTitleSend="+noticeTitle.getText()+"&noticeDateSend="+
								selectedDate+"%20"+selectedTime+"&noticeInfoSend="+noticeInfo.getText()+"&calendarIdSend="+currentCalendar;
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
					
					er.printStackTrace();
				}
			}
		});
	}
		
	public void getEvent()
	{
		String eventData = "http://localhost/kalendersystem/getEvents.php?calendarIdSend="+currentCalendar;
		eventData = eventData.replaceAll(" ", "%20");
		String detEventData = db(eventData);
		System.out.println(detEventData);
	}
	
	public void getNotices()
	{
		String noticeData = "http://localhost/kalendersystem/getNotices.php?calendarIdSend="+currentCalendar;
		noticeData = noticeData.replaceAll(" ", "%20");
		String getEventData = db(noticeData);
		System.out.println(getEventData);
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

