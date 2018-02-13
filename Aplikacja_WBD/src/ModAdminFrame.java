import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class ModAdminFrame extends JFrame implements ActionListener{

	protected JPanel mAdminPanel;
	protected JPanel childPanel;
	protected JPanel childPanel1;
	protected JPanel childPanel2;
	protected JPanel childPanel3;
	protected JPanel childPanelB;
	protected JPanel childPanelP;
	protected JPanel childPanelO;
	protected JPanel inputPanel;
	
	protected JComboBox<String> listTab; 
	protected JComboBox<String> listCol;
	
	protected DefaultTableModel model;
	protected JTable table;
	protected static String chosenTab;
	
	private JTextField IDField;
	private JTextField iF, nF, ntF, eF, uF, nbF, nlF, pF  ;
	private JTextField nameF, streetF, nBudF, nLokF, nTelF,emF; 
	private JTextField nameOF, logoF, dataF; 
	
	private String sql;
	private String imie;
	private String nazw;
	private String nr_tel;
	private String email;
	private String ulica;
	private String nr_bud;
	private String nr_lok;
	private String pesel;
	
	protected JDBC jdbc;
	
	private boolean flagaErr;
	private boolean flagaSQL;
	private boolean prev;
	private boolean bledneID;
	private static boolean anothPanel;
	
	public ModAdminFrame()
	{
		super("Modyfikuj");
		Dimension dim = new Dimension(900, 700);
		setPreferredSize(dim);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        createPanel();
        add(mAdminPanel);
        
        setVisible(true);
	}
	
	public void createPanel()
	{
		mAdminPanel = new JPanel();
		anothPanel = false;
		childPanel = new JPanel();
		mAdminPanel.setLayout(new BorderLayout());
		childPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 50));
		
		JLabel tab = new JLabel("Wybierz tabelê: ");
		
		JLabel col = new JLabel("Wpisz ID wiersza, który chcesz zmieniæ: ");
		
		String[] tables = new String[] {"Operatorzy", "Biura", "Pracownicy"};
		listTab = new JComboBox<String>(tables);
		//listTab.setEditable(true); <- do edytowalnej listy
		chosenTab = (String) listTab.getSelectedItem();
		
		//listCol.setEditable(true); <- do edytowalnej listy
		IDField = new JTextField();		
		Dimension d = new Dimension(50,25);
		IDField.setPreferredSize(d);
		childPanel.add(IDField, SwingConstants.CENTER); //<- tu jest problem
		childPanel.add(col, SwingConstants.CENTER);
		childPanel.add(listTab, SwingConstants.CENTER);
		childPanel.add(tab, SwingConstants.CENTER);
		
		mAdminPanel.add(childPanel, BorderLayout.PAGE_START);
		
		childPanel2 = new JPanel();
		childPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 50));

		JButton send = new JButton("Zatwierdz");
		send.setActionCommand("Zatwierdz");
		send.addActionListener(this);
		
		JButton back = new JButton("Cofnij");
		back.setActionCommand("Cofnij");
		back.addActionListener(this);
		
		childPanel2.add(back, SwingConstants.CENTER);
		childPanel2.add(send, SwingConstants.CENTER);

		mAdminPanel.add(childPanel2, BorderLayout.PAGE_END);
		
		jdbc = new JDBC();
		
		listTab.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				    listTab = (JComboBox<String>) e.getSource();

			       chosenTab = (String) listTab.getSelectedItem();
			        //jdbc = new JDBC();
			        if (anothPanel==true)
			        	{
			        	mAdminPanel.remove(childPanelB);//childPanelB.;		       
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
			      
			        mAdminPanel.revalidate();
			    	mAdminPanel.repaint(); 
			}
		});
	}
	
	public void getNew()	
	{
		int ID = 0, amountID=0;
		flagaErr = false;
		bledneID = false;
		
		

		try
		{
			ID = Integer.parseInt(IDField.getText());
		}
		
		catch (NullPointerException e)
		{
			JOptionPane.showMessageDialog(null, "Nie uzupe³niono pola ID.");
			flagaErr = true;
			bledneID = true;
		}

		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "B³êdnie podane ID wiersza");
			bledneID = true;
			flagaErr = true;
		}	
		
		jdbc.startJDBC();
		
		
		
	switch(chosenTab)
	{
		case "Biura":
		{
			
			try {
				jdbc.rset = jdbc.stat.executeQuery("select MAX(ID_"+chosenTab+") from " + chosenTab);
				while(jdbc.rset.next())
				{
					amountID = jdbc.rset.getInt(1);
				}	
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Wysy³anie zapytania do bazy nie powiod³o siê."); 
			}
			jdbc.close();
			
			try{
				System.out.println(amountID);
			}
			catch (NullPointerException e)
			{
				JOptionPane.showMessageDialog(null, "Nie uzupe³niono pola ID.");
				flagaErr = true;
				bledneID = true;
			}
		
			if ((ID<1 || ID > amountID) && !bledneID)
			{
				JOptionPane.showMessageDialog(null, "Wiersz o podanym ID nie istnieje");
				flagaErr = true;
				bledneID = true;
			}
		
			
			sql= "UPDATE Biura SET ";
			
			String nameb = nameF.getText();
			System.out.println(nameb);
			if(nameb.equals("")) 
				sql = sql ;
			else if(Pattern.matches("[A-Z][a-z]+", nameb) || Pattern.matches(".+_.+", nameb) )
			{
				if(prev == true)
					sql= sql+", Nazwa_Biura= '" + nameb + "'" ;
				else 
					sql= sql+" Nazwa_Biura= '" + nameb + "'";
				prev=true;	
			}
			else flagaErr = true;
			
			String street = streetF.getText();		
			System.out.println(street);
			if(street.equals(""))
				sql= sql;
			else if(Pattern.matches("[A-Z][a-z]+", street))
			{
				if(prev == true)
					sql= sql+", Ulica= '" + street + "'" ;
				else 
					sql= sql+" Ulica= '" + street + "'";
				prev=true;	
			}
			else flagaErr = true;
			
			String nrBud = nBudF.getText();
			System.out.println(nrBud);
			if(nrBud.equals(""))
				sql= sql;
			else if(Pattern.matches("[0-9]{1,3}[a-z]{0,1}[A-Z]{0,1}", nrBud) == true)
			{
				if(prev == true)
					sql= sql+ ", Nr_budynku='" + nrBud + "'" ;
				else 
					sql= sql+ " Nr_budynku='" + nrBud + "'" ;
				prev=true;
			}
			else flagaErr = true;
				
			String nrLok = nLokF.getText();
			System.out.println(nrLok);
			if(nrLok.equals("")
					)sql= sql;
			else if(Pattern.matches("[0-9]{1,3}", nrLok) == true)
			{
				if(prev == true)
					sql= sql+ ", Nr_lokalu=" + nrLok ;
				else 
					sql= sql+ " Nr_lokalu=" + nrLok ;;
				prev=true;
			}
			else flagaErr = true;
			
			String nrTel = nTelF.getText();
			System.out.println(nrTel);
			if(nrTel.equals(""))
				sql= sql;
			else if(Pattern.matches("[0-9]{9}", nrTel) == true)
			{
				if(prev == true)
					sql= sql+ ", Nr_telefonu=" + nrTel ;
				else 
					sql= sql+" Nr_telefonu=" + nrTel ;
				prev=true;
			}
			else flagaErr = true;
			
			String eMail = emF.getText();
			System.out.println(eMail);
			if(eMail.equals("")) 
				sql= sql + "WHERE ID_Biura = "+ID;
			else if(Pattern.matches(".+@.+\\.pl", eMail) == true)
			{
				if(prev == true)
					sql= sql+ ", Email='" + eMail + "' WHERE ID_Biura = "+ID;
				else 
					sql= sql+" Email='" + eMail + "'WHERE ID_Biura = "+ID;
				prev=true;
			}
			else flagaErr = true;
			
			if(sql.equals("UPDATE Biura SET WHERE ID = "+ID)) flagaSQL = false;
			else flagaSQL = true;
			
			if (flagaSQL == true && flagaErr == false)
			{
				try{
				jdbc.startJDBC();
				jdbc.stat.executeUpdate(sql);
				System.out.println("Trying to get answer...");
				jdbc.close();
			
			}
		catch (SQLException e2)
		{
			e2.printStackTrace();
			JOptionPane.showMessageDialog(null, "Wysy³anie zapytania do bazy nie powiod³o siê."); 
		}
		}
			break;
		}
	
	case "Operatorzy":
		{
			//private JTextField nameOF, logoF, dataF;
			
			try {
					jdbc.rset = jdbc.stat.executeQuery("select MAX(ID_Operatora) from " + chosenTab);
					while(jdbc.rset.next())
				{
					amountID = jdbc.rset.getInt(1);
				}	
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Wysy³anie zapytania do bazy nie powiod³o siê."); 
			}
			jdbc.close();
			
			try{
				System.out.println(amountID);
			}
			catch (NullPointerException e)
			{
				JOptionPane.showMessageDialog(null, "Nie uzupe³niono pola ID.");
				flagaErr = true;
				bledneID = true;
			}
		
			if ((ID<1 || ID > amountID) && !bledneID)
			{
				JOptionPane.showMessageDialog(null, "Wiersz o podanym ID nie istnieje");
				flagaErr = true;
			}
			
		
			try	{
						
			sql= "UPDATE OPERATORZY SET";
			String nameO = nameOF.getText();
			System.out.println(nameO);
			if(nameO.equals("")) sql = sql ;
			else if(Pattern.matches("[A-Z][a-z]+", nameO) )
			{
				if(prev == true)sql= sql+", Nazwa_Operatora= '" + nameO + "'" ;
				else sql= sql+" Nazwa_Operatora= '" + nameO + "'";
				prev=true;	
			}
			else flagaErr = true;
			
			String logo = logoF.getText();
			System.out.println(logo);
			if(logo.equals("")) sql = sql ;
			else if(Pattern.matches("[A-Z][a-z]+", logo) )
			{
				if(prev == true)sql= sql+", Logo= '" + logo + "'" ;
				else sql= sql+" Logo= '" + logo + "'";
				prev=true;	
			}
			else flagaErr = true;
			
			String dataO = dataF.getText();
			System.out.println(dataO);
			if(dataO.equals("")) 
				sql = sql + "WHERE ID_Operatora = "+ID ;
			else if(Pattern.matches("([0-9]{2}/){2}[0-9]{2}", dataO) )
			{
				if(prev == true)
					sql= sql+", Data_Zalozenia = '" + dataO + "' WHERE ID_Operatora = "+ID ;
				else 
					sql= sql+" Data_Zalozenia= '" + dataO + "' WHERE ID_Operatora = "+ID;
				prev=true;	
			}
			else flagaErr = true;
			
			if(sql.equals("UPDATE OPERATORZY SET WHERE ID = "+ID)) 
				flagaSQL = false;
			else 
				flagaSQL = true;
			
			}
			catch (NullPointerException e)
			{
				JOptionPane.showMessageDialog(null, "Nie uzupe³niono ¿adnego pola.");
				flagaErr = true;
			}
			
			if (flagaSQL == true && flagaErr == false)
			{
				try{
				jdbc.startJDBC();
				jdbc.stat.executeUpdate(sql);
				System.out.println("Trying to get answer...");
				jdbc.close();
			
			}
		catch (SQLException e2)
		{
			e2.printStackTrace();
			JOptionPane.showMessageDialog(null, "Wysy³anie zapytania do bazy nie powiod³o siê."); 
		}
		}
			
			break;
		}
		
	case "Pracownicy":
		{
		
			try {
				jdbc.rset = jdbc.stat.executeQuery("select MAX(ID_Pracownika) from " + chosenTab);
				while(jdbc.rset.next())
				{
					amountID = jdbc.rset.getInt(1);
				}	
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Wysy³anie zapytania do bazy nie powiod³o siê."); 
			}
			jdbc.close();
		
			try{
				System.out.println(amountID);
			}
			catch (NullPointerException e)
			{
				JOptionPane.showMessageDialog(null, "Nie uzupe³niono pola ID.");
				flagaErr = true;
				bledneID = true;
			}
			
			if ((ID<1 || ID > amountID) && !bledneID)
			{
				JOptionPane.showMessageDialog(null, "Wiersz o podanym ID nie istnieje");
				flagaErr = true;
			}
		
			
			
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
			if(pesel.equals(""))sql= sql+ " WHERE ID_Pracownika = "+ID;
				else if(Pattern.matches("[0-9]{11}", pesel) == true)
				{
					if(prev == true)sql= sql+ ", PESEL=" + pesel + " WHERE ID_Pracownika = "+ID;
					else sql= sql+ " PESEL=" + pesel + " WHERE ID_Pracownika = "+ID;
				}
				else flagaErr = true;
			
			if(sql.equals("UPDATE PRACOWNICY SET WHERE ID_Pracownika = "+ID)) flagaSQL = false;
				else flagaSQL = true;			
			
			if (flagaSQL == true && flagaErr == false)
			{
				try
				{
					jdbc.startJDBC();
					jdbc.stat.executeUpdate(sql);
					System.out.println("Trying to get answer...");
					jdbc.close();
				}
				
				catch (SQLException e2)
				{
					e2.printStackTrace();
					JOptionPane.showMessageDialog(null, "Wysy³anie zapytania do bazy nie powiod³o siê."); 
					
				}
		}
			
			break;
		}
		default:
			break;
	}
}

		

	
	 @Override
	    public void actionPerformed(ActionEvent event) {
	       
	 String komenda = event.getActionCommand();
     switch(komenda)
     {
     	case "Cofnij":
     	{
     		setVisible(false);
     		AdminFrame adminFrame = new AdminFrame();	
     		break;
     	}
     	
     	case "Zatwierdz":
     	{
     		setVisible(false);
     		getNew();
     	
 			if (flagaSQL == false & flagaErr == false)
 			{
 				JOptionPane.showMessageDialog(null, "Nie wprowadzono ¿adnych zmian"); 
 			}
 			else if (flagaErr == true && bledneID == false)  
 			{
 				JOptionPane.showMessageDialog(null, "Z³y format wprowadzonych danych"); 
 			
     		}
 			else if (flagaSQL == true && bledneID == false)
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
			childPanelB = new JPanel();
			childPanelB.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 100));
			
			inputPanel = new JPanel();
			inputPanel.setLayout(new GridLayout(15, 2));
			inputPanel.add( new JLabel(" ") );
			inputPanel.add( new JLabel("Uzupe³nij pola, których wartoœci chcesz zmieniæ:") );
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
				
			childPanelB.add(inputPanel);       
			mAdminPanel.add(childPanelB, BorderLayout.CENTER);
			
		}
		public void createPanelBiura()
		{
			childPanelB = new JPanel();
			childPanelB.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 100));
			
			inputPanel = new JPanel();
			inputPanel.setLayout(new GridLayout(8, 2));
			inputPanel.add( new JLabel(" ") );
			inputPanel.add( new JLabel("Uzupe³nij pola, których wartoœci chcesz zmieniæ:") );
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
			
			childPanelB.add(inputPanel);       
			mAdminPanel.add(childPanelB, BorderLayout.CENTER);
			
		}
		
		public void createPanelOperatorzy()
		{
			childPanelB = new JPanel();
			childPanelB.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 100));
			
			inputPanel = new JPanel();
			inputPanel.setLayout(new GridLayout(5, 2));
			inputPanel.add( new JLabel(" ") );
			inputPanel.add( new JLabel("Uzupe³nij pola, których wartoœci chcesz zmieniæ:") );
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
			
			childPanelB.add(inputPanel);       
			mAdminPanel.add(childPanelB, BorderLayout.CENTER);
		}
}