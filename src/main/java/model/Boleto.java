package model;

import interfaces.Pagavel;
import java.util.Random;

public class Boleto implements Pagavel {
    private int codigoBoleto;
    private String vencimento;
    private double valor;
    private boolean pago;

    public Boleto(String vencimento, double valor) {
        this.codigoBoleto = gerarCodigo();
        this.vencimento = vencimento;
        this.valor = valor;
        this.pago = false;
    }

    private int gerarCodigo() {
        Random random = new Random();
        return 10000 + random.nextInt(90000);
    }

    public int getCodigoBoleto() {
        return codigoBoleto;
    }

    public String getVencimento() {
        return vencimento;
    }

    public void setVencimento(String vencimento) {
        this.vencimento = vencimento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        if (valor >= 0) {
            this.valor = valor;
        }
    }

    public boolean isPago() {
        return pago;
    }

    @Override
    public void pagar() {
        this.pago = true;
    }

    @Override
    public String toString() {
        return "model.Boleto #" + codigoBoleto +
                " | Vencimento: " + vencimento +
                " | Valor: R$ " + valor +
                " | Pago: " + (pago ? "Sim" : "Não");
    }
}