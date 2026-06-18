package dao;

import utils.ConexaoMySQL;
import model.ConfigComissao;
import model.Operadora;
import model.Vendedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConfigComissaoDAO {

    public void salvar(ConfigComissao config) {
        String sql = "INSERT INTO config_comissao (descricao_config, percentual_comissao, operadora_idoperadora, vendedor_idvendedor) VALUES (?, ?, ?, ?)";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, config.getDescricaoConfig());
            stmt.setFloat(2, config.getPercentualComissao());
            stmt.setInt(3, config.getOperadora().getIdOperadora());
            stmt.setInt(4, config.getVendedor().getIdVendedor());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) config.setIdConfigComissao(rs.getInt(1));
            System.out.println("Config. de comissão salva com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao salvar config. de comissão: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
    }

    public List<ConfigComissao> listarTodos() {
        List<ConfigComissao> lista = new ArrayList<>();
        String sql = """
            SELECT cc.*,
                   o.id_operadora, o.nome_operadora, o.cnpj AS cnpj_op,
                   o.registro_ans, o.telefone AS tel_op, o.email AS email_op,
                   v.idvendedor, v.nome_vendedor
            FROM config_comissao cc
            JOIN operadora o ON cc.operadora_idoperadora = o.id_operadora
            JOIN vendedor v ON cc.vendedor_idvendedor = v.idvendedor
            """;
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(montar(rs));
        } catch (SQLException e) {
            System.out.println("Erro ao listar configs de comissão: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
        return lista;
    }

    public ConfigComissao buscarPorId(int id) {
        String sql = """
            SELECT cc.*,
                   o.id_operadora, o.nome_operadora, o.cnpj AS cnpj_op,
                   o.registro_ans, o.telefone AS tel_op, o.email AS email_op,
                   v.idvendedor, v.nome_vendedor
            FROM config_comissao cc
            JOIN operadora o ON cc.operadora_idoperadora = o.id_operadora
            JOIN vendedor v ON cc.vendedor_idvendedor = v.idvendedor
            WHERE cc.id_config_comissao = ?
            """;
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return montar(rs);
        } catch (SQLException e) {
            System.out.println("Erro ao buscar config. de comissão: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
        return null;
    }

    public void atualizar(ConfigComissao config) {
        String sql = "UPDATE config_comissao SET descricao_config=?, percentual_comissao=?, operadora_idoperadora=?, vendedor_idvendedor=? WHERE id_config_comissao=?";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, config.getDescricaoConfig());
            stmt.setFloat(2, config.getPercentualComissao());
            stmt.setInt(3, config.getOperadora().getIdOperadora());
            stmt.setInt(4, config.getVendedor().getIdVendedor());
            stmt.setInt(5, config.getIdConfigComissao());
            stmt.executeUpdate();
            System.out.println("Config. de comissão atualizada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar config. de comissão: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM config_comissao WHERE id_config_comissao = ?";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Config. de comissão excluída com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao excluir config. de comissão: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
    }

    // Corrigido: popula idOperadora e idVendedor corretamente
    private ConfigComissao montar(ResultSet rs) throws SQLException {
        Operadora op = new Operadora(
                rs.getInt("id_operadora"),
                rs.getString("nome_operadora"),
                rs.getString("cnpj_op"),
                rs.getInt("registro_ans"),
                rs.getString("tel_op"),
                rs.getString("email_op")
        );
        Vendedor v = new Vendedor(
                rs.getInt("idvendedor"),
                rs.getString("nome_vendedor")
        );
        return new ConfigComissao(
                rs.getInt("id_config_comissao"),
                rs.getString("descricao_config"),
                rs.getFloat("percentual_comissao"),
                op,
                v
        );
    }
}