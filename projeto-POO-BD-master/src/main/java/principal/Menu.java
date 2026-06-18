package principal;

import dao.*;
import model.*;
import utils.Escritor;
import utils.Leitor;

import java.util.List;

public class Menu {
    private static final Escritor escritor = new Escritor();
    private static final Leitor leitor = new Leitor();

    private static final RegiaoDAO regiaoDAO           = new RegiaoDAO();
    private static final PessoaDAO pessoaDAO           = new PessoaDAO();
    private static final OperadoraDAO operadoraDAO     = new OperadoraDAO();
    private static final PlanoDAO planoDAO             = new PlanoDAO();
    private static final TabelaPrecoDAO tabelaDAO      = new TabelaPrecoDAO();
    private static final VendedorDAO vendedorDAO       = new VendedorDAO();
    private static final ConfigComissaoDAO configDAO   = new ConfigComissaoDAO();
    private static final AssociacaoDAO associacaoDAO   = new AssociacaoDAO();
    private static final ContratoDAO contratoDAO       = new ContratoDAO();

    public static void main(String[] args) {
        executarSistema();
    }

    public static void executarSistema() {
        boolean executando = true;
        while (executando) {
            escritor.exibirTitulo("Administradora de Planos de Saude");
            escritor.escreverLinha("--- Cadastros ---");
            escritor.escreverLinha("1.  Cadastrar Região");
            escritor.escreverLinha("2.  Listar Regiões");
            escritor.escreverLinha("3.  Cadastrar Pessoa");
            escritor.escreverLinha("4.  Listar Pessoas");
            escritor.escreverLinha("5.  Cadastrar Operadora");
            escritor.escreverLinha("6.  Listar Operadoras");
            escritor.escreverLinha("7.  Cadastrar Plano");
            escritor.escreverLinha("8.  Listar Planos");
            escritor.escreverLinha("9.  Cadastrar Tabela de Preço");
            escritor.escreverLinha("10. Listar Tabelas de Preço");
            escritor.escreverLinha("11. Cadastrar Vendedor");
            escritor.escreverLinha("12. Listar Vendedores");
            escritor.escreverLinha("13. Cadastrar Config. de Comissão");
            escritor.escreverLinha("14. Listar Configs. de Comissão");
            escritor.escreverLinha("15. Cadastrar Associação");
            escritor.escreverLinha("16. Listar Associações");
            escritor.escreverLinha("--- Contratos ---");
            escritor.escreverLinha("17. Criar Contrato");
            escritor.escreverLinha("18. Listar Contratos");
            escritor.escreverLinha("19. Cancelar Contrato");
            escritor.escreverLinha("0.  Sair");
            escritor.pularLinhas(1);

            escritor.escrever("Selecione uma opcao: ");
            int opcao = leitor.lerInteiro();

            switch (opcao) {
                case 1  -> cadastrarRegiao();
                case 2  -> listar("Regiões", regiaoDAO.listarTodos());
                case 3  -> cadastrarPessoa();
                case 4  -> listar("Pessoas", pessoaDAO.listarTodos());
                case 5  -> cadastrarOperadora();
                case 6  -> listar("Operadoras", operadoraDAO.listarTodos());
                case 7  -> cadastrarPlano();
                case 8  -> listar("Planos", planoDAO.listarTodos());
                case 9  -> cadastrarTabelaPreco();
                case 10 -> listar("Tabelas de Preço", tabelaDAO.listarTodos());
                case 11 -> cadastrarVendedor();
                case 12 -> listar("Vendedores", vendedorDAO.listarTodos());
                case 13 -> cadastrarConfigComissao();
                case 14 -> listar("Configs. de Comissão", configDAO.listarTodos());
                case 15 -> cadastrarAssociacao();
                case 16 -> listar("Associações", associacaoDAO.listarTodos());
                case 17 -> criarContrato();
                case 18 -> listar("Contratos", contratoDAO.listarTodos());
                case 19 -> cancelarContrato();
                case 0  -> { escritor.exibirAviso("Saindo do sistema..."); executando = false; }
                default -> escritor.exibirErro("Opcao invalida! Tente novamente.");
            }
        }
    }

    // ── Helpers ──────────────────────────────────────────────

    private static void listar(String titulo, List<?> lista) {
        escritor.exibirTitulo("Lista de " + titulo);
        if (lista.isEmpty()) { escritor.exibirAviso("Nenhum registro encontrado."); return; }
        for (Object obj : lista) { escritor.escreverLinha(obj.toString()); escritor.pularLinhas(1); }
    }

    private static Regiao selecionarRegiao() {
        List<Regiao> regioes = regiaoDAO.listarTodos();
        if (regioes.isEmpty()) { escritor.exibirAviso("Nenhuma região cadastrada. Cadastre uma primeiro."); return null; }
        escritor.exibirTitulo("Regiões disponíveis");
        for (Regiao r : regioes) escritor.escreverLinha(r.toString());
        escritor.escrever("ID da Região: ");
        return regiaoDAO.buscarPorId(leitor.lerInteiro());
    }

    private static Operadora selecionarOperadora() {
        List<Operadora> lista = operadoraDAO.listarTodos();
        if (lista.isEmpty()) { escritor.exibirAviso("Nenhuma operadora cadastrada."); return null; }
        escritor.exibirTitulo("Operadoras disponíveis");
        for (Operadora o : lista) escritor.escreverLinha(o.toString());
        escritor.escrever("ID da Operadora: ");
        return operadoraDAO.buscarPorId(leitor.lerInteiro());
    }

    private static Plano selecionarPlano() {
        List<Plano> lista = planoDAO.listarTodos();
        if (lista.isEmpty()) { escritor.exibirAviso("Nenhum plano cadastrado."); return null; }
        escritor.exibirTitulo("Planos disponíveis");
        for (Plano p : lista) escritor.escreverLinha(p.toString());
        escritor.escrever("ID do Plano: ");
        return planoDAO.buscarPorId(leitor.lerInteiro());
    }

    // ── Cadastros ─────────────────────────────────────────────

    private static void cadastrarRegiao() {
        escritor.exibirTitulo("Cadastro de Região");
        escritor.escrever("Nome da região: "); String nome = leitor.lerString();
        escritor.escrever("UF (ex: SC): ");    String uf   = leitor.lerString();
        escritor.escrever("Descrição: ");      String desc = leitor.lerString();
        regiaoDAO.salvar(new Regiao(nome, uf, desc));
        escritor.exibirSucesso("Região cadastrada!");
    }

    private static void cadastrarPessoa() {
        escritor.exibirTitulo("Cadastro de Pessoa");
        Regiao regiao = selecionarRegiao();
        if (regiao == null) return;
        escritor.escrever("Nome: ");         String nome  = leitor.lerString();
        escritor.escrever("CPF/CNPJ: ");     String cpf   = leitor.lerString();
        escritor.escrever("Idade: ");        int    idade = leitor.lerInteiro();
        escritor.escrever("Telefone: ");     String tel   = leitor.lerString();
        escritor.escrever("Email: ");        String email = leitor.lerString();
        escritor.escrever("Tipo (P=Física / J=Jurídica): "); String tipo = leitor.lerString().toUpperCase();
        pessoaDAO.salvar(new Pessoa(nome, cpf, idade, tel, email, tipo, regiao));
        escritor.exibirSucesso("Pessoa cadastrada!");
    }

    private static void cadastrarOperadora() {
        escritor.exibirTitulo("Cadastro de Operadora");
        escritor.escrever("Nome: ");         String nome  = leitor.lerString();
        escritor.escrever("CNPJ: ");         String cnpj  = leitor.lerString();
        escritor.escrever("Registro ANS: "); int    ans   = leitor.lerInteiro();
        escritor.escrever("Telefone: ");     String tel   = leitor.lerString();
        escritor.escrever("Email: ");        String email = leitor.lerString();
        operadoraDAO.salvar(new Operadora(nome, cnpj, ans, tel, email));
        escritor.exibirSucesso("Operadora cadastrada!");
    }

    private static void cadastrarPlano() {
        escritor.exibirTitulo("Cadastro de Plano");
        Operadora op = selecionarOperadora();
        if (op == null) return;
        escritor.escrever("Nome do plano: ");           String nome  = leitor.lerString();
        escritor.escrever("Tipo (M=Médico/O=Odonto): "); String tipo = leitor.lerString().toUpperCase();
        escritor.escrever("Cobertura: ");               String cob   = leitor.lerString();
        escritor.escrever("Acomodação: ");              String acom  = leitor.lerString();
        planoDAO.salvar(new Plano(nome, tipo, cob, acom, op));
        escritor.exibirSucesso("Plano cadastrado!");
    }

    private static void cadastrarTabelaPreco() {
        escritor.exibirTitulo("Cadastro de Tabela de Preço");
        Plano plano = selecionarPlano();
        if (plano == null) return;
        escritor.escrever("Faixa etária (ex: 18-28): "); String faixa = leitor.lerString();
        escritor.escrever("Valor base (R$): ");          float valor  = (float) leitor.lerDouble();
        escritor.escrever("Data início vigência (AAAA-MM-DD): "); String ini = leitor.lerString();
        escritor.escrever("Data fim vigência (AAAA-MM-DD): ");    String fim = leitor.lerString();
        tabelaDAO.salvar(new TabelaPreco(ini, fim, valor, faixa, plano));
        escritor.exibirSucesso("Tabela de preço cadastrada!");
    }

    private static void cadastrarVendedor() {
        escritor.exibirTitulo("Cadastro de Vendedor");
        escritor.escrever("Nome: "); String nome = leitor.lerString();
        vendedorDAO.salvar(new Vendedor(nome));
        escritor.exibirSucesso("Vendedor cadastrado!");
    }

    private static void cadastrarConfigComissao() {
        escritor.exibirTitulo("Cadastro de Config. de Comissão");
        Operadora op = selecionarOperadora();
        if (op == null) return;
        List<Vendedor> vendedores = vendedorDAO.listarTodos();
        if (vendedores.isEmpty()) { escritor.exibirAviso("Nenhum vendedor cadastrado."); return; }
        escritor.exibirTitulo("Vendedores disponíveis");
        for (Vendedor v : vendedores) escritor.escreverLinha(v.toString());
        escritor.escrever("ID do Vendedor: ");
        Vendedor vendedor = vendedorDAO.buscarPorId(leitor.lerInteiro());
        if (vendedor == null) { escritor.exibirErro("Vendedor não encontrado."); return; }
        escritor.escrever("Descrição: ");           String desc = leitor.lerString();
        escritor.escrever("Percentual comissão: "); float perc  = (float) leitor.lerDouble();
        configDAO.salvar(new ConfigComissao(desc, perc, op, vendedor));
        escritor.exibirSucesso("Config. de comissão cadastrada!");
    }

    private static void cadastrarAssociacao() {
        escritor.exibirTitulo("Cadastro de Associação");
        Regiao regiao = selecionarRegiao();
        if (regiao == null) return;
        escritor.escrever("Nome: ");   String nome  = leitor.lerString();
        escritor.escrever("CNPJ: ");   String cnpj  = leitor.lerString();
        escritor.escrever("Telefone: "); String tel = leitor.lerString();
        escritor.escrever("Email: ");  String email = leitor.lerString();
        associacaoDAO.salvar(new Associacao(nome, cnpj, tel, email, regiao));
        escritor.exibirSucesso("Associação cadastrada!");
    }

    private static void criarContrato() {
        escritor.exibirTitulo("Criação de Contrato");

        // Pessoa
        List<Pessoa> pessoas = pessoaDAO.listarTodos();
        if (pessoas.isEmpty()) { escritor.exibirAviso("Cadastre uma pessoa antes."); return; }
        escritor.exibirTitulo("Pessoas disponíveis");
        for (Pessoa p : pessoas) escritor.escreverLinha(p.toString());
        escritor.escrever("ID da Pessoa: ");
        Pessoa pessoa = pessoaDAO.buscarPorId(leitor.lerInteiro());
        if (pessoa == null) { escritor.exibirErro("Pessoa não encontrada."); return; }

        // Config de comissão
        List<ConfigComissao> configs = configDAO.listarTodos();
        if (configs.isEmpty()) { escritor.exibirAviso("Cadastre uma config. de comissão antes."); return; }
        escritor.exibirTitulo("Configs. de Comissão disponíveis");
        for (ConfigComissao c : configs) escritor.escreverLinha(c.toString());
        escritor.escrever("ID da Config. de Comissão: ");
        ConfigComissao config = configDAO.buscarPorId(leitor.lerInteiro());
        if (config == null) { escritor.exibirErro("Config. não encontrada."); return; }

        // Associação
        List<Associacao> assocs = associacaoDAO.listarTodos();
        if (assocs.isEmpty()) { escritor.exibirAviso("Cadastre uma associação antes."); return; }
        escritor.exibirTitulo("Associações disponíveis");
        for (Associacao a : assocs) escritor.escreverLinha(a.toString());
        escritor.escrever("ID da Associação: ");
        Associacao assoc = associacaoDAO.buscarPorId(leitor.lerInteiro());
        if (assoc == null) { escritor.exibirErro("Associação não encontrada."); return; }

        // Tabela de preço
        List<TabelaPreco> tabelas = tabelaDAO.listarTodos();
        if (tabelas.isEmpty()) { escritor.exibirAviso("Cadastre uma tabela de preço antes."); return; }
        escritor.exibirTitulo("Tabelas de Preço disponíveis");
        for (TabelaPreco t : tabelas) escritor.escreverLinha(t.toString());
        escritor.escrever("ID da Tabela de Preço: ");
        TabelaPreco tabela = tabelaDAO.buscarPorId(leitor.lerInteiro());
        if (tabela == null) { escritor.exibirErro("Tabela não encontrada."); return; }

        escritor.escrever("Número do contrato: ");            int    num  = leitor.lerInteiro();
        escritor.escrever("Data início (AAAA-MM-DD): ");      String ini  = leitor.lerString();
        escritor.escrever("Data fim previsto (AAAA-MM-DD): "); String fim = leitor.lerString();

        contratoDAO.salvar(new Contrato(num, ini, fim, pessoa, config, assoc, tabela));
        escritor.exibirSucesso("Contrato criado com sucesso!");
    }

    private static void cancelarContrato() {
        escritor.exibirTitulo("Cancelamento de Contrato");
        List<Contrato> contratos = contratoDAO.listarTodos();
        if (contratos.isEmpty()) { escritor.exibirAviso("Nenhum contrato cadastrado."); return; }
        for (Contrato c : contratos) { escritor.escreverLinha(c.toString()); escritor.pularLinhas(1); }
        escritor.escrever("ID do contrato a cancelar: ");
        int id = leitor.lerInteiro();
        contratoDAO.cancelarContrato(id);
        escritor.exibirSucesso("Contrato cancelado!");
    }
}