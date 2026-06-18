package model;

public class ConfigComissao {
    private int idConfigComissao;
    private String descricaoConfig;
    private float percentualComissao;
    private Operadora operadora;
    private Vendedor vendedor;

    public ConfigComissao(int idConfigComissao, String descricaoConfig,
                          float percentualComissao, Operadora operadora, Vendedor vendedor) {
        this.idConfigComissao = idConfigComissao;
        this.descricaoConfig = descricaoConfig;
        this.percentualComissao = percentualComissao;
        this.operadora = operadora;
        this.vendedor = vendedor;
    }

    public ConfigComissao(String descricaoConfig, float percentualComissao,
                          Operadora operadora, Vendedor vendedor) {
        this(0, descricaoConfig, percentualComissao, operadora, vendedor);
    }

    public int getIdConfigComissao() { return idConfigComissao; }
    public void setIdConfigComissao(int idConfigComissao) { if (idConfigComissao > 0) this.idConfigComissao = idConfigComissao; }

    public String getDescricaoConfig() { return descricaoConfig; }
    public void setDescricaoConfig(String descricaoConfig) { this.descricaoConfig = descricaoConfig; }

    public float getPercentualComissao() { return percentualComissao; }
    public void setPercentualComissao(float percentualComissao) { this.percentualComissao = percentualComissao; }

    public Operadora getOperadora() { return operadora; }
    public void setOperadora(Operadora operadora) { this.operadora = operadora; }

    public Vendedor getVendedor() { return vendedor; }
    public void setVendedor(Vendedor vendedor) { this.vendedor = vendedor; }

    @Override
    public String toString() {
        return "ConfigComissao #" + idConfigComissao
                + " | " + descricaoConfig
                + " | " + percentualComissao + "%"
                + " | Operadora: " + (operadora != null ? operadora.getNomeOperadora() : "—")
                + " | Vendedor: " + (vendedor != null ? vendedor.getNomeVendedor() : "—");
    }
}