package utils;

/**
 * Classe utilitária para facilitar a exibição de dados no terminal.
 * Ajuda os alunos a criarem interfaces de texto mais organizadas e bonitas.
 * @author Lucas Bueno
 */
public class Escritor {

    /**
     * Escreve um texto no terminal e pula para a próxima linha.
     * Equivalente ao System.out.println().
     * @param texto O texto a ser exibido.
     */
    public void escreverLinha(String texto) {
        System.out.println(texto);
    }

    /**
     * Escreve um texto no terminal, mas continua na mesma linha.
     * Equivalente ao System.out.print().
     * @param texto O texto a ser exibido.
     */
    public void escrever(String texto) {
        System.out.print(texto);
    }

    /**
     * Pula uma ou mais linhas em branco no terminal.
     * @param quantidade O número de linhas em branco para pular.
     */
    public void pularLinhas(int quantidade) {
        for (int linha = 0; linha < quantidade; linha++) {
            System.out.println();
        }
    }

    /**
     * Exibe um título formatado com linhas em cima e embaixo para destaque.
     * @param texto O título a ser exibido.
     */
    public void exibirTitulo(String texto) {
        String divisoria = "========================================";
        System.out.println("\n" + divisoria);
        System.out.println("  " + texto.toUpperCase());
        System.out.println(divisoria);
    }

    /**
     * Exibe uma mensagem de erro padronizada.
     * @param mensagem A mensagem de erro.
     */
    public void exibirErro(String mensagem) {
        System.err.println("❌ [ERRO]: " + mensagem);
    }

    /**
     * Exibe uma mensagem de sucesso padronizada.
     * @param mensagem A mensagem de sucesso.
     */
    public void exibirSucesso(String mensagem) {
        System.out.println("✅ [SUCESSO]: " + mensagem);
    }

    /**
     * Exibe uma mensagem de aviso padronizada.
     * @param mensagem A mensagem de aviso.
     */
    public void exibirAviso(String mensagem) {
        System.out.println("⚠️ [AVISO]: " + mensagem);
    }

    /**
     * Um "truque" simples para limpar a tela do terminal exibindo várias linhas em branco.
     * (A limpeza real de terminal varia muito entre sistemas operacionais, 
     * então esta é a abordagem mais segura).
     */
    public void limparTela() {
        pularLinhas(50);
    }
}