import java.sql.*;
import java.util.*;
import java.util.Vector;

public class JDBC {

	protected String url;
	protected String username;
	protected String password;
	protected Connection con;
	protected Statement stat;
	protected ResultSet rset;
	protected Vector kolumny=new Vector();
	protected Vector dane=new Vector(7);
	
	protected JDBC()
	{		
		url = "jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf";
		username = "azadrozn";
		password = "WBD_jest!";
	}
	
	public void startJDBC()
	{
		System.out.println("Start JDBC program.."); 
		
		try {
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			Class.forName("oracle.jdbc.OracleDriver");
			System.out.println("Connecting to database.."); 
			con = DriverManager.getConnection(url, username, password);
			stat = con.createStatement();
			
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void close()
	{
		try
		{
			
			rset.close();
			stat.close();
			con.close();
			
			}		
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			
				
		}
	
}
	
