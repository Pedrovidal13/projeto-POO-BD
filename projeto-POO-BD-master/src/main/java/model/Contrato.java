package model;

public class Contrato {
    private int idContrato;
    private int numeroContrato;
    private String dataInicio;
    private String dataFim;
    private Pessoa pessoa;
    private ConfigComissao configComissao;
    private Associacao associacao;
    private TabelaPreco tabelaPreco;

    public Contrato(int idContrato, int numeroContrato, String dataInicio, String dataFim,
                    Pessoa pessoa, ConfigComissao configComissao,
                    Associacao associacao, TabelaPreco tabelaPreco) {
        this.idContrato = idContrato;
        this.numeroContrato = numeroContrato;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.pessoa = pessoa;
        this.configComissao = configComissao;
        this.associacao = associacao;
        this.tabelaPreco = tabelaPreco;
    }

    public Contrato(int numeroContrato, String dataInicio, String dataFim,
                    Pessoa pessoa, ConfigComissao configComissao,
                    Associacao associacao, TabelaPreco tabelaPreco) {
        this(0, numeroContrato, dataInicio, dataFim, pessoa, configComissao, associacao, tabelaPreco);
    }

    public int getIdContrato() { return idContrato; }
    public void setIdContrato(int idContrato) { if (idContrato > 0) this.idContrato = idContrato; }

    public int getNumeroContrato() { return numeroContrato; }
    public void setNumeroContrato(int numeroContrato) { this.numeroContrato = numeroContrato; }

    public String getDataInicio() { return dataInicio; }
    public void setDataInicio(String dataInicio) { this.dataInicio = dataInicio; }

    public String getDataFim() { return dataFim; }
    public void setDataFim(String dataFim) { this.dataFim = dataFim; }

    public Pessoa getPessoa() { return pessoa; }
    public void setPessoa(Pessoa pessoa) { this.pessoa = pessoa; }

    public ConfigComissao getConfigComissao() { return configComissao; }
    public void setConfigComissao(ConfigComissao configComissao) { this.configComissao = configComissao; }

    public Associacao getAssociacao() { return associacao; }
    public void setAssociacao(Associacao associacao) { this.associacao = associacao; }

    public TabelaPreco getTabelaPreco() { return tabelaPreco; }
    public void setTabelaPreco(TabelaPreco tabelaPreco) { this.tabelaPreco = tabelaPreco; }

    @Override
    public String toString() {
        return "Contrato #" + numeroContrato + " (ID: " + idContrato + ")"
                + "\n  Pessoa:      " + (pessoa != null ? pessoa.getNomePessoa() : "—")
                + "\n  Plano:       " + (tabelaPreco != null && tabelaPreco.getPlano() != null ? tabelaPreco.getPlano().getNomePlano() : "—")
                + "\n  Operadora:   " + (configComissao != null && configComissao.getOperadora() != null ? configComissao.getOperadora().getNomeOperadora() : "—")
                + "\n  Associação:  " + (associacao != null ? associacao.getNomeAssociacao() : "—")
                + "\n  Vendedor:    " + (configComissao != null && configComissao.getVendedor() != null ? configComissao.getVendedor().getNomeVendedor() : "—")
                + "\n  Valor Base:  R$ " + (tabelaPreco != null ? String.format("%.2f", tabelaPreco.getValorBase()) : "—")
                + "\n  Vigência:    " + dataInicio + " até " + dataFim;
    }
}