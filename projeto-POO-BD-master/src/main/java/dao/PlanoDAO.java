package dao;

import utils.ConexaoMySQL;
import model.Operadora;
import model.Plano;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanoDAO {

    public void salvar(Plano plano) {
        String sql = "INSERT INTO plano (nome_plano, tipo_plano, cobertura, acomodacao, id_operadora) VALUES (?, ?, ?, ?, ?)";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, plano.getNomePlano());
            stmt.setString(2, plano.getTipoPlano());
            stmt.setString(3, plano.getCobertura());
            stmt.setString(4, plano.getAcomodacao());
            stmt.setInt(5, plano.getOperadora().getIdOperadora());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) plano.setIdPlano(rs.getInt(1));
            System.out.println("Plano salvo com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao salvar plano: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
    }

    public List<Plano> listarTodos() {
        List<Plano> lista = new ArrayList<>();
        String sql = """
            SELECT p.*, o.id_operadora, o.nome_operadora, o.cnpj,
                   o.registro_ans, o.telefone AS tel_op, o.email AS email_op
            FROM plano p
            JOIN operadora o ON p.id_operadora = o.id_operadora
            """;
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(montar(rs));
        } catch (SQLException e) {
            System.out.println("Erro ao listar planos: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
        return lista;
    }

    public Plano buscarPorId(int id) {
        String sql = """
            SELECT p.*, o.id_operadora, o.nome_operadora, o.cnpj,
                   o.registro_ans, o.telefone AS tel_op, o.email AS email_op
            FROM plano p
            JOIN operadora o ON p.id_operadora = o.id_operadora
            WHERE p.id_plano = ?
            """;
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return montar(rs);
        } catch (SQLException e) {
            System.out.println("Erro ao buscar plano: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
        return null;
    }

    public void atualizar(Plano plano) {
        String sql = "UPDATE plano SET nome_plano=?, tipo_plano=?, cobertura=?, acomodacao=?, id_operadora=? WHERE id_plano=?";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, plano.getNomePlano());
            stmt.setString(2, plano.getTipoPlano());
            stmt.setString(3, plano.getCobertura());
            stmt.setString(4, plano.getAcomodacao());
            stmt.setInt(5, plano.getOperadora().getIdOperadora());
            stmt.setInt(6, plano.getIdPlano());
            stmt.executeUpdate();
            System.out.println("Plano atualizado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar plano: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM plano WHERE id_plano = ?";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Plano excluído com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao excluir plano: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
    }

    // Corrigido: popula idOperadora e todos os campos da operadora
    private Plano montar(ResultSet rs) throws SQLException {
        Operadora op = new Operadora(
                rs.getInt("id_operadora"),
                rs.getString("nome_operadora"),
                rs.getString("cnpj"),
                rs.getInt("registro_ans"),
                rs.getString("tel_op"),
                rs.getString("email_op")
        );
        return new Plano(
                rs.getInt("id_plano"),
                rs.getString("nome_plano"),
                rs.getString("tipo_plano"),
                rs.getString("cobertura"),
                rs.getString("acomodacao"),
                op
        );
    }
}