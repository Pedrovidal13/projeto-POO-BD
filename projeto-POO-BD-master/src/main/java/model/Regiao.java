package model;

public class Regiao {
    private int idRegiao;
    private String nomeRegiao;
    private String uf;
    private String descricaoRegiao;

    public Regiao(int idRegiao, String nomeRegiao, String uf, String descricaoRegiao) {
        this.idRegiao = idRegiao;
        this.nomeRegiao = nomeRegiao;
        this.uf = uf;
        this.descricaoRegiao = descricaoRegiao;
    }

    public Regiao(String nomeRegiao, String uf, String descricaoRegiao) {
        this(0, nomeRegiao, uf, descricaoRegiao);
    }

    public int getIdRegiao() { return idRegiao; }
    public void setIdRegiao(int idRegiao) { if (idRegiao > 0) this.idRegiao = idRegiao; }

    public String getNomeRegiao() { return nomeRegiao; }
    public void setNomeRegiao(String nomeRegiao) { this.nomeRegiao = nomeRegiao; }

    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }

    public String getDescricaoRegiao() { return descricaoRegiao; }
    public void setDescricaoRegiao(String descricaoRegiao) { this.descricaoRegiao = descricaoRegiao; }

    @Override
    public String toString() {
        return "Regiao #" + idRegiao + " | " + nomeRegiao + " - " + uf;
    }
}