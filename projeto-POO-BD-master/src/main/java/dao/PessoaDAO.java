package dao;

import utils.ConexaoMySQL;
import model.Pessoa;
import model.Regiao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAO {

    private final RegiaoDAO regiaoDAO = new RegiaoDAO();

    public void salvar(Pessoa pessoa) {
        String sql = "INSERT INTO pessoa (nome_pessoa, cpf_cnpj, idade, telefone, email, tipo_pessoa, regiao_id_regiao) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, pessoa.getNomePessoa());
            stmt.setString(2, pessoa.getCpfCnpj());
            stmt.setInt(3, pessoa.getIdade());
            stmt.setString(4, pessoa.getTelefone());
            stmt.setString(5, pessoa.getEmail());
            stmt.setString(6, pessoa.getTipoPessoa());
            stmt.setInt(7, pessoa.getRegiao().getIdRegiao());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) pessoa.setIdPessoa(rs.getInt(1));
            System.out.println("Pessoa salva com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao salvar pessoa: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
    }

    public List<Pessoa> listarTodos() {
        List<Pessoa> lista = new ArrayList<>();
        String sql = """
            SELECT p.*, r.nome_regiao, r.uf, r.descricao_regiao
            FROM pessoa p
            JOIN regiao r ON p.regiao_id_regiao = r.id_regiao
            """;
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(montar(rs));
        } catch (SQLException e) {
            System.out.println("Erro ao listar pessoas: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
        return lista;
    }

    public Pessoa buscarPorId(int id) {
        String sql = """
            SELECT p.*, r.nome_regiao, r.uf, r.descricao_regiao
            FROM pessoa p
            JOIN regiao r ON p.regiao_id_regiao = r.id_regiao
            WHERE p.id_pessoa = ?
            """;
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return montar(rs);
        } catch (SQLException e) {
            System.out.println("Erro ao buscar pessoa: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
        return null;
    }

    public void atualizar(Pessoa pessoa) {
        String sql = "UPDATE pessoa SET nome_pessoa=?, cpf_cnpj=?, idade=?, telefone=?, email=?, tipo_pessoa=?, regiao_id_regiao=? WHERE id_pessoa=?";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pessoa.getNomePessoa());
            stmt.setString(2, pessoa.getCpfCnpj());
            stmt.setInt(3, pessoa.getIdade());
            stmt.setString(4, pessoa.getTelefone());
            stmt.setString(5, pessoa.getEmail());
            stmt.setString(6, pessoa.getTipoPessoa());
            stmt.setInt(7, pessoa.getRegiao().getIdRegiao());
            stmt.setInt(8, pessoa.getIdPessoa());
            stmt.executeUpdate();
            System.out.println("Pessoa atualizada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar pessoa: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM pessoa WHERE id_pessoa = ?";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Pessoa excluída com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao excluir pessoa: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
    }

    private Pessoa montar(ResultSet rs) throws SQLException {
        Regiao regiao = new Regiao(
                rs.getInt("regiao_id_regiao"),
                rs.getString("nome_regiao"),
                rs.getString("uf"),
                rs.getString("descricao_regiao")
        );
        return new Pessoa(
                rs.getInt("id_pessoa"),
                rs.getString("nome_pessoa"),
                rs.getString("cpf_cnpj"),
                rs.getInt("idade"),
                rs.getString("telefone"),
                rs.getString("email"),
                rs.getString("tipo_pessoa"),
                regiao
        );
    }
}