package model;

public class Contrato {
    private int numeroContrato;
    private Cliente cliente;
    private Plano plano;
    private Operadora operadora;
    private Boleto boleto;
    private String status;

    public Contrato(int numeroContrato, Cliente cliente, Plano plano, Operadora operadora, String vencimentoBoleto) {
        this.numeroContrato = numeroContrato;
        this.cliente = cliente;
        this.plano = plano;
        this.operadora = operadora;
        this.boleto = new Boleto(vencimentoBoleto, plano.getValorMensal());
        this.status = "ATIVO";
    }

    public int getNumeroContrato() {
        return numeroContrato;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Plano getPlano() {
        return plano;
    }

    public Operadora getOperadora() {
        return operadora;
    }

    public Boleto getBoleto() {
        return boleto;
    }

    public String getStatus() {
        return status;
    }

    public void cancelarContrato() {
        this.status = "CANCELADO";
    }

    @Override
    public String toString() {
        return "model.Contrato #" + numeroContrato +
                "\nmodel.Cliente: " + cliente.getNome() +
                "\nmodel.Plano: " + plano.getNome() +
                "\nmodel.Operadora: " + operadora.getNome() +
                "\nStatus: " + status +
                "\n" + boleto;
    }
}