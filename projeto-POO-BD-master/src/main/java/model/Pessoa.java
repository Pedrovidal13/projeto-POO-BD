package model;

public class Pessoa {
    private int idPessoa;
    private String nomePessoa;
    private String cpfCnpj;
    private int idade;
    private String telefone;
    private String email;
    private String tipoPessoa; // "J" = Jurídica, "P" = Física
    private Regiao regiao;

    public Pessoa(int idPessoa, String nomePessoa, String cpfCnpj, int idade,
                  String telefone, String email, String tipoPessoa, Regiao regiao) {
        this.idPessoa = idPessoa;
        this.nomePessoa = nomePessoa;
        this.cpfCnpj = cpfCnpj;
        this.idade = idade;
        this.telefone = telefone;
        this.email = email;
        this.tipoPessoa = tipoPessoa;
        this.regiao = regiao;
    }

    public Pessoa(String nomePessoa, String cpfCnpj, int idade,
                  String telefone, String email, String tipoPessoa, Regiao regiao) {
        this(0, nomePessoa, cpfCnpj, idade, telefone, email, tipoPessoa, regiao);
    }

    public Pessoa(String nome, int idade, String cpf, String telefone, String email, String endereco) {
    }

    public int getIdPessoa() { return idPessoa; }
    public void setIdPessoa(int idPessoa) { if (idPessoa > 0) this.idPessoa = idPessoa; }

    public String getNomePessoa() { return nomePessoa; }
    public void setNomePessoa(String nomePessoa) { this.nomePessoa = nomePessoa; }

    public String getCpfCnpj() { return cpfCnpj; }
    public void setCpfCnpj(String cpfCnpj) { this.cpfCnpj = cpfCnpj; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { if (idade >= 0) this.idade = idade; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTipoPessoa() { return tipoPessoa; }
    public void setTipoPessoa(String tipoPessoa) { this.tipoPessoa = tipoPessoa; }

    public Regiao getRegiao() { return regiao; }
    public void setRegiao(Regiao regiao) { this.regiao = regiao; }

    @Override
    public String toString() {
        return "Pessoa #" + idPessoa + " | " + nomePessoa
                + " | CPF/CNPJ: " + cpfCnpj
                + " | Tipo: " + ("P".equals(tipoPessoa) ? "Física" : "Jurídica")
                + " | Região: " + (regiao != null ? regiao.getNomeRegiao() : "—");
    }
}