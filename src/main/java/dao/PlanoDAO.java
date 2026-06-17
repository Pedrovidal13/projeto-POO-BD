package dao;

import connection.ConexaoMySQL;
import model.Operadora;
import model.Plano;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanoDAO {

    private final OperadoraDAO operadoraDAO = new OperadoraDAO();

    public void salvar(Plano plano) {
        // Busca a operadora no banco pelo CNPJ para obter o código FK
        Operadora operadora = operadoraDAO.buscarPorCnpj(plano.getOperadora().getCnpj());
        if (operadora == null) {
            System.out.println("Erro ao salvar plano: Operadora não encontrada para o CNPJ: " + plano.getOperadora().getCnpj());
            return;
        }

        String sql = "INSERT INTO plano (nome, cobertura, valor_mensal, status, codigo_operadora) VALUES (?, ?, ?, ?, ?)";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, plano.getNome());
            stmt.setString(2, plano.getCobertura());
            stmt.setDouble(3, plano.getValorMensal());
            stmt.setString(4, plano.getStatus());
            stmt.setInt(5, operadora.getCodigo());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                plano.setCodigo(rs.getInt(1));
            }
            System.out.println("Plano salvo com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao salvar plano: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
    }

    public List<Plano> listarTodos() {
        List<Plano> planos = new ArrayList<>();
        String sql = """
                SELECT p.*, o.codigo AS codigo_operadora, o.nome AS nome_operadora, o.cnpj
                FROM plano p
                JOIN operadora o ON p.codigo_operadora = o.codigo
                """;
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                planos.add(montarPlano(rs));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar planos: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
        return planos;
    }

    public Plano buscarPorCodigo(int codigo) {
        String sql = """
                SELECT p.*, o.codigo AS codigo_operadora, o.nome AS nome_operadora, o.cnpj
                FROM plano p
                JOIN operadora o ON p.codigo_operadora = o.codigo
                WHERE p.codigo = ?
                """;
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return montarPlano(rs);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar plano: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
        return null;
    }

    public void atualizar(Plano plano) {
        Operadora operadora = operadoraDAO.buscarPorCnpj(plano.getOperadora().getCnpj());
        if (operadora == null) {
            System.out.println("Erro ao atualizar plano: Operadora não encontrada.");
            return;
        }

        String sql = "UPDATE plano SET nome=?, cobertura=?, valor_mensal=?, status=?, codigo_operadora=? WHERE codigo=?";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, plano.getNome());
            stmt.setString(2, plano.getCobertura());
            stmt.setDouble(3, plano.getValorMensal());
            stmt.setString(4, plano.getStatus());
            stmt.setInt(5, operadora.getCodigo());
            stmt.setInt(6, plano.getCodigo());
            stmt.executeUpdate();
            System.out.println("Plano atualizado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar plano: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
    }

    public void excluir(int codigo) {
        String sql = "DELETE FROM plano WHERE codigo = ?";
        Connection conn = ConexaoMySQL.obterConexao();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            stmt.executeUpdate();
            System.out.println("Plano excluído com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao excluir plano: " + e.getMessage());
        } finally {
            ConexaoMySQL.fecharConexao(conn);
        }
    }

    private Plano montarPlano(ResultSet rs) throws SQLException {
        Operadora operadora = new Operadora(
                rs.getInt("codigo_operadora"),
                rs.getString("nome_operadora"),
                rs.getString("cnpj")
        );
        return new Plano(
                rs.getInt("codigo"),
                rs.getString("nome"),
                rs.getString("cobertura"),
                rs.getDouble("valor_mensal"),
                rs.getString("status"),
                operadora
        );
    }
}
