package model;

public class Associacao {
    private int idAssociacao;
    private String nomeAssociacao;
    private String cnpj;
    private String telefone;
    private String email;
    private Regiao regiao;

    public Associacao(int idAssociacao, String nomeAssociacao, String cnpj,
                      String telefone, String email, Regiao regiao) {
        this.idAssociacao = idAssociacao;
        this.nomeAssociacao = nomeAssociacao;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.email = email;
        this.regiao = regiao;
    }

    public Associacao(String nomeAssociacao, String cnpj,
                      String telefone, String email, Regiao regiao) {
        this(0, nomeAssociacao, cnpj, telefone, email, regiao);
    }

    public int getIdAssociacao() { return idAssociacao; }
    public void setIdAssociacao(int idAssociacao) { if (idAssociacao > 0) this.idAssociacao = idAssociacao; }

    public String getNomeAssociacao() { return nomeAssociacao; }
    public void setNomeAssociacao(String nomeAssociacao) { this.nomeAssociacao = nomeAssociacao; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Regiao getRegiao() { return regiao; }
    public void setRegiao(Regiao regiao) { this.regiao = regiao; }

    @Override
    public String toString() {
        return "Associacao #" + idAssociacao + " | " + nomeAssociacao
                + " | CNPJ: " + cnpj
                + " | Região: " + (regiao != null ? regiao.getNomeRegiao() : "—");
    }
}