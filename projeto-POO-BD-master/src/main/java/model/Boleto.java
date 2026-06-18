package model;

public class Boleto {
    private int idBoleto;
    private String numeroBoleto;
    private String dataEmissao;
    private String dataVencimento;
    private double valor;
    private String statusPagamento;
    private int contratoId;

    public Boleto(int idBoleto, String numeroBoleto, String dataEmissao,
                  String dataVencimento, double valor, String statusPagamento, int contratoId) {
        this.idBoleto = idBoleto;
        this.numeroBoleto = numeroBoleto;
        this.dataEmissao = dataEmissao;
        this.dataVencimento = dataVencimento;
        this.valor = valor;
        this.statusPagamento = statusPagamento;
        this.contratoId = contratoId;
    }

    public Boleto(String numeroBoleto, String dataEmissao,
                  String dataVencimento, double valor, String statusPagamento, int contratoId) {
        this(0, numeroBoleto, dataEmissao, dataVencimento, valor, statusPagamento, contratoId);
    }

    public int getIdBoleto() { return idBoleto; }
    public void setIdBoleto(int idBoleto) { if (idBoleto > 0) this.idBoleto = idBoleto; }

    public String getNumeroBoleto() { return numeroBoleto; }
    public void setNumeroBoleto(String numeroBoleto) { this.numeroBoleto = numeroBoleto; }

    public String getDataEmissao() { return dataEmissao; }
    public void setDataEmissao(String dataEmissao) { this.dataEmissao = dataEmissao; }

    public String getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(String dataVencimento) { this.dataVencimento = dataVencimento; }

    public double getValor() { return valor; }
    public void setValor(double valor) { if (valor >= 0) this.valor = valor; }

    public String getStatusPagamento() { return statusPagamento; }
    public void setStatusPagamento(String statusPagamento) { this.statusPagamento = statusPagamento; }

    public int getContratoId() { return contratoId; }
    public void setContratoId(int contratoId) { this.contratoId = contratoId; }

    public void pagar() { this.statusPagamento = "PAGO"; }

    @Override
    public String toString() {
        return "Boleto #" + idBoleto
                + " | Nº: " + numeroBoleto
                + " | Emissão: " + dataEmissao
                + " | Vencimento: " + dataVencimento
                + " | Valor: R$ " + String.format("%.2f", valor)
                + " | Status: " + statusPagamento;
    }

    public boolean isPago() {
        return false;
    }

    public String getVencimento() {
        return "";
    }
}