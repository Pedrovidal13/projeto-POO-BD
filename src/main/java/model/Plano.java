package model;

public class Plano {
    private int codigo;
    private String nome;
    private String cobertura;
    private double valorMensal;
    private String status;
    private Operadora operadora;

    public Plano(int codigo, String nome, String cobertura, double valorMensal, String status, Operadora operadora) {
        this.codigo = codigo;
        this.nome = nome;
        this.cobertura = cobertura;
        this.valorMensal = valorMensal;
        this.status = status;
        this.operadora = operadora;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        if (codigo > 0) {
            this.codigo = codigo;
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome != null && !nome.isBlank()) {
            this.nome = nome;
        }
    }

    public String getCobertura() {
        return cobertura;
    }

    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }

    public double getValorMensal() {
        return valorMensal;
    }

    public void setValorMensal(double valorMensal) {
        if (valorMensal >= 0) {
            this.valorMensal = valorMensal;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Operadora getOperadora() {
        return operadora;
    }

    public void setOperadora(Operadora operadora) {
        this.operadora = operadora;
    }

    @Override
    public String toString() {
        return "model.Plano #" + codigo +
                " | Nome: " + nome +
                " | Cobertura: " + cobertura +
                " | Valor: R$ " + valorMensal +
                " | Status: " + status +
                " | model.Operadora: " + operadora.getNome();
    }
}