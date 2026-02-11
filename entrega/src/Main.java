import controller.ControladorJogo;
import view.JanelaPrincipal;

import javax.swing.*;
import java.io.IOException;

/**
 * Classe Main - Ponto de entrada do jogo TERMO
 * 
 * Esta classe contém o método main que inicia o jogo.
 * Recebe o caminho do arquivo de palavras como argumento de linha de comando.
 * 
 * Uso: java Main <caminho_arquivo_palavras>
 * Exemplo: java Main resources/palavras.txt
 * 
 * @author [Seu Nome]
 * @version 1.0
 */
public class Main {

    /**
     * Método principal - ponto de entrada do programa
     * 
     * @param args Argumentos de linha de comando
     *             args[0] deve conter o caminho do arquivo de palavras
     */
    public static void main(String[] args) {

        // Verifica se o argumento foi fornecido
        if (args.length < 1) {
            System.err.println("Erro: Caminho do arquivo de palavras não informado!");
            System.err.println("Uso: java Main <caminho_arquivo_palavras>");
            System.err.println("Exemplo: java Main resources/palavras.txt");
            return;
        }

        String caminhoArquivo = args[0];

        // Configura Look and Feel para melhor aparência
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Usando Look and Feel padrão.");
        }

        // Executa na thread de eventos do Swing
        SwingUtilities.invokeLater(() -> {
            try {
                iniciarJogo(caminhoArquivo);
            } catch (IOException e) {
                exibirErro("Erro ao carregar o banco de palavras:\n" + e.getMessage());
            } catch (Exception e) {
                exibirErro("Erro inesperado:\n" + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    /**
     * Inicializa o jogo criando o controlador e a interface
     * 
     * @param caminhoArquivo Caminho do arquivo de palavras
     * @throws IOException Se houver erro ao ler o arquivo
     */
    private static void iniciarJogo(String caminhoArquivo) throws IOException {

        System.out.println("=================================");
        System.out.println("       TERMO - Jogo de Palavras");
        System.out.println("=================================");
        System.out.println("Carregando banco de palavras...");

        // Cria o controlador e carrega as palavras
        ControladorJogo controlador = new ControladorJogo(caminhoArquivo);

        if (!controlador.isInicializado()) {
            throw new IOException("Falha ao inicializar o controlador do jogo.");
        }

        System.out.println("Criando interface gráfica...");

        // Cria e exibe a janela principal
        JanelaPrincipal janela = new JanelaPrincipal(controlador);
        janela.setVisible(true);

        System.out.println("Jogo iniciado com sucesso!");
        System.out.println("=================================");

        // Exibe diálogo inicial
        int opcao = JOptionPane.showConfirmDialog(
            janela,
            "Bem-vindo ao TERMO!\n\n" +
            "Objetivo: Adivinhar a palavra secreta de 5 letras em até 6 tentativas.\n\n" +
            "Cores:\n" +
            "• Verde: Letra na posição correta\n" +
            "• Amarelo: Letra existe, mas em outra posição\n" +
            "• Cinza: Letra não existe na palavra\n\n" +
            "Deseja iniciar uma partida agora?",
            "Bem-vindo ao TERMO!",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE
        );

        if (opcao == JOptionPane.YES_OPTION) {
            controlador.iniciarNovaPartida();
        }
    }

    /**
     * Exibe uma mensagem de erro em diálogo
     * 
     * @param mensagem Mensagem de erro
     */
    private static void exibirErro(String mensagem) {
        System.err.println(mensagem);
        JOptionPane.showMessageDialog(
            null,
            mensagem,
            "Erro",
            JOptionPane.ERROR_MESSAGE
        );
    }
}
