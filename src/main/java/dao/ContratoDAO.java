package dao;

import connection.ConexaoMySQL;
import model.Cliente;
import model.Contrato;
import model.Operadora;
import model.Plano;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContratoDAO {

    public void salvar(Contrato contrato) {
        String sqlContrato = "INSERT INTO contrato (codigo_cliente, codigo_plano, status) VALUES (?, ?, ?)";
        String sqlBoleto   = "INSERT INTO boleto (vencimento, valor, pago, numero_contrato) VALUES (?, ?, ?, ?)";

        Connection conn = ConexaoMySQL.obterConexao();
        try {
            conn.setAutoCommit(false); // transação: salva contrato e boleto juntos

            // Salva o contrato
            try (PreparedStatement stmt = conn.prepareStatement(sqlContrato, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, contrato.getCliente().getCodigoCliente());
                stmt.setInt(2, contrato.getPlano().getCodigo());
                stmt.setString(3, contrato.getStatus());
                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int numeroGerado = rs.getInt(1);

                    // Salva o boleto vinculado ao contrato
                    try (PreparedStatement stmtBoleto = conn.prepareStatement(sqlBoleto)) {
                        stmtBoleto.setString(1, contrato.getBoleto().getVencimento());
                        stmtBoleto.setDouble(2, contrato.getBoleto().getValor());
                        stmtBoleto.setBoolean(3, contrato.getBoleto().isPago());
                        stmtBoleto.setInt(4, numeroGerado);
                        stmtBoleto.executeUpdate();
                    }
                }
            }

            conn.commit();
            System.out.println("Contrato salvo com sucesso!");

        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            System.out.println("Erro ao salvar contrato: " + e.getMessage());
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
            ConexaoMySQL.fecharConexao(conn);
        }
    }

    public List<Contrato> listarTodos() {
        List<Contrato> contratos = new ArrayList<>();
        String sql = """
                SELECT c.numero_contrato, c.status,
                       cl.codigo_cliente, cl.nome AS nome_cliente, cl.idade, cl.cpf, cl.telefone, cl.email, cl.endereco,
                       p.codigo AS codigo_plano, p.nome AS nome_plano, p.cobertura, p.valor_mensal, p.status AS status_plano,
                       o.nome AS nome_operadora, o.cnpj,
                       b.vencimento, b.valor AS valor_boleto, b.pago
                FROM contrato c
                JOIN cliente cl ON c.codigo_cliente = cl.codigo_cliente
                JOIN plano p ON c.codigo_plano = p.codigo
                JOIN operadora o ON p.codigo_operadora = o.codigo
                JOIN boleto b ON b.numero_contrato = c.numero_contrato
                """;
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                contratos.add(montarContrato(rs));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar contratos: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
        return contratos;
    }

    public Contrato buscarPorNumero(int numero) {
        String sql = """
                SELECT c.numero_contrato, c.status,
                       cl.codigo_cliente, cl.nome AS nome_cliente, cl.idade, cl.cpf, cl.telefone, cl.email, cl.endereco,
                       p.codigo AS codigo_plano, p.nome AS nome_plano, p.cobertura, p.valor_mensal, p.status AS status_plano,
                       o.nome AS nome_operadora, o.cnpj,
                       b.vencimento, b.valor AS valor_boleto, b.pago
                FROM contrato c
                JOIN cliente cl ON c.codigo_cliente = cl.codigo_cliente
                JOIN plano p ON c.codigo_plano = p.codigo
                JOIN operadora o ON p.codigo_operadora = o.codigo
                JOIN boleto b ON b.numero_contrato = c.numero_contrato
                WHERE c.numero_contrato = ?
                """;
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, numero);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return montarContrato(rs);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar contrato: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
        return null;
    }

    public void cancelarContrato(int numero) {
        String sql = "UPDATE contrato SET status = 'CANCELADO' WHERE numero_contrato = ?";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, numero);
            stmt.executeUpdate();
            System.out.println("Contrato cancelado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao cancelar contrato: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
    }

    // Monta um objeto Contrato a partir do ResultSet (evita repetição de código)
    private Contrato montarContrato(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente(
                rs.getInt("codigo_cliente"),
                rs.getString("nome_cliente"),
                rs.getInt("idade"),
                rs.getString("cpf"),
                rs.getString("telefone"),
                rs.getString("email"),
                rs.getString("endereco")
        );

        Operadora operadora = new Operadora(
                rs.getString("nome_operadora"),
                rs.getString("cnpj")
        );

        Plano plano = new Plano(
                rs.getInt("codigo_plano"),
                rs.getString("nome_plano"),
                rs.getString("cobertura"),
                rs.getDouble("valor_mensal"),
                rs.getString("status_plano"),
                operadora
        );

        Contrato contrato = new Contrato(
                rs.getInt("numero_contrato"),
                cliente,
                plano,
                operadora,
                rs.getString("vencimento")
        );

        // Sincroniza status e pagamento com o que está no banco
        if ("CANCELADO".equals(rs.getString("status"))) {
            contrato.cancelarContrato();
        }
        if (rs.getBoolean("pago")) {
            contrato.getBoleto().pagar();
        }

        return contrato;
    }
}
