package dao;

import utils.ConexaoMySQL;
import model.Operadora;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OperadoraDAO {

    public void salvar(Operadora operadora) {
        String sql = "INSERT INTO operadora (nome_operadora, cnpj, registro_ans, telefone, email) VALUES (?, ?, ?, ?, ?)";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, operadora.getNomeOperadora());
            stmt.setString(2, operadora.getCnpj());
            stmt.setInt(3, operadora.getRegistroAns());
            stmt.setString(4, operadora.getTelefone());
            stmt.setString(5, operadora.getEmail());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) operadora.setIdOperadora(rs.getInt(1));
            System.out.println("Operadora salva com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao salvar operadora: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
    }

    public List<Operadora> listarTodos() {
        List<Operadora> lista = new ArrayList<>();
        String sql = "SELECT * FROM operadora";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(montar(rs));
        } catch (SQLException e) {
            System.out.println("Erro ao listar operadoras: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
        return lista;
    }

    public Operadora buscarPorId(int id) {
        String sql = "SELECT * FROM operadora WHERE id_operadora = ?";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return montar(rs);
        } catch (SQLException e) {
            System.out.println("Erro ao buscar operadora: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
        return null;
    }

    public Operadora buscarPorCnpj(String cnpj) {
        String sql = "SELECT * FROM operadora WHERE cnpj = ?";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cnpj);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return montar(rs);
        } catch (SQLException e) {
            System.out.println("Erro ao buscar operadora por CNPJ: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
        return null;
    }

    public void atualizar(Operadora operadora) {
        String sql = "UPDATE operadora SET nome_operadora=?, cnpj=?, registro_ans=?, telefone=?, email=? WHERE id_operadora=?";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, operadora.getNomeOperadora());
            stmt.setString(2, operadora.getCnpj());
            stmt.setInt(3, operadora.getRegistroAns());
            stmt.setString(4, operadora.getTelefone());
            stmt.setString(5, operadora.getEmail());
            stmt.setInt(6, operadora.getIdOperadora());
            stmt.executeUpdate();
            System.out.println("Operadora atualizada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar operadora: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM operadora WHERE id_operadora = ?";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Operadora excluída com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao excluir operadora: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
    }

    // Popula todos os campos, incluindo idOperadora — corrigido
    private Operadora montar(ResultSet rs) throws SQLException {
        return new Operadora(
                rs.getInt("id_operadora"),
                rs.getString("nome_operadora"),
                rs.getString("cnpj"),
                rs.getInt("registro_ans"),
                rs.getString("telefone"),
                rs.getString("email")
        );
    }
}