package view;

import controller.ControladorJogo;
import model.Estatisticas;
import model.Letra;
import model.Palavra;
import model.Partida;
import util.ConstantesVisuais;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Janela principal do jogo TERMO
 * VERSÃO PREMIUM - Design moderno e profissional
 */
public class JanelaPrincipal extends JFrame
        implements ControladorJogo.JogoListener,
                   TecladoVirtual.TecladoListener,
                   KeyListener {

    private ControladorJogo controlador;
    private PainelGrade painelGrade;
    private TecladoVirtual teclado;
    private PainelEstatisticas painelEstatisticas;
    private JButton btnNovaPartida;
    private JButton btnSair;
    private JLabel lblMensagem;
    private boolean bloqueado;

    public JanelaPrincipal(ControladorJogo controlador) {
        this.controlador = controlador;
        bloqueado = true;
        controlador.setListener(this);
        
        configurarJanela();
        criarComponentes();
        configurarEventos();
        
        painelEstatisticas.atualizar(controlador.getEstatisticas());
    }

    private void configurarJanela() {
        setTitle(ConstantesVisuais.TITULO_JOGO);
        setSize(ConstantesVisuais.LARGURA_JANELA, ConstantesVisuais.ALTURA_JANELA);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        getContentPane().setBackground(ConstantesVisuais.COR_FUNDO);
        
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
            "Deseja reiniciar a partida atual?\nO progresso sera perdido.",
            "Reiniciar Partida",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (opcao == JOptionPane.YES_OPTION) {
            painelGrade.limpar();
            teclado.resetar();
            lblMensagem.setText("");
            bloqueado = false;
            painelEstatisticas.resetar();
            requestFocusInWindow();
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
        // Painel principal moderno
        JPanel painelPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Gradiente sutil de fundo
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(255, 255, 255),
                    0, getHeight(), new Color(248, 250, 252)
                );
                g2.setPaint(gradient);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // TÍTULO moderno
        JLabel lblTitulo = new JLabel(ConstantesVisuais.TITULO_JOGO) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                
                // Sombra do texto
                g2.setColor(new Color(0, 0, 0, 30));
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2.drawString(getText(), x + 2, y + 2);
                
                // Texto principal
                g2.setColor(getForeground());
                g2.drawString(getText(), x, y);
            }
        };
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        lblTitulo.setForeground(new Color(30, 41, 59));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));

        // ESTATÍSTICAS
        painelEstatisticas = new PainelEstatisticas();
        painelEstatisticas.setAlignmentX(Component.CENTER_ALIGNMENT);

        // MENSAGEM moderna
        lblMensagem = new JLabel("") {
            @Override
            protected void paintComponent(Graphics g) {
                if (getText() != null && !getText().trim().isEmpty()) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    // Fundo arredondado
                    g2.setColor(new Color(241, 245, 249));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                }
                super.paintComponent(g);
            }
        };
        lblMensagem.setFont(new Font("Arial", Font.BOLD, 13));
        lblMensagem.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMensagem.setPreferredSize(new Dimension(450, 35));
        lblMensagem.setMaximumSize(new Dimension(450, 35));
        lblMensagem.setHorizontalAlignment(SwingConstants.CENTER);
        lblMensagem.setOpaque(false);

        // GRADE
        painelGrade = new PainelGrade();
        JPanel containerGrade = new JPanel();
        containerGrade.setOpaque(false);
        containerGrade.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        containerGrade.add(painelGrade);
        containerGrade.setAlignmentX(Component.CENTER_ALIGNMENT);

        // TECLADO
        teclado = new TecladoVirtual();
        teclado.setListener(this);
        teclado.setAlignmentX(Component.CENTER_ALIGNMENT);

        // BOTÕES modernos
        btnNovaPartida = criarBotaoModerno("Nova Partida", new Color(16, 185, 129), new Color(5, 150, 105));
        btnSair = criarBotaoModerno("Sair", new Color(239, 68, 68), new Color(220, 38, 38));

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        painelBotoes.setOpaque(false);
        painelBotoes.add(btnNovaPartida);
        painelBotoes.add(btnSair);
        painelBotoes.setAlignmentX(Component.CENTER_ALIGNMENT);

        // MONTAGEM
        painelPrincipal.add(lblTitulo);
        painelPrincipal.add(Box.createVerticalStrut(5));
        painelPrincipal.add(painelEstatisticas);
        painelPrincipal.add(Box.createVerticalStrut(10));
        painelPrincipal.add(lblMensagem);
        painelPrincipal.add(Box.createVerticalStrut(10));
        painelPrincipal.add(containerGrade);
        painelPrincipal.add(Box.createVerticalStrut(12));
        painelPrincipal.add(teclado);
        painelPrincipal.add(Box.createVerticalStrut(12));
        painelPrincipal.add(painelBotoes);
        painelPrincipal.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(painelPrincipal);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        add(scrollPane);

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    private JButton criarBotaoModerno(String texto, Color cor, Color corHover) {
        JButton btn = new JButton(texto) {
            private boolean hover = false;
            
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Sombra
                g2.setColor(new Color(0, 0, 0, 20));
                g2.fillRoundRect(0, 3, getWidth(), getHeight(), 10, 10);
                
                // Fundo com gradiente
                Color c1 = hover ? corHover : cor;
                Color c2 = c1.darker();
                GradientPaint gradient = new GradientPaint(0, 0, c1, 0, getHeight(), c2);
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, getWidth(), getHeight() - 3, 10, 10);
                
                g2.dispose();
                super.paintComponent(g);
            }
        };
        
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setPreferredSize(new Dimension(130, 40));
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
        btnNovaPartida.addActionListener(e -> iniciarNovaPartida());
        btnSair.addActionListener(e -> confirmarSaida());
    }

    private void iniciarNovaPartida() {
        if (controlador.temPartidaEmAndamento()) {
            int opcao = JOptionPane.showConfirmDialog(
                this,
                "Ha uma partida em andamento. Deseja iniciar uma nova?",
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

        if (palavra.length() < Palavra.TAMANHO_PALAVRA) {
            mostrarMensagem(ConstantesVisuais.MSG_PALAVRA_INCOMPLETA, ConstantesVisuais.COR_AVISO);
            painelGrade.animarShake();
            return;
        }

        Palavra resultado = controlador.processarTentativa(palavra);

        if (resultado == null) {
            painelGrade.animarShake();
        } else {
            int tentativasUsadas = painelGrade.getLinhaAtual() + 1;
            int tentativasRestantes = Partida.MAX_TENTATIVAS - tentativasUsadas;
            painelEstatisticas.atualizarTentativas(tentativasRestantes);
        }
    }

    private void confirmarSaida() {
        int opcao = JOptionPane.showConfirmDialog(
            this,
            "Deseja realmente sair?\n\n" + controlador.getEstatisticas(),
            "Sair",
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

        Timer timer = new Timer(3000, e -> {
            lblMensagem.setText("");
            lblMensagem.setForeground(ConstantesVisuais.COR_TEXTO_ESCURO);
        });
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

        painelEstatisticas.atualizarTentativas(tentativasRestantes);

        if (controlador.temPartidaEmAndamento()) {
            painelGrade.avancarLinha();
        }

        requestFocus();
    }

    @Override
    public void onVitoria(int tentativasUsadas) {
        bloqueado = true;
        mostrarMensagem(ConstantesVisuais.MSG_VITORIA, ConstantesVisuais.COR_SUCESSO);

        SwingUtilities.invokeLater(() -> painelGrade.animarVitoria());

        Timer timer = new Timer(800, e -> {
            JOptionPane.showMessageDialog(
                this, 
                "Parabens! Voce venceu em " + tentativasUsadas + " tentativa(s)!",
                "Vitoria!",
                JOptionPane.INFORMATION_MESSAGE
            );
        });
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    public void onDerrota(String palavraSecreta) {
        bloqueado = true;
        mostrarMensagem(ConstantesVisuais.MSG_DERROTA + palavraSecreta, ConstantesVisuais.COR_ERRO);

        Timer timer = new Timer(500, e -> {
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
        mostrarMensagem(mensagem, ConstantesVisuais.COR_ERRO);
    }

    @Override
    public void onNovaPartida() {
        bloqueado = false;
        painelEstatisticas.resetar();
    }

    @Override
    public void onEstatisticasAtualizadas(Estatisticas estatisticas) {
        painelEstatisticas.atualizar(estatisticas);
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