import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ShowAdminFrame extends JFrame implements ActionListener{
	
	protected JPanel sAdminPanel;
	protected JPanel childPanel;
	protected JPanel childPanel2;
	protected JPanel childPanel3;
	protected JPanel child;
	protected JPanel child1;

	protected JComboBox<String> listTab; 
	protected JComboBox<String> listCol;
	protected JComboBox<String> listCol1;
	
	protected DefaultTableModel model; //= new DefaultTableModel(); 
	protected JTable table; //= new JTable(model); 
	protected String[] columns;
	protected String query;
	
	protected JDBC jdbc;
	
	protected static String chosenCol;
	protected static String chosenTab;
	
	protected static int p = 0;
	protected static boolean sortA;
	protected static boolean sortD;
	protected static boolean iflistcol;
	
	protected boolean inBi;
	protected boolean inOp;
	protected boolean inPr;
	
	
	public ShowAdminFrame()
	{
		super("Show");
		Dimension dim = new Dimension(1380, 760);
		setPreferredSize(dim);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        createPanel();
        
        add(sAdminPanel);
        
        setVisible(true);
		
	}

	public void createPanel()
	{
		sAdminPanel = new JPanel();
		
		childPanel = new JPanel();
		sAdminPanel.setLayout(new BorderLayout());
		childPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));
		sortA = false;
		sortD = false;
		iflistcol = false;
		inBi = false;
		inOp = false;
		inPr = false;
		JLabel tab = new JLabel("Wybierz tabelê: ");
		
		
		String[] tables = new String[] {"Operatorzy", "Biura", "Pracownicy"};
		listTab = new JComboBox<String>(tables);
		//listTab.setEditable(true); <- do edytowalnej listy
		chosenTab = (String) listTab.getSelectedItem();
		childPanel.add( new JLabel("    Wybierz pole do sortowania:") );
		childPanel.add(listTab, SwingConstants.CENTER);
		childPanel.add(tab, SwingConstants.CENTER);
	
		sAdminPanel.add(childPanel, BorderLayout.PAGE_START);
		
		childPanel2 = new JPanel();
		childPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 50));
		
		JLabel sort = new JLabel("Sortuj: ");
		
		JButton sortDesc = new JButton("Malejąco");
		sortDesc.setActionCommand("SortujM");
		sortDesc.addActionListener(this);
		
		JButton sortAsc = new JButton("Rosnąco");
		sortAsc.setActionCommand("SortujR");
		sortAsc.addActionListener(this);
		
		JButton back = new JButton("Cofnij");
		back.setActionCommand("Cofnij");
		back.addActionListener(this);
		
		childPanel2.add(back, SwingConstants.CENTER);
		childPanel2.add(sortDesc, SwingConstants.CENTER);
		childPanel2.add(sortAsc, SwingConstants.CENTER);
		childPanel2.add(sort, SwingConstants.CENTER);
		
		sAdminPanel.add(childPanel2, BorderLayout.PAGE_END);
	
		childPanel3 = new JPanel();
		childPanel3.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 50));
		
		listTab.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{				
				chosenTab = (String) listTab.getSelectedItem();

		        jdbc = new JDBC();
		        jdbc.startJDBC();

		        
				createTable(); 
					
			}
			
		});
			
		
	}
	
	protected void createTable()
	{
		
		 if (model != null)
		        childPanel3.removeAll();
		 
		if (chosenTab == "Biura")
        {			            
            if(inOp || inPr)
            {
            	chosenCol = "ID_Biura";
            	inOp = false;
            	inPr = false;
            }
            inBi = true;
            
        	if ((sortA == false && sortD == false)||chosenCol == null)
        		query = "SELECT * FROM Biura";
        	else if (sortA == true && chosenCol!=null)
        		query = "SELECT * FROM Biura ORDER BY "+chosenCol;
        	else if (sortD == true && chosenCol!=null)
        		query = "SELECT * FROM Biura ORDER BY "+chosenCol+ " DESC";
            model = new DefaultTableModel(); 
    		table = new JTable(model); 			
           
           try
           {
        	  // jdbc.startJDBC();
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
            table.getColumnModel().getColumn(0).setPreferredWidth(70);	
            table.getColumnModel().getColumn(1).setPreferredWidth(120);
            table.getColumnModel().getColumn(2).setPreferredWidth(100);
            table.getColumnModel().getColumn(3).setPreferredWidth(90);
            table.getColumnModel().getColumn(4).setPreferredWidth(85);
            table.getColumnModel().getColumn(5).setPreferredWidth(95);
            table.getColumnModel().getColumn(6).setPreferredWidth(150);
            table.getColumnModel().getColumn(7).setPreferredWidth(70);
            table.getColumnModel().getColumn(8).setPreferredWidth(100);
     	
    		childPanel3.add(table,SwingConstants.CENTER);
    		
   
    		sAdminPanel.add(childPanel3, BorderLayout.CENTER);
    		
    		if (iflistcol)
    			{
    				childPanel.remove(listCol);
    				iflistcol = false;
    			}
    		
    		if (iflistcol == false)
    	
    		{
    		
    			child = new JPanel(); 
    			child.setLayout(new GridLayout(10,2));   
    			listCol = new JComboBox<String>(columns);
    			iflistcol = true;
    			childPanel.add(listCol);
    			childPanel.revalidate();
    			childPanel.repaint();  
    		
    		}
    		
    			listCol.addActionListener(new ActionListener()
    			{
    				public void actionPerformed(ActionEvent ev) 
    				{
    					chosenCol = (String) listCol.getSelectedItem();
    				}
    			
    		});
    				      	
        
           } 			     

		catch(SQLException e1)
		{
			JOptionPane.showMessageDialog(null, "Wysyłanie zapytania do bazy nie powiodło się.");
			e1.printStackTrace();
		}
				          			           
        }
        
        else if (chosenTab == "Operatorzy") 
        {
            if(inBi || inPr)
            {
            	chosenCol = "ID_Operatora";
            	inBi = false;
            	inPr = false;
            }
            inOp = true;
        	if ((sortA == false && sortD == false)||chosenCol == null)
        		query = "SELECT * FROM Operatorzy";
        	else if (sortA == true && chosenCol!=null)
        		query = "SELECT * FROM Operatorzy ORDER BY "+chosenCol;
        	else if (sortD == true && chosenCol!=null)
        		query = "SELECT * FROM Operatorzy ORDER BY "+chosenCol+ " DESC";
            model = new DefaultTableModel(); 
    		table = new JTable(model); 		
      
           try
           {
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
    		sAdminPanel.add(childPanel3, BorderLayout.CENTER);
   
    		if (iflistcol)
			{
    			childPanel.remove(listCol);
				iflistcol = false;
			}
		
		if (iflistcol == false)
	
		{

			child = new JPanel(); 
			child.setLayout(new GridLayout(10,2));   
		
			listCol = new JComboBox<String>(columns);
			iflistcol = true;
		
			childPanel.add(listCol);
			childPanel.revalidate();
			childPanel.repaint();  
		
		}
		
			listCol.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ev) 
				{
					chosenCol = (String) listCol.getSelectedItem();
				}
			
		});

        } 			     

		catch(SQLException e1)
		{
			JOptionPane.showMessageDialog(null, "Wysyłanie zapytania do bazy nie powiodło się.");
			e1.printStackTrace();
		}
	
            
        }
        else if (chosenTab == "Pracownicy") 
        {
            if(inBi || inOp)
            {
            	chosenCol = "ID_Pracownika";
            	inBi = false;
            	inOp = false;
            }
            inPr = true;
        	
        	if ((sortA == false && sortD == false)||chosenCol == null)
        		query = "SELECT * FROM Pracownicy";
        	else if (sortA == true && chosenCol!=null)
        		query = "SELECT * FROM Pracownicy ORDER BY "+chosenCol;
        	else if (sortD == true && chosenCol!=null)
        		query = "SELECT * FROM Pracownicy ORDER BY "+chosenCol+ " DESC";
            model = new DefaultTableModel(); 
    		table = new JTable(model); 		 			
	           
	           try
	           {
	        	  // jdbc.startJDBC();
	        	   jdbc.rset = jdbc.stat.executeQuery(query);
				
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
	            
	            table.getColumnModel().getColumn(0).setPreferredWidth(110);
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
	    		sAdminPanel.add(childPanel3, BorderLayout.CENTER);
	    		if (iflistcol)
    			{
	    			childPanel.remove(listCol);
    				iflistcol = false;
    			}
    		
    		if (iflistcol == false)
    	
    		{
 
    			child = new JPanel(); 
    			child.setLayout(new GridLayout(10,2));   

    			listCol = new JComboBox<String>(columns);
    			iflistcol = true;

    			childPanel.add(listCol);
    			childPanel.revalidate();
    			childPanel.repaint();  

    		}
    		
    			listCol.addActionListener(new ActionListener()
    			{
    				public void actionPerformed(ActionEvent ev) 
    				{
    					chosenCol = (String) listCol.getSelectedItem();
    				}
    			
    		});
	    		sAdminPanel.setVisible(true);
	     
	        } 			     
	
			catch(SQLException e1)
			{
				JOptionPane.showMessageDialog(null, "Wysyłanie zapytania do bazy nie powiodło się.");
				e1.printStackTrace();
			}
        }

        sAdminPanel.revalidate();
    	sAdminPanel.repaint();    
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
     	
     	case "SortujM":
     	{
     		
     		sortD = true;	
     		sortA = false;
     		createTable();
     		break;
     	}
     	
     	case "SortujR":
     	{
  
     		sortA = true;
     		sortD = false;
     		createTable();
     		break;
     	}
     	
     	default:
     		break;
     }
	 
}
}
