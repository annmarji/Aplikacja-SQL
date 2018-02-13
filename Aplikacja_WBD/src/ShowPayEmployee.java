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
import java.sql.ResultSetMetaData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ShowPayEmployee extends JFrame implements ActionListener{
	
	protected JPanel sEmployeePanel;
	protected JPanel childPanel;
	protected JPanel childPanelC;
	
	public ShowPayEmployee()
	{
		super("Poka¿ historiê wynagrodzenia");
		Dimension dim = new Dimension(800, 600);
		setPreferredSize(dim);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        dbconnect();
        
        add(sEmployeePanel);
        
        setVisible(true);	
	}

	public void createPanel(String[] columnNames, Object[][] data)
	{	
		sEmployeePanel = new JPanel();
		childPanel = new JPanel();
		sEmployeePanel.setLayout(new BorderLayout());
		childPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 100));
		
		JTable table = new JTable(data, columnNames);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(120);
		table.getColumnModel().getColumn(2).setPreferredWidth(120);
		table.getColumnModel().getColumn(3).setMinWidth(170);

		childPanel.add(new JScrollPane(table));    
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
			String sql = "SELECT Kwota_Podstawowa, Kwota_Dodatkowa, Data_Wynagrodzenia FROM Wynagrodzenia WHERE ID_Pracownika = (SELECT ID_Pracownika FROM Pracownicy WHERE Imie='Janina')";
			ResultSet rs = stmt.executeQuery(sql);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();
			//System.out.println(numberOfColumns);
			
			int i=0;
			String[] columnNames = {"ID", "Kwota Podstawowa", "Kwota Dodatkowa", "Data Wynagrodzenia"};
			Object[][] data = new Object [10][numberOfColumns+1];
			while(rs.next()){ 
				data[i][0] =i+1;
				data[i][1] = rs.getInt("Kwota_Podstawowa");
				data[i][2]= rs.getInt("Kwota_Dodatkowa");
				data[i][3]= rs.getString("Data_Wynagrodzenia");
				i++;
			}
			//System.out.println(i);
			Object[][] data1 = new Object [i][numberOfColumns+1];
			
			for(int k=0; k<=i-1; k++)
			{
				for(int l=0; l<=numberOfColumns; l++) {
				data1[k][l] =data[k][l];}
			}
			createPanel(columnNames, data1);

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
