import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class AddAdminFrame extends JFrame implements ActionListener{

	protected JPanel aAdminPanel;
	protected JPanel childPanel;
	protected JPanel childPanel2;
	protected JPanel childPanelA;
	protected JPanel inputPanel;
	
	protected JComboBox<String> listTab; 
	protected static String chosenTab;
	private JTextField IDField;
	
	protected JDBC jdbc;
	
	private JTextField iF, nF, ntF, eF, uF, nbF, nlF, pF  ;
	private JTextField nameF, streetF, nBudF, nLokF, nTelF,emF; 
	private JTextField nameOF, logoF, dataF;  
	
	private boolean flagaErr;
	private boolean empty;
	private boolean flagaSQL;
	private static boolean anothPanel;
	private boolean pre;
	
	private String query;
	
	public AddAdminFrame()
	{
		super("Dodaj");

		Dimension dim = new Dimension(900, 700);
		setPreferredSize(dim);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        createPanel();
        
        add(aAdminPanel);
        
        setVisible(true);
	}
	
	public void createPanel()
	{
		aAdminPanel = new JPanel();
		childPanel = new JPanel();
		aAdminPanel.setLayout(new BorderLayout());
		childPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 50));
		
		anothPanel = false;
		empty = false;
		
		JLabel tab = new JLabel("Wybierz tabelê: ");
		
		String[] tables = new String[] {"Operatorzy", "Biura", "Pracownicy"};
		listTab = new JComboBox<String>(tables);
		//listTab.setEditable(true); <- do edytowalnej listy
		chosenTab = (String) listTab.getSelectedItem();
		
		childPanel.add(listTab, SwingConstants.CENTER);
		childPanel.add(tab, SwingConstants.CENTER);
		
		aAdminPanel.add(childPanel, BorderLayout.PAGE_START);
		
		jdbc = new JDBC();
		
		childPanel2 = new JPanel();
		childPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 50));

		JButton send = new JButton("Dodaj");
		send.setActionCommand("Dodaj");
		send.addActionListener(this);
		
		JButton back = new JButton("Cofnij");
		back.setActionCommand("Cofnij");
		back.addActionListener(this);
		
		childPanel2.add(back, SwingConstants.CENTER);
		childPanel2.add(send, SwingConstants.CENTER);

		aAdminPanel.add(childPanel2, BorderLayout.PAGE_END);
		
		listTab.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				    listTab = (JComboBox<String>) e.getSource();

			       chosenTab = (String) listTab.getSelectedItem();
			        jdbc = new JDBC();
			        
			        if (anothPanel==true)
		        	{
			        	aAdminPanel.remove(childPanelA);//childPanelB.;		       
			        	anothPanel = false;
		        	}

		        if (chosenTab == "Biura")
		        {
		            createPanelBiura();
		            anothPanel = true;
		        }
		        else if (chosenTab=="Operatorzy") 
		        {
		           createPanelOperatorzy();
		           anothPanel = true;
		        }
		        			
		        else if (chosenTab=="Pracownicy") 
		        {
		           createPanelPracownicy();
		           anothPanel = true;
		        }
		      
		        aAdminPanel.revalidate();
		    	aAdminPanel.repaint(); 
			}
		});
	}
	
	protected void getAdd()
	{
		
		flagaSQL = true;
		flagaErr = false;
		
		int amountID = 0;
		jdbc.startJDBC();
		
		switch(chosenTab)
		{
			case "Biura":
			{
				try {
					//jdbc.rset = jdbc.stat.executeQuery("select count (*) from " + chosenTab);
					jdbc.rset = jdbc.stat.executeQuery("select MAX(ID_"+chosenTab+") from " + chosenTab);
					while(jdbc.rset.next())
					{
						amountID = jdbc.rset.getInt(1);
					}	
				} 
			catch (SQLException e) 
			{
				JOptionPane.showMessageDialog(null, "Wysy³anie zapytania do bazy nie powiod³o siê."); 
				e.printStackTrace();
			}
				
			jdbc.close();

			amountID = amountID + 1;
				
				query = "INSERT INTO " + chosenTab+ 
						" (ID_Biura, Nazwa_Biura, Ulica, Nr_Budynku, Nr_Lokalu, Nr_Telefonu, Email, ID_Poczty, ID_Operatora) VALUES ("+amountID;
				System.out.println("dodaje BIURO");
				
				String nameb = nameF.getText();
				System.out.println(nameb);
				
				if(nameb.equals("")) 
					flagaSQL = false ;
				else if(Pattern.matches("[A-Z][a-z]+", nameb) || Pattern.matches(".+_.+", nameb) )
				{
						query= query+", '" + nameb + "',";
				}
				else 
					flagaErr = true;
				
				String street = streetF.getText();		
				System.out.println(street);
				if(street.equals(""))
					flagaSQL = false ;
				else if(Pattern.matches("[A-Z][a-z]+", street))
				{ 
						query = query+" '" + street + "',";	
				}
				else 
					flagaErr = true;
				
				String nrBud = nBudF.getText();
				System.out.println(nrBud);
				if(nrBud.equals(""))
					flagaSQL = false ;
				else if(Pattern.matches("[0-9]{1,3}[a-z]{0,1}[A-Z]{0,1}", nrBud))
				{
					query = query+ " '" + nrBud + "'," ;
				}
				else flagaErr = true;
					
				String nrLok = nLokF.getText();
				System.out.println(nrLok);
				if(nrLok.equals(""))
					flagaSQL = false ;
				else if(Pattern.matches("[0-9]{1,3}", nrLok))
				{	
					query = query+ " '" + nrLok +"',";
				}
				else 
					flagaErr = true;
				
				String nrTel = nTelF.getText();
				System.out.println(nrTel);
				if(nrTel.equals(""))
					flagaSQL = false ;
				else if(Pattern.matches("[0-9]{9}", nrTel))
				{
					query = query+" '" + nrTel+"', ";
				}
				else 
					flagaErr = true;
				
				String eMail = emF.getText();
				System.out.println(eMail);
				if(eMail.equals("")) 
					flagaSQL = false ;
				else if(Pattern.matches(".+@.+\\.pl", eMail))
				{
					query = query+ " '" + eMail + "', 1, 1";
				}
				else 
					flagaErr = true;
				
				query = query +")";
				
				if (flagaSQL == true && flagaErr == false)
				{
					try
					{
						jdbc.startJDBC();
						jdbc.stat.executeUpdate(query);
						System.out.println("Trying to get answer...");
						jdbc.close();
				
					}
					catch (SQLException e2)
					{
						JOptionPane.showMessageDialog(null, "Wysy³anie zapytania do bazy nie powiod³o siê."); 
						e2.printStackTrace();
					}
				}
				break;
			}
		
			
			
			case "Operatorzy":
			{
				
				try {
					//jdbc.rset = jdbc.stat.executeQuery("select count (*) from " + chosenTab);
					jdbc.rset = jdbc.stat.executeQuery("select MAX(ID_Operatora) from " + chosenTab);
					while(jdbc.rset.next())
					{
						amountID = jdbc.rset.getInt(1);
					}	
				} 
			catch (SQLException e) 
			{
				JOptionPane.showMessageDialog(null, "Wysy³anie zapytania do bazy nie powiod³o siê."); 
				e.printStackTrace();
			}
			jdbc.close();

			amountID = amountID + 1;
			
				query = "INSERT INTO " + chosenTab+ 
						" (ID_Operatora, Nazwa_Operatora, Logo, Data_Zalozenia) VALUES ("+amountID;
				System.out.println("dodaje Operatora");
						
				try{
					
				String nameo = nameOF.getText();
				System.out.println(nameo);
				
				if(nameo.equals("")) 
					flagaSQL = false ;
				else if(Pattern.matches("[A-Z][a-z]+", nameo))
				{
						query= query+", '" + nameo + "',";
				}
				else 
					flagaErr = true;
				
				String logo = logoF.getText();		
				System.out.println(logo);
				if(logo.equals(""))
					flagaSQL = false ;
				else if(Pattern.matches("[A-Z][a-z]+", logo))
				{ 
						query = query+" '" + logo + "',";	
				}
				else 
					flagaErr = true;
				
				String dataO = dataF.getText();
				System.out.println(dataO);
				if(dataO.equals(""))
					flagaSQL = false ;
				else if(Pattern.matches("[0-9]{2}.+/.+[0-9]{2}.+/.+[0-9]{2}", dataO))
				{
					query = query+ " '" + dataO + "'" ;
				}
				else 
					flagaErr = true;
				
				query = query +")";
				}
				catch(NullPointerException e)
				{
					JOptionPane.showMessageDialog(null, "Nie wybrano tabeli."); 
					empty = true;
				}
				
				if (flagaSQL == true && flagaErr == false && !empty)
				{
					try
					{
						jdbc.startJDBC();
						jdbc.stat.executeUpdate(query);
						System.out.println("Trying to get answer...");
						jdbc.close();
				
					}
					catch (SQLException e2)
					{
						JOptionPane.showMessageDialog(null, "Wysy³anie zapytania do bazy nie powiod³o siê."); 
						e2.printStackTrace();
					}
				}
				break;
			}
			
			
			
			case "Pracownicy":
			{			
				try {
					//jdbc.rset = jdbc.stat.executeQuery("select count (*) from " + chosenTab);
						jdbc.rset = jdbc.stat.executeQuery("select MAX(ID_Pracownika) from " + chosenTab);
						while(jdbc.rset.next())
					{
						amountID = jdbc.rset.getInt(1);
					}	
				} 
			catch (SQLException e) 
			{
				JOptionPane.showMessageDialog(null, "Wysy³anie zapytania do bazy nie powiod³o siê."); 
				e.printStackTrace();
			}
			jdbc.close();

			amountID = amountID + 1;
				
				
				query = "INSERT INTO " + chosenTab+ 
						" (ID_Pracownika, Imie, Nazwisko, Nr_Telefonu, Email, Ulica, Nr_Budynku, Nr_Lokalu, PESEL, ID_Stanowiska,"
						+ " ID_Poczty, ID_Biura, ID_Operatora) VALUES ("+amountID;
				
				System.out.println("dodaje Pracownikow");
				
				String nameb = iF.getText();
				System.out.println(nameb);
				
				if(nameb.equals("")) 
					flagaSQL = false ;
				else if(Pattern.matches("[A-Z][a-z]+", nameb))
				{
						query= query+", '" + nameb + "',";
				}
				else 
					flagaErr = true;
				
				String nazw = nF.getText();		
				System.out.println(nazw);
				if(nazw.equals(""))
					flagaSQL = false ;
				else if(Pattern.matches("[A-Z][a-z]+", nazw))
				{ 
						query = query+" '" + nazw + "',";	
				}
				else 
					flagaErr = true;
				
				String nrtel = ntF.getText();
				System.out.println(nrtel);
				if(nrtel.equals(""))
					flagaSQL = false ;
				else if(Pattern.matches("[0-9]{9}", nrtel))
				{
					query = query+ " '" + nrtel + "'," ;
				}
				else flagaErr = true;
					
				
				
				String em = eF.getText();
				System.out.println(em);
				if(em.equals(""))
					flagaSQL = false ;
				else if(Pattern.matches(".+@.+\\.pl", em))
				{	
					query = query+ " '" + em +"',";
				}
				else 
					flagaErr = true;
				
				String ul = uF.getText();
				System.out.println(ul);
				if(ul.equals(""))
					flagaSQL = false ;
				else if(Pattern.matches("[A-Z][a-z]+", ul))
				{
					query = query+" '" + ul+"', ";
				}
				else 
					flagaErr = true;
				
				String nrbud = nbF.getText();
				System.out.println(nrbud);
				if(nrbud.equals("")) 
					flagaSQL = false ;
				else if(Pattern.matches("[0-9]{1,3}[a-z]{0,1}[A-Z]{0,1}", nrbud))
				{
					query = query+ " '" + nrbud + "', ";
				}
				else 
					flagaErr = true;
				
				String nrlok = nlF.getText();
				System.out.println(nrlok);
				if(nrlok.equals("")) 
					flagaSQL = false ;
				else if(Pattern.matches("[0-9]{1,3}", nrlok))
				{
					query = query+ " '" + nrlok + "', ";
				}
				else 
					flagaErr = true;
				
				//private JTextField iF, nF, ntF, eF, uF, nbF, nlF, pF  ;
				
				String pes = pF.getText();
				System.out.println(pes);
				if(pes.equals("")) 
					flagaSQL = false ;
				else if(Pattern.matches("[0-9]{11}", pes))
				{
					query = query+ " '" + pes + "', 1, 1, 1, 1";
				}
				else 
					flagaErr = true;
				
				query = query +")";
				
				if (flagaSQL == true && flagaErr == false)
				{
					try
					{
						jdbc.startJDBC();
						jdbc.stat.executeUpdate(query);
						System.out.println("Trying to get answer...");
						jdbc.close();
				
					}
					catch (SQLException e2)
					{
						JOptionPane.showMessageDialog(null, "Wysy³anie zapytania do bazy nie powiod³o siê."); 
						e2.printStackTrace();
					}
				}
				break;
			}
			default:
				break;
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) 
	{
	
	       
		String komenda = event.getActionCommand();
		switch(komenda)
		{
		case "Cofnij":
			{
				setVisible(false);
				AdminFrame adminFrame = new AdminFrame();	
				break;
			}
			
		case "Dodaj":
			{
				setVisible(false);
				getAdd(); 
	
				if (flagaSQL == false & flagaErr == false)
				
					JOptionPane.showMessageDialog(null, "Nie uzupe³niono wszystkich pól"); 
				
				else if (flagaErr == true)  
				
					JOptionPane.showMessageDialog(null, "Z³y format wprowadzonych danych"); 
						
				else if (flagaSQL == true && !empty)
					JOptionPane.showMessageDialog(null, "Modyfikacja przeprowadzona pomyœlnie"); 

			setVisible(true);

		break;
	}
	
	default:
		break;
}

}
	
	public void createPanelPracownicy()
	{
		childPanelA = new JPanel();
		childPanelA.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 100));
		
		inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(15, 2));
		inputPanel.add( new JLabel(" ") );
		inputPanel.add( new JLabel("Uzupe³nij wszystkie pola") );
		inputPanel.add( new JLabel(" ") );
		inputPanel.add( new JLabel(" ") );
		inputPanel.add( new JLabel("Imiê :    ") );
		iF = new JTextField();
		inputPanel.add(iF);

		inputPanel.add( new JLabel("Nazwisko :    "));
		nF = new JTextField();
		inputPanel.add(nF);

		inputPanel.add( new JLabel("Nr telefonu :    ") );
		ntF = new JTextField();
		inputPanel.add(ntF);

		inputPanel.add( new JLabel("Email :    "));
		eF = new JTextField();
		inputPanel.add(eF);

		inputPanel.add( new JLabel("Ulica :    ") );
		uF = new JTextField();
		inputPanel.add(uF);
	
		inputPanel.add( new JLabel("Nr budynku :    "));
		nbF = new JTextField();
		inputPanel.add(nbF);

		inputPanel.add( new JLabel("Nr lokalu :    ") );
		nlF = new JTextField();
		inputPanel.add(nlF);

		inputPanel.add( new JLabel("PESEL :    "));
		pF = new JTextField();
		inputPanel.add(pF);
			
		childPanelA.add(inputPanel);       
		aAdminPanel.add(childPanelA, BorderLayout.CENTER);
		
	}
	
	public void createPanelBiura()
	{
		childPanelA = new JPanel();
		childPanelA.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 100));
		
		inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(8, 2));
		inputPanel.add( new JLabel(" ") );
		inputPanel.add( new JLabel("Uzupe³nij wszystkie pola") );
		inputPanel.add( new JLabel(" ") );
		inputPanel.add( new JLabel(" ") );
		inputPanel.add( new JLabel("Nazwa Biura :    ") );
		nameF = new JTextField();
		inputPanel.add(nameF);
	
		inputPanel.add( new JLabel("Ulica :    "));
		streetF = new JTextField();
		inputPanel.add(streetF);

		inputPanel.add( new JLabel("Nr Budynku :    ") );
		nBudF = new JTextField();
		inputPanel.add(nBudF);

		inputPanel.add( new JLabel("Nr Lokalu :    ") );
		nLokF = new JTextField();
		inputPanel.add(nLokF);

		inputPanel.add( new JLabel("Nr Telefonu :    ") );
		nTelF = new JTextField();
		inputPanel.add(nTelF);

		inputPanel.add( new JLabel("Email :    ") );
		emF = new JTextField();
		inputPanel.add(emF);
		
		childPanelA.add(inputPanel);       
		aAdminPanel.add(childPanelA, BorderLayout.CENTER);
		
	}
	
	public void createPanelOperatorzy()
	{
		childPanelA = new JPanel();
		childPanelA.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 100));
		
		inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(5, 2));
		inputPanel.add( new JLabel(" ") );
		inputPanel.add( new JLabel("Uzupe³nij wszystkie pola") );
		inputPanel.add( new JLabel(" ") );
		inputPanel.add( new JLabel(" ") );
		inputPanel.add( new JLabel("Nazwa Operatora :    ") );
		nameOF = new JTextField();
		inputPanel.add(nameOF);
	
		inputPanel.add( new JLabel("Logo :    "));
		logoF = new JTextField();
		inputPanel.add(logoF);
	
		inputPanel.add( new JLabel("Data za³o¿enia :    ") );
		dataF = new JTextField();
		inputPanel.add(dataF);
		
		childPanelA.add(inputPanel);       
		aAdminPanel.add(childPanelA, BorderLayout.CENTER);
	}
	
}
