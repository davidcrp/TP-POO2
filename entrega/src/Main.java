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

// classe principal do programa
// ponto de entrada da aplicacao
public class Main {

    // metodo main - onde o programa comeca
    public static void main(String[] args) {

        // verifica se o caminho do arquivo foi informado
        if (args.length < 1) {

            System.err.println("Erro: Caminho do arquivo de palavras não informado!");
            System.err.println("Uso: java Main <caminho_arquivo_palavras>");
            System.err.println("Exemplo: java Main resources/palavras.txt");

            return; // encerra o programa
        }

        // pega o caminho do arquivo passado por argumento
        String caminhoArquivo = args[0];

        // define o visual da interface com o estilo do sistema operacional
        try {
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception e) {
            System.out.println("Usando Look and Feel padrão.");
        }

        // executa a interface na thread correta do swing
        SwingUtilities.invokeLater(() -> {
            try {
                iniciarJogo(caminhoArquivo);
            } catch (IOException e) {
                exibirErro("Erro ao carregar o banco de palavras:\n" 
                        + e.getMessage());
            } catch (Exception e) {
                exibirErro("Erro inesperado:\n" 
                        + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    // metodo responsavel por inicializar o jogo
    private static void iniciarJogo(String caminhoArquivo) 
            throws IOException {

        // mensagens no console
        System.out.println("=================================");
        System.out.println("       TERMO - Jogo de Palavras");
        System.out.println("       Sistema Multi-Usuários");
        System.out.println("=================================");
        System.out.println("Carregando banco de palavras...");

        // cria o controlador e carrega palavras
        ControladorJogo controlador =
                new ControladorJogo(caminhoArquivo);

        // verifica se foi inicializado corretamente
        if (!controlador.isInicializado()) {
            throw new IOException(
                    "Falha ao inicializar o controlador do jogo.");
        }

        System.out.println("Abrindo tela de login...");

        // obtém o gerenciador de usuarios
        GerenciadorUsuarios gerenciadorUsuarios =
                controlador.getGerenciadorUsuarios();

        // abre dialogo de login
        DialogoLogin dialogoLogin =
                new DialogoLogin(null, gerenciadorUsuarios);

        dialogoLogin.setVisible(true);

        // verifica se login foi cancelado
        if (!dialogoLogin.isLoginBemSucedido()) {
            System.out.println(
                    "Login cancelado. Encerrando aplicação.");
            return;
        }

        // login realizado com sucesso
        String nomeJogador =
                gerenciadorUsuarios
                        .getJogadorLogado()
                        .getNome();

        System.out.println("Login bem-sucedido: " 
                + nomeJogador);

        // pega estatisticas do jogador
        var estatisticas =
                gerenciadorUsuarios
                        .getJogadorLogado()
                        .getEstatisticas();

        // imprime estatisticas no console
        System.out.println("Jogos: " 
                + estatisticas.getTotalPartidas());
        System.out.println("Vitórias: " 
                + estatisticas.getPartidasGanhas());
        System.out.println("Sequência atual: " 
                + estatisticas.getSequenciaAtual());

        System.out.println("Criando interface gráfica...");

        // cria janela principal do jogo
        JanelaPrincipal janela =
                new JanelaPrincipal(controlador);

        janela.setVisible(true);

        System.out.println("Jogo iniciado com sucesso!");
        System.out.println("=================================");

        // mostra mensagem de boas vindas personalizada
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

        // se usuario quiser iniciar nova partida
        if (opcao == JOptionPane.YES_OPTION) {
            controlador.iniciarNovaPartida();
        }
    }

    // metodo que exibe dialogo de erro
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
