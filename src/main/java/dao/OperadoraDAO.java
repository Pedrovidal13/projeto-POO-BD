package dao;

import connection.ConexaoMySQL;
import model.Operadora;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OperadoraDAO {

    public void salvar(Operadora operadora) {
        String sql = "INSERT INTO operadora (nome, cnpj) VALUES (?, ?)";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, operadora.getNome());
            stmt.setString(2, operadora.getCnpj());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                operadora.setCodigo(rs.getInt(1));
            }
            System.out.println("Operadora salva com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao salvar operadora: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
    }

    public List<Operadora> listarTodos() {
        List<Operadora> operadoras = new ArrayList<>();
        String sql = "SELECT * FROM operadora";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Operadora o = new Operadora(
                        rs.getInt("codigo"),
                        rs.getString("nome"),
                        rs.getString("cnpj")
                );
                operadoras.add(o);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar operadoras: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
        return operadoras;
    }

    public Operadora buscarPorCodigo(int codigo) {
        String sql = "SELECT * FROM operadora WHERE codigo = ?";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Operadora(
                        rs.getInt("codigo"),
                        rs.getString("nome"),
                        rs.getString("cnpj")
                );
            }
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
            if (rs.next()) {
                return new Operadora(
                        rs.getInt("codigo"),
                        rs.getString("nome"),
                        rs.getString("cnpj")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar operadora: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
        return null;
    }

    public void atualizar(Operadora operadora) {
        String sql = "UPDATE operadora SET nome=?, cnpj=? WHERE codigo=?";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, operadora.getNome());
            stmt.setString(2, operadora.getCnpj());
            stmt.setInt(3, operadora.getCodigo());
            stmt.executeUpdate();
            System.out.println("Operadora atualizada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar operadora: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
    }

    public void excluir(int codigo) {
        String sql = "DELETE FROM operadora WHERE codigo = ?";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            stmt.executeUpdate();
            System.out.println("Operadora excluída com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao excluir operadora: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
    }
}
