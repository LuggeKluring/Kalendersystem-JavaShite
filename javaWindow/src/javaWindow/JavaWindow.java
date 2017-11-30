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

public class JavaWindow extends JFrame{
	
	JLabel label;
	JLabel pwdLabel;
	JLabel welcometxt;
	JLabel rightTxt;
	public void drawMainWindow() {
		// *** Create colors ***
		Color darkGray = new Color(30,30,30);
		Color gray = new Color(45,45,45);
		// ***
		
		// *** Creates the JPanels and modifies them ***
		welcometxt = new JLabel("V�lkommen hit!");
		rightTxt = new JLabel("H�ger textelement");
		JPanel leftSide = new JPanel(new BorderLayout());
		JTabbedPane rightSide = new JTabbedPane(JTabbedPane.TOP);
		JPanel topSide = new JPanel(new BorderLayout());
		JPanel month = new JPanel();
		JPanel week = new JPanel();
		JPanel day = new JPanel();
		leftSide.add(welcometxt);
		topSide.setPreferredSize(new Dimension(1000, 100));
		leftSide.setPreferredSize(new Dimension(250, 600));
		rightSide.setPreferredSize(new Dimension(750, 600));
		topSide.setBackground(darkGray);
		leftSide.setBackground(darkGray);
		rightSide.setBackground(darkGray);
		topSide.setVisible(true);
		leftSide.setVisible(true);
		rightSide.setVisible(true);
		
		//JTabbedPane
		month.setBackground(gray);
		week.setBackground(gray);
		day.setBackground(gray);
		month.add(rightTxt);
		rightSide.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT); //s�tter tabbarna till h�gra sidan
		rightSide.setBorder(BorderFactory.createLineBorder(Color.darkGray, 0)); //F�rs�ker s�tta border color
		UIManager.put("TabbedPane.foreground", Color.lightGray); //�ndrar f�rgen p� texten till ljus gr�
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
			       /*�ndrar f�rger p� olika effekter*/
			   }
			});
		// ***
		
		// *** Adds components to the Frame ***
		rightSide.addTab("Dag", day);
		rightSide.addTab("Vecka", week);
		rightSide.addTab("M�nad", month);
		
		add(topSide, BorderLayout.NORTH);
		add(leftSide, BorderLayout.WEST);
		add(rightSide, BorderLayout.CENTER);
		
		setSize(1000, 600);
		setVisible(true);
		db();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void start() {
		label = new JLabel("Anv�ndar-ID: ");
		pwdLabel = new JLabel("L�senord: ");
		JTextField txt = new JTextField();
		JPasswordField pass = new JPasswordField();
		JButton button = new JButton("Submit");
		
		
		txt.setPreferredSize(new Dimension(200, 30));
		pass.setPreferredSize(new Dimension(200, 30));
		button.setPreferredSize(new Dimension(200,50));
		
		
		add(label);
		add(txt);
		add(pwdLabel);
		add(pass);
		add(button);
		setLayout(new FlowLayout());
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!txt.getText().isEmpty() && pass.getPassword().length > 0) {
					new JavaWindow().drawMainWindow();
				}
				else
				{
					
				}
			}
		});
		setSize(800, 400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void db() {
		try {
			URL url = new URL("http://localhost/kalendersystem/kalendersystem.php");
			URLConnection phpConnection = url.openConnection();
			InputStream getData = phpConnection.getInputStream();
			String data = "";
			
			while(getData.available()>0)
				{
				data=data+((char) getData.read());
				}
			getData.close();
			
			System.out.println(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new JavaWindow().start();
	}

}
