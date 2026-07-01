package principal;

import dao.*;
import model.*;
import utils.Escritor;
import utils.Leitor;

import java.util.List;

public class Menu {
    private static final Escritor escritor = new Escritor();
    private static final Leitor leitor     = new Leitor();

    private static final RegiaoDAO        regiaoDAO    = new RegiaoDAO();
    private static final PessoaDAO        pessoaDAO    = new PessoaDAO();
    private static final OperadoraDAO     operadoraDAO = new OperadoraDAO();
    private static final PlanoDAO         planoDAO     = new PlanoDAO();
    private static final TabelaPrecoDAO   tabelaDAO    = new TabelaPrecoDAO();
    private static final VendedorDAO      vendedorDAO  = new VendedorDAO();
    private static final ConfigComissaoDAO configDAO   = new ConfigComissaoDAO();
    private static final AssociacaoDAO    associacaoDAO = new AssociacaoDAO();
    private static final ContratoDAO      contratoDAO  = new ContratoDAO();

    public static void main(String[] args) {
        executarSistema();
    }

    public static void executarSistema() {
        boolean executando = true;
        while (executando) {
            escritor.exibirTitulo("Administradora de Planos de Saude");
            escritor.escreverLinha("1. Regiões");
            escritor.escreverLinha("2. Pessoas");
            escritor.escreverLinha("3. Operadoras");
            escritor.escreverLinha("4. Planos");
            escritor.escreverLinha("5. Tabelas de Preço");
            escritor.escreverLinha("6. Vendedores");
            escritor.escreverLinha("7. Config. de Comissão");
            escritor.escreverLinha("8. Associações");
            escritor.escreverLinha("9. Contratos");
            escritor.escreverLinha("0. Sair");
            escritor.pularLinhas(1);
            escritor.escrever("Selecione: ");

            switch (leitor.lerInteiro()) {
                case 1 -> menuRegiao();
                case 2 -> menuPessoa();
                case 3 -> menuOperadora();
                case 4 -> menuPlano();
                case 5 -> menuTabelaPreco();
                case 6 -> menuVendedor();
                case 7 -> menuConfigComissao();
                case 8 -> menuAssociacao();
                case 9 -> menuContrato();
                case 0 -> { escritor.exibirAviso("Saindo do sistema..."); executando = false; }
                default -> escritor.exibirErro("Opção inválida!");
            }
        }
    }

    // ══════════════════════════════════════════════════════════
    // MENUS POR ENTIDADE
    // ══════════════════════════════════════════════════════════

    private static void menuRegiao() {
        boolean voltar = false;
        while (!voltar) {
            escritor.exibirTitulo("Regiões");
            escritor.escreverLinha("1. Cadastrar");
            escritor.escreverLinha("2. Listar");
            escritor.escreverLinha("3. Atualizar");
            escritor.escreverLinha("4. Deletar");
            escritor.escreverLinha("0. Voltar");
            escritor.escrever("Selecione: ");

            switch (leitor.lerInteiro()) {
                case 1 -> cadastrarRegiao();
                case 2 -> listar("Regiões", regiaoDAO.listarTodos());
                case 3 -> atualizarRegiao();
                case 4 -> deletarRegiao();
                case 0 -> voltar = true;
                default -> escritor.exibirErro("Opção inválida!");
            }
        }
    }

    private static void menuPessoa() {
        boolean voltar = false;
        while (!voltar) {
            escritor.exibirTitulo("Pessoas");
            escritor.escreverLinha("1. Cadastrar");
            escritor.escreverLinha("2. Listar");
            escritor.escreverLinha("3. Atualizar");
            escritor.escreverLinha("4. Deletar");
            escritor.escreverLinha("0. Voltar");
            escritor.escrever("Selecione: ");

            switch (leitor.lerInteiro()) {
                case 1 -> cadastrarPessoa();
                case 2 -> listar("Pessoas", pessoaDAO.listarTodos());
                case 3 -> atualizarPessoa();
                case 4 -> deletarPessoa();
                case 0 -> voltar = true;
                default -> escritor.exibirErro("Opção inválida!");
            }
        }
    }

    private static void menuOperadora() {
        boolean voltar = false;
        while (!voltar) {
            escritor.exibirTitulo("Operadoras");
            escritor.escreverLinha("1. Cadastrar");
            escritor.escreverLinha("2. Listar");
            escritor.escreverLinha("3. Atualizar");
            escritor.escreverLinha("4. Deletar");
            escritor.escreverLinha("0. Voltar");
            escritor.escrever("Selecione: ");

            switch (leitor.lerInteiro()) {
                case 1 -> cadastrarOperadora();
                case 2 -> listar("Operadoras", operadoraDAO.listarTodos());
                case 3 -> atualizarOperadora();
                case 4 -> deletarOperadora();
                case 0 -> voltar = true;
                default -> escritor.exibirErro("Opção inválida!");
            }
        }
    }

    private static void menuPlano() {
        boolean voltar = false;
        while (!voltar) {
            escritor.exibirTitulo("Planos");
            escritor.escreverLinha("1. Cadastrar");
            escritor.escreverLinha("2. Listar");
            escritor.escreverLinha("3. Atualizar");
            escritor.escreverLinha("4. Deletar");
            escritor.escreverLinha("0. Voltar");
            escritor.escrever("Selecione: ");

            switch (leitor.lerInteiro()) {
                case 1 -> cadastrarPlano();
                case 2 -> listar("Planos", planoDAO.listarTodos());
                case 3 -> atualizarPlano();
                case 4 -> deletarPlano();
                case 0 -> voltar = true;
                default -> escritor.exibirErro("Opção inválida!");
            }
        }
    }

    private static void menuTabelaPreco() {
        boolean voltar = false;
        while (!voltar) {
            escritor.exibirTitulo("Tabelas de Preço");
            escritor.escreverLinha("1. Cadastrar");
            escritor.escreverLinha("2. Listar");
            escritor.escreverLinha("3. Atualizar");
            escritor.escreverLinha("4. Deletar");
            escritor.escreverLinha("0. Voltar");
            escritor.escrever("Selecione: ");

            switch (leitor.lerInteiro()) {
                case 1 -> cadastrarTabelaPreco();
                case 2 -> listar("Tabelas de Preço", tabelaDAO.listarTodos());
                case 3 -> atualizarTabelaPreco();
                case 4 -> deletarTabelaPreco();
                case 0 -> voltar = true;
                default -> escritor.exibirErro("Opção inválida!");
            }
        }
    }

    private static void menuVendedor() {
        boolean voltar = false;
        while (!voltar) {
            escritor.exibirTitulo("Vendedores");
            escritor.escreverLinha("1. Cadastrar");
            escritor.escreverLinha("2. Listar");
            escritor.escreverLinha("3. Atualizar");
            escritor.escreverLinha("4. Deletar");
            escritor.escreverLinha("0. Voltar");
            escritor.escrever("Selecione: ");

            switch (leitor.lerInteiro()) {
                case 1 -> cadastrarVendedor();
                case 2 -> listar("Vendedores", vendedorDAO.listarTodos());
                case 3 -> atualizarVendedor();
                case 4 -> deletarVendedor();
                case 0 -> voltar = true;
                default -> escritor.exibirErro("Opção inválida!");
            }
        }
    }

    private static void menuConfigComissao() {
        boolean voltar = false;
        while (!voltar) {
            escritor.exibirTitulo("Config. de Comissão");
            escritor.escreverLinha("1. Cadastrar");
            escritor.escreverLinha("2. Listar");
            escritor.escreverLinha("3. Atualizar");
            escritor.escreverLinha("4. Deletar");
            escritor.escreverLinha("0. Voltar");
            escritor.escrever("Selecione: ");

            switch (leitor.lerInteiro()) {
                case 1 -> cadastrarConfigComissao();
                case 2 -> listar("Configs. de Comissão", configDAO.listarTodos());
                case 3 -> atualizarConfigComissao();
                case 4 -> deletarConfigComissao();
                case 0 -> voltar = true;
                default -> escritor.exibirErro("Opção inválida!");
            }
        }
    }

    private static void menuAssociacao() {
        boolean voltar = false;
        while (!voltar) {
            escritor.exibirTitulo("Associações");
            escritor.escreverLinha("1. Cadastrar");
            escritor.escreverLinha("2. Listar");
            escritor.escreverLinha("3. Atualizar");
            escritor.escreverLinha("4. Deletar");
            escritor.escreverLinha("0. Voltar");
            escritor.escrever("Selecione: ");

            switch (leitor.lerInteiro()) {
                case 1 -> cadastrarAssociacao();
                case 2 -> listar("Associações", associacaoDAO.listarTodos());
                case 3 -> atualizarAssociacao();
                case 4 -> deletarAssociacao();
                case 0 -> voltar = true;
                default -> escritor.exibirErro("Opção inválida!");
            }
        }
    }

    private static void menuContrato() {
        boolean voltar = false;
        while (!voltar) {
            escritor.exibirTitulo("Contratos");
            escritor.escreverLinha("1. Criar Contrato");
            escritor.escreverLinha("2. Listar Contratos");
            escritor.escreverLinha("3. Cancelar Contrato");
            escritor.escreverLinha("4. Deletar Contrato");
            escritor.escreverLinha("0. Voltar");
            escritor.escrever("Selecione: ");

            switch (leitor.lerInteiro()) {
                case 1 -> criarContrato();
                case 2 -> listar("Contratos", contratoDAO.listarTodos());
                case 3 -> cancelarContrato();
                case 4 -> deletarContrato();
                case 0 -> voltar = true;
                default -> escritor.exibirErro("Opção inválida!");
            }
        }
    }

    // ══════════════════════════════════════════════════════════
    // HELPERS
    // ══════════════════════════════════════════════════════════

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
        Regiao r = regiaoDAO.buscarPorId(leitor.lerInteiro());
        if (r == null) { escritor.exibirErro("Região não encontrada."); }
        return r;
    }

    private static Operadora selecionarOperadora() {
        List<Operadora> lista = operadoraDAO.listarTodos();
        if (lista.isEmpty()) { escritor.exibirAviso("Nenhuma operadora cadastrada."); return null; }
        escritor.exibirTitulo("Operadoras disponíveis");
        for (Operadora o : lista) escritor.escreverLinha(o.toString());
        escritor.escrever("ID da Operadora: ");
        Operadora o = operadoraDAO.buscarPorId(leitor.lerInteiro());
        if (o == null) { escritor.exibirErro("Operadora não encontrada."); }
        return o;
    }

    private static Plano selecionarPlano() {
        List<Plano> lista = planoDAO.listarTodos();
        if (lista.isEmpty()) { escritor.exibirAviso("Nenhum plano cadastrado."); return null; }
        escritor.exibirTitulo("Planos disponíveis");
        for (Plano p : lista) escritor.escreverLinha(p.toString());
        escritor.escrever("ID do Plano: ");
        Plano p = planoDAO.buscarPorId(leitor.lerInteiro());
        if (p == null) { escritor.exibirErro("Plano não encontrado."); }
        return p;
    }

    // ══════════════════════════════════════════════════════════
    // CADASTROS
    // ══════════════════════════════════════════════════════════

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
        escritor.escrever("Nome do plano: ");            String nome = leitor.lerString();
        escritor.escrever("Tipo (M=Médico / O=Odonto): "); String tipo = leitor.lerString().toUpperCase();
        escritor.escrever("Cobertura: ");                String cob  = leitor.lerString();
        escritor.escrever("Acomodação: ");               String acom = leitor.lerString();
        planoDAO.salvar(new Plano(nome, tipo, cob, acom, op));
        escritor.exibirSucesso("Plano cadastrado!");
    }

    private static void cadastrarTabelaPreco() {
        escritor.exibirTitulo("Cadastro de Tabela de Preço");
        Plano plano = selecionarPlano();
        if (plano == null) return;
        escritor.escrever("Faixa etária (ex: 18-28): ");         String faixa = leitor.lerString();
        escritor.escrever("Valor base (R$): ");                   float  valor = (float) leitor.lerDouble();
        escritor.escrever("Data início vigência (AAAA-MM-DD): "); String ini   = leitor.lerString();
        escritor.escrever("Data fim vigência (AAAA-MM-DD): ");    String fim   = leitor.lerString();
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
        escritor.escrever("Percentual comissão: "); float  perc = (float) leitor.lerDouble();
        configDAO.salvar(new ConfigComissao(desc, perc, op, vendedor));
        escritor.exibirSucesso("Config. de comissão cadastrada!");
    }

    private static void cadastrarAssociacao() {
        escritor.exibirTitulo("Cadastro de Associação");
        Regiao regiao = selecionarRegiao();
        if (regiao == null) return;
        escritor.escrever("Nome: ");     String nome  = leitor.lerString();
        escritor.escrever("CNPJ: ");     String cnpj  = leitor.lerString();
        escritor.escrever("Telefone: "); String tel   = leitor.lerString();
        escritor.escrever("Email: ");    String email = leitor.lerString();
        associacaoDAO.salvar(new Associacao(nome, cnpj, tel, email, regiao));
        escritor.exibirSucesso("Associação cadastrada!");
    }

    // ══════════════════════════════════════════════════════════
    // ATUALIZAÇÕES
    // ══════════════════════════════════════════════════════════

    private static void atualizarRegiao() {
        escritor.exibirTitulo("Atualizar Região");
        listar("Regiões", regiaoDAO.listarTodos());
        escritor.escrever("ID da região a atualizar: ");
        Regiao r = regiaoDAO.buscarPorId(leitor.lerInteiro());
        if (r == null) { escritor.exibirErro("Região não encontrada."); return; }
        escritor.escrever("Novo nome [" + r.getNomeRegiao() + "]: ");    String nome = leitor.lerString();
        escritor.escrever("Nova UF [" + r.getUf() + "]: ");              String uf   = leitor.lerString();
        escritor.escrever("Nova descrição [" + r.getDescricaoRegiao() + "]: "); String desc = leitor.lerString();
        r.setNomeRegiao(nome);
        r.setUf(uf);
        r.setDescricaoRegiao(desc);
        regiaoDAO.atualizar(r);
        escritor.exibirSucesso("Região atualizada!");
    }

    private static void atualizarPessoa() {
        escritor.exibirTitulo("Atualizar Pessoa");
        listar("Pessoas", pessoaDAO.listarTodos());
        escritor.escrever("ID da pessoa a atualizar: ");
        Pessoa p = pessoaDAO.buscarPorId(leitor.lerInteiro());
        if (p == null) { escritor.exibirErro("Pessoa não encontrada."); return; }
        escritor.escrever("Novo nome [" + p.getNomePessoa() + "]: ");     String nome  = leitor.lerString();
        escritor.escrever("Novo CPF/CNPJ [" + p.getCpfCnpj() + "]: ");   String cpf   = leitor.lerString();
        escritor.escrever("Nova idade [" + p.getIdade() + "]: ");         int    idade = leitor.lerInteiro();
        escritor.escrever("Novo telefone [" + p.getTelefone() + "]: ");   String tel   = leitor.lerString();
        escritor.escrever("Novo email [" + p.getEmail() + "]: ");         String email = leitor.lerString();
        escritor.escrever("Novo tipo (P/J) [" + p.getTipoPessoa() + "]: "); String tipo = leitor.lerString().toUpperCase();
        Regiao regiao = selecionarRegiao();
        if (regiao == null) return;
        p.setNomePessoa(nome);
        p.setCpfCnpj(cpf);
        p.setIdade(idade);
        p.setTelefone(tel);
        p.setEmail(email);
        p.setTipoPessoa(tipo);
        p.setRegiao(regiao);
        pessoaDAO.atualizar(p);
        escritor.exibirSucesso("Pessoa atualizada!");
    }

    private static void atualizarOperadora() {
        escritor.exibirTitulo("Atualizar Operadora");
        listar("Operadoras", operadoraDAO.listarTodos());
        escritor.escrever("ID da operadora a atualizar: ");
        Operadora op = operadoraDAO.buscarPorId(leitor.lerInteiro());
        if (op == null) { escritor.exibirErro("Operadora não encontrada."); return; }
        escritor.escrever("Novo nome [" + op.getNomeOperadora() + "]: ");   String nome  = leitor.lerString();
        escritor.escrever("Novo CNPJ [" + op.getCnpj() + "]: ");            String cnpj  = leitor.lerString();
        escritor.escrever("Novo Registro ANS [" + op.getRegistroAns() + "]: "); int ans  = leitor.lerInteiro();
        escritor.escrever("Novo telefone [" + op.getTelefone() + "]: ");    String tel   = leitor.lerString();
        escritor.escrever("Novo email [" + op.getEmail() + "]: ");          String email = leitor.lerString();
        op.setNomeOperadora(nome);
        op.setCnpj(cnpj);
        op.setRegistroAns(ans);
        op.setTelefone(tel);
        op.setEmail(email);
        operadoraDAO.atualizar(op);
        escritor.exibirSucesso("Operadora atualizada!");
    }

    private static void atualizarPlano() {
        escritor.exibirTitulo("Atualizar Plano");
        listar("Planos", planoDAO.listarTodos());
        escritor.escrever("ID do plano a atualizar: ");
        Plano pl = planoDAO.buscarPorId(leitor.lerInteiro());
        if (pl == null) { escritor.exibirErro("Plano não encontrado."); return; }
        escritor.escrever("Novo nome [" + pl.getNomePlano() + "]: ");       String nome = leitor.lerString();
        escritor.escrever("Novo tipo (M/O) [" + pl.getTipoPlano() + "]: "); String tipo = leitor.lerString().toUpperCase();
        escritor.escrever("Nova cobertura [" + pl.getCobertura() + "]: ");  String cob  = leitor.lerString();
        escritor.escrever("Nova acomodação [" + pl.getAcomodacao() + "]: "); String acom = leitor.lerString();
        Operadora op = selecionarOperadora();
        if (op == null) return;
        pl.setNomePlano(nome);
        pl.setTipoPlano(tipo);
        pl.setCobertura(cob);
        pl.setAcomodacao(acom);
        pl.setOperadora(op);
        planoDAO.atualizar(pl);
        escritor.exibirSucesso("Plano atualizado!");
    }

    private static void atualizarTabelaPreco() {
        escritor.exibirTitulo("Atualizar Tabela de Preço");
        listar("Tabelas de Preço", tabelaDAO.listarTodos());
        escritor.escrever("ID da tabela a atualizar: ");
        TabelaPreco tp = tabelaDAO.buscarPorId(leitor.lerInteiro());
        if (tp == null) { escritor.exibirErro("Tabela não encontrada."); return; }
        escritor.escrever("Nova faixa etária [" + tp.getFaixaEtaria() + "]: ");           String faixa = leitor.lerString();
        escritor.escrever("Novo valor base [" + tp.getValorBase() + "]: ");               float  valor = (float) leitor.lerDouble();
        escritor.escrever("Nova data início vigência (AAAA-MM-DD) [" + tp.getDataInicioVigencia() + "]: "); String ini = leitor.lerString();
        escritor.escrever("Nova data fim vigência (AAAA-MM-DD) [" + tp.getDataFinalVigencia() + "]: ");     String fim = leitor.lerString();
        Plano plano = selecionarPlano();
        if (plano == null) return;
        tp.setFaixaEtaria(faixa);
        tp.setValorBase(valor);
        tp.setDataInicioVigencia(ini);
        tp.setDataFinalVigencia(fim);
        tp.setPlano(plano);
        tabelaDAO.atualizar(tp);
        escritor.exibirSucesso("Tabela de preço atualizada!");
    }

    private static void atualizarVendedor() {
        escritor.exibirTitulo("Atualizar Vendedor");
        listar("Vendedores", vendedorDAO.listarTodos());
        escritor.escrever("ID do vendedor a atualizar: ");
        Vendedor v = vendedorDAO.buscarPorId(leitor.lerInteiro());
        if (v == null) { escritor.exibirErro("Vendedor não encontrado."); return; }
        escritor.escrever("Novo nome [" + v.getNomeVendedor() + "]: "); String nome = leitor.lerString();
        v.setNomeVendedor(nome);
        vendedorDAO.atualizar(v);
        escritor.exibirSucesso("Vendedor atualizado!");
    }

    private static void atualizarConfigComissao() {
        escritor.exibirTitulo("Atualizar Config. de Comissão");
        listar("Configs. de Comissão", configDAO.listarTodos());
        escritor.escrever("ID da config. a atualizar: ");
        ConfigComissao c = configDAO.buscarPorId(leitor.lerInteiro());
        if (c == null) { escritor.exibirErro("Config. não encontrada."); return; }
        escritor.escrever("Nova descrição [" + c.getDescricaoConfig() + "]: ");       String desc = leitor.lerString();
        escritor.escrever("Novo percentual [" + c.getPercentualComissao() + "]: ");   float  perc = (float) leitor.lerDouble();
        Operadora op = selecionarOperadora();
        if (op == null) return;
        List<Vendedor> vendedores = vendedorDAO.listarTodos();
        escritor.exibirTitulo("Vendedores disponíveis");
        for (Vendedor v : vendedores) escritor.escreverLinha(v.toString());
        escritor.escrever("ID do Vendedor: ");
        Vendedor vendedor = vendedorDAO.buscarPorId(leitor.lerInteiro());
        if (vendedor == null) { escritor.exibirErro("Vendedor não encontrado."); return; }
        c.setDescricaoConfig(desc);
        c.setPercentualComissao(perc);
        c.setOperadora(op);
        c.setVendedor(vendedor);
        configDAO.atualizar(c);
        escritor.exibirSucesso("Config. de comissão atualizada!");
    }

    private static void atualizarAssociacao() {
        escritor.exibirTitulo("Atualizar Associação");
        listar("Associações", associacaoDAO.listarTodos());
        escritor.escrever("ID da associação a atualizar: ");
        Associacao a = associacaoDAO.buscarPorId(leitor.lerInteiro());
        if (a == null) { escritor.exibirErro("Associação não encontrada."); return; }
        escritor.escrever("Novo nome [" + a.getNomeAssociacao() + "]: ");     String nome  = leitor.lerString();
        escritor.escrever("Novo CNPJ [" + a.getCnpj() + "]: ");              String cnpj  = leitor.lerString();
        escritor.escrever("Novo telefone [" + a.getTelefone() + "]: ");       String tel   = leitor.lerString();
        escritor.escrever("Novo email [" + a.getEmail() + "]: ");             String email = leitor.lerString();
        Regiao regiao = selecionarRegiao();
        if (regiao == null) return;
        a.setNomeAssociacao(nome);
        a.setCnpj(cnpj);
        a.setTelefone(tel);
        a.setEmail(email);
        a.setRegiao(regiao);
        associacaoDAO.atualizar(a);
        escritor.exibirSucesso("Associação atualizada!");
    }

    // ══════════════════════════════════════════════════════════
    // DELEÇÕES
    // ══════════════════════════════════════════════════════════

    private static void deletarRegiao() {
        escritor.exibirTitulo("Deletar Região");
        listar("Regiões", regiaoDAO.listarTodos());
        escritor.escrever("ID da região a deletar: ");
        regiaoDAO.excluir(leitor.lerInteiro());
        escritor.exibirSucesso("Região deletada!");
    }

    private static void deletarPessoa() {
        escritor.exibirTitulo("Deletar Pessoa");
        listar("Pessoas", pessoaDAO.listarTodos());
        escritor.escrever("ID da pessoa a deletar: ");
        pessoaDAO.excluir(leitor.lerInteiro());
        escritor.exibirSucesso("Pessoa deletada!");
    }

    private static void deletarOperadora() {
        escritor.exibirTitulo("Deletar Operadora");
        listar("Operadoras", operadoraDAO.listarTodos());
        escritor.escrever("ID da operadora a deletar: ");
        operadoraDAO.excluir(leitor.lerInteiro());
        escritor.exibirSucesso("Operadora deletada!");
    }

    private static void deletarPlano() {
        escritor.exibirTitulo("Deletar Plano");
        listar("Planos", planoDAO.listarTodos());
        escritor.escrever("ID do plano a deletar: ");
        planoDAO.excluir(leitor.lerInteiro());
        escritor.exibirSucesso("Plano deletado!");
    }

    private static void deletarTabelaPreco() {
        escritor.exibirTitulo("Deletar Tabela de Preço");
        listar("Tabelas de Preço", tabelaDAO.listarTodos());
        escritor.escrever("ID da tabela a deletar: ");
        tabelaDAO.excluir(leitor.lerInteiro());
        escritor.exibirSucesso("Tabela deletada!");
    }

    private static void deletarVendedor() {
        escritor.exibirTitulo("Deletar Vendedor");
        listar("Vendedores", vendedorDAO.listarTodos());
        escritor.escrever("ID do vendedor a deletar: ");
        vendedorDAO.excluir(leitor.lerInteiro());
        escritor.exibirSucesso("Vendedor deletado!");
    }

    private static void deletarConfigComissao() {
        escritor.exibirTitulo("Deletar Config. de Comissão");
        listar("Configs. de Comissão", configDAO.listarTodos());
        escritor.escrever("ID da config. a deletar: ");
        configDAO.excluir(leitor.lerInteiro());
        escritor.exibirSucesso("Config. deletada!");
    }

    private static void deletarAssociacao() {
        escritor.exibirTitulo("Deletar Associação");
        listar("Associações", associacaoDAO.listarTodos());
        escritor.escrever("ID da associação a deletar: ");
        associacaoDAO.excluir(leitor.lerInteiro());
        escritor.exibirSucesso("Associação deletada!");
    }

    private static void deletarContrato() {
        escritor.exibirTitulo("Deletar Contrato");
        listar("Contratos", contratoDAO.listarTodos());
        escritor.escrever("ID do contrato a deletar: ");
        contratoDAO.excluir(leitor.lerInteiro());
        escritor.exibirSucesso("Contrato deletado!");
    }

    // ══════════════════════════════════════════════════════════
    // CONTRATOS
    // ══════════════════════════════════════════════════════════

    private static void criarContrato() {
        escritor.exibirTitulo("Criação de Contrato");

        List<Pessoa> pessoas = pessoaDAO.listarTodos();
        if (pessoas.isEmpty()) { escritor.exibirAviso("Cadastre uma pessoa antes."); return; }
        escritor.exibirTitulo("Pessoas disponíveis");
        for (Pessoa p : pessoas) escritor.escreverLinha(p.toString());
        escritor.escrever("ID da Pessoa: ");
        Pessoa pessoa = pessoaDAO.buscarPorId(leitor.lerInteiro());
        if (pessoa == null) { escritor.exibirErro("Pessoa não encontrada."); return; }

        List<ConfigComissao> configs = configDAO.listarTodos();
        if (configs.isEmpty()) { escritor.exibirAviso("Cadastre uma config. de comissão antes."); return; }
        escritor.exibirTitulo("Configs. de Comissão disponíveis");
        for (ConfigComissao c : configs) escritor.escreverLinha(c.toString());
        escritor.escrever("ID da Config. de Comissão: ");
        ConfigComissao config = configDAO.buscarPorId(leitor.lerInteiro());
        if (config == null) { escritor.exibirErro("Config. não encontrada."); return; }

        List<Associacao> assocs = associacaoDAO.listarTodos();
        if (assocs.isEmpty()) { escritor.exibirAviso("Cadastre uma associação antes."); return; }
        escritor.exibirTitulo("Associações disponíveis");
        for (Associacao a : assocs) escritor.escreverLinha(a.toString());
        escritor.escrever("ID da Associação: ");
        Associacao assoc = associacaoDAO.buscarPorId(leitor.lerInteiro());
        if (assoc == null) { escritor.exibirErro("Associação não encontrada."); return; }

        List<TabelaPreco> tabelas = tabelaDAO.listarTodos();
        if (tabelas.isEmpty()) { escritor.exibirAviso("Cadastre uma tabela de preço antes."); return; }
        escritor.exibirTitulo("Tabelas de Preço disponíveis");
        for (TabelaPreco t : tabelas) escritor.escreverLinha(t.toString());
        escritor.escrever("ID da Tabela de Preço: ");
        TabelaPreco tabela = tabelaDAO.buscarPorId(leitor.lerInteiro());
        if (tabela == null) { escritor.exibirErro("Tabela não encontrada."); return; }

        escritor.escrever("Número do contrato: ");             int    num = leitor.lerInteiro();
        escritor.escrever("Data início (AAAA-MM-DD): ");       String ini = leitor.lerString();
        escritor.escrever("Data fim previsto (AAAA-MM-DD): "); String fim = leitor.lerString();

        contratoDAO.salvar(new Contrato(num, ini, fim, pessoa, config, assoc, tabela));
        escritor.exibirSucesso("Contrato criado com sucesso!");
    }

    private static void cancelarContrato() {
        escritor.exibirTitulo("Cancelamento de Contrato");
        listar("Contratos", contratoDAO.listarTodos());
        escritor.escrever("ID do contrato a cancelar: ");
        contratoDAO.cancelarContrato(leitor.lerInteiro());
        escritor.exibirSucesso("Contrato cancelado!");
    }
}