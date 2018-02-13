import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class DeleteAdminFrame extends JFrame implements ActionListener{

	protected JPanel dAdminPanel;
	protected JPanel childPanel;
	protected JPanel childPanel2;
	protected JPanel childPanel3;
	
	protected JComboBox<String> listTab; 
	protected static String chosenTab;
	private JTextField IDField;
	
	protected DefaultTableModel model; //= new DefaultTableModel(); 
	protected JTable table; //= new JTable(model); 
	protected String[] columns;
	protected String query;
	
	private JDBC jdbc;
	
	private boolean flagaErr;
	private boolean bledneID;
	
	public DeleteAdminFrame()
	{
		super("Usun");
		//Dimension dim = new Dimension(800, 600);
		Dimension dim = new Dimension(1380, 760);
		setPreferredSize(dim);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        createPanel();
        
        add(dAdminPanel);
        
        setVisible(true);
	}
	
	public void createPanel()
	{
		dAdminPanel = new JPanel();		
		childPanel = new JPanel();
		dAdminPanel.setLayout(new BorderLayout());
		childPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 50));
		
		JLabel tab = new JLabel("Wybierz tabelê: ");
		
		JLabel col = new JLabel("Wpisz ID wiersza, który chcesz usun¹æ: ");
		
		String[] tables = new String[] {"Operatorzy", "Biura", "Pracownicy"};
		listTab = new JComboBox<String>(tables);
		//listTab.setEditable(true); <- do edytowalnej listy
		chosenTab = (String) listTab.getSelectedItem();
		
		//listCol.setEditable(true); <- do edytowalnej listy
		IDField = new JTextField();		
		Dimension d = new Dimension(50,25);
		IDField.setPreferredSize(d);
		childPanel.add(IDField, SwingConstants.CENTER); 
		childPanel.add(col, SwingConstants.CENTER); 
		childPanel.add(listTab, SwingConstants.CENTER); 
		childPanel.add(tab, SwingConstants.CENTER); 
		dAdminPanel.add(childPanel, BorderLayout.PAGE_START);
		
		childPanel2 = new JPanel();
		childPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 50));

		JButton send = new JButton("Usuñ");
		send.setActionCommand("Usuñ");
		send.addActionListener(this);
		
		JButton back = new JButton("Cofnij");
		back.setActionCommand("Cofnij");
		back.addActionListener(this);
		
		childPanel2.add(back, SwingConstants.CENTER);
		childPanel2.add(send, SwingConstants.CENTER);
		
		childPanel3 = new JPanel();
		childPanel3.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 50));

		dAdminPanel.add(childPanel2, BorderLayout.PAGE_END);
		listTab.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				    listTab = (JComboBox<String>) e.getSource();

			       chosenTab = (String) listTab.getSelectedItem();
			       
			       if (model != null)
				        childPanel3.removeAll();
			       
			       switch(chosenTab)
			       
			       {
			       		case "Biura":
			       		{
			       			
			       		// if (model != null)
						     //   childPanel3.remove(table);
						 		    
				        	query = "SELECT * FROM Biura";
				            model = new DefaultTableModel(); 
				    		table = new JTable(model); 			
				           
				    		//childPanel3 = new JPanel();
				    		
				           try
				           {
				        	   jdbc = new JDBC();
				        	   jdbc.startJDBC();
				        	   jdbc.rset = jdbc.stat.executeQuery(query);
							
				        	   for (int i = 1; i<10; i++ )
				        	   		{model.addColumn("Col" + i); }
				        	   columns = new String[9];
				        	   for (int i = 1; i<10; i++ )
				    		{
				    			columns[(i-1)] = jdbc.rset.getMetaData().getColumnLabel(i);
				    		}
				        	
				    		model.addRow(new Object[]{columns[0], columns[1], columns[2], columns[3],columns[4], columns[5],columns[6], columns[7], columns[8]});
				            while(jdbc.rset.next())
								{
				            		
				            		jdbc.rset.getMetaData().getColumnLabel(1);
				            		String row[] = new String[9];
				            		row[0] = (Integer.toString(jdbc.rset.getInt(1)));
				            		row[1] = jdbc.rset.getString("Nazwa_Biura");
				            		row[2] = jdbc.rset.getString("Ulica");
				            		row[3] = jdbc.rset.getString("Nr_Budynku");
				            		row[4] = jdbc.rset.getString("Nr_Lokalu");
				            		row[5] = jdbc.rset.getString("Nr_Telefonu");
				            		row[6] = jdbc.rset.getString("Email");
				            		row[7] = Integer.toString(jdbc.rset.getInt(8));
				            		row[8] = Integer.toString(jdbc.rset.getInt(9));
				            		model.addRow(new Object[]{row[0], row[1], row[2], row[3],row[4], row[5],row[6], row[7], row[8]});
								}
				            table.getColumnModel().getColumn(0).setPreferredWidth(70);	///dopisz szerokoœci kolumn
				            table.getColumnModel().getColumn(1).setPreferredWidth(120);
				            table.getColumnModel().getColumn(2).setPreferredWidth(100);
				            table.getColumnModel().getColumn(3).setPreferredWidth(90);
				            table.getColumnModel().getColumn(4).setPreferredWidth(85);
				            table.getColumnModel().getColumn(5).setPreferredWidth(95);
				            table.getColumnModel().getColumn(6).setPreferredWidth(150);
				            table.getColumnModel().getColumn(7).setPreferredWidth(70);
				            table.getColumnModel().getColumn(8).setPreferredWidth(100);
				     	
				    		childPanel3.add(table,SwingConstants.CENTER);
				    		
				   
				    		dAdminPanel.add(childPanel3, BorderLayout.CENTER);
				    		//jdbc.close();
				           }
				    		 
				    		catch(SQLException e1)
				    		{
				    			JOptionPane.showMessageDialog(null, "Wysy³anie zapytania do bazy nie powiod³o siê.");
				    			e1.printStackTrace();
				    		}
				    		
				    		break;
				    		
				           }
				           
			       		case "Operatorzy":
				           {
				        	   		        		
					      // 	 if (model != null)
							     //   childPanel3.remove(table);
				        	   
				        	   query = "SELECT * FROM Operatorzy";
				        	   model = new DefaultTableModel(); 
				       		   table = new JTable(model); 
				       		   
				       		//childPanel3 = new JPanel();
				       		   
				       		 try
				             {
				          	   jdbc = new JDBC();
				          	   jdbc.startJDBC();
				          	   jdbc.rset = jdbc.stat.executeQuery(query);
				  			
				          	   for (int i = 1; i<5; i++ )
				          	   		{model.addColumn("Col" + i); }
				          	   columns = new String[9];
				          	   for (int i = 1; i<5; i++ )
				      		{
				      			columns[(i-1)] = jdbc.rset.getMetaData().getColumnLabel(i);
				      		}
				      		model.addRow(new Object[]{columns[0], columns[1], columns[2], columns[3]});
				              while(jdbc.rset.next())
				  				{		            		
				              		jdbc.rset.getMetaData().getColumnLabel(1);
				              		String row[] = new String[4];
				              		row[0] = (Integer.toString(jdbc.rset.getInt(1)));
				              		row[1] = jdbc.rset.getString("Nazwa_Operatora");
				              		row[2] = jdbc.rset.getString("Logo");
				              		row[3] = jdbc.rset.getString("Data_Zalozenia");
				              
				              		model.addRow(new Object[]{row[0], row[1], row[2], row[3]});
				  				}
				              
				              table.getColumnModel().getColumn(0).setPreferredWidth(100);
				              table.getColumnModel().getColumn(1).setPreferredWidth(130);
				              table.getColumnModel().getColumn(2).setPreferredWidth(120);
				              table.getColumnModel().getColumn(3).setPreferredWidth(140);
				      		childPanel3.add(table,SwingConstants.CENTER);
				      		dAdminPanel.add(childPanel3, BorderLayout.CENTER);
				      		//jdbc.close();	  		

				          } 			     

				  		catch(SQLException e1)
				  		{
				  			JOptionPane.showMessageDialog(null, "Wysy³anie zapytania do bazy nie powiod³o siê.");
				  			e1.printStackTrace();
				  		}
				  	
				        	   
				        	   
				        	   break;
				        	   
				           }
				           
			       		case "Pracownicy":
			       		{
			       						       			
			       			//if (model != null)
			       			//	childPanel3.remove(table);
			       			
			       			query = "SELECT * FROM Pracownicy";
			       			model = new DefaultTableModel(); 
			       			table = new JTable(model);
			       			
			       			
			       		 try
				           {
				        	 jdbc = new JDBC();  
			       			 jdbc.startJDBC();
				        	   jdbc.rset = jdbc.stat.executeQuery(query);
							
				        	   //childPanel3 = new JPanel();
				        	   
				        	   for (int i = 1; i<14; i++ )
				        	   		{model.addColumn("Col" + i); }
				        	  columns = new String[13];
				        	   for (int i = 1; i<14; i++ )
			        		{
			        			columns[(i-1)] = jdbc.rset.getMetaData().getColumnLabel(i);
			        		}
			        		model.addRow(new Object[]{columns[0], columns[1], columns[2], columns[3],columns[4], columns[5],columns[6], columns[7], columns[8], columns[9], columns[10], columns[11], columns[12]});
				            while(jdbc.rset.next())
								{
				            		
				            		jdbc.rset.getMetaData().getColumnLabel(1);
				            		String row[] = new String[13];
				            		row[0] = (Integer.toString(jdbc.rset.getInt(1)));
				            		row[1] = jdbc.rset.getString("Imie");
				            		row[2] = jdbc.rset.getString("Nazwisko");
				            		row[3] = jdbc.rset.getString("Nr_Telefonu");
				            		row[4] = jdbc.rset.getString("Email");
				            		row[5] = jdbc.rset.getString("Ulica");
				            		row[6] = jdbc.rset.getString("Nr_Budynku");
				            		row[7] = jdbc.rset.getString("Nr_Lokalu");
				            		row[8] = jdbc.rset.getString("PESEL");
				            		row[9] = Integer.toString(jdbc.rset.getInt(10));
				            		row[10] = Integer.toString(jdbc.rset.getInt(11));
				            		row[11] = Integer.toString(jdbc.rset.getInt(12));
				            		row[12] = Integer.toString(jdbc.rset.getInt(13));
				            		model.addRow(new Object[]{row[0], row[1], row[2], row[3],row[4], row[5],row[6], row[7], row[8], row[9], row[10], row[11], row[12]});
								}
				            
				            table.getColumnModel().getColumn(0).setPreferredWidth(110);	///dopisz szerokoœci kolumn
				            table.getColumnModel().getColumn(1).setPreferredWidth(75);
				            table.getColumnModel().getColumn(2).setPreferredWidth(80);
				            table.getColumnModel().getColumn(3).setPreferredWidth(100);
				            table.getColumnModel().getColumn(4).setPreferredWidth(130);
				            table.getColumnModel().getColumn(5).setPreferredWidth(80);
				            table.getColumnModel().getColumn(6).setPreferredWidth(95);
				            table.getColumnModel().getColumn(7).setPreferredWidth(80);
				            table.getColumnModel().getColumn(8).setPreferredWidth(90);
				            table.getColumnModel().getColumn(9).setPreferredWidth(105);
				            table.getColumnModel().getColumn(10).setPreferredWidth(70);
				            table.getColumnModel().getColumn(11).setPreferredWidth(80);
				            table.getColumnModel().getColumn(12).setPreferredWidth(100);
				            
				    		childPanel3.add(table,SwingConstants.CENTER);
				    		dAdminPanel.add(childPanel3, BorderLayout.CENTER);
				    	
				    		dAdminPanel.setVisible(true);
				    		//jdbc.close();
				     
				        } 			     
				
						catch(SQLException e1)
						{
							JOptionPane.showMessageDialog(null, "Wysy³anie zapytania do bazy nie powiod³o siê.");
							e1.printStackTrace();
						}
			       		 
			       		break;
			       		
			       		}			       			       						       			
			       		
			       		
			       		
			       		default:
			       			break;
			       			
			       }
			       		
			dAdminPanel.revalidate();
	    	dAdminPanel.repaint();        
				           
			       											       			       		   		
		
			}
			});
		
		 	 
		
	}

	protected void getDelete()
	{
		int ID = 0, amountID=0;
		flagaErr = false;
		bledneID = false;
		jdbc = new JDBC();
		
		try
		{
			ID = Integer.parseInt(IDField.getText());
		}

		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "B³êdnie podane ID wiersza");
			flagaErr = true;
			bledneID = true;
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
				JOptionPane.showMessageDialog(null, "Wysy³anie zapytania do bazy nie powiod³o siê.");
				flagaErr = true;
				e.printStackTrace();
			}
			jdbc.close();
		
			if ((ID<1 || ID > amountID) && !bledneID)
			{
				JOptionPane.showMessageDialog(null, "Wiersz o podanym ID nie istnieje");
				flagaErr = true;
				bledneID = true;
			}
			
			String query = "DELETE FROM " + chosenTab + " WHERE ID_BIURA = "+ ID;
			System.out.println("usuwam z BIUR");
			
			
			
			if (flagaErr == false && bledneID == false)
				{
				
				// z showAdminPanel
				
				
		           
		    		
		    
		    		// dot¹d z showAdminPanel
				
				try
					{
						jdbc.startJDBC();
						jdbc.stat.executeUpdate(query);
						System.out.println("Trying to delete...");
						jdbc.close();
			
					}
		
					catch (SQLException e2)
					{
						JOptionPane.showMessageDialog(null, "Wysy³anie zapytania do bazy nie powiod³o siê.");
						e2.printStackTrace();
						flagaErr = true;
					}
				}
			
			break;
		}
		
		case "Operatorzy":
		{
			
			try {
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
				flagaErr = true;
			}
			jdbc.close();
		
			if ((ID<1 || ID > amountID) && !bledneID)
			{
				JOptionPane.showMessageDialog(null, "Wiersz o podanym ID nie istnieje");
				flagaErr = true;
				bledneID = true;
			}
			
			String query = "DELETE FROM " + chosenTab + " WHERE ID_Operatora = "+ ID;
			System.out.println("usuwam z Operatorow");
			
			if (flagaErr == false && !bledneID)
			{
				try
				{
					jdbc.startJDBC();
					jdbc.stat.executeUpdate(query);
					System.out.println("Trying to delete...");
					jdbc.close();
			
				}
		
				catch (SQLException e2)
				{
					JOptionPane.showMessageDialog(null, "Wysy³anie zapytania do bazy nie powiod³o siê.");
					e2.printStackTrace();
					flagaErr = true;
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
				JOptionPane.showMessageDialog(null, "Wysy³anie zapytania do bazy nie powiod³o siê.");
				e.printStackTrace();
				flagaErr = true;
			}
			
			jdbc.close();
		
			if ((ID<1 || ID > amountID) && !bledneID)
			{
				JOptionPane.showMessageDialog(null, "Wiersz o podanym ID nie istnieje");
				flagaErr = true;
				bledneID = true;
			}
			
			String query = "DELETE FROM " + chosenTab + " WHERE ID_Pracownika = "+ ID;
			System.out.println("usuwam z Pracownikow");
			
			if (flagaErr == false && !bledneID)
			{
				try
				{
					jdbc.startJDBC();
					jdbc.stat.executeUpdate(query);
					System.out.println("Trying to delete...");
					jdbc.close();
			
				}
				
				catch (SQLException e2)
				{
					JOptionPane.showMessageDialog(null, "Wysy³anie zapytania do bazy nie powiod³o siê.");
					e2.printStackTrace();
					flagaErr = true;
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
 	
 	case "Usuñ":
 	{
 		if (chosenTab != null)
 		//jdbc.startJDBC();
 		{
 			setVisible(false);
 			int dialogButton = JOptionPane.YES_NO_OPTION;
 			int dialogResult = JOptionPane.showConfirmDialog (null, "Czy na pewno chcesz usun¹æ wybrany wiersz?","Warning",dialogButton);
 			if(dialogResult == JOptionPane.YES_OPTION)
 			{
 				System.out.println("yes");
 				getDelete(); 
 				if(!flagaErr && !bledneID)
 					JOptionPane.showMessageDialog(null, "Rekord zosta³ usuniêty");
 			}	
 		}
 		else if (chosenTab == null)
 			JOptionPane.showMessageDialog(null, "Wybierz tabelê");
			
 		setVisible(true);

 		break;
 	}
 	
 	default:
 		break;
 }
 
}
}
