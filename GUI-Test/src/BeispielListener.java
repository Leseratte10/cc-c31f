
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
 
// Damit Objekte der Klasse BeispielListener
// zum ActionListener werden kann, muss das Interface
// ActionListener implementiert werden
public class BeispielListener extends JFrame implements ActionListener 
{
	
			@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("TEST 9in Universal");
		
	}
	
   
    
}

