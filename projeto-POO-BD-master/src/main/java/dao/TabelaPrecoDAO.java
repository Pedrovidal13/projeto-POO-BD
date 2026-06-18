package dao;

import utils.ConexaoMySQL;
import model.Operadora;
import model.Plano;
import model.TabelaPreco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TabelaPrecoDAO {

    private final PlanoDAO planoDAO = new PlanoDAO();

    public void salvar(TabelaPreco tp) {
        String sql = "INSERT INTO tabela_preco_operadora (data_inicio_vigencia, data_final_vigencia, valor_base, faixa_etaria, plano_id) VALUES (?, ?, ?, ?, ?)";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, tp.getDataInicioVigencia());
            stmt.setString(2, tp.getDataFinalVigencia());
            stmt.setFloat(3, tp.getValorBase());
            stmt.setString(4, tp.getFaixaEtaria());
            stmt.setInt(5, tp.getPlano().getIdPlano());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) tp.setIdTabelaPreco(rs.getInt(1));
            System.out.println("Tabela de preço salva com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao salvar tabela de preço: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
    }

    public List<TabelaPreco> listarTodos() {
        List<TabelaPreco> lista = new ArrayList<>();
        String sql = """
            SELECT t.*, p.nome_plano, p.tipo_plano, p.cobertura, p.acomodacao,
                   o.id_operadora, o.nome_operadora, o.cnpj, o.registro_ans, o.telefone AS tel_op, o.email AS email_op
            FROM tabela_preco_operadora t
            JOIN plano p ON t.plano_id = p.id_plano
            JOIN operadora o ON p.id_operadora = o.id_operadora
            """;
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(montar(rs));
        } catch (SQLException e) {
            System.out.println("Erro ao listar tabelas de preço: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
        return lista;
    }

    public TabelaPreco buscarPorId(int id) {
        String sql = """
            SELECT t.*, p.nome_plano, p.tipo_plano, p.cobertura, p.acomodacao,
                   o.id_operadora, o.nome_operadora, o.cnpj, o.registro_ans, o.telefone AS tel_op, o.email AS email_op
            FROM tabela_preco_operadora t
            JOIN plano p ON t.plano_id = p.id_plano
            JOIN operadora o ON p.id_operadora = o.id_operadora
            WHERE t.id_tabela_preco_operadora = ?
            """;
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return montar(rs);
        } catch (SQLException e) {
            System.out.println("Erro ao buscar tabela de preço: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
        return null;
    }

    public void atualizar(TabelaPreco tp) {
        String sql = "UPDATE tabela_preco_operadora SET data_inicio_vigencia=?, data_final_vigencia=?, valor_base=?, faixa_etaria=?, plano_id=? WHERE id_tabela_preco_operadora=?";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tp.getDataInicioVigencia());
            stmt.setString(2, tp.getDataFinalVigencia());
            stmt.setFloat(3, tp.getValorBase());
            stmt.setString(4, tp.getFaixaEtaria());
            stmt.setInt(5, tp.getPlano().getIdPlano());
            stmt.setInt(6, tp.getIdTabelaPreco());
            stmt.executeUpdate();
            System.out.println("Tabela de preço atualizada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar tabela de preço: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM tabela_preco_operadora WHERE id_tabela_preco_operadora = ?";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Tabela de preço excluída com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao excluir tabela de preço: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
    }

    private TabelaPreco montar(ResultSet rs) throws SQLException {
        Operadora op = new Operadora(
                rs.getString("nome_operadora"),
                rs.getString("cnpj")
        );
        Plano plano = new Plano(
                rs.getInt("plano_id"),
                rs.getString("nome_plano"),
                rs.getString("tipo_plano"),
                rs.getString("cobertura"),
                rs.getString("acomodacao"),
                op
        );
        return new TabelaPreco(
                rs.getInt("id_tabela_preco_operadora"),
                rs.getString("data_inicio_vigencia"),
                rs.getString("data_final_vigencia"),
                rs.getFloat("valor_base"),
                rs.getString("faixa_etaria"),
                plano
        );
    }
}