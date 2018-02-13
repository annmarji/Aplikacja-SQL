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
        
    static boolean flagaPracownik;
    static boolean flagaAdministrator;
    
    protected static JDBC jdbc;
 
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
