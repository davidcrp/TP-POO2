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

public class JanelaPrincipal extends JFrame
        implements ControladorJogo.JogoListener,
                   TecladoVirtual.TecladoListener,
                   KeyListener {

    private ControladorJogo controlador;
    private PainelGrade painelGrade;
    private TecladoVirtual teclado;
    private JLabel lblMensagem;
    private JButton btnInfo;
    private JButton btnEstatisticas;
    private JButton btnReiniciar;
    private JButton btnSair;
    private boolean bloqueado;

    public JanelaPrincipal(ControladorJogo controlador) {
        this.controlador = controlador;
        bloqueado = true;
        controlador.setListener(this);
        
        configurarJanela();
        criarComponentes();
        configurarEventos();
    }

    private void configurarJanela() {
        setTitle("TERMO");
        setSize(540, 800);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        getContentPane().setBackground(new Color(18, 18, 19));
        
        criarMenu();
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmarSaida();
            }
        });
    }
    
    private void criarMenu() {
        MenuPrincipal menu = new MenuPrincipal(controlador, this);
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
    
    private void reiniciarPartida() {
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
    
    private void mostrarEstatisticas() {
        DialogoEstatisticas dialogo = new DialogoEstatisticas(this, controlador.getEstatisticas());
        dialogo.setVisible(true);
        requestFocusInWindow();
    }
    
    private void mostrarAjuda() {
        DialogoAjuda dialogo = new DialogoAjuda(this);
        dialogo.setVisible(true);
        requestFocusInWindow();
    }
    
    private void mostrarSobre() {
        DialogoSobre dialogo = new DialogoSobre(this);
        dialogo.setVisible(true);
        requestFocusInWindow();
    }

    private void criarComponentes() {
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BorderLayout());
        painelPrincipal.setBackground(new Color(18, 18, 19));

        JPanel header = criarHeader();
        
        JPanel areaCentral = new JPanel();
        areaCentral.setLayout(new BoxLayout(areaCentral, BoxLayout.Y_AXIS));
        areaCentral.setBackground(new Color(18, 18, 19));
        areaCentral.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        lblMensagem = new JLabel("", SwingConstants.CENTER);
        lblMensagem.setFont(new Font("Arial", Font.BOLD, 14));
        lblMensagem.setForeground(Color.WHITE);
        lblMensagem.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMensagem.setPreferredSize(new Dimension(500, 25));
        lblMensagem.setMaximumSize(new Dimension(500, 25));

        painelGrade = new PainelGrade();
        painelGrade.setAlignmentX(Component.CENTER_ALIGNMENT);

        teclado = new TecladoVirtual();
        teclado.setListener(this);
        teclado.setAlignmentX(Component.CENTER_ALIGNMENT);

        areaCentral.add(Box.createVerticalStrut(5));
        areaCentral.add(lblMensagem);
        areaCentral.add(Box.createVerticalStrut(10));
        areaCentral.add(painelGrade);
        areaCentral.add(Box.createVerticalStrut(15));
        areaCentral.add(teclado);
        areaCentral.add(Box.createVerticalStrut(10));

        painelPrincipal.add(header, BorderLayout.NORTH);
        painelPrincipal.add(areaCentral, BorderLayout.CENTER);

        add(painelPrincipal);
    }

    private JPanel criarHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(18, 18, 19));
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(58, 58, 60)),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));

        // Botões à esquerda
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

        // Botão de estatísticas à direita
        JPanel painelDireita = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        painelDireita.setOpaque(false);
        
        btnEstatisticas = criarBotaoIcone("i");  // i de informação
        painelDireita.add(btnEstatisticas);

        header.add(painelEsquerda, BorderLayout.WEST);
        header.add(lblTitulo, BorderLayout.CENTER);
        header.add(painelDireita, BorderLayout.EAST);

        return header;
    }

    private JButton criarBotaoIcone(String icone) {
        JButton btn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
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

                // Desenha o ícone/texto
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 20));
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(icone)) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2.drawString(icone, x, y);

                g2.dispose();
            }
        };

        btn.setPreferredSize(new Dimension(40, 40));
        btn.setMinimumSize(new Dimension(40, 40));
        btn.setMaximumSize(new Dimension(40, 40));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

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

    private void configurarEventos() {
        btnInfo.addActionListener(e -> mostrarAjuda());
        btnEstatisticas.addActionListener(e -> mostrarEstatisticas());
        btnReiniciar.addActionListener(e -> reiniciarPartida());
        btnSair.addActionListener(e -> confirmarSaida());
        
        addKeyListener(this);
        setFocusable(true);
    }

    private void iniciarNovaPartida() {
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

        if (controlador.iniciarNovaPartida()) {
            painelGrade.limpar();
            teclado.resetar();
            lblMensagem.setText("");
            bloqueado = false;
            requestFocus();
        }
    }

    private void processarTentativa() {
        if (bloqueado) return;

        String palavra = painelGrade.getPalavraAtual();

        if (palavra.length() < 5) {
            mostrarMensagem("Palavra incompleta", new Color(255, 193, 7));
            painelGrade.animarShake();
            return;
        }

        Palavra resultado = controlador.processarTentativa(palavra);

        if (resultado == null) {
            painelGrade.animarShake();
        }
    }

    private void confirmarSaida() {
        // Primeiro mostra estatísticas finais (REQUISITO DO PDF)
        Estatisticas stats = controlador.getEstatisticas();
        
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
        
        int opcao = JOptionPane.showConfirmDialog(
            this,
            mensagem,
            "Sair - Estatísticas Finais",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (opcao == JOptionPane.YES_OPTION) {
            controlador.finalizarJogo();
            dispose();
            System.exit(0);
        }
    }

    private void mostrarMensagem(String texto, Color cor) {
        lblMensagem.setText(texto);
        lblMensagem.setForeground(cor);

        Timer timer = new Timer(2500, e -> lblMensagem.setText(""));
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    public void onTentativaProcessada(Palavra tentativa, int tentativasRestantes) {
        painelGrade.atualizarLinhaAtual(tentativa);

        Map<Character, Letra.Estado> melhoresEstados = new HashMap<>();
        
        for (Letra letra : tentativa.getLetras()) {
            char c = Character.toUpperCase(letra.getCaractere());
            Letra.Estado estadoAtual = letra.getEstado();
            Letra.Estado estadoAnterior = melhoresEstados.get(c);
            
            if (estadoAnterior == null) {
                melhoresEstados.put(c, estadoAtual);
            } else if (estadoAtual == Letra.Estado.CORRETA) {
                melhoresEstados.put(c, estadoAtual);
            } else if (estadoAtual == Letra.Estado.POSICAO_ERRADA && 
                       estadoAnterior != Letra.Estado.CORRETA) {
                melhoresEstados.put(c, estadoAtual);
            }
        }
        
        for (Map.Entry<Character, Letra.Estado> entry : melhoresEstados.entrySet()) {
            teclado.atualizarCorTecla(entry.getKey(), entry.getValue());
        }

        if (controlador.temPartidaEmAndamento()) {
            painelGrade.avancarLinha();
        }

        requestFocus();
    }

    @Override
    public void onVitoria(int tentativasUsadas) {
        bloqueado = true;
        mostrarMensagem(" Parabéns! Você venceu!", new Color(106, 170, 100));

        SwingUtilities.invokeLater(() -> painelGrade.animarVitoria());

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

    @Override
    public void onDerrota(String palavraSecreta) {
        bloqueado = true;
        mostrarMensagem("A palavra era: " + palavraSecreta, new Color(220, 38, 38));

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

    @Override
    public void onErro(String mensagem) {
        mostrarMensagem(mensagem, new Color(220, 38, 38));
    }

    @Override
    public void onNovaPartida() {
        bloqueado = false;
    }

    @Override
    public void onEstatisticasAtualizadas(Estatisticas estatisticas) {
    }

    @Override
    public void onLetraPressionada(char letra) {
        if (!bloqueado) painelGrade.adicionarLetra(letra);
        requestFocus();
    }

    @Override
    public void onEnterPressionado() {
        processarTentativa();
        requestFocus();
    }

    @Override
    public void onBackspacePressionado() {
        if (!bloqueado) painelGrade.removerLetra();
        requestFocus();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (bloqueado) return;

        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_ENTER) {
            processarTentativa();
        } else if (keyCode == KeyEvent.VK_BACK_SPACE) {
            painelGrade.removerLetra();
        } else if (keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_Z) {
            char letra = (char) ('A' + (keyCode - KeyEvent.VK_A));
            painelGrade.adicionarLetra(letra);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}