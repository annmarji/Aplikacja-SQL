import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
 
public class LogFrame extends JFrame{
    public LogFrame() {
        super("Logowanie");
        LogListener listener = new LogListener(this);
        LogPanel loginPanel = new LogPanel(this);
        add(loginPanel);
 
        setPreferredSize(new Dimension(600, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        //loginPanel.verify();
        //JButton butt = new JButton();
        //add(butt);
        
    }

}
