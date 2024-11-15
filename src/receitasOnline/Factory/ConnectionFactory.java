package receitasOnline.Factory;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
	private static final String USERNAME = "root";
	private static final String PASSWORD = "1q2w3e4r5t";
	private static final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/?user=root"; 

	public static Connection createConnectionToMySql() throws Exception{
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
		return connection;
	}
	public static void main(String[] args) throws Exception {
		Connection con = createConnectionToMySql();
		if(con != null) {
			System.out.println("Conexão obtida com sucesso");
			con.close();
		}
	}
}
