package model;

public class Operadora {
    private int idOperadora;
    private String nomeOperadora;
    private String cnpj;
    private int registroAns;
    private String telefone;
    private String email;

    public Operadora(int idOperadora, String nomeOperadora, String cnpj,
                     int registroAns, String telefone, String email) {
        this.idOperadora = idOperadora;
        this.nomeOperadora = nomeOperadora;
        this.cnpj = cnpj;
        this.registroAns = registroAns;
        this.telefone = telefone;
        this.email = email;
    }

    // Construtor usado nos DAOs que fazem JOIN parcial (sem registroAns/tel/email)
    public Operadora(String nomeOperadora, String cnpj) {
        this(0, nomeOperadora, cnpj, 0, "", "");
    }

    public Operadora(String nomeOperadora, String cnpj, int registroAns,
                     String telefone, String email) {
        this(0, nomeOperadora, cnpj, registroAns, telefone, email);
    }

    public int getIdOperadora() { return idOperadora; }
    public void setIdOperadora(int idOperadora) { if (idOperadora > 0) this.idOperadora = idOperadora; }

    public String getNomeOperadora() { return nomeOperadora; }
    public void setNomeOperadora(String nomeOperadora) { this.nomeOperadora = nomeOperadora; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public int getRegistroAns() { return registroAns; }
    public void setRegistroAns(int registroAns) { this.registroAns = registroAns; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "Operadora #" + idOperadora + " | " + nomeOperadora
                + " | CNPJ: " + cnpj + " | ANS: " + registroAns;
    }
}