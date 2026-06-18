package model;

public class Plano {
    private int idPlano;
    private String nomePlano;
    private String tipoPlano; // "O" = Odonto, "M" = Médico
    private String cobertura;
    private String acomodacao;
    private Operadora operadora;

    public Plano(int idPlano, String nomePlano, String tipoPlano,
                 String cobertura, String acomodacao, Operadora operadora) {
        this.idPlano = idPlano;
        this.nomePlano = nomePlano;
        this.tipoPlano = tipoPlano;
        this.cobertura = cobertura;
        this.acomodacao = acomodacao;
        this.operadora = operadora;
    }

    public Plano(String nomePlano, String tipoPlano, String cobertura,
                 String acomodacao, Operadora operadora) {
        this(0, nomePlano, tipoPlano, cobertura, acomodacao, operadora);
    }

    public int getIdPlano() { return idPlano; }
    public void setIdPlano(int idPlano) { if (idPlano > 0) this.idPlano = idPlano; }

    public String getNomePlano() { return nomePlano; }
    public void setNomePlano(String nomePlano) { this.nomePlano = nomePlano; }

    public String getTipoPlano() { return tipoPlano; }
    public void setTipoPlano(String tipoPlano) { this.tipoPlano = tipoPlano; }

    public String getCobertura() { return cobertura; }
    public void setCobertura(String cobertura) { this.cobertura = cobertura; }

    public String getAcomodacao() { return acomodacao; }
    public void setAcomodacao(String acomodacao) { this.acomodacao = acomodacao; }

    public Operadora getOperadora() { return operadora; }
    public void setOperadora(Operadora operadora) { this.operadora = operadora; }

    @Override
    public String toString() {
        return "Plano #" + idPlano + " | " + nomePlano
                + " | Tipo: " + ("M".equals(tipoPlano) ? "Médico" : "Odonto")
                + " | Cobertura: " + cobertura
                + " | Acomodação: " + acomodacao
                + " | Operadora: " + (operadora != null ? operadora.getNomeOperadora() : "—");
    }
}