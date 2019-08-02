import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.Writer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.util.Random;



public class Versuch1 implements ActionListener{

		public static void main (String Args[])  {
		int forName = 0;
		int forPassword = 0;	
		JDialog Dialog1 = new JDialog();
		Dialog1.setTitle("ComputerCamp2019-ChatTool");
		Dialog1.setSize(800,550);
		Dialog1.setLocation(0,0);
		
		JDialog NutzerNameDialog = new JDialog();
		NutzerNameDialog.setTitle("Anmeldung");
		NutzerNameDialog.setLocation(200,200);
		NutzerNameDialog.setSize(100,100);
		JTextField userNameField = new JTextField("Name");
		JTextField userPasswordField = new JTextField("Passwort");
		userNameField.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent ae) {
				if (userNameField.getText().contentEquals("Name")) {
					userNameField.setText(null);
				}
				int id = ae.getID();
				int UNcode = ae.getKeyCode();
				if (id == KeyEvent.KEY_PRESSED && UNcode == 10) {
					String userEingabeName = userNameField.getText();
					userNameField.setText(null); }}
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}
			});
		userPasswordField.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent be) {
				if (userPasswordField.getText().contentEquals("Passwort")) {
					userPasswordField.setText(null);
				}
				int id =be.getID();
				int UPcode = be.getKeyCode();
				if (id == KeyEvent.KEY_PRESSED && UPcode == 10) {
				String userEingabePasswort = userPasswordField.getText();
				userPasswordField.setText(null);
			}
			}
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}
		});
		NutzerNameDialog.add(userNameField, java.awt.BorderLayout.NORTH);
		NutzerNameDialog.add(userPasswordField, java.awt.BorderLayout.SOUTH);
		
		
		
		
		Border bo =new LineBorder(Color.blue);
		JMenuBar bar = new JMenuBar();
		bar.setBorder(bo);
		String text23 = "";
		JLabel Label2 = new JLabel();
		Label2.setBackground(Color.blue);
		Label2.setLocation(700,400);
		Label2.setAlignmentY(770);
		Label2.addKeyListener(null);
		String neuString = "DA";
		int neuInt = 23;
		int experimental = 0;
		boolean ba1 = false;
		
		
		
		
		JMenu menu1 =new JMenu("Räume");
		JMenu menu2 = new JMenu("Optionen");
		JMenu menu3 = new JMenu("Hilfe");
		JMenu menu4 = new JMenu("Profil");
		JMenuItem m1item1 = new JMenuItem("Neuem Raum beitreten");
		JMenuItem m1item2 = new JMenuItem("Raum verlassen");
		JMenuItem m1item3 = new JMenuItem("Raumliste anzeigen");
		JMenuItem m2item1 = new JMenuItem("Schriftfarbe");
		JMenuItem m3item1 = new JMenuItem("Hilfe");
		JMenuItem m4item1 = new JMenuItem("Benutzername ändern");
		JMenuItem m4item2 = new JMenuItem("Berechtigungen");
		JMenuItem m2item2 = new JMenuItem("Dateien versenden");
		m1item1.setName("B");
		menu1.add(m1item1);
		menu1.add(m1item2);
		menu1.add(m1item3);
		menu2.add(m2item1);
		menu2.add(m2item2);
		menu3.add(m3item1);
		menu4.add(m4item1);
		menu4.add(m4item2);
		bar.add(menu1);
		bar.add(menu2);
		bar.add(menu3);
		bar.add(menu4);
		Dialog1.setJMenuBar(bar);
		Dialog1.add(Label2);
		Action Tat = new EXPAction();
		Action Raumbeitritt = ActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			JDialog Raumliste = new JDialog("");
			JTextField RaumwahlField = new JTextField("");
			
			Raumliste.setSize(200, 800);
			Raumliste.setLocation(100,100);
			Raumliste.add(RaumwahlField, java.awt.BorderLayout.SOUTH);
			Raumliste.setVisible(true);
			System.out.println("BA");
			}});
		m1item1.addActionListener(Raumbeitritt);
		m1item2.addActionListener(Tat);
		m1item3.addActionListener(Tat);
		m2item1.addActionListener(Tat);
		m3item1.addActionListener(Tat);
		m4item1.addActionListener(Tat);
		m4item2.addActionListener(Tat);
		
		Dialog1.setLayout(new java.awt.BorderLayout());
		JTextField eingabeFeld = new JTextField();
		eingabeFeld.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent ke) {
				int id = ke.getID();
				String userEingabe;
				int code = ke.getKeyCode();
				if (id == KeyEvent.KEY_PRESSED && code == 10) {
					System.out.println("Erfolg");
				
					userEingabe = eingabeFeld.getText();
					
					eingabeFeld.setText(null);
					System.out.println(userEingabe);
				}
			}public void keyReleased(KeyEvent arg0) {
			}
			public void keyTyped(KeyEvent arg0) {
			}
		});
		text23 = "";
		eingabeFeld.setLocation(770,300);
		eingabeFeld.setAlignmentY(770);
		Dialog1.add(eingabeFeld, java.awt.BorderLayout.SOUTH);
		Dialog1.setVisible(true);
		NutzerNameDialog.setVisible(true);
		text23 = eingabeFeld.getText();
		
		}

		
		private static Action ActionListener(ActionListener actionListener) {
			// TODO Auto-generated method stub
			return null;
		}


		public void actionPerformed(ActionEvent arg0) {
			
			
			
			
			
		}

	



}
		