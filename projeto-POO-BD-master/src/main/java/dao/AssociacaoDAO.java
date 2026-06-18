package dao;

import utils.ConexaoMySQL;
import model.Associacao;
import model.Regiao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssociacaoDAO {

    public void salvar(Associacao associacao) {
        String sql = "INSERT INTO associacao (nome_associacao, cnpj, telefone, email, regiao_id_regiao) VALUES (?, ?, ?, ?, ?)";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, associacao.getNomeAssociacao());
            stmt.setString(2, associacao.getCnpj());
            stmt.setString(3, associacao.getTelefone());
            stmt.setString(4, associacao.getEmail());
            stmt.setInt(5, associacao.getRegiao().getIdRegiao());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) associacao.setIdAssociacao(rs.getInt(1));
            System.out.println("Associação salva com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao salvar associação: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
    }

    public List<Associacao> listarTodos() {
        List<Associacao> lista = new ArrayList<>();
        String sql = """
            SELECT a.*, r.nome_regiao, r.uf, r.descricao_regiao
            FROM associacao a
            JOIN regiao r ON a.regiao_id_regiao = r.id_regiao
            """;
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(montar(rs));
        } catch (SQLException e) {
            System.out.println("Erro ao listar associações: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
        return lista;
    }

    public Associacao buscarPorId(int id) {
        String sql = """
            SELECT a.*, r.nome_regiao, r.uf, r.descricao_regiao
            FROM associacao a
            JOIN regiao r ON a.regiao_id_regiao = r.id_regiao
            WHERE a.id_associacao = ?
            """;
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return montar(rs);
        } catch (SQLException e) {
            System.out.println("Erro ao buscar associação: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
        return null;
    }

    public void atualizar(Associacao associacao) {
        String sql = "UPDATE associacao SET nome_associacao=?, cnpj=?, telefone=?, email=?, regiao_id_regiao=? WHERE id_associacao=?";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, associacao.getNomeAssociacao());
            stmt.setString(2, associacao.getCnpj());
            stmt.setString(3, associacao.getTelefone());
            stmt.setString(4, associacao.getEmail());
            stmt.setInt(5, associacao.getRegiao().getIdRegiao());
            stmt.setInt(6, associacao.getIdAssociacao());
            stmt.executeUpdate();
            System.out.println("Associação atualizada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar associação: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM associacao WHERE id_associacao = ?";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Associação excluída com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao excluir associação: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
    }

    private Associacao montar(ResultSet rs) throws SQLException {
        Regiao regiao = new Regiao(
                rs.getInt("regiao_id_regiao"),
                rs.getString("nome_regiao"),
                rs.getString("uf"),
                rs.getString("descricao_regiao")
        );
        return new Associacao(
                rs.getInt("id_associacao"),
                rs.getString("nome_associacao"),
                rs.getString("cnpj"),
                rs.getString("telefone"),
                rs.getString("email"),
                regiao
        );
    }
}