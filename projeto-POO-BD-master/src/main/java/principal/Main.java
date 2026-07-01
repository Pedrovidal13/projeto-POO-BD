package principal;

import utils.Escritor;

public class Main {
    public static void main(String[] args) {
        Escritor escritor = new Escritor();
        escritor.exibirTitulo("Sistema Administradora de Planos de Saude");
        Menu.main(args);
    }
}