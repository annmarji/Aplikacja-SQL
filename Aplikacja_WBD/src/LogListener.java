import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
 
public class LogListener  {
   
    private final JFrame frame;
    private LogPanel logPanel;
    private ShowAdminPanel showAdminPanel;
    
    //do logowania
    //ograniczony dostep
  	private static final String name1 = "Janina";
    private static final String password1 = "1234";
    //admin
    private static final String name2 = "root";
    private static final String password2 = "toor";
    
    static boolean flagaPracownik;
    static boolean flagaAdministrator;
    
    protected static JDBC jdbc;
 
   /* public void setPanel(AdminPanel adminPanel) {
        this.AdminPanel = adminPanel;
    }*/
 
    public LogListener(JFrame frame) {
        this.frame = frame;
    }
   
      public static boolean authenticate(String name, String password) {
          
    	  flagaPracownik = false;
    	  flagaAdministrator = false;
    	  
    	  jdbc = new JDBC();
    	  
    	  jdbc.startJDBC();
    	  String query = "Select * from AUTHORIZED";
    	  
    	  try
          {
       	  
       	   jdbc.rset = jdbc.stat.executeQuery(query);
       	   int i = 0;
       	   boolean logOk = false;
       	   boolean passOk = false;
       	   
           while(jdbc.rset.next() && !passOk)
				{
           			i++;
           			String login;
           			String passw;
           			String perspective;
           			login = jdbc.rset.getString("Log");
           			if (name.equals(login))
           			{	           		
           				logOk = true;
           				passw = jdbc.rset.getString("Pass");
           				if (password.equals(passw))
               			{	 
           					passOk = true;
           					perspective = Integer.toString(jdbc.rset.getInt(3));
           					if (perspective.equals(Integer.toString(1)))
           					{
           						flagaAdministrator = true;
           		          		
           					}
           					else if (perspective.equals(Integer.toString(2)))
           					{
           						flagaPracownik = true;
           					 	
           					}
               			}
           			}
				}
           
         
           			//jdbc.rset.getMetaData().getColumnLabel(1);
           			/*String row[] = new String[9];
           			row[0] = (Integer.toString(jdbc.rset.getInt(1)));
           			row[1] = jdbc.rset.getString("Nazwa_Biura");
           			row[2] = jdbc.rset.getString("Ulica");
           			row[3] = jdbc.rset.getString("Nr_Budynku");
           			row[4] = jdbc.rset.getString("Nr_Lokalu");
           			row[5] = jdbc.rset.getString("Nr_Telefonu");
           			row[6] = jdbc.rset.getString("Email");
           			row[7] = Integer.toString(jdbc.rset.getInt(8));
           			row[8] = Integer.toString(jdbc.rset.getInt(9)); */           				
    	  
    	  
    	  
    	/*  if(name1.equals(name) & password1.equals(password))
          {
        	  flagaPracownik = true;
        	  return true;
          }        
          else
              {
          	if(name2.equals(name) & password2.equals(password))
          	{
          		flagaAdministrator = true;
          		return true;
          	}       		
          	else
          		return false;
              }*/
          
      }
  		catch(SQLException e1)
  		{
  			JOptionPane.showMessageDialog(null, "Wysy³anie zapytania do bazy nie powiod³o siê.");
  			e1.printStackTrace();
  		}
    	  
    	  if(flagaAdministrator || flagaPracownik)
    		  return true;
    	 
    	  else          
    		  return false;
           
    	  
    }
}
