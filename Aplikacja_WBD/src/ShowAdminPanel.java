import javax.swing.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.*;


//@SuppressWarnings("serial")
public class ShowAdminPanel extends JFrame implements ActionListener{

	protected JComboBox<String> listaTablic;
	protected DefaultComboBoxModel bModel;
	//ActionListener actListener;
	
	public ShowAdminPanel() {

		 super("AdminPanel");
	     //LogListener listener = new LogListener(this);
	     //JPanel showPanel = new JPanel();
	    // add(loginPanel);
	 
	     setPreferredSize(new Dimension(800, 600));
	     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         pack();
         setVisible(true);
         
		 setLayout(new BorderLayout(3,3));
		 JPanel wybor = new JPanel();
		 wybor.setLayout(new GridLayout(1,2));
		 String[] tablice = new String[] {"Operatorzy", "Biura", "Pracownicy", "Produkty"};
		 bModel = new DefaultComboBoxModel(tablice);
		 //istaTablic.addActionListener(this);
		 //listaTablic.addActionListener(this);
		 //listaTablic.setActionCommand("Tablica");
			
		
		 JComboBox<String> listaTablic = new JComboBox<String>(bModel);
		 
		 JLabel tab = new JLabel("Wybierz tablicê któr¹ chcesz wyœweitliæ:");
		 wybor.add(tab);
		 wybor.add(listaTablic);
		 
		 add(wybor, BorderLayout.NORTH);
		 
		 JButton sql = new JButton("Zapytanie");
		 sql.addActionListener(this);
		 sql.setActionCommand("Wyslij");
		 add(sql);
		 JButton sql1 = new JButton("Zapytanie1");
		 sql1.addActionListener(this);
		 sql1.setActionCommand("Wyslij1");
		 add(sql1);
		 JButton sql2 = new JButton("Zapytanie2");
		 sql2.addActionListener(this);
		 sql2.setActionCommand("Wyslij2");
		 add(sql2);
		            
								//automatyczne dopasowanie zarówno do rozmiarów (preferred size)
										//dodanych komponentów jak i zdefiniowanego zarz¹dcy rozk³adem
		
		

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String komenda = e.getActionCommand();
		
		switch(komenda)
		{
		case "Wyslij1":
			{
				JDBC jdbc = new JDBC();
				//jdbc.startJDBC();
				break;
			}
		case "Tablica":
		{
			String tabela = (String) listaTablic.getSelectedItem();
			System.out.println(tabela);
		}	
		default:	
			break;
		
	}

	
	
	}


}
