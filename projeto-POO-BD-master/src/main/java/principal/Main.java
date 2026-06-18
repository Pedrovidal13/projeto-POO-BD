package principal;

import utils.Escritor;

public class Main {
    public static void main(String[] args) {

        Escritor escritor = new Escritor();
        escritor.exibirTitulo("Sistema Administradora de Planos de Saude");

        // Sobe o servidor HTTP em background (interface web no navegador)
        try {
            ApiServidor.iniciar();
        } catch (Exception e) {
            System.out.println("[AVISO] Não foi possível iniciar o servidor web: " + e.getMessage());
            System.out.println("[AVISO] O sistema de menu continuará funcionando normalmente.\n");
        }

        Menu.main(args);
    }
}