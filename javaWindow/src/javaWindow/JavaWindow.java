package javaWindow;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class JavaWindow extends JFrame{

	//private JButton[][] t = new JButton[9][9]; //Declared much earlier in the program, right after the class declaration.
	
	public void dayGrid() {
		
		
	}
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
		welcometxt = new JLabel("Välkommen hit!");
		rightTxt = new JLabel("Höger textelement");
		JPanel leftSide = new JPanel(new BorderLayout());
		JPanel rightSide = new JPanel(new BorderLayout());
		JPanel topSide = new JPanel(new BorderLayout());
		leftSide.add(welcometxt);
		rightSide.add(rightTxt);
		topSide.setPreferredSize(new Dimension(1000, 100));
		leftSide.setPreferredSize(new Dimension(250, 600));
		rightSide.setPreferredSize(new Dimension(750, 600));
		topSide.setBackground(darkGray);
		leftSide.setBackground(darkGray);
		rightSide.setBackground(gray);
		topSide.setVisible(true);
		leftSide.setVisible(true);
		rightSide.setVisible(true);
		// ***
		
		// *** Adds components to the Frame ***
		add(topSide, BorderLayout.NORTH);
		add(leftSide, BorderLayout.WEST);
		add(rightSide, BorderLayout.CENTER);
		setSize(1000, 600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void start() {
		label = new JLabel("Användar-ID: ");
		pwdLabel = new JLabel("Lösenord: ");
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
				new JavaWindow().drawMainWindow();
			}
		});
		setSize(800, 400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
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
