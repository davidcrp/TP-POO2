import controller.ControladorJogo;
import controller.GerenciadorUsuarios;
import view.DialogoLogin;
import view.JanelaPrincipal;

import javax.swing.*;
import java.io.IOException;


/*
Matheus Cardoso de Araújo 24.1.4033
Pedro Souza Goularte 24.2.4084
Pedro Henrique Boseja Carolino Barbosa 24.2.4092
David Cristian de Paula Rodrigues 24.2.4094
 */


  //Uso: java Main <caminho_arquivo_palavras>
  //Exemplo: java Main resources/palavras.txt

 
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
     * Inclui tela de login antes de abrir o jogo
     * 
     * @param caminhoArquivo Caminho do arquivo de palavras
     * @throws IOException Se houver erro ao ler o arquivo
     */
    private static void iniciarJogo(String caminhoArquivo) throws IOException {

        System.out.println("=================================");
        System.out.println("       TERMO - Jogo de Palavras");
        System.out.println("       Sistema Multi-Usuários");
        System.out.println("=================================");
        System.out.println("Carregando banco de palavras...");

        // Cria o controlador e carrega as palavras
        ControladorJogo controlador = new ControladorJogo(caminhoArquivo);

        if (!controlador.isInicializado()) {
            throw new IOException("Falha ao inicializar o controlador do jogo.");
        }

        System.out.println("Abrindo tela de login...");

        // Exibe tela de login
        GerenciadorUsuarios gerenciadorUsuarios = controlador.getGerenciadorUsuarios();
        DialogoLogin dialogoLogin = new DialogoLogin(null, gerenciadorUsuarios);
        dialogoLogin.setVisible(true);

        // Verifica se o login foi bem-sucedido
        if (!dialogoLogin.isLoginBemSucedido()) {
            System.out.println("Login cancelado. Encerrando aplicação.");
            return;
        }

        // Login bem-sucedido - exibe informações do jogador
        String nomeJogador = gerenciadorUsuarios.getJogadorLogado().getNome();
        System.out.println("Login bem-sucedido: " + nomeJogador);

        // Exibe estatísticas do jogador
        var estatisticas = gerenciadorUsuarios.getJogadorLogado().getEstatisticas();
        System.out.println("Jogos: " + estatisticas.getTotalPartidas());
        System.out.println("Vitórias: " + estatisticas.getPartidasGanhas());
        System.out.println("Sequência atual: " + estatisticas.getSequenciaAtual());

        System.out.println("Criando interface gráfica...");

        // Cria e exibe a janela principal
        JanelaPrincipal janela = new JanelaPrincipal(controlador);
        janela.setVisible(true);

        System.out.println("Jogo iniciado com sucesso!");
        System.out.println("=================================");

        // Exibe diálogo de boas-vindas personalizado
        int opcao = JOptionPane.showConfirmDialog(
            janela,
            String.format(
                "Bem-vindo de volta, %s!\n\n" +
                "Suas Estatísticas:\n" +
                "• Jogos: %d\n" +
                "• Vitórias: %d (%.0f%%)\n" +
                "• Sequência Atual: %d\n" +
                "• Melhor Sequência: %d\n\n" +
                "Como Jogar:\n" +
                "• Verde: Letra na posição correta\n" +
                "• Amarelo: Letra existe, mas em outra posição\n" +
                "• Cinza: Letra não existe na palavra\n\n" +
                "Deseja iniciar uma nova partida agora?",
                nomeJogador,
                estatisticas.getTotalPartidas(),
                estatisticas.getPartidasGanhas(),
                estatisticas.getPorcentagemVitorias(),
                estatisticas.getSequenciaAtual(),
                estatisticas.getMelhorSequencia()
            ),
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
