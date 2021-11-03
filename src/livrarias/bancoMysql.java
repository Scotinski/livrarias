
package livrarias;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class bancoMysql {
  
    public static Connection conectaBanco (){
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost/db_livraria?" +
                                   "user=root&password=root");
            
        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException("Erro ao conectar ao Banco de dados!", ex);
        }
    }
}
   