package principal;

import dao.ClienteDAO;
import dao.ContratoDAO;
import dao.OperadoraDAO;
import dao.PlanoDAO;
import model.Cliente;
import model.Contrato;
import model.Operadora;
import model.Plano;
import utils.Escritor;
import utils.Leitor;

import java.util.List;

public class Menu {
    private static final Escritor escritor = new Escritor();
    private static final Leitor leitor = new Leitor();

    private static final ClienteDAO clienteDAO = new ClienteDAO();
    private static final PlanoDAO planoDAO = new PlanoDAO();
    private static final ContratoDAO contratoDAO = new ContratoDAO();
    private static final OperadoraDAO operadoraDAO = new OperadoraDAO();

    private static Operadora operadoraPadrao;

    public static void main(String[] args) {
        inicializarOperadora();
        cadastrarPlanosIniciais();
        executarSistema();
    }

    // Garante que a operadora padrão existe no banco antes de qualquer operação
    private static void inicializarOperadora() {
        String cnpjPadrao = "12.345.678/0001-99";
        operadoraPadrao = operadoraDAO.buscarPorCnpj(cnpjPadrao);
        if (operadoraPadrao == null) {
            operadoraPadrao = new Operadora("LevMed", cnpjPadrao);
            operadoraDAO.salvar(operadoraPadrao);
        }
    }

    public static void executarSistema() {
        boolean executando = true;

        while (executando) {
            escritor.exibirTitulo("Administradora de Planos de Saude");
            escritor.escreverLinha("1. Cadastrar cliente");
            escritor.escreverLinha("2. Listar clientes");
            escritor.escreverLinha("3. Cadastrar plano");
            escritor.escreverLinha("4. Listar planos");
            escritor.escreverLinha("5. Criar contrato");
            escritor.escreverLinha("6. Listar contratos");
            escritor.escreverLinha("7. Cancelar contrato");
            escritor.escreverLinha("0. Sair");
            escritor.pularLinhas(1);

            escritor.escrever("Selecione uma opcao: ");
            int opcao = leitor.lerInteiro();

            switch (opcao) {
                case 1 -> cadastrarCliente();
                case 2 -> listarClientes();
                case 3 -> cadastrarPlano();
                case 4 -> listarPlanos();
                case 5 -> criarContrato();
                case 6 -> listarContratos();
                case 7 -> cancelarContrato();
                case 0 -> {
                    escritor.exibirAviso("Saindo do sistema...");
                    executando = false;
                }
                default -> escritor.exibirErro("Opcao invalida! Tente novamente.");
            }
        }
    }

    // Cadastra os planos iniciais no banco apenas se ainda não existirem
    private static void cadastrarPlanosIniciais() {
        List<Plano> planosExistentes = planoDAO.listarTodos();
        if (planosExistentes.isEmpty()) {
            planoDAO.salvar(new Plano(0, "Basico",     "Ambulatorial", 180.0, "ATIVO", operadoraPadrao));
            planoDAO.salvar(new Plano(0, "Enfermaria", "Hospitalar",   250.0, "ATIVO", operadoraPadrao));
            planoDAO.salvar(new Plano(0, "Premium",    "Completo",     420.0, "ATIVO", operadoraPadrao));
        }
    }

    private static void cadastrarCliente() {
        escritor.exibirTitulo("Cadastro de Cliente");

        escritor.escrever("Nome: ");
        String nome = leitor.lerString();

        escritor.escrever("Idade: ");
        int idade = leitor.lerInteiro();

        escritor.escrever("CPF: ");
        String cpf = leitor.lerString();

        escritor.escrever("Telefone: ");
        String telefone = leitor.lerString();

        escritor.escrever("Email: ");
        String email = leitor.lerString();

        escritor.escrever("Endereco: ");
        String endereco = leitor.lerString();

        Cliente cliente = new Cliente(0, nome, idade, cpf, telefone, email, endereco);
        clienteDAO.salvar(cliente);

        escritor.exibirSucesso("Cliente cadastrado com sucesso!");
    }

    private static void listarClientes() {
        escritor.exibirTitulo("Lista de Clientes");

        List<Cliente> clientes = clienteDAO.listarTodos();
        if (clientes.isEmpty()) {
            escritor.exibirAviso("Nenhum cliente cadastrado.");
            return;
        }

        for (Cliente c : clientes) {
            escritor.escreverLinha(c.toString());
        }
    }

    private static void cadastrarPlano() {
        escritor.exibirTitulo("Cadastro de Plano");

        escritor.escrever("Nome do plano: ");
        String nome = leitor.lerString();

        escritor.escrever("Cobertura: ");
        String cobertura = leitor.lerString();

        escritor.escrever("Valor mensal: ");
        double valor = leitor.lerDouble();

        Plano plano = new Plano(0, nome, cobertura, valor, "ATIVO", operadoraPadrao);
        planoDAO.salvar(plano);

        escritor.exibirSucesso("Plano cadastrado com sucesso!");
    }

    private static void listarPlanos() {
        escritor.exibirTitulo("Lista de Planos");

        List<Plano> planos = planoDAO.listarTodos();
        if (planos.isEmpty()) {
            escritor.exibirAviso("Nenhum plano cadastrado.");
            return;
        }

        for (Plano p : planos) {
            escritor.escreverLinha(p.toString());
        }
    }

    private static void criarContrato() {
        escritor.exibirTitulo("Criacao de Contrato");

        List<Cliente> clientes = clienteDAO.listarTodos();
        if (clientes.isEmpty()) {
            escritor.exibirAviso("Cadastre pelo menos um cliente antes.");
            return;
        }

        List<Plano> planos = planoDAO.listarTodos();
        if (planos.isEmpty()) {
            escritor.exibirAviso("Cadastre pelo menos um plano antes.");
            return;
        }

        escritor.exibirTitulo("Clientes disponiveis");
        for (Cliente c : clientes) {
            escritor.escreverLinha(c.toString());
        }
        escritor.escrever("Digite o codigo do cliente: ");
        int codigoCliente = leitor.lerInteiro();

        Cliente clienteSelecionado = clienteDAO.buscarPorCodigo(codigoCliente);
        if (clienteSelecionado == null) {
            escritor.exibirErro("Cliente nao encontrado.");
            return;
        }

        escritor.exibirTitulo("Planos disponiveis");
        for (Plano p : planos) {
            escritor.escreverLinha(p.toString());
        }
        escritor.escrever("Digite o codigo do plano: ");
        int codigoPlano = leitor.lerInteiro();

        Plano planoSelecionado = planoDAO.buscarPorCodigo(codigoPlano);
        if (planoSelecionado == null) {
            escritor.exibirErro("Plano nao encontrado.");
            return;
        }

        escritor.escrever("Digite o vencimento do boleto (ex: 10/05/2026): ");
        String vencimento = leitor.lerString();

        Contrato contrato = new Contrato(0, clienteSelecionado, planoSelecionado, operadoraPadrao, vencimento);
        contratoDAO.salvar(contrato);

        escritor.exibirSucesso("Contrato criado com sucesso!");
    }

    private static void listarContratos() {
        escritor.exibirTitulo("Lista de Contratos");

        List<Contrato> contratos = contratoDAO.listarTodos();
        if (contratos.isEmpty()) {
            escritor.exibirAviso("Nenhum contrato cadastrado.");
            return;
        }

        for (Contrato c : contratos) {
            escritor.escreverLinha(c.toString());
            escritor.pularLinhas(1);
        }
    }

    private static void cancelarContrato() {
        escritor.exibirTitulo("Cancelamento de Contrato");

        List<Contrato> contratos = contratoDAO.listarTodos();
        if (contratos.isEmpty()) {
            escritor.exibirAviso("Nao existem contratos cadastrados.");
            return;
        }

        for (Contrato c : contratos) {
            escritor.escreverLinha(c.toString());
            escritor.pularLinhas(1);
        }

        escritor.escrever("Digite o numero do contrato para cancelar: ");
        int numero = leitor.lerInteiro();

        Contrato contrato = contratoDAO.buscarPorNumero(numero);
        if (contrato == null) {
            escritor.exibirErro("Contrato nao encontrado.");
            return;
        }

        contratoDAO.cancelarContrato(numero);
        escritor.exibirSucesso("Contrato cancelado com sucesso!");
    }
}
