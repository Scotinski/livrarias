
package livrarias.DAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import livrarias.bancoMysql;
import livrarias.classes.autores;
import livrarias.classes.editoras;


public class autoresDAO {
    
    public void cadastrar (autores a) {
        
        Connection conn = bancoMysql.conectaBanco();
        
        PreparedStatement smtm = null;
        ResultSet rs = null;
        
        try{
            smtm = conn.prepareStatement("INSERT INTO autores (nome,endereco,numero,bairro,cidade,cpf) VALUES (?,?,?,?,?,?)");
            smtm.setString(1, a.getNome());
            smtm.setString(2, a.getEndereco());
            smtm.setInt(3, a.getNumero());
            smtm.setString(4, a.getBairro());
            smtm.setString(5, a.getCidade());
            smtm.setBigDecimal(6, new BigDecimal(a.getCpf()));
            
            smtm.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Autor cadastrado com sucesso!");
            
        } catch(SQLException ex) { 
        }
    }
    public List<autores> listar() {

        Connection conn = bancoMysql.conectaBanco();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<autores> autores = new ArrayList<>();
        try {
            stmt = conn.prepareStatement("SELECT * FROM autores");
            rs = stmt.executeQuery();
            while (rs.next()) {
                autores e = new autores();
                e.setId(rs.getInt("id_autor"));
                e.setNome(rs.getString("nome"));
                autores.add(e);
            }
        } catch (SQLException ex) {
            Logger.getLogger(editorasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return autores;
    }
}