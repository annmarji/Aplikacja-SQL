
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
import java.util.regex.Pattern;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ModEmployeeFrame extends JFrame implements ActionListener{
	
	protected JPanel sEmployeePanel;
	protected JPanel childPanel;
	protected JPanel childPanelC;
	private JTextField iF, nF, ntF, eF, uF, nbF, nlF, pF;
	static boolean flagaSql = false;
	static boolean flagaErr = false;
	private String sql;
	
	public ModEmployeeFrame()
	{
		super("Zmieñ swoje dane");
		Dimension dim = new Dimension(800, 600);
		setPreferredSize(dim);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        createPanel();
        
        add(sEmployeePanel);
        
        setVisible(true);	
	}

	public void createPanel()
	{			
		sEmployeePanel = new JPanel();
		childPanel = new JPanel();
		sEmployeePanel.setLayout(new BorderLayout());
		childPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 100));
		
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(14, 2));
		inputPanel.add( new JLabel(" ") );
		inputPanel.add( new JLabel("Uzupe³nij pola, których wartoœci chcesz zmieniæ:") );
		inputPanel.add( new JLabel(" ") );
		inputPanel.add( new JLabel(" ") );
		//inputPanel.add( new JLabel("Imiê :    ") );
		//iF = new JTextField();
		//inputPanel.add(iF);
		//inputPanel.add( new JLabel(" ") );
		//inputPanel.add( new JLabel(" ") );
		inputPanel.add( new JLabel("Nazwisko :    "));
		nF = new JTextField();
		inputPanel.add(nF);
		//inputPanel.add( new JLabel(" ") );
		//inputPanel.add( new JLabel(" ") );
		inputPanel.add( new JLabel("Nr telefonu :    ") );
		ntF = new JTextField();
		inputPanel.add(ntF);
		//inputPanel.add( new JLabel(" ") );
		//inputPanel.add( new JLabel(" ") );
		inputPanel.add( new JLabel("Email :    "));
		eF = new JTextField();
		inputPanel.add(eF);
		//inputPanel.add( new JLabel(" ") );
		//inputPanel.add( new JLabel(" ") );
		inputPanel.add( new JLabel("Ulica :    ") );
		uF = new JTextField();
		inputPanel.add(uF);
		//inputPanel.add( new JLabel(" ") );
		//inputPanel.add( new JLabel(" ") );
		inputPanel.add( new JLabel("Nr budynku :    "));
		nbF = new JTextField();
		inputPanel.add(nbF);
		//inputPanel.add( new JLabel(" ") );
		//inputPanel.add( new JLabel(" ") );
		inputPanel.add( new JLabel("Nr lokalu :    ") );
		nlF = new JTextField();
		inputPanel.add(nlF);
		//inputPanel.add( new JLabel(" ") );
		//inputPanel.add( new JLabel(" ") );
		inputPanel.add( new JLabel("PESEL :    "));
		pF = new JTextField();
		inputPanel.add(pF);
			
		childPanel.add(inputPanel);       
		sEmployeePanel.add(childPanel, BorderLayout.CENTER);
			
		childPanelC = new JPanel();
		childPanelC.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 50));
		JButton back = new JButton("Cofnij");
		back.setActionCommand("Cofnij");
		back.addActionListener(this);
		childPanelC.add(back, SwingConstants.CENTER);
		JButton commit = new JButton("ZatwierdŸ");
		commit.setActionCommand("ZatwierdŸ");
		commit.addActionListener(this);
		childPanelC.add(commit, SwingConstants.CENTER);
		
		sEmployeePanel.add(childPanelC, BorderLayout.PAGE_END);
		
		}
	 
	public String getnew()
	{
		boolean prev = false;
		//String imie = iF.getText();
		//System.out.println(imie);
		//if(imie.equals("")) sql= "UPDATE PRACOWNICY SET";
		//	else if(Pattern.matches("[A-Z][a-z]+", imie) == true)
		//		{
		//			sql= "UPDATE PRACOWNICY SET Imie ='" + imie + "'";
		//			prev=true;
		//		}
		//	else System.out.println("Z³y format wprowadzonych danych");
		sql= "UPDATE PRACOWNICY SET";
		String nazw = nF.getText();		
		System.out.println(nazw);
		if(nazw.equals("")) prev = false;
			else if(Pattern.matches("[A-Z][a-z]+", nazw) == true)
			{
				if(prev == true)sql= sql+", Nazwisko='" + nazw + "'" ;
				else sql= sql+" Nazwisko='" + nazw + "'";
				prev=true;
			}
			else flagaErr = true;

		String nr_tel = ntF.getText();
		System.out.println(nr_tel);
		if(nr_tel.equals("")) prev = false;
			else if(Pattern.matches("[0-9]{9}", nr_tel) == true)
				{
					if(prev == true)sql= sql+ ", Nr_telefonu=" + nr_tel ;
					else sql= sql+" Nr_telefonu=" + nr_tel ;
					prev=true;
				}
			else flagaErr = true;
		String email = eF.getText();
		System.out.println(email);
		if(email.equals("")) prev = false;
			else if(Pattern.matches(".+@.+\\.pl", email) == true)
			{
				if(prev == true)sql= sql+ ", Email='" + email + "'";
				else sql= sql+" Email='" + email + "'";
				prev=true;
			}
			else flagaErr = true;
		String ulica = uF.getText();
		System.out.println(ulica);
		if(ulica.equals("")) prev = false;
			else if(Pattern.matches("[A-Z][a-z]+", ulica) == true)
			{
				if(prev == true)sql= sql+ ", Ulica='" + ulica + "'";
				else sql= sql+ " Ulica='" + ulica + "'";
				prev=true;
			}
			else flagaErr = true;
		
		String nr_bud = nbF.getText();
		System.out.println(nr_bud);
		if(nr_bud.equals(""))prev = false;
			else if(Pattern.matches("[0-9]{1,3}[a-z]{0,1}[A-Z]{0,1}", nr_bud) == true)
			{
				if(prev == true)sql= sql+ ", Nr_budynku='" + nr_bud + "'" ;
				else sql= sql+ " Nr_budynku='" + nr_bud + "'" ;
				prev=true;
			}
			else flagaErr = true;

		String nr_lok = nlF.getText();
		System.out.println(nr_lok);
		if(nr_lok.equals("")) prev = false;
			else if(Pattern.matches("[0-9]{1,3}", nr_lok) == true)
			{
				if(prev == true)sql= sql+ ", Nr_lokalu=" + nr_lok ;
				else sql= sql+ " Nr_lokalu=" + nr_lok ;;
				prev=true;
			}
			else flagaErr = true;
		
		String pesel = pF.getText();
		System.out.println(pesel);
		if(pesel.equals(""))sql= sql+ " WHERE Imie = 'Janina'";
			else if(Pattern.matches("[0-9]{11}", pesel) == true)
			{
				if(prev == true)sql= sql+ ", PESEL=" + pesel + " WHERE Imie = 'Janina'";
				else sql= sql+ " PESEL=" + pesel + " WHERE Imie = 'Janina'";
			}
			else flagaErr = true;
		
		if(sql.equals("UPDATE PRACOWNICY SET WHERE Imie = 'Janina'")) flagaSql = false;
			else flagaSql = true;
		
		return sql;
	}
		
	public void dbconnect(String sql)
	{
		String USER = "azadrozn";
		String PASS = "WBD_jest!";
		String DB_URL = "jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf";
		System.out.println("Start JDBC program ...");
			
			try{
				Class.forName("oracle.jdbc.OracleDriver");
				System.out.println("Creating query...");
				Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
				Statement stmt = conn.createStatement();
				System.out.println(sql);
				stmt.executeUpdate(sql);
				
				System.out.println("Trying to get answer...");
				
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

     		case "ZatwierdŸ":
     		{
     			setVisible(false);
     			getnew();
     			if (flagaSql == true && flagaErr == false) 
     			{
     				dbconnect(sql);
     				ShowEmployeeFrame showEmployeeFrame = new ShowEmployeeFrame();
     			}
     			else if (flagaSql == false & flagaErr == false)
     			{
     				JOptionPane.showMessageDialog(null, "Nie wprowadzono ¿adnych zmian"); 
     				ModEmployeeFrame modEmployeeFrame = new ModEmployeeFrame();
     				//System.out.println("Nie wprowadzono ¿adnych zmian");
     			}
     			else // flagaSql == false & flagaErr == true  
     			{
     				JOptionPane.showMessageDialog(null, "Z³y format wprowadzonych danych"); 
     				ModEmployeeFrame modEmployeeFrame = new ModEmployeeFrame();
     			}
     			break;
     		}
    		default:
    			break;
     			
     	}
	 }
}
