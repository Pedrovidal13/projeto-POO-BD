package dao;

import utils.ConexaoMySQL;
import model.Vendedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendedorDAO {

    public void salvar(Vendedor vendedor) {
        String sql = "INSERT INTO vendedor (nome_vendedor) VALUES (?)";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, vendedor.getNomeVendedor());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) vendedor.setIdVendedor(rs.getInt(1));
            System.out.println("Vendedor salvo com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao salvar vendedor: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
    }

    public List<Vendedor> listarTodos() {
        List<Vendedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM vendedor";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(montar(rs));
        } catch (SQLException e) {
            System.out.println("Erro ao listar vendedores: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
        return lista;
    }

    public Vendedor buscarPorId(int id) {
        String sql = "SELECT * FROM vendedor WHERE idvendedor = ?";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return montar(rs);
        } catch (SQLException e) {
            System.out.println("Erro ao buscar vendedor: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
        return null;
    }

    public void atualizar(Vendedor vendedor) {
        String sql = "UPDATE vendedor SET nome_vendedor=? WHERE idvendedor=?";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, vendedor.getNomeVendedor());
            stmt.setInt(2, vendedor.getIdVendedor());
            stmt.executeUpdate();
            System.out.println("Vendedor atualizado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar vendedor: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM vendedor WHERE idvendedor = ?";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Vendedor excluído com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao excluir vendedor: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
    }

    private Vendedor montar(ResultSet rs) throws SQLException {
        return new Vendedor(
                rs.getInt("idvendedor"),
                rs.getString("nome_vendedor")
        );
    }
}