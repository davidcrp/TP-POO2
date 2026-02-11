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
 * Conecta interface gráfica com o controlador
 * VERSÃO MELHORADA - Layout otimizado e responsivo
 */
public class JanelaPrincipal extends JFrame
        implements ControladorJogo.JogoListener,
                   TecladoVirtual.TecladoListener,
                   KeyListener {

    // controlador principal do jogo
    private ControladorJogo controlador;

    // componentes principais da interface
    private PainelGrade painelGrade;
    private TecladoVirtual teclado;
    private PainelEstatisticas painelEstatisticas;

    // botoes de controle
    private JButton btnNovaPartida;
    private JButton btnSair;

    // label de mensagens temporarias
    private JLabel lblMensagem;

    // indica se a partida esta bloqueada
    private boolean bloqueado;

    // cria a janela principal
    public JanelaPrincipal(ControladorJogo controlador) {
        this.controlador = controlador;
        bloqueado = true;

        controlador.setListener(this);

        configurarJanela();
        criarComponentes();
        configurarEventos();

        painelEstatisticas.atualizar(controlador.getEstatisticas());
    }

    // configuracoes basicas da janela - MELHORADO
    private void configurarJanela() {
        setTitle(ConstantesVisuais.TITULO_JOGO);
        setSize(ConstantesVisuais.LARGURA_JANELA, ConstantesVisuais.ALTURA_JANELA);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Define cor de fundo padrão
        getContentPane().setBackground(ConstantesVisuais.COR_FUNDO);
        
        // Adiciona barra de menu
        criarMenu();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmarSaida();
            }
        });
    }
    
    /**
     * Cria a barra de menu
     */
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
    
    /**
     * Reinicia a partida atual
     */
    private void reiniciarPartida() {
        int opcao = JOptionPane.showConfirmDialog(
            this,
            "Deseja reiniciar a partida atual?\nO progresso será perdido.",
            "Reiniciar Partida",
            JOptionPane.YES_NO_OPTION
        );
        
        if (opcao == JOptionPane.YES_OPTION) {
            painelGrade.limpar();
            teclado.resetar();
            lblMensagem.setText(" ");
            bloqueado = true;
            painelEstatisticas.resetar();
        }
    }
    
    /**
     * Mostra diálogo de estatísticas detalhadas
     */
    private void mostrarEstatisticas() {
        DialogoEstatisticas dialogo = new DialogoEstatisticas(
            this, 
            controlador.getEstatisticas()
        );
        dialogo.setVisible(true);
    }
    
    /**
     * Mostra diálogo de ajuda
     */
    private void mostrarAjuda() {
        DialogoAjuda dialogo = new DialogoAjuda(this);
        dialogo.setVisible(true);
    }
    
    /**
     * Mostra diálogo sobre o jogo
     */
    private void mostrarSobre() {
        DialogoSobre dialogo = new DialogoSobre(this);
        dialogo.setVisible(true);
    }

    // cria todos os componentes da interface - MELHORADO
    private void criarComponentes() {
        // Painel principal com BoxLayout vertical
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        painelPrincipal.setBackground(ConstantesVisuais.COR_FUNDO);

        // ===== TOPO: TÍTULO =====
        JLabel lblTitulo = new JLabel(ConstantesVisuais.TITULO_JOGO);
        lblTitulo.setFont(ConstantesVisuais.FONTE_TITULO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(2, 0, 6, 0));

        // ===== ESTATÍSTICAS =====
        painelEstatisticas = new PainelEstatisticas();
        painelEstatisticas.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== MENSAGEM =====
        lblMensagem = new JLabel(" ");
        lblMensagem.setFont(ConstantesVisuais.FONTE_NORMAL);
        lblMensagem.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMensagem.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        lblMensagem.setPreferredSize(new Dimension(450, 25));
        lblMensagem.setHorizontalAlignment(SwingConstants.CENTER);

        // ===== GRADE =====
        painelGrade = new PainelGrade();
        
        // Container para centralizar a grade
        JPanel containerGrade = new JPanel();
        containerGrade.setBackground(ConstantesVisuais.COR_FUNDO);
        containerGrade.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        containerGrade.add(painelGrade);
        containerGrade.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== TECLADO =====
        teclado = new TecladoVirtual();
        teclado.setListener(this);
        teclado.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== BOTÕES =====
        btnNovaPartida = criarBotao("Nova Partida", ConstantesVisuais.COR_SUCESSO);
        btnSair = criarBotao("Sair", ConstantesVisuais.COR_ERRO);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        painelBotoes.setBackground(ConstantesVisuais.COR_FUNDO);
        painelBotoes.add(btnNovaPartida);
        painelBotoes.add(btnSair);
        painelBotoes.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== MONTAGEM FINAL =====
        painelPrincipal.add(lblTitulo);
        painelPrincipal.add(painelEstatisticas);
        painelPrincipal.add(Box.createVerticalStrut(2));
        painelPrincipal.add(lblMensagem);
        painelPrincipal.add(Box.createVerticalStrut(2));
        painelPrincipal.add(containerGrade);
        painelPrincipal.add(Box.createVerticalStrut(6));
        painelPrincipal.add(teclado);
        painelPrincipal.add(Box.createVerticalStrut(8));
        painelPrincipal.add(painelBotoes);
        painelPrincipal.add(Box.createVerticalStrut(5)); // Margem inferior

        // Adiciona com scroll para garantir que tudo apareça
        JScrollPane scrollPane = new JScrollPane(painelPrincipal);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        add(scrollPane);

        addKeyListener(this);
        setFocusable(true);
    }

    /**
     * Cria um botão estilizado
     * @param texto Texto do botão
     * @param cor Cor de fundo
     * @return JButton configurado
     */
    private JButton criarBotao(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setFont(ConstantesVisuais.FONTE_NORMAL);
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setPreferredSize(new Dimension(140, 35));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Efeito hover
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(cor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(cor);
            }
        });
        
        return btn;
    }

    // ==================== EVENTOS ====================

    /**
     * Configura eventos dos botões principais
     */
    private void configurarEventos() {
        btnNovaPartida.addActionListener(e -> iniciarNovaPartida());
        btnSair.addActionListener(e -> confirmarSaida());
    }

    /**
     * Inicia uma nova partida
     */
    private void iniciarNovaPartida() {
        if (controlador.temPartidaEmAndamento()) {
            int opcao = JOptionPane.showConfirmDialog(
                    this,
                    "Há uma partida em andamento. Deseja iniciar uma nova?",
                    "Nova Partida",
                    JOptionPane.YES_NO_OPTION
            );

            if (opcao != JOptionPane.YES_OPTION) {
                return;
            }
        }

        if (controlador.iniciarNovaPartida()) {
            painelGrade.limpar();
            teclado.resetar();
            lblMensagem.setText(" ");
            bloqueado = false;
            requestFocus();
        }
    }

    /**
     * Processa a tentativa atual
     */
    private void processarTentativa() {
        if (bloqueado) {
            return;
        }

        String palavra = painelGrade.getPalavraAtual();

        if (palavra.length() < Palavra.TAMANHO_PALAVRA) {
            mostrarMensagem(
                ConstantesVisuais.MSG_PALAVRA_INCOMPLETA, 
                ConstantesVisuais.COR_AVISO
            );
            painelGrade.animarShake();
            return;
        }

        Palavra resultado = controlador.processarTentativa(palavra);

        if (resultado == null) {
            painelGrade.animarShake();
        } else {
            // Atualiza tentativas restantes
            int tentativasUsadas = painelGrade.getLinhaAtual() + 1;
            int tentativasRestantes = Partida.MAX_TENTATIVAS - tentativasUsadas;
            painelEstatisticas.atualizarTentativas(tentativasRestantes);
        }
    }

    /**
     * Confirma a saída do jogo
     */
    private void confirmarSaida() {
        int opcao = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente sair?\n\n" + controlador.getEstatisticas(),
                "Sair",
                JOptionPane.YES_NO_OPTION
        );

        if (opcao == JOptionPane.YES_OPTION) {
            controlador.finalizarJogo();
            dispose();
            System.exit(0);
        }
    }

    /**
     * Mostra mensagem temporária na tela
     * @param texto Texto da mensagem
     * @param cor Cor do texto
     */
    private void mostrarMensagem(String texto, Color cor) {
        lblMensagem.setText(texto);
        lblMensagem.setForeground(cor);

        Timer timer = new Timer(2500, e -> {
            lblMensagem.setText(" ");
            lblMensagem.setForeground(ConstantesVisuais.COR_TEXTO_ESCURO);
        });
        timer.setRepeats(false);
        timer.start();
    }

    // ==================== CALLBACKS DO CONTROLADOR ====================

    @Override
    public void onTentativaProcessada(Palavra tentativa, int tentativasRestantes) {
        painelGrade.atualizarLinhaAtual(tentativa);

        // Coleta o melhor estado de cada letra (prioridade: CORRETA > POSICAO_ERRADA > AUSENTE)
        Map<Character, Letra.Estado> melhoresEstados = new HashMap<>();
        
        for (Letra letra : tentativa.getLetras()) {
            char c = Character.toUpperCase(letra.getCaractere());
            Letra.Estado estadoAtual = letra.getEstado();
            Letra.Estado estadoAnterior = melhoresEstados.get(c);
            
            // Se não tinha estado ou o novo é melhor, atualiza
            if (estadoAnterior == null) {
                melhoresEstados.put(c, estadoAtual);
            } else if (estadoAtual == Letra.Estado.CORRETA) {
                // CORRETA sempre prevalece
                melhoresEstados.put(c, estadoAtual);
            } else if (estadoAtual == Letra.Estado.POSICAO_ERRADA && 
                       estadoAnterior != Letra.Estado.CORRETA) {
                // POSICAO_ERRADA só prevalece sobre AUSENTE
                melhoresEstados.put(c, estadoAtual);
            }
        }
        
        // Agora atualiza o teclado com os melhores estados
        for (Map.Entry<Character, Letra.Estado> entry : melhoresEstados.entrySet()) {
            teclado.atualizarCorTecla(entry.getKey(), entry.getValue());
        }

        // Atualiza estatísticas
        painelEstatisticas.atualizarTentativas(tentativasRestantes);

        if (controlador.temPartidaEmAndamento()) {
            painelGrade.avancarLinha();
        }

        requestFocus();
    }

    @Override
    public void onVitoria(int tentativasUsadas) {
        bloqueado = true;
        
        mostrarMensagem(
            ConstantesVisuais.MSG_VITORIA, 
            ConstantesVisuais.COR_SUCESSO
        );

        // Animação de vitória
        SwingUtilities.invokeLater(() -> {
            painelGrade.animarVitoria();
        });

        // Mensagem com delay
        Timer timer = new Timer(800, e -> {
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
        
        mostrarMensagem(
            ConstantesVisuais.MSG_DERROTA + palavraSecreta, 
            ConstantesVisuais.COR_ERRO
        );

        // Mensagem com delay
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

    // ==================== TECLADO VIRTUAL ====================

    @Override
    public void onLetraPressionada(char letra) {
        if (!bloqueado) {
            painelGrade.adicionarLetra(letra);
        }
        requestFocus();
    }

    @Override
    public void onEnterPressionado() {
        processarTentativa();
        requestFocus();
    }

    @Override
    public void onBackspacePressionado() {
        if (!bloqueado) {
            painelGrade.removerLetra();
        }
        requestFocus();
    }

    // ==================== TECLADO FÍSICO ====================

    @Override
    public void keyTyped(KeyEvent e) {
        // Não usado
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (bloqueado) {
            return;
        }

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
    public void keyReleased(KeyEvent e) {
        // Não usado
    }
}