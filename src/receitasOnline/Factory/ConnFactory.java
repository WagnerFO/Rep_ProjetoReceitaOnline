package receitasOnline.Factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnFactory{
	@SuppressWarnings("finally")
    public static Connection createConnection(){
		Connection conexao = null;
		try{

			String url = "jdbc:mysql://127.0.0.1:3306/receitaonline?useTimezone=true&serverTimezone=UTC";
			String user = "root";
			String password = "4019";

			conexao = DriverManager.getConnection(url, user, password);
			
		}catch(SQLException e){
			System.out.printf("Erro ao conectar ao banco de dados %s", e.getMessage());
		}
		finally{
			return conexao;
		}
	}
}