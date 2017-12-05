package javaWindow;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
	JTextField txt = new JTextField();
	JPasswordField pass = new JPasswordField();
		//Ritar ut fönstret efter att lyckats logga in
	public void drawMainWindow() 
	{
		// *** Create colors ***
		Color darkGray = new Color(30,30,30);
		Color gray = new Color(45,45,45);
		// ***
		// *** Creates the JPanels and modifies them ***
		welcometxt = new JLabel("Välkommen hit!");
		JPanel leftSide = new JPanel(new BorderLayout());
		leftSide.add(welcometxt);
		leftSide.setPreferredSize(new Dimension(250, 600));
		leftSide.setBackground(darkGray);
		leftSide.setVisible(true);			
		rightTxt = new JLabel("Höger textelement");
		JTabbedPane rightSide = new JTabbedPane(JTabbedPane.TOP);
		JPanel month = new JPanel();
		JPanel week = new JPanel();
		JPanel day = new JPanel();
		rightSide.setPreferredSize(new Dimension(750, 600));
		rightSide.setBackground(darkGray);
		rightSide.setVisible(true);		
		JPanel topSide = new JPanel(new BorderLayout());
		topSide.setPreferredSize(new Dimension(1000, 100));
		topSide.setBackground(darkGray);
		topSide.setVisible(true);		
		//JTabbedPane
		month.setBackground(gray);
		week.setBackground(gray);
		day.setBackground(gray);
		month.add(rightTxt);	
		//UI ändringar för rightSide
		rightSide.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT); //sätter tabbarna till högra sidan
		rightSide.setBorder(BorderFactory.createLineBorder(Color.darkGray, 0)); //Försöker sätta border color
		UIManager.put("TabbedPane.foreground", Color.lightGray); //ändrar färgen på texten till ljus grå
		UIManager.put("TabbedPane.opaque", true);
		rightSide.setUI(new BasicTabbedPaneUI() 
		{
		   @Override
		   protected void installDefaults() 
		   {
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
		rightSide.addTab("Dag", day);
		rightSide.addTab("Vecka", week);
		rightSide.addTab("Månad", month);
		add(topSide, BorderLayout.NORTH);
		add(leftSide, BorderLayout.WEST);
		add(rightSide, BorderLayout.CENTER);
		setSize(1000, 600);
		setVisible(true);
		//db();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) 
	{
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (InstantiationException e) 
		{
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) 
		{
			e.printStackTrace();
		} 
		catch (UnsupportedLookAndFeelException e) 
		{
			e.printStackTrace();
		}
		new JavaWindow().start();
	}
	
	//Detta är login fönstret som startas allra först, denna skickar även data till php filen vilket sedan checkar om lösen och användarnamn är korrekta. 
	public void start() 
	{
		//Skapar Labels och en knapp
		label = new JLabel("Användar-ID: ");
		pwdLabel = new JLabel("Lösenord: ");
		JButton button = new JButton("Submit");
		//Storleksändringar för olika komponenter
		txt.setPreferredSize(new Dimension(200, 30));
		pass.setPreferredSize(new Dimension(200, 30));
		button.setPreferredSize(new Dimension(200,50));
		//Lägger till komponenter till fönstret
		add(label);
		add(txt);
		add(pwdLabel);
		add(pass);
		add(button);
		//Sätter FlowLayout på fönstret
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
							//Skapar ny sträng som håller koll på användarnamnet. 
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
						System.out.println("Någon eller båda fälten är tomma.");
					}
				} 
				catch (Exception er) 
				{
					// TODO Auto-generated catch block
					er.printStackTrace();
				}
			}
		});
		//sätter storleken, gör den synlig och ger krysset funktionen att stänga av applikationen
		setSize(800, 400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	//Detta är metoden för koppling till php filen, denna ser även till så att vi får returned data. 
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

}