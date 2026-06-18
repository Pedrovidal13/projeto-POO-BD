package model;

public class Vendedor {
    private int idVendedor;
    private String nomeVendedor;

    public Vendedor(int idVendedor, String nomeVendedor) {
        this.idVendedor = idVendedor;
        this.nomeVendedor = nomeVendedor;
    }

    public Vendedor(String nomeVendedor) {
        this(0, nomeVendedor);
    }

    public int getIdVendedor() { return idVendedor; }
    public void setIdVendedor(int idVendedor) { if (idVendedor > 0) this.idVendedor = idVendedor; }

    public String getNomeVendedor() { return nomeVendedor; }
    public void setNomeVendedor(String nomeVendedor) { this.nomeVendedor = nomeVendedor; }

    @Override
    public String toString() {
        return "Vendedor #" + idVendedor + " | " + nomeVendedor;
    }
}