package dao;

import utils.ConexaoMySQL;
import model.Regiao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegiaoDAO {

    public void salvar(Regiao regiao) {
        String sql = "INSERT INTO regiao (nome_regiao, uf, descricao_regiao) VALUES (?, ?, ?)";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, regiao.getNomeRegiao());
            stmt.setString(2, regiao.getUf());
            stmt.setString(3, regiao.getDescricaoRegiao());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) regiao.setIdRegiao(rs.getInt(1));
            System.out.println("Região salva com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao salvar região: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
    }

    public List<Regiao> listarTodos() {
        List<Regiao> lista = new ArrayList<>();
        String sql = "SELECT * FROM regiao";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(montar(rs));
        } catch (SQLException e) {
            System.out.println("Erro ao listar regiões: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
        return lista;
    }

    public Regiao buscarPorId(int id) {
        String sql = "SELECT * FROM regiao WHERE id_regiao = ?";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return montar(rs);
        } catch (SQLException e) {
            System.out.println("Erro ao buscar região: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
        return null;
    }

    public void atualizar(Regiao regiao) {
        String sql = "UPDATE regiao SET nome_regiao=?, uf=?, descricao_regiao=? WHERE id_regiao=?";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, regiao.getNomeRegiao());
            stmt.setString(2, regiao.getUf());
            stmt.setString(3, regiao.getDescricaoRegiao());
            stmt.setInt(4, regiao.getIdRegiao());
            stmt.executeUpdate();
            System.out.println("Região atualizada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar região: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
    }

    public void excluir(int id) {
        Connection conn = ConexaoMySQL.obterConexao();
        try {
            // Verifica se há pessoas ou associações vinculadas
            String checkSql = """
            SELECT
                (SELECT COUNT(*) FROM pessoa WHERE regiao_id_regiao = ?) +
                (SELECT COUNT(*) FROM associacao WHERE regiao_id_regiao = ?) AS total
            """;
            try (PreparedStatement check = conn.prepareStatement(checkSql)) {
                check.setInt(1, id);
                check.setInt(2, id);
                ResultSet rs = check.executeQuery();
                if (rs.next() && rs.getInt("total") > 0) {
                    System.out.println("Não é possível excluir: esta região possui pessoas ou associações vinculadas.");
                    return;
                }
            }

            String sql = "DELETE FROM regiao WHERE id_regiao = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
                System.out.println("Região excluída com sucesso!");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao excluir região: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
    }

    private Regiao montar(ResultSet rs) throws SQLException {
        return new Regiao(
                rs.getInt("id_regiao"),
                rs.getString("nome_regiao"),
                rs.getString("uf"),
                rs.getString("descricao_regiao")
        );
    }
}