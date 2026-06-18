package model;

public class TabelaPreco {
    private int idTabelaPreco;
    private String dataInicioVigencia;
    private String dataFinalVigencia;
    private float valorBase;
    private String faixaEtaria;
    private Plano plano;

    public TabelaPreco(int idTabelaPreco, String dataInicioVigencia, String dataFinalVigencia,
                       float valorBase, String faixaEtaria, Plano plano) {
        this.idTabelaPreco = idTabelaPreco;
        this.dataInicioVigencia = dataInicioVigencia;
        this.dataFinalVigencia = dataFinalVigencia;
        this.valorBase = valorBase;
        this.faixaEtaria = faixaEtaria;
        this.plano = plano;
    }

    public TabelaPreco(String dataInicioVigencia, String dataFinalVigencia,
                       float valorBase, String faixaEtaria, Plano plano) {
        this(0, dataInicioVigencia, dataFinalVigencia, valorBase, faixaEtaria, plano);
    }

    public int getIdTabelaPreco() { return idTabelaPreco; }
    public void setIdTabelaPreco(int idTabelaPreco) { if (idTabelaPreco > 0) this.idTabelaPreco = idTabelaPreco; }

    public String getDataInicioVigencia() { return dataInicioVigencia; }
    public void setDataInicioVigencia(String dataInicioVigencia) { this.dataInicioVigencia = dataInicioVigencia; }

    public String getDataFinalVigencia() { return dataFinalVigencia; }
    public void setDataFinalVigencia(String dataFinalVigencia) { this.dataFinalVigencia = dataFinalVigencia; }

    public float getValorBase() { return valorBase; }
    public void setValorBase(float valorBase) { if (valorBase >= 0) this.valorBase = valorBase; }

    public String getFaixaEtaria() { return faixaEtaria; }
    public void setFaixaEtaria(String faixaEtaria) { this.faixaEtaria = faixaEtaria; }

    public Plano getPlano() { return plano; }
    public void setPlano(Plano plano) { this.plano = plano; }

    @Override
    public String toString() {
        return "TabelaPreco #" + idTabelaPreco
                + " | Plano: " + (plano != null ? plano.getNomePlano() : "—")
                + " | Faixa: " + faixaEtaria
                + " | Valor: R$ " + String.format("%.2f", valorBase)
                + " | Vigência: " + dataInicioVigencia + " até " + dataFinalVigencia;
    }
}