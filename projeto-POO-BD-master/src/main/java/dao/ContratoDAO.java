package dao;

import utils.ConexaoMySQL;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContratoDAO {

    public void salvar(Contrato contrato) {
        String sql = """
            INSERT INTO contrato (numero_contrato, data_inicio, data_fim,
                pessoa_id, id_config_comissao, associacao_id_associacao,
                tabela_preco_operadora_id_tabela_preco_operadora)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, contrato.getNumeroContrato());
            stmt.setString(2, contrato.getDataInicio());
            stmt.setString(3, contrato.getDataFim());
            stmt.setInt(4, contrato.getPessoa().getIdPessoa());
            stmt.setInt(5, contrato.getConfigComissao().getIdConfigComissao());
            stmt.setInt(6, contrato.getAssociacao().getIdAssociacao());
            stmt.setInt(7, contrato.getTabelaPreco().getIdTabelaPreco());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) contrato.setIdContrato(rs.getInt(1));
            System.out.println("Contrato salvo com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao salvar contrato: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
    }

    public List<Contrato> listarTodos() {
        List<Contrato> lista = new ArrayList<>();
        String sql = buildSelectSQL() + " ORDER BY c.id_contrato DESC";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(montar(rs));
        } catch (SQLException e) {
            System.out.println("Erro ao listar contratos: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
        return lista;
    }

    public Contrato buscarPorId(int id) {
        String sql = buildSelectSQL() + " WHERE c.id_contrato = ?";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return montar(rs);
        } catch (SQLException e) {
            System.out.println("Erro ao buscar contrato por ID: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
        return null;
    }

    // Corrigido: implementado corretamente
    public Contrato buscarPorNumero(int numero) {
        String sql = buildSelectSQL() + " WHERE c.numero_contrato = ?";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, numero);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return montar(rs);
        } catch (SQLException e) {
            System.out.println("Erro ao buscar contrato por número: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
        return null;
    }

    public void atualizar(Contrato contrato) {
        String sql = """
            UPDATE contrato SET numero_contrato=?, data_inicio=?, data_fim=?,
                pessoa_id=?, id_config_comissao=?, associacao_id_associacao=?,
                tabela_preco_operadora_id_tabela_preco_operadora=?
            WHERE id_contrato=?
            """;
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, contrato.getNumeroContrato());
            stmt.setString(2, contrato.getDataInicio());
            stmt.setString(3, contrato.getDataFim());
            stmt.setInt(4, contrato.getPessoa().getIdPessoa());
            stmt.setInt(5, contrato.getConfigComissao().getIdConfigComissao());
            stmt.setInt(6, contrato.getAssociacao().getIdAssociacao());
            stmt.setInt(7, contrato.getTabelaPreco().getIdTabelaPreco());
            stmt.setInt(8, contrato.getIdContrato());
            stmt.executeUpdate();
            System.out.println("Contrato atualizado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar contrato: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
    }

    // Corrigido: deleta o contrato pelo id_contrato (excluir real)
    public void excluir(int id) {
        String sql = "DELETE FROM contrato WHERE id_contrato = ?";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Contrato excluído com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao excluir contrato: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
    }

    // Corrigido: implementado — cancela por id_contrato via data_fim = hoje
    public void cancelarContrato(int id) {
        String sql = "UPDATE contrato SET data_fim = CURDATE() WHERE id_contrato = ?";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("Contrato cancelado com sucesso!");
            else System.out.println("Contrato não encontrado para cancelamento.");
        } catch (SQLException e) {
            System.out.println("Erro ao cancelar contrato: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
    }

    private String buildSelectSQL() {
        return """
            SELECT
                c.id_contrato, c.numero_contrato, c.data_inicio, c.data_fim,
                p.id_pessoa, p.nome_pessoa, p.cpf_cnpj, p.idade,
                p.telefone AS tel_pessoa, p.email AS email_pessoa, p.tipo_pessoa,
                r.id_regiao, r.nome_regiao, r.uf, r.descricao_regiao,
                cc.id_config_comissao, cc.descricao_config, cc.percentual_comissao,
                o.id_operadora, o.nome_operadora, o.cnpj AS cnpj_op,
                o.registro_ans, o.telefone AS tel_op, o.email AS email_op,
                v.idvendedor, v.nome_vendedor,
                a.id_associacao, a.nome_associacao, a.cnpj AS cnpj_assoc,
                a.telefone AS tel_assoc, a.email AS email_assoc,
                ra.id_regiao AS id_regiao_assoc, ra.nome_regiao AS nome_regiao_assoc,
                ra.uf AS uf_assoc, ra.descricao_regiao AS desc_regiao_assoc,
                t.id_tabela_preco_operadora, t.data_inicio_vigencia, t.data_final_vigencia,
                t.valor_base, t.faixa_etaria,
                pl.id_plano, pl.nome_plano, pl.tipo_plano, pl.cobertura, pl.acomodacao
            FROM contrato c
            JOIN pessoa p ON c.pessoa_id = p.id_pessoa
            JOIN regiao r ON p.regiao_id_regiao = r.id_regiao
            JOIN config_comissao cc ON c.id_config_comissao = cc.id_config_comissao
            JOIN operadora o ON cc.operadora_idoperadora = o.id_operadora
            JOIN vendedor v ON cc.vendedor_idvendedor = v.idvendedor
            JOIN associacao a ON c.associacao_id_associacao = a.id_associacao
            JOIN regiao ra ON a.regiao_id_regiao = ra.id_regiao
            JOIN tabela_preco_operadora t ON c.tabela_preco_operadora_id_tabela_preco_operadora = t.id_tabela_preco_operadora
            JOIN plano pl ON t.plano_id = pl.id_plano
            """;
    }

    private Contrato montar(ResultSet rs) throws SQLException {
        Regiao regiaoPessoa = new Regiao(
                rs.getInt("id_regiao"), rs.getString("nome_regiao"),
                rs.getString("uf"), rs.getString("descricao_regiao"));

        Pessoa pessoa = new Pessoa(
                rs.getInt("id_pessoa"), rs.getString("nome_pessoa"),
                rs.getString("cpf_cnpj"), rs.getInt("idade"),
                rs.getString("tel_pessoa"), rs.getString("email_pessoa"),
                rs.getString("tipo_pessoa"), regiaoPessoa);

        Operadora op = new Operadora(
                rs.getInt("id_operadora"), rs.getString("nome_operadora"),
                rs.getString("cnpj_op"), rs.getInt("registro_ans"),
                rs.getString("tel_op"), rs.getString("email_op"));

        Vendedor v = new Vendedor(rs.getInt("idvendedor"), rs.getString("nome_vendedor"));

        ConfigComissao config = new ConfigComissao(
                rs.getInt("id_config_comissao"), rs.getString("descricao_config"),
                rs.getFloat("percentual_comissao"), op, v);

        Regiao regiaoAssoc = new Regiao(
                rs.getInt("id_regiao_assoc"), rs.getString("nome_regiao_assoc"),
                rs.getString("uf_assoc"), rs.getString("desc_regiao_assoc"));

        Associacao assoc = new Associacao(
                rs.getInt("id_associacao"), rs.getString("nome_associacao"),
                rs.getString("cnpj_assoc"), rs.getString("tel_assoc"),
                rs.getString("email_assoc"), regiaoAssoc);

        Plano plano = new Plano(
                rs.getInt("id_plano"), rs.getString("nome_plano"),
                rs.getString("tipo_plano"), rs.getString("cobertura"),
                rs.getString("acomodacao"), op);

        TabelaPreco tabela = new TabelaPreco(
                rs.getInt("id_tabela_preco_operadora"),
                rs.getString("data_inicio_vigencia"),
                rs.getString("data_final_vigencia"),
                rs.getFloat("valor_base"),
                rs.getString("faixa_etaria"), plano);

        return new Contrato(
                rs.getInt("id_contrato"),
                rs.getInt("numero_contrato"),
                rs.getString("data_inicio"),
                rs.getString("data_fim"),
                pessoa, config, assoc, tabela);
    }
}