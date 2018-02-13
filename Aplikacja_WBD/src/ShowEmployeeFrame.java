import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ShowEmployeeFrame extends JFrame implements ActionListener{
	
	protected JPanel sEmployeePanel;
	protected JPanel childPanel;
	protected JPanel childPanelC;
	
	public ShowEmployeeFrame()
	{
		super("Wyœwietl swoje dane");
		Dimension dim = new Dimension(800, 600);
		setPreferredSize(dim);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        dbconnect();
        
        add(sEmployeePanel);
        
        setVisible(true);	
	}

	public void createPanel(String simie, String snazw, String snr_tel, String semail, String sulica, String snr_bud, String snr_lok, String spesel)
	{	/*sEmployeePanel = new JPanel();
		sEmployeePanel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		childPanel = new JPanel();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		//c.ipady = 40; 
		c.weightx = 2.0;
		c.gridwidth = 1;
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(8,1));
		inputPanel.add( new JLabel("Imiê: ") );
		inputPanel.add( new JLabel("Nazwisko: "));
		inputPanel.add( new JLabel("Nr telefonu: ") );
		inputPanel.add( new JLabel("Email: "));
		inputPanel.add( new JLabel("Ulica: ") );
		inputPanel.add( new JLabel("Nr budynku: "));
		inputPanel.add( new JLabel("Nr lokalu: ") );
		inputPanel.add( new JLabel("PESEL: "));
		childPanel.add(inputPanel); 
		sEmployeePanel.add(childPanel, c);
		
		GridBagConstraints c1 = new GridBagConstraints();
		childPanel1 = new JPanel();
		c1.fill = GridBagConstraints.HORIZONTAL;
		c1.gridx = 1;
		c1.gridy = 1;
		//c1.ipady = 40; 
		c1.weightx = 2.0;
		c1.gridwidth = 2;
		JPanel inputPanel1 = new JPanel();
		inputPanel1.setLayout(new GridLayout(8,1));
		inputPanel1.add( new JLabel("simie") );
		inputPanel1.add( new JLabel("snazw") );
		inputPanel1.add( new JLabel("snr_tel") );
		inputPanel1.add( new JLabel("semail") );
		inputPanel1.add( new JLabel("sulica") );
		inputPanel1.add( new JLabel("snr_bud") );
		inputPanel1.add( new JLabel("snr_lok") );
		inputPanel1.add( new JLabel("spesel") );
		childPanel1.add(inputPanel1, SwingConstants.CENTER);  
		sEmployeePanel.add(childPanel1, c1);
		
		
		GridBagConstraints c2 = new GridBagConstraints();
		childPanelC = new JPanel();
		c2.fill = GridBagConstraints.HORIZONTAL;
		c2.gridx = 1;
		c2.gridy = 2;
		c2.weightx = 1.0;
		c2.gridwidth = 1;
		//c2.ipady = 0;   
		c2.weighty = 1.0; 
		c2.anchor = GridBagConstraints.PAGE_END;

		JButton back = new JButton("Cofnij");
		back.setActionCommand("Cofnij");
		back.addActionListener(this);
		childPanelC.add(back, SwingConstants.CENTER);
		sEmployeePanel.add(childPanelC, c2);*/
		
		sEmployeePanel = new JPanel();
		childPanel = new JPanel();
		sEmployeePanel.setLayout(new BorderLayout());
		childPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 80));
				
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(15, 2));
		inputPanel.add( new JLabel("Imiê: ") );
		inputPanel.add( new JLabel(simie) );
		inputPanel.add( new JLabel(" ") );
		inputPanel.add( new JLabel(" ") );
		inputPanel.add( new JLabel("Nazwisko: "));
		inputPanel.add( new JLabel(snazw) );
		inputPanel.add( new JLabel(" ") );
		inputPanel.add( new JLabel(" ") );
		inputPanel.add( new JLabel("Nr telefonu: ") );
		inputPanel.add( new JLabel(snr_tel) );
		inputPanel.add( new JLabel(" ") );
		inputPanel.add( new JLabel(" ") );
		inputPanel.add( new JLabel("Email: "));
		inputPanel.add( new JLabel(semail) );
		inputPanel.add( new JLabel(" ") );
		inputPanel.add( new JLabel(" ") );
		inputPanel.add( new JLabel("Ulica: ") );
		inputPanel.add( new JLabel(sulica) );
		inputPanel.add( new JLabel(" ") );
		inputPanel.add( new JLabel(" ") );
		inputPanel.add( new JLabel("Nr budynku: "));
		inputPanel.add( new JLabel(snr_bud) );
		inputPanel.add( new JLabel(" ") );
		inputPanel.add( new JLabel(" ") );
		inputPanel.add( new JLabel("Nr lokalu: ") );
		inputPanel.add( new JLabel(snr_lok) );
		inputPanel.add( new JLabel(" ") );
		inputPanel.add( new JLabel(" ") );
		inputPanel.add( new JLabel("PESEL: "));
		inputPanel.add( new JLabel(spesel) );
		
		//System.out.println(simie + " " + snazw + " " +  snr_tel + " " +  semail + " " +  sulica + " " +  snr_bud + " " +  snr_lok + " " +  spesel);
		childPanel.add(inputPanel);       
		sEmployeePanel.add(childPanel, BorderLayout.CENTER);
		
		childPanelC = new JPanel();
		childPanelC.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 50));
		
		JButton back = new JButton("Cofnij");
		back.setActionCommand("Cofnij");
		back.addActionListener(this);
		childPanelC.add(back, SwingConstants.CENTER);
		
		sEmployeePanel.add(childPanelC, BorderLayout.PAGE_END);
	}
	
	public void dbconnect()
	{
		String USER = "azadrozn";
		String PASS = "WBD_jest!";
		String DB_URL = "jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf";
		System.out.println("Start JDBC program ...");
		
		try{
			Class.forName("oracle.jdbc.OracleDriver");
			System.out.println("Connecting to database...");
			Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
			Statement stmt = conn.createStatement();
			String sql = "SELECT Imie, Nazwisko, Nr_telefonu, Email, Ulica, Nr_budynku, Nr_lokalu, PESEL FROM Pracownicy WHERE Imie='Janina'";
			ResultSet rs = stmt.executeQuery(sql);
				
			while(rs.next()){ 
				String simie = rs.getString("Imie");
				String snazw = rs.getString("Nazwisko");
				String snr_tel = rs.getString("Nr_telefonu");
				String semail = rs.getString("Email");
				String sulica = rs.getString("Ulica");
				String snr_bud = rs.getString("Nr_budynku");
				String snr_lok = rs.getString("Nr_lokalu");
				String spesel = rs.getString("PESEL");
				
				createPanel(simie, snazw, snr_tel, semail, sulica, snr_bud, snr_lok, spesel);
			}

		rs.close();
		stmt.close();
		conn.close();
		
	}catch(Exception e){System.err.println(e);}
	System.out.println("End of program...");
	
	}
	
	 @Override
	    public void actionPerformed(ActionEvent event) {
	       
	 String komenda = event.getActionCommand();
     	switch(komenda)
     	{
     		case "Cofnij":
     		{
     			setVisible(false);
     			EmployeeFrame employeeFrame = new EmployeeFrame();	
     			break;
     		}

     		default:
     			break;
     	}
	 }
}
