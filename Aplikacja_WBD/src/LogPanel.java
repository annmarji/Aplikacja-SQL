import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
 
public class LogPanel extends JPanel implements ActionListener {
    
	private JTextField nameField; //pole na nazwê
    private JPasswordField passField; //pole na has³o
    protected JButton loginButton; //przycisk logowania
   // private LogListener listener; //s³uchacz przycisku
    private LogFrame logFrame;
    private AdminFrame AdminFrame;
    private EmployeeFrame EmployeeFrame;
    private String login;
    private String passwd;
 
    /**
     * @return wprowadzona nazwa u¿ytkownika
     */
   /* public String getName() {
        return nameField.getText();
    }*/
 
    /**
     * @return wprowadzone przez u¿ytkownika has³o
     */
    public String getPassword() {
        String password = "";
        char[] pass = passField.getPassword();
        for(int i=0; i<pass.length; i++) {
            password += pass[i];
        }
        return password;
    }
 
    public LogPanel(LogFrame app){
        super();
        // ustawiamy layout
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.CENTER;
        gridBag.setConstraints(this, constraints);
        setLayout(gridBag);
        this.logFrame=app;
        // tworzymy komponenty logowania
        //this.listener = listener;
        //this.listener.setPanel(this);
        createComponents();
       
    }
 
    /**
     * Metoda, która tworzy etykiety i pola do wprowadzania danych.
     */
    private void createComponents() {
        JLabel name = new JLabel("Login: ");
        JLabel password = new JLabel("Haslo: ");
        nameField = new JTextField();
        passField = new JPasswordField();
 
        //pomocniczy panel do wprowadzania danych
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));
        inputPanel.add(name);
        inputPanel.add(nameField);
        inputPanel.add(password);
        inputPanel.add(passField);
        //tworzymy przycisk logowania
        loginButton = new JButton("Zaloguj");
        loginButton.setActionCommand("Zaloguj");
        loginButton.addActionListener(this);

 
        //pomocniczy panel do wyœrodkowania elementów
        JPanel parentPanel = new JPanel();
        parentPanel.setLayout(new BorderLayout());
        parentPanel.add(inputPanel, BorderLayout.CENTER);
        parentPanel.add(loginButton, BorderLayout.SOUTH);

        // dodajemy do g³ównego panelu
        this.add(parentPanel);
         
    }

   
    public void verify()
    { 	 
    	 String login = nameField.getText();
         String passwd = getPassword();         
         LogListener.authenticate(login, passwd);
    }
    
    
	@Override
	public void actionPerformed(ActionEvent e) {
		verify();
		if (e.getSource() == loginButton)
		{
			if (LogListener.flagaAdministrator == true)
			{
				logFrame.setVisible(false);
				AdminFrame = new AdminFrame();		
			}
			else if(LogListener.flagaPracownik == true)
			{
				logFrame.setVisible(false);
				EmployeeFrame = new EmployeeFrame();
			}
			else
			{
				JOptionPane.showMessageDialog(null, "B³êdne dane logowania"); 
			}
		}
		
	}
}
