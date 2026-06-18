package utils;

import java.util.Scanner;

/**
 * Classe utilitária para facilitar a leitura de dados do terminal.
 * Resolve o problema do buffer do Scanner e trata erros de digitação.
 *
 * @author Lucas Bueno
 */
public class Leitor {
    private final Scanner scanner;
    private final Escritor escritor;

    public Leitor() {
        this.scanner = new Scanner(System.in);
        this.escritor = new Escritor();
    }

    /**
     * Tenta ler uma linha do scanner.
     * Centraliza o tratamento de erro de entrada interrompida.
     */
    private String lerLinha() {
        try {
            return scanner.nextLine();
        } catch (Exception e) {
            escritor.exibirErro("Entrada do terminal fechada ou vazia.");
            throw e;
        }
    }

    /**
     * Lê uma linha inteira de texto (String).
     *
     * @return O texto digitado.
     */
    public String lerString() {
        return lerLinha();
    }

    /**
     * Lê um número inteiro (int). Se o usuário digitar algo inválido,
     * o método continuará pedindo até que um valor válido seja inserido.
     *
     * @return O número inteiro digitado.
     */
    public int lerInteiro() {
        while (true) {
            String entrada = lerString().trim();
            if (entrada.isEmpty()) continue;
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                escritor.exibirAviso("Por favor, digite um número inteiro válido (ex: 42).");
            }
        }
    }

    /**
     * Lê um número decimal (double). Se o usuário digitar algo inválido,
     * o método continuará pedindo até que um valor válido seja inserido.
     *
     * @return O número decimal digitado.
     */
    public double lerDouble() {
        while (true) {
            String entrada = lerString().trim();
            if (entrada.isEmpty()) continue;
            try {
                entrada = entrada.replace(',', '.');
                return Double.parseDouble(entrada);
            } catch (NumberFormatException e) {
                escritor.exibirAviso("Por favor, digite um número decimal válido (ex: 3.14 ou 3,14).");
            }
        }
    }

    /**
     * Lê um caractere (char). Pega apenas a primeira letra do que foi digitado.
     * Se o usuário digitar algo inválido, o método continuará pedindo até que
     * um valor válido seja inserido.
     *
     * @return O caractere digitado.
     */
    public char lerCaractere() {
        while (true) {
            String entrada = lerString().trim();
            if (!entrada.isEmpty()) {
                return entrada.charAt(0);
            }
            escritor.exibirAviso("Por favor, digite pelo menos um caractere.");
        }
    }

    /**
     * Lê um valor booleano (sim/s ou nao/n). Se o usuário digitar algo inválido,
     * o método continuará pedindo até que um valor válido seja inserido.
     *
     * @return true se o usuário digitar 'sim' ou 's', false se o usuário digitar 'não' ou 'n'.
     */
    public boolean lerBooleano() {
        while (true) {
            String entrada = lerString().trim().toLowerCase();
            if (entrada.equals("sim") || entrada.equals("s")) return true;
            else if (entrada.equals("nao") || entrada.equals("não") || entrada.equals("n")) return false;
            else escritor.exibirAviso("Por favor, digite sim/nao ou s/n");
        }
    }
}