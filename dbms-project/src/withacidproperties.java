import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class withacidproperties {
	private final String url="jdbc:postgresql://localhost/postgres";
    private final String user="postgres";
	private final String password="root1245";
	
	private void connect() {
		try (Connection connection=DriverManager.getConnection(url,user,password);){
			if(connection != null) {
				//for atomictity
				connection.setAutoCommit(false);
				// For isolation
				connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				Statement stmt1 = null;
				try {
				    stmt1=connection.createStatement();
					stmt1.executeUpdate("delete from product where prod='P1'");
					System.out.println("deleted sucessfully");
					}
				catch (SQLException e) {
					System.out.println("An exception was thrown"); 
					e.printStackTrace();
					// For atomicity 
					connection.rollback();
					stmt1.close();
					connection. close();
					return;
					}
				connection.commit();
				stmt1.close();
				connection.close();
				//System.out.println("connected to database");
		}
			else
				System.out.println("Not connected to database");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		withacidproperties sqlconnect= new withacidproperties();
		sqlconnect.connect();
		
	}
			

}
