package view;

import controller.ControladorJogo;
import model.Estatisticas;
import model.Letra;
import model.Palavra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

/*
Matheus Cardoso de Araújo 24.1.4033
Pedro Souza Goularte 24.2.4084
Pedro Henrique Boseja Carolino Barbosa 24.2.4092
David Cristian de Paula Rodrigues 24.2.4094
*/

// Classe principal da janela do jogo, estende JFrame e implementa listeners
public class JanelaPrincipal extends JFrame
        implements ControladorJogo.JogoListener,
                   TecladoVirtual.TecladoListener,
                   KeyListener {

    // Controlador do jogo que gerencia a lógica
    private ControladorJogo controlador;
    // Painel que exibe a grade de letras
    private PainelGrade painelGrade;
    // Teclado virtual na tela
    private TecladoVirtual teclado;
    // Label para exibir mensagens ao usuário
    private JLabel lblMensagem;
    // Botão de informações/ajuda
    private JButton btnInfo;
    // Botão de estatísticas
    private JButton btnEstatisticas;
    // Botão para reiniciar partida
    private JButton btnReiniciar;
    // Botão para sair
    private JButton btnSair;
    // Flag que indica se a entrada está bloqueada
    private boolean bloqueado;

    // Construtor da janela principal
    public JanelaPrincipal(ControladorJogo controlador) {
        this.controlador = controlador;
        bloqueado = true;
        // Registra esta janela como listener do controlador
        controlador.setListener(this);
        
        configurarJanela();
        criarComponentes();
        configurarEventos();
    }

    // Configura as propriedades básicas da janela
    private void configurarJanela() {
        setTitle("TERMO");
        setSize(540, 800);
        setLocationRelativeTo(null);
        setResizable(false);
        // Não fecha direto ao clicar no X
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        getContentPane().setBackground(new Color(18, 18, 19));
        
        criarMenu();
        
        // Adiciona listener para interceptar o fechamento da janela
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmarSaida();
            }
        });
    }
    
    // Cria a barra de menu superior
    private void criarMenu() {
        MenuPrincipal menu = new MenuPrincipal(controlador, this);
        // Define as ações de cada item do menu
        menu.setListener(new MenuPrincipal.MenuListener() {
            @Override
            public void onNovoJogo() {
                iniciarNovaPartida();
            }
            
            @Override
            public void onReiniciar() {
                reiniciarPartida();
            }
            
            @Override
            public void onSair() {
                confirmarSaida();
            }
            
            @Override
            public void onMostrarEstatisticas() {
                mostrarEstatisticas();
            }
            
            @Override
            public void onMostrarAjuda() {
                mostrarAjuda();
            }
            
            @Override
            public void onMostrarSobre() {
                mostrarSobre();
            }
        });
        
        setJMenuBar(menu);
    }
    
    // Reinicia a partida atual após confirmação
    private void reiniciarPartida() {
        // Pede confirmação ao usuário
        int opcao = JOptionPane.showConfirmDialog(
            this,
            "Deseja reiniciar a partida atual?\nO progresso será perdido.",
            "Reiniciar Partida",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (opcao == JOptionPane.YES_OPTION) {
            // Limpa toda a interface
            painelGrade.limpar();
            teclado.resetar();
            lblMensagem.setText("");
            
            // Inicia nova partida
            boolean sucesso = controlador.iniciarNovaPartida();
            
            if (sucesso) {
                bloqueado = false;
                requestFocusInWindow();
                mostrarMensagem("Partida reiniciada!", new Color(106, 170, 100));
            } else {
                mostrarMensagem("Erro ao reiniciar partida", new Color(220, 38, 38));
                bloqueado = true;
            }
        }
    }
    
    // Exibe o diálogo de estatísticas
    private void mostrarEstatisticas() {
        DialogoEstatisticas dialogo = new DialogoEstatisticas(this, controlador.getEstatisticas());
        dialogo.setVisible(true);
        requestFocusInWindow();
    }
    
    // Exibe o diálogo de ajuda
    private void mostrarAjuda() {
        DialogoAjuda dialogo = new DialogoAjuda(this);
        dialogo.setVisible(true);
        requestFocusInWindow();
    }
    
    // Exibe o diálogo sobre o jogo
    private void mostrarSobre() {
        DialogoSobre dialogo = new DialogoSobre(this);
        dialogo.setVisible(true);
        requestFocusInWindow();
    }

    // Cria todos os componentes visuais da janela
    private void criarComponentes() {
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BorderLayout());
        painelPrincipal.setBackground(new Color(18, 18, 19));

        // Cria o cabeçalho
        JPanel header = criarHeader();
        
        // Painel central que conterá grade e teclado
        JPanel areaCentral = new JPanel();
        areaCentral.setLayout(new BoxLayout(areaCentral, BoxLayout.Y_AXIS));
        areaCentral.setBackground(new Color(18, 18, 19));
        areaCentral.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        // Label para mensagens temporárias
        lblMensagem = new JLabel("", SwingConstants.CENTER);
        lblMensagem.setFont(new Font("Arial", Font.BOLD, 14));
        lblMensagem.setForeground(Color.WHITE);
        lblMensagem.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMensagem.setPreferredSize(new Dimension(500, 25));
        lblMensagem.setMaximumSize(new Dimension(500, 25));

        // Cria a grade de letras
        painelGrade = new PainelGrade();
        painelGrade.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Cria o teclado virtual
        teclado = new TecladoVirtual();
        teclado.setListener(this);
        teclado.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Adiciona componentes na área central com espaçamentos
        areaCentral.add(Box.createVerticalStrut(5));
        areaCentral.add(lblMensagem);
        areaCentral.add(Box.createVerticalStrut(10));
        areaCentral.add(painelGrade);
        areaCentral.add(Box.createVerticalStrut(15));
        areaCentral.add(teclado);
        areaCentral.add(Box.createVerticalStrut(10));

        // Monta o painel principal
        painelPrincipal.add(header, BorderLayout.NORTH);
        painelPrincipal.add(areaCentral, BorderLayout.CENTER);

        add(painelPrincipal);
    }

    // Cria o cabeçalho com título e botões
    private JPanel criarHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(18, 18, 19));
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(58, 58, 60)),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));

        // Botões a esquerda
        JPanel painelEsquerda = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        painelEsquerda.setOpaque(false);
        
        btnInfo = criarBotaoIcone("?");
        btnReiniciar = criarBotaoIcone("R");  // R de Reiniciar
        btnSair = criarBotaoIcone("X");       // X para sair
        
        painelEsquerda.add(btnInfo);
        painelEsquerda.add(btnReiniciar);
        painelEsquerda.add(btnSair);

        // Título
        JLabel lblTitulo = new JLabel("TERMO");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 32));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        // Botão de estatísticas a direita
        JPanel painelDireita = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        painelDireita.setOpaque(false);
        
        btnEstatisticas = criarBotaoIcone("i");  // i de informação
        painelDireita.add(btnEstatisticas);

        header.add(painelEsquerda, BorderLayout.WEST);
        header.add(lblTitulo, BorderLayout.CENTER);
        header.add(painelDireita, BorderLayout.EAST);

        return header;
    }

    // Cria um botão circular com ícone
    private JButton criarBotaoIcone(String icone) {
        JButton btn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                // Ativa suavização de bordas
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                // Desenha fundo hover
                boolean hover = Boolean.TRUE.equals(getClientProperty("hover"));
                if (hover) {
                    g2.setColor(new Color(58, 58, 60));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                }

                // Desenha o ícone
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 20));
                FontMetrics fm = g2.getFontMetrics();
                // Centraliza o texto
                int x = (getWidth() - fm.stringWidth(icone)) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2.drawString(icone, x, y);

                g2.dispose();
            }
        };

        // Define tamanho do botão
        btn.setPreferredSize(new Dimension(40, 40));
        btn.setMinimumSize(new Dimension(40, 40));
        btn.setMaximumSize(new Dimension(40, 40));
        // Remove bordas e efeitos padrão
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Adiciona efeito hover
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.putClientProperty("hover", true);
                btn.repaint();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.putClientProperty("hover", false);
                btn.repaint();
            }
        });

        return btn;
    }

    // Configura os eventos dos botões e teclado
    private void configurarEventos() {
        btnInfo.addActionListener(e -> mostrarAjuda());
        btnEstatisticas.addActionListener(e -> mostrarEstatisticas());
        btnReiniciar.addActionListener(e -> reiniciarPartida());
        btnSair.addActionListener(e -> confirmarSaida());
        
        // Adiciona listener de teclado físico
        addKeyListener(this);
        setFocusable(true);
    }

    // Inicia uma nova partida com confirmação se houver jogo em andamento
    private void iniciarNovaPartida() {
        // Verifica se já há partida em andamento
        if (controlador.temPartidaEmAndamento()) {
            int opcao = JOptionPane.showConfirmDialog(
                this,
                "Há uma partida em andamento. Deseja iniciar uma nova?",
                "Nova Partida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if (opcao != JOptionPane.YES_OPTION) return;
        }

        // Inicia nova partida e limpa a interface
        if (controlador.iniciarNovaPartida()) {
            painelGrade.limpar();
            teclado.resetar();
            lblMensagem.setText("");
            bloqueado = false;
            requestFocus();
        }
    }

    // Processa a tentativa do jogador
    private void processarTentativa() {
        // Não processa se estiver bloqueado
        if (bloqueado) return;

        // Obtém a palavra digitada
        String palavra = painelGrade.getPalavraAtual();

        // Verifica se a palavra está completa
        if (palavra.length() < 5) {
            mostrarMensagem("Palavra incompleta", new Color(255, 193, 7));
            painelGrade.animarShake();
            return;
        }

        // Envia para o controlador processar
        Palavra resultado = controlador.processarTentativa(palavra);

        // Se retornou null, palavra inválida
        if (resultado == null) {
            painelGrade.animarShake();
        }
    }

    // Confirma saída e mostra estatísticas finais
    private void confirmarSaida() {
        // Primeiro mostra estatísticas finais (REQUISITO DO PDF)
        Estatisticas stats = controlador.getEstatisticas();
        
        // Formata mensagem com estatísticas
        String mensagem = String.format(
            "=== ESTATÍSTICAS FINAIS ===\n\n" +
            "Total de Jogos: %d\n" +
            "Vitórias: %d\n" +
            "Derrotas: %d\n" +
            "Taxa de Vitória: %.1f%%\n" +
            "Sequência Atual: %d\n\n" +
            "Deseja realmente sair?",
            stats.getTotalPartidas(),
            stats.getPartidasGanhas(),
            stats.getPartidasPerdidas(),
            stats.getPorcentagemVitorias(),
            stats.getSequenciaAtual()
        );
        
        // Pede confirmação
        int opcao = JOptionPane.showConfirmDialog(
            this,
            mensagem,
            "Sair - Estatísticas Finais",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        // Se confirmou, finaliza o jogo
        if (opcao == JOptionPane.YES_OPTION) {
            controlador.finalizarJogo();
            dispose();
            System.exit(0);
        }
    }

    // Mostra mensagem temporária na tela
    private void mostrarMensagem(String texto, Color cor) {
        lblMensagem.setText(texto);
        lblMensagem.setForeground(cor);

        // Timer para limpar mensagem após 2.5 segundos
        Timer timer = new Timer(2500, e -> lblMensagem.setText(""));
        timer.setRepeats(false);
        timer.start();
    }

    // Callback quando uma tentativa é processada
    @Override
    public void onTentativaProcessada(Palavra tentativa, int tentativasRestantes) {
        // Atualiza a linha atual com os resultados
        painelGrade.atualizarLinhaAtual(tentativa);

        // Mapa para armazenar o melhor estado de cada letra
        Map<Character, Letra.Estado> melhoresEstados = new HashMap<>();
        
        // Percorre todas as letras da tentativa
        for (Letra letra : tentativa.getLetras()) {
            char c = Character.toUpperCase(letra.getCaractere());
            Letra.Estado estadoAtual = letra.getEstado();
            Letra.Estado estadoAnterior = melhoresEstados.get(c);
            
            // Se é primeira vez que aparece, adiciona
            if (estadoAnterior == null) {
                melhoresEstados.put(c, estadoAtual);
            // Se está correta, sempre atualiza
            } else if (estadoAtual == Letra.Estado.CORRETA) {
                melhoresEstados.put(c, estadoAtual);
            // Se está na posição errada e não era correta antes, atualiza
            } else if (estadoAtual == Letra.Estado.POSICAO_ERRADA && 
                       estadoAnterior != Letra.Estado.CORRETA) {
                melhoresEstados.put(c, estadoAtual);
            }
        }
        
        // Atualiza as cores do teclado com os melhores estados
        for (Map.Entry<Character, Letra.Estado> entry : melhoresEstados.entrySet()) {
            teclado.atualizarCorTecla(entry.getKey(), entry.getValue());
        }

        // Se ainda há partida em andamento, avança para próxima linha
        if (controlador.temPartidaEmAndamento()) {
            painelGrade.avancarLinha();
        }

        requestFocus();
    }

    // Callback quando o jogador vence
    @Override
    public void onVitoria(int tentativasUsadas) {
        bloqueado = true;
        mostrarMensagem("Parabéns! Você venceu!", new Color(106, 170, 100));

        // Executa animação de vitória
        SwingUtilities.invokeLater(() -> painelGrade.animarVitoria());

        // Mostra diálogo após animação
        Timer timer = new Timer(1200, e -> {
            JOptionPane.showMessageDialog(
                this, 
                "Parabéns! Você venceu em " + tentativasUsadas + " tentativa(s)!",
                "Vitória!",
                JOptionPane.INFORMATION_MESSAGE
            );
        });
        timer.setRepeats(false);
        timer.start();
    }

    // Callback quando o jogador perde
    @Override
    public void onDerrota(String palavraSecreta) {
        bloqueado = true;
        mostrarMensagem("A palavra era: " + palavraSecreta, new Color(220, 38, 38));

        // Mostra diálogo de derrota após delay
        Timer timer = new Timer(800, e -> {
            JOptionPane.showMessageDialog(
                this, 
                "Que pena! A palavra era: " + palavraSecreta,
                "Derrota",
                JOptionPane.INFORMATION_MESSAGE
            );
        });
        timer.setRepeats(false);
        timer.start();
    }

    // Callback quando ocorre um erro
    @Override
    public void onErro(String mensagem) {
        mostrarMensagem(mensagem, new Color(220, 38, 38));
    }

    // Callback quando inicia nova partida
    @Override
    public void onNovaPartida() {
        bloqueado = false;
    }

    // Callback quando as estatísticas são atualizadas
    @Override
    public void onEstatisticasAtualizadas(Estatisticas estatisticas) {
    }

    // Callback do teclado virtual - letra pressionada
    @Override
    public void onLetraPressionada(char letra) {
        if (!bloqueado) painelGrade.adicionarLetra(letra);
        requestFocus();
    }

    // Callback do teclado virtual - Enter pressionado
    @Override
    public void onEnterPressionado() {
        processarTentativa();
        requestFocus();
    }

    // Callback do teclado virtual - Backspace pressionado
    @Override
    public void onBackspacePressionado() {
        if (!bloqueado) painelGrade.removerLetra();
        requestFocus();
    }

    // Listener do teclado físico - tecla digitada (não usado)
    @Override
    public void keyTyped(KeyEvent e) {}

    // Listener do teclado físico - tecla pressionada
    @Override
    public void keyPressed(KeyEvent e) {
        // Não processa se estiver bloqueado
        if (bloqueado) return;

        int keyCode = e.getKeyCode();

        // Enter - processar tentativa
        if (keyCode == KeyEvent.VK_ENTER) {
            processarTentativa();
        // Backspace - remover letra
        } else if (keyCode == KeyEvent.VK_BACK_SPACE) {
            painelGrade.removerLetra();
        // Letras A-Z - adicionar letra
        } else if (keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_Z) {
            char letra = (char) ('A' + (keyCode - KeyEvent.VK_A));
            painelGrade.adicionarLetra(letra);
        }
    }

    // Listener do teclado físico - tecla solta (não usado)
    @Override
    public void keyReleased(KeyEvent e) {}
}