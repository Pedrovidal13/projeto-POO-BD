package principal;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import dao.*;
import model.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Servidor HTTP leve — sem Spring, sem dependências externas.
 * Expõe REST para todas as entidades do banco "projeto".
 *
 * Como rodar:
 *   Execute Main.java (que chama ApiServidor.iniciar()) e abra o index.html no navegador.
 *
 * Endpoints:
 *   GET/POST         /api/regioes
 *   GET/PUT/DELETE   /api/regioes/{id}
 *   GET/POST         /api/pessoas
 *   GET/PUT/DELETE   /api/pessoas/{id}
 *   GET/POST         /api/operadoras
 *   GET/PUT/DELETE   /api/operadoras/{id}
 *   GET/POST         /api/planos
 *   GET/PUT/DELETE   /api/planos/{id}
 *   GET/POST         /api/tabelas
 *   GET/PUT/DELETE   /api/tabelas/{id}
 *   GET/POST         /api/vendedores
 *   GET/PUT/DELETE   /api/vendedores/{id}
 *   GET/POST         /api/configs
 *   GET/PUT/DELETE   /api/configs/{id}
 *   GET/POST         /api/associacoes
 *   GET/PUT/DELETE   /api/associacoes/{id}
 *   GET/POST         /api/contratos
 *   DELETE           /api/contratos/{id}   → cancela (data_fim = hoje)
 */
public class ApiServidor {

    private static final int PORTA = 8080;

    private static final RegiaoDAO         regiaoDAO     = new RegiaoDAO();
    private static final PessoaDAO         pessoaDAO     = new PessoaDAO();
    private static final OperadoraDAO      operadoraDAO  = new OperadoraDAO();
    private static final PlanoDAO          planoDAO      = new PlanoDAO();
    private static final TabelaPrecoDAO    tabelaDAO     = new TabelaPrecoDAO();
    private static final VendedorDAO       vendedorDAO   = new VendedorDAO();
    private static final ConfigComissaoDAO configDAO     = new ConfigComissaoDAO();
    private static final AssociacaoDAO     associacaoDAO = new AssociacaoDAO();
    private static final ContratoDAO       contratoDAO   = new ContratoDAO();

    public static void main(String[] args) throws IOException { iniciar(); }

    public static void iniciar() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORTA), 0);
        server.createContext("/api/regioes",    e -> handle(e, "regioes"));
        server.createContext("/api/pessoas",    e -> handle(e, "pessoas"));
        server.createContext("/api/operadoras", e -> handle(e, "operadoras"));
        server.createContext("/api/planos",     e -> handle(e, "planos"));
        server.createContext("/api/tabelas",    e -> handle(e, "tabelas"));
        server.createContext("/api/vendedores", e -> handle(e, "vendedores"));
        server.createContext("/api/configs",    e -> handle(e, "configs"));
        server.createContext("/api/associacoes",e -> handle(e, "associacoes"));
        server.createContext("/api/contratos",  e -> handle(e, "contratos"));
        server.setExecutor(Executors.newFixedThreadPool(4));
        server.start();
        System.out.println("======================================");
        System.out.println("  API rodando em http://localhost:" + PORTA);
        System.out.println("  Abra o index.html no navegador.");
        System.out.println("======================================");
    }

    private static void handle(HttpExchange ex, String recurso) throws IOException {
        addCors(ex);
        if ("OPTIONS".equals(ex.getRequestMethod())) { respond(ex, 204, ""); return; }
        String[] parts = ex.getRequestURI().getPath().split("/");
        boolean temId  = parts.length == 4;
        int id = temId ? Integer.parseInt(parts[3]) : 0;
        String method  = ex.getRequestMethod();
        String body    = method.equals("POST") || method.equals("PUT") ? lerBody(ex) : "";

        try {
            switch (recurso) {
                case "regioes"     -> handleRegioes(ex, method, id, temId, body);
                case "pessoas"     -> handlePessoas(ex, method, id, temId, body);
                case "operadoras"  -> handleOperadoras(ex, method, id, temId, body);
                case "planos"      -> handlePlanos(ex, method, id, temId, body);
                case "tabelas"     -> handleTabelas(ex, method, id, temId, body);
                case "vendedores"  -> handleVendedores(ex, method, id, temId, body);
                case "configs"     -> handleConfigs(ex, method, id, temId, body);
                case "associacoes" -> handleAssociacoes(ex, method, id, temId, body);
                case "contratos"   -> handleContratos(ex, method, id, temId, body);
            }
        } catch (Exception e) {
            respond(ex, 500, "{\"erro\":\"" + esc(e.getMessage()) + "\"}");
        }
    }

    // ── REGIOES ──────────────────────────────────────────────

    private static void handleRegioes(HttpExchange ex, String m, int id, boolean temId, String body) throws IOException {
        if ("GET".equals(m) && !temId) {
            respond(ex, 200, toJsonArray(regiaoDAO.listarTodos(), ApiServidor::regiaoJson));
        } else if ("POST".equals(m)) {
            Regiao r = new Regiao(campo(body,"nomeRegiao"), campo(body,"uf"), campo(body,"descricaoRegiao"));
            regiaoDAO.salvar(r); respond(ex, 201, regiaoJson(r));
        } else if ("PUT".equals(m) && temId) {
            Regiao r = new Regiao(id, campo(body,"nomeRegiao"), campo(body,"uf"), campo(body,"descricaoRegiao"));
            regiaoDAO.atualizar(r); respond(ex, 200, regiaoJson(r));
        } else if ("DELETE".equals(m) && temId) {
            regiaoDAO.excluir(id); respond(ex, 200, "{\"ok\":true}");
        } else respond(ex, 405, "{\"erro\":\"Método não permitido\"}");
    }

    // ── PESSOAS ──────────────────────────────────────────────

    private static void handlePessoas(HttpExchange ex, String m, int id, boolean temId, String body) throws IOException {
        if ("GET".equals(m) && !temId) {
            respond(ex, 200, toJsonArray(pessoaDAO.listarTodos(), ApiServidor::pessoaJson));
        } else if ("POST".equals(m)) {
            int idRegiao = Integer.parseInt(campo(body,"idRegiao"));
            Regiao reg = regiaoDAO.buscarPorId(idRegiao);
            if (reg == null) { respond(ex, 400, "{\"erro\":\"Região não encontrada\"}"); return; }
            Pessoa p = new Pessoa(campo(body,"nomePessoa"), campo(body,"cpfCnpj"),
                    Integer.parseInt(campo(body,"idade")), campo(body,"telefone"),
                    campo(body,"email"), campo(body,"tipoPessoa"), reg);
            pessoaDAO.salvar(p); respond(ex, 201, pessoaJson(p));
        } else if ("PUT".equals(m) && temId) {
            int idRegiao = Integer.parseInt(campo(body,"idRegiao"));
            Regiao reg = regiaoDAO.buscarPorId(idRegiao);
            if (reg == null) { respond(ex, 400, "{\"erro\":\"Região não encontrada\"}"); return; }
            Pessoa p = new Pessoa(id, campo(body,"nomePessoa"), campo(body,"cpfCnpj"),
                    Integer.parseInt(campo(body,"idade")), campo(body,"telefone"),
                    campo(body,"email"), campo(body,"tipoPessoa"), reg);
            pessoaDAO.atualizar(p); respond(ex, 200, pessoaJson(p));
        } else if ("DELETE".equals(m) && temId) {
            pessoaDAO.excluir(id); respond(ex, 200, "{\"ok\":true}");
        } else respond(ex, 405, "{\"erro\":\"Método não permitido\"}");
    }

    // ── OPERADORAS ───────────────────────────────────────────

    private static void handleOperadoras(HttpExchange ex, String m, int id, boolean temId, String body) throws IOException {
        if ("GET".equals(m) && !temId) {
            respond(ex, 200, toJsonArray(operadoraDAO.listarTodos(), ApiServidor::operadoraJson));
        } else if ("POST".equals(m)) {
            Operadora o = new Operadora(campo(body,"nomeOperadora"), campo(body,"cnpj"),
                    intCampo(body,"registroAns"), campo(body,"telefone"), campo(body,"email"));
            operadoraDAO.salvar(o); respond(ex, 201, operadoraJson(o));
        } else if ("PUT".equals(m) && temId) {
            Operadora o = new Operadora(id, campo(body,"nomeOperadora"), campo(body,"cnpj"),
                    intCampo(body,"registroAns"), campo(body,"telefone"), campo(body,"email"));
            operadoraDAO.atualizar(o); respond(ex, 200, operadoraJson(o));
        } else if ("DELETE".equals(m) && temId) {
            operadoraDAO.excluir(id); respond(ex, 200, "{\"ok\":true}");
        } else respond(ex, 405, "{\"erro\":\"Método não permitido\"}");
    }

    // ── PLANOS ───────────────────────────────────────────────

    private static void handlePlanos(HttpExchange ex, String m, int id, boolean temId, String body) throws IOException {
        if ("GET".equals(m) && !temId) {
            respond(ex, 200, toJsonArray(planoDAO.listarTodos(), ApiServidor::planoJson));
        } else if ("POST".equals(m)) {
            Operadora op = operadoraDAO.buscarPorId(intCampo(body,"idOperadora"));
            if (op == null) { respond(ex, 400, "{\"erro\":\"Operadora não encontrada\"}"); return; }
            Plano p = new Plano(campo(body,"nomePlano"), campo(body,"tipoPlano"),
                    campo(body,"cobertura"), campo(body,"acomodacao"), op);
            planoDAO.salvar(p); respond(ex, 201, planoJson(p));
        } else if ("PUT".equals(m) && temId) {
            Operadora op = operadoraDAO.buscarPorId(intCampo(body,"idOperadora"));
            if (op == null) { respond(ex, 400, "{\"erro\":\"Operadora não encontrada\"}"); return; }
            Plano p = new Plano(id, campo(body,"nomePlano"), campo(body,"tipoPlano"),
                    campo(body,"cobertura"), campo(body,"acomodacao"), op);
            planoDAO.atualizar(p); respond(ex, 200, planoJson(p));
        } else if ("DELETE".equals(m) && temId) {
            planoDAO.excluir(id); respond(ex, 200, "{\"ok\":true}");
        } else respond(ex, 405, "{\"erro\":\"Método não permitido\"}");
    }

    // ── TABELAS DE PREÇO ─────────────────────────────────────

    private static void handleTabelas(HttpExchange ex, String m, int id, boolean temId, String body) throws IOException {
        if ("GET".equals(m) && !temId) {
            respond(ex, 200, toJsonArray(tabelaDAO.listarTodos(), ApiServidor::tabelaJson));
        } else if ("POST".equals(m)) {
            Plano pl = planoDAO.buscarPorId(intCampo(body,"idPlano"));
            if (pl == null) { respond(ex, 400, "{\"erro\":\"Plano não encontrado\"}"); return; }
            TabelaPreco t = new TabelaPreco(campo(body,"dataInicioVigencia"),
                    campo(body,"dataFinalVigencia"), floatCampo(body,"valorBase"),
                    campo(body,"faixaEtaria"), pl);
            tabelaDAO.salvar(t); respond(ex, 201, tabelaJson(t));
        } else if ("PUT".equals(m) && temId) {
            Plano pl = planoDAO.buscarPorId(intCampo(body,"idPlano"));
            if (pl == null) { respond(ex, 400, "{\"erro\":\"Plano não encontrado\"}"); return; }
            TabelaPreco t = new TabelaPreco(id, campo(body,"dataInicioVigencia"),
                    campo(body,"dataFinalVigencia"), floatCampo(body,"valorBase"),
                    campo(body,"faixaEtaria"), pl);
            tabelaDAO.atualizar(t); respond(ex, 200, tabelaJson(t));
        } else if ("DELETE".equals(m) && temId) {
            tabelaDAO.excluir(id); respond(ex, 200, "{\"ok\":true}");
        } else respond(ex, 405, "{\"erro\":\"Método não permitido\"}");
    }

    // ── VENDEDORES ───────────────────────────────────────────

    private static void handleVendedores(HttpExchange ex, String m, int id, boolean temId, String body) throws IOException {
        if ("GET".equals(m) && !temId) {
            respond(ex, 200, toJsonArray(vendedorDAO.listarTodos(), ApiServidor::vendedorJson));
        } else if ("POST".equals(m)) {
            Vendedor v = new Vendedor(campo(body,"nomeVendedor"));
            vendedorDAO.salvar(v); respond(ex, 201, vendedorJson(v));
        } else if ("PUT".equals(m) && temId) {
            Vendedor v = new Vendedor(id, campo(body,"nomeVendedor"));
            vendedorDAO.atualizar(v); respond(ex, 200, vendedorJson(v));
        } else if ("DELETE".equals(m) && temId) {
            vendedorDAO.excluir(id); respond(ex, 200, "{\"ok\":true}");
        } else respond(ex, 405, "{\"erro\":\"Método não permitido\"}");
    }

    // ── CONFIGS ──────────────────────────────────────────────

    private static void handleConfigs(HttpExchange ex, String m, int id, boolean temId, String body) throws IOException {
        if ("GET".equals(m) && !temId) {
            respond(ex, 200, toJsonArray(configDAO.listarTodos(), ApiServidor::configJson));
        } else if ("POST".equals(m)) {
            Operadora op = operadoraDAO.buscarPorId(intCampo(body,"idOperadora"));
            Vendedor v   = vendedorDAO.buscarPorId(intCampo(body,"idVendedor"));
            if (op == null || v == null) { respond(ex, 400, "{\"erro\":\"Operadora ou Vendedor não encontrado\"}"); return; }
            ConfigComissao c = new ConfigComissao(campo(body,"descricaoConfig"), floatCampo(body,"percentualComissao"), op, v);
            configDAO.salvar(c); respond(ex, 201, configJson(c));
        } else if ("PUT".equals(m) && temId) {
            Operadora op = operadoraDAO.buscarPorId(intCampo(body,"idOperadora"));
            Vendedor v   = vendedorDAO.buscarPorId(intCampo(body,"idVendedor"));
            if (op == null || v == null) { respond(ex, 400, "{\"erro\":\"Operadora ou Vendedor não encontrado\"}"); return; }
            ConfigComissao c = new ConfigComissao(id, campo(body,"descricaoConfig"), floatCampo(body,"percentualComissao"), op, v);
            configDAO.atualizar(c); respond(ex, 200, configJson(c));
        } else if ("DELETE".equals(m) && temId) {
            configDAO.excluir(id); respond(ex, 200, "{\"ok\":true}");
        } else respond(ex, 405, "{\"erro\":\"Método não permitido\"}");
    }

    // ── ASSOCIACOES ──────────────────────────────────────────

    private static void handleAssociacoes(HttpExchange ex, String m, int id, boolean temId, String body) throws IOException {
        if ("GET".equals(m) && !temId) {
            respond(ex, 200, toJsonArray(associacaoDAO.listarTodos(), ApiServidor::associacaoJson));
        } else if ("POST".equals(m)) {
            Regiao reg = regiaoDAO.buscarPorId(intCampo(body,"idRegiao"));
            if (reg == null) { respond(ex, 400, "{\"erro\":\"Região não encontrada\"}"); return; }
            Associacao a = new Associacao(campo(body,"nomeAssociacao"), campo(body,"cnpj"),
                    campo(body,"telefone"), campo(body,"email"), reg);
            associacaoDAO.salvar(a); respond(ex, 201, associacaoJson(a));
        } else if ("PUT".equals(m) && temId) {
            Regiao reg = regiaoDAO.buscarPorId(intCampo(body,"idRegiao"));
            if (reg == null) { respond(ex, 400, "{\"erro\":\"Região não encontrada\"}"); return; }
            Associacao a = new Associacao(id, campo(body,"nomeAssociacao"), campo(body,"cnpj"),
                    campo(body,"telefone"), campo(body,"email"), reg);
            associacaoDAO.atualizar(a); respond(ex, 200, associacaoJson(a));
        } else if ("DELETE".equals(m) && temId) {
            associacaoDAO.excluir(id); respond(ex, 200, "{\"ok\":true}");
        } else respond(ex, 405, "{\"erro\":\"Método não permitido\"}");
    }

    // ── CONTRATOS ────────────────────────────────────────────

    private static void handleContratos(HttpExchange ex, String m, int id, boolean temId, String body) throws IOException {
        if ("GET".equals(m) && !temId) {
            respond(ex, 200, toJsonArray(contratoDAO.listarTodos(), ApiServidor::contratoJson));
        } else if ("POST".equals(m)) {
            Pessoa pessoa   = pessoaDAO.buscarPorId(intCampo(body,"idPessoa"));
            ConfigComissao config = configDAO.buscarPorId(intCampo(body,"idConfigComissao"));
            Associacao assoc      = associacaoDAO.buscarPorId(intCampo(body,"idAssociacao"));
            TabelaPreco tabela    = tabelaDAO.buscarPorId(intCampo(body,"idTabelaPreco"));
            if (pessoa==null||config==null||assoc==null||tabela==null) {
                respond(ex, 400, "{\"erro\":\"Dependências não encontradas. Verifique os IDs.\"}"); return;
            }
            Contrato c = new Contrato(intCampo(body,"numeroContrato"), campo(body,"dataInicio"),
                    campo(body,"dataFim"), pessoa, config, assoc, tabela);
            contratoDAO.salvar(c); respond(ex, 201, contratoJson(c));
        } else if ("DELETE".equals(m) && temId) {
            contratoDAO.cancelarContrato(id); respond(ex, 200, "{\"ok\":true}");
        } else respond(ex, 405, "{\"erro\":\"Método não permitido\"}");
    }

    // ── JSON Serializers ─────────────────────────────────────

    private static String regiaoJson(Regiao r) {
        return String.format("{\"idRegiao\":%d,\"nomeRegiao\":\"%s\",\"uf\":\"%s\",\"descricaoRegiao\":\"%s\"}",
                r.getIdRegiao(), esc(r.getNomeRegiao()), esc(r.getUf()), esc(r.getDescricaoRegiao()));
    }

    private static String pessoaJson(Pessoa p) {
        return String.format("{\"idPessoa\":%d,\"nomePessoa\":\"%s\",\"cpfCnpj\":\"%s\",\"idade\":%d," +
                        "\"telefone\":\"%s\",\"email\":\"%s\",\"tipoPessoa\":\"%s\"," +
                        "\"idRegiao\":%d,\"nomeRegiao\":\"%s\",\"uf\":\"%s\"}",
                p.getIdPessoa(), esc(p.getNomePessoa()), esc(p.getCpfCnpj()), p.getIdade(),
                esc(p.getTelefone()), esc(p.getEmail()), esc(p.getTipoPessoa()),
                p.getRegiao().getIdRegiao(), esc(p.getRegiao().getNomeRegiao()), esc(p.getRegiao().getUf()));
    }

    private static String operadoraJson(Operadora o) {
        return String.format("{\"idOperadora\":%d,\"nomeOperadora\":\"%s\",\"cnpj\":\"%s\"," +
                        "\"registroAns\":%d,\"telefone\":\"%s\",\"email\":\"%s\"}",
                o.getIdOperadora(), esc(o.getNomeOperadora()), esc(o.getCnpj()),
                o.getRegistroAns(), esc(o.getTelefone()), esc(o.getEmail()));
    }

    private static String planoJson(Plano p) {
        return String.format("{\"idPlano\":%d,\"nomePlano\":\"%s\",\"tipoPlano\":\"%s\"," +
                        "\"cobertura\":\"%s\",\"acomodacao\":\"%s\",\"idOperadora\":%d,\"nomeOperadora\":\"%s\"}",
                p.getIdPlano(), esc(p.getNomePlano()), esc(p.getTipoPlano()),
                esc(p.getCobertura()), esc(p.getAcomodacao()),
                p.getOperadora().getIdOperadora(), esc(p.getOperadora().getNomeOperadora()));
    }

    private static String tabelaJson(TabelaPreco t) {
        return String.format("{\"idTabelaPreco\":%d,\"dataInicioVigencia\":\"%s\",\"dataFinalVigencia\":\"%s\"," +
                        "\"valorBase\":%.2f,\"faixaEtaria\":\"%s\",\"idPlano\":%d,\"nomePlano\":\"%s\"}",
                t.getIdTabelaPreco(), esc(t.getDataInicioVigencia()), esc(t.getDataFinalVigencia()),
                t.getValorBase(), esc(t.getFaixaEtaria()),
                t.getPlano().getIdPlano(), esc(t.getPlano().getNomePlano()));
    }

    private static String vendedorJson(Vendedor v) {
        return String.format("{\"idVendedor\":%d,\"nomeVendedor\":\"%s\"}",
                v.getIdVendedor(), esc(v.getNomeVendedor()));
    }

    private static String configJson(ConfigComissao c) {
        return String.format("{\"idConfigComissao\":%d,\"descricaoConfig\":\"%s\",\"percentualComissao\":%.2f," +
                        "\"idOperadora\":%d,\"nomeOperadora\":\"%s\",\"idVendedor\":%d,\"nomeVendedor\":\"%s\"}",
                c.getIdConfigComissao(), esc(c.getDescricaoConfig()), c.getPercentualComissao(),
                c.getOperadora().getIdOperadora(), esc(c.getOperadora().getNomeOperadora()),
                c.getVendedor().getIdVendedor(), esc(c.getVendedor().getNomeVendedor()));
    }

    private static String associacaoJson(Associacao a) {
        return String.format("{\"idAssociacao\":%d,\"nomeAssociacao\":\"%s\",\"cnpj\":\"%s\"," +
                        "\"telefone\":\"%s\",\"email\":\"%s\",\"idRegiao\":%d,\"nomeRegiao\":\"%s\",\"uf\":\"%s\"}",
                a.getIdAssociacao(), esc(a.getNomeAssociacao()), esc(a.getCnpj()),
                esc(a.getTelefone()), esc(a.getEmail()),
                a.getRegiao().getIdRegiao(), esc(a.getRegiao().getNomeRegiao()), esc(a.getRegiao().getUf()));
    }

    private static String contratoJson(Contrato c) {
        Pessoa p   = c.getPessoa();
        TabelaPreco t = c.getTabelaPreco();
        ConfigComissao cc = c.getConfigComissao();
        Associacao a = c.getAssociacao();
        return String.format("{\"idContrato\":%d,\"numeroContrato\":%d,\"dataInicio\":\"%s\",\"dataFim\":\"%s\"," +
                        "\"nomePessoa\":\"%s\",\"nomePlano\":\"%s\",\"valorBase\":%.2f,\"faixaEtaria\":\"%s\"," +
                        "\"nomeOperadora\":\"%s\",\"nomeVendedor\":\"%s\",\"nomeAssociacao\":\"%s\"}",
                c.getIdContrato(), c.getNumeroContrato(), esc(c.getDataInicio()), esc(c.getDataFim()),
                esc(p != null ? p.getNomePessoa() : ""),
                esc(t != null && t.getPlano() != null ? t.getPlano().getNomePlano() : ""),
                t != null ? t.getValorBase() : 0f,
                esc(t != null ? t.getFaixaEtaria() : ""),
                esc(cc != null && cc.getOperadora() != null ? cc.getOperadora().getNomeOperadora() : ""),
                esc(cc != null && cc.getVendedor() != null ? cc.getVendedor().getNomeVendedor() : ""),
                esc(a != null ? a.getNomeAssociacao() : ""));
    }

    // ── Helpers ──────────────────────────────────────────────

    @FunctionalInterface interface JsonFn<T> { String apply(T t); }

    private static <T> String toJsonArray(List<T> lista, JsonFn<T> fn) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < lista.size(); i++) { if (i>0) sb.append(","); sb.append(fn.apply(lista.get(i))); }
        return sb.append("]").toString();
    }

    private static void addCors(HttpExchange ex) {
        ex.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        ex.getResponseHeaders().add("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        ex.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
        ex.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
    }

    private static void respond(HttpExchange ex, int status, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        ex.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = ex.getResponseBody()) { os.write(bytes); }
    }

    private static String lerBody(HttpExchange ex) throws IOException {
        try (InputStream is = ex.getRequestBody()) { return new String(is.readAllBytes(), StandardCharsets.UTF_8); }
    }

    private static String campo(String json, String key) {
        String chave = "\"" + key + "\"";
        int idx = json.indexOf(chave); if (idx == -1) return "";
        int colon = json.indexOf(":", idx) + 1;
        while (colon < json.length() && json.charAt(colon) == ' ') colon++;
        if (json.charAt(colon) == '"') {
            int start = colon+1; int end = json.indexOf('"', start); return json.substring(start, end);
        } else {
            int end = colon;
            while (end < json.length() && ",}\n\r ".indexOf(json.charAt(end)) == -1) end++;
            return json.substring(colon, end).trim();
        }
    }

    private static int intCampo(String json, String key) {
        String v = campo(json, key); return v.isEmpty() ? 0 : Integer.parseInt(v);
    }
    private static float floatCampo(String json, String key) {
        String v = campo(json, key); return v.isEmpty() ? 0f : Float.parseFloat(v);
    }
    private static String esc(String s) {
        if (s == null) return ""; return s.replace("\\","\\\\").replace("\"","\\\"");
    }
}