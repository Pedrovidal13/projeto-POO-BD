package model;

public class Cliente extends Pessoa {
    private int codigoCliente;

    public Cliente(int codigoCliente, String nome, int idade, String cpf, String telefone, String email, String endereco) {
        super(nome, idade, cpf, telefone, email, endereco);
        this.codigoCliente = codigoCliente;
    }

    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        if (codigoCliente > 0) {
            this.codigoCliente = codigoCliente;
        }
    }

    @Override
    public String toString() {
        return "model.Cliente #" + codigoCliente + " | " + super.toString();
    }
}