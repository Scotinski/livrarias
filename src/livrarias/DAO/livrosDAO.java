package livrarias.DAO;

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
import livrarias.classes.livros;

public class livrosDAO {

    public void cadastrar(livros l) {

        Connection conn = bancoMysql.conectaBanco();

        PreparedStatement stmn = null;

        ResultSet rs = null;

        try {
            stmn = conn.prepareStatement("INSERT INTO livros (id_editoras, id_autor, titulo, ano) VALUES (?, ?, ?, ?)");
            stmn.setInt(1, l.getEditora().getId());
            stmn.setInt(2, l.getAutor().getId());
            stmn.setString(3, l.getTitulo());
            stmn.setInt(4, l.getAno());

            stmn.executeUpdate();
            JOptionPane.showMessageDialog(null, "Livro cadastrado com sucesso!");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar!");
            Logger.getLogger(livrosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void alterar(livros l) {

        Connection conn = bancoMysql.conectaBanco();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement("UPDATE livros SET id_editoras = ?, id_autor = ?, titulo = ?, ano = ? WHERE id_livros = ?");
            stmt.setInt(1, l.getEditora().getId());
            stmt.setInt(2, l.getAutor().getId());
            stmt.setString(3, l.getTitulo());
            stmt.setInt(4, l.getAno());
            stmt.setInt(5, l.getId_livro());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Livro alterado com sucesso!");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao alterar!");
            Logger.getLogger(livrosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void excluir(livros l) {

        Connection conn = bancoMysql.conectaBanco();

        PreparedStatement stmn = null;

        ResultSet rs = null;

        try {
            stmn = conn.prepareStatement("DELETE FROM livros WHERE id_livros = ?");
            stmn.setInt(1, l.getId_livro());

            stmn.executeUpdate();
            JOptionPane.showMessageDialog(null, "Livro excluído com sucesso!");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir!");
            Logger.getLogger(livrosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<livros> listar() {

        Connection conn = bancoMysql.conectaBanco();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<livros> Livros = new ArrayList<>();

        try {
            stmt = conn.prepareStatement("SELECT livros.id_livros, editoras.nome as nome_editora, autores.nome as nome_autor, livros.titulo, livros.ano FROM livros \n"
                    + "INNER JOIN editoras on (editoras.id_editora = livros.id_editoras)\n"
                    + "INNER JOIN autores on (autores.id_autor = livros.id_autor);");
            rs = stmt.executeQuery();

            while (rs.next()) {
                livros l = new livros();

                l.setId_livro(rs.getInt("id_livros"));
                l.setTitulo(rs.getString("titulo"));
                l.setAno(rs.getInt("ano"));

                editoras e = new editoras();
                e.setNome(rs.getString("nome_editora"));
                l.setEditora(e);

                autores a = new autores();
                a.setNome(rs.getString("nome_autor"));
                l.setAutor(a);

                Livros.add(l);
            }
        } catch (SQLException ex) {
            Logger.getLogger(livrosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Livros;
    }

    public List<livros> pesquisar(String texto) {

        Connection conn = bancoMysql.conectaBanco();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<livros> Livros = new ArrayList<>();

        try {
            stmt = conn.prepareStatement("SELECT livros.id_livros, editoras.nome as nome_editora, autores.nome as nome_autor, livros.titulo, livros.ano FROM livros\n"
                    + "INNER JOIN editoras on (editoras.id_editora = livros.id_editora)\n"
                    + "INNER JOIN autores on (autores.id_autores = livros.id_autor) WHERE titulo like ?");
            stmt.setString(1, "%" + texto + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                livros l = new livros();
                l.setId_livro(rs.getInt("id_livros"));
                l.setTitulo(rs.getString("titulo"));
                l.setAno(rs.getInt("ano"));

                editoras e = new editoras();
                e.setNome(rs.getString("nome_editora"));
                l.setEditora(e);

                autores a = new autores();
                a.setNome(rs.getString("nome_autor"));
                l.setAutor(a);

                Livros.add(l);
            }
        } catch (SQLException ex) {
            Logger.getLogger(livrosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Livros;
    }
}
