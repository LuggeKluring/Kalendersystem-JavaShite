package javaWindow;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
@SuppressWarnings("serial")


public class JavaWindow extends JFrame
{
	JLabel label;
	JLabel pwdLabel;
	JLabel welcometxt;
	JLabel rightTxt;
	JLabel calendarNameLabel;
	JTextField txt = new JTextField();
	JTextField calendarName = new JTextField();
	JPasswordField pass = new JPasswordField();
	JButton createCalendar = new JButton("Skapa ny kalender");
		//Ritar ut fönstret efter att lyckats logga in
	public void drawMainWindow() 
	{
		// *** Create colors ***
		Color darkGray = new Color(30,30,30);
		Color gray = new Color(45,45,45);
		// ***
		// *** Creates the JPanels and modifies them ***

		welcometxt = new JLabel("Välkommen hit!");
		rightTxt = new JLabel("Höger textelement");
		JPanel leftSide = new JPanel(new BorderLayout());
		JTabbedPane rightSide = new JTabbedPane(JTabbedPane.TOP);
		JPanel month = new JPanel();
		JPanel week = new JPanel();
		JPanel day = new JPanel();
		JPanel topSide = new JPanel(new FlowLayout(FlowLayout.LEADING));
    
		//topSide
		topSide.setPreferredSize(new Dimension(1000, 100));
		topSide.setBackground(darkGray);
		topSide.setVisible(true);
	
		createCalendar.setPreferredSize(new Dimension(140,25));

		//rightSide
		rightSide.setPreferredSize(new Dimension(750, 600));
		rightSide.setBackground(darkGray);
		rightSide.setVisible(true);
		
		//leftSide
		leftSide.add(welcometxt);
		leftSide.setPreferredSize(new Dimension(250, 600));
		leftSide.setBackground(darkGray);
		leftSide.setVisible(true);
		
		//JTabbedPane
		month.setBackground(gray);
		week.setBackground(gray);
		day.setBackground(gray);

		month.add(rightTxt);
		rightSide.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT); //sätter tabbarna till högra sidan
		rightSide.setBorder(BorderFactory.createLineBorder(Color.darkGray, 0)); //Försöker sätta border color
		UIManager.put("TabbedPane.foreground", Color.lightGray); //ändrar färgen på texten till ljus grå
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
		topSide.add(createCalendar);
		rightSide.addTab("Dag", day);
		rightSide.addTab("Vecka", week);
		rightSide.addTab("Månad", month);
    
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
		//db();
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}	
	
	//skapar login-rutan
	public void start() {
		label = new JLabel("Användar-ID: ");
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
					//omvandlar lösenord till en string
					char[] pswChar = pass.getPassword();
					String pswString = String.valueOf(pswChar);
					//Kopplar till php filen
					String str = "http://localhost/kalendersystem/kalendersystem.php?uNameSend="+txt.getText()+"&pswSend="+pswString;
					String returnValue = db(str);
					//kollar om fälterna är tomma eller ej
				    if (!txt.getText().isEmpty() && !pswString.isEmpty()) 
				    {
				    	//kollar om värdet php filen skickar tillbaka är 1 eller något annat
						if(returnValue.equals("1")) 
						{
							//Skapar ny sträng som håller koll pä användarnamnet. 
							String Username = txt.getText();		
							//Kallar på metoden som ritar ut kalender fönstret 
							new JavaWindow().drawMainWindow();
							System.out.println(Username);
						}	
						//Om värdet man får tillbaka från php filen är något annat än 1
						else
						{
							System.out.println("Fel användarnamn eller lösenord.");
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
						String str = "http://localhost/kalendersystem/calendarCreate.php?calendarNameSend="+calendarName.getText();
						str = str.replaceAll(" ", "%20");
						System.out.println(str);
						String returnValue = db(str);
						System.out.println(returnValue);
						dispose();
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


	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		UIManager.getLookAndFeelDefaults().put("TabbedPane:TabbedPaneTab.contentMargins", new Insets(30, 200, 0, 0));
		new JavaWindow().start();
	}

	
}

