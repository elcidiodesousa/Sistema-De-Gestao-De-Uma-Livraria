package Modulos;

import java.sql.*;


public class ModuloConexao {

    //Metodo responsavel pela conexao com o banco de dados
    public static Connection conector() {
        java.sql.Connection conexao = null;
        
         String driver = "com.mysql.jdbc.Driver";
         String url = "jdbc:mysql://localhost:3306/bdlivraria";
         String user = "root";
         String password = "elcidiosousa84";
         
         try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            return null;
        }
    }

}
