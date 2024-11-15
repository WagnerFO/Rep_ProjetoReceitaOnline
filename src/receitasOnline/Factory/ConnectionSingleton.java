package receitasOnline.Factory;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public final class ConnectionSingleton {

	private static ConnectionSingleton instance;
	public Connection conexao = null;

	private ConnectionSingleton() throws SQLException, InterruptedException {

		final String url ="jdbc:mysql://127.0.0.1:3306/receitaonline?useTimezone=true&serverTimezone=UTC";
	    final String user= "root";
	    final String password = "4019";

		this.conexao = DriverManager.getConnection(url, user, password);

	}

	public static ConnectionSingleton getInstance() throws SQLException, InterruptedException {
		if (instance == null|| instance.conexao.isClosed()) {
			instance = new ConnectionSingleton();
			System.out.println("Novo Objeto");
		}
		else {
			
			System.out.println("REUSO DE CONEXAO");
		}
		return instance;
	}
}